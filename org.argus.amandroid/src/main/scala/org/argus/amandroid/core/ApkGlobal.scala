/*
 * Copyright (c) 2017. Fengguo Wei and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Detailed contributors are listed in the CONTRIBUTOR.md
 */
package org.argus.amandroid.core

import org.argus.jawa.core.util._
import java.util.zip.ZipInputStream
import java.io.FileInputStream
import java.util.zip.ZipEntry

import org.argus.amandroid.alir.componentSummary.ComponentSummaryTable
import org.argus.amandroid.core.jawaCodeGenerator.AndroidEntryPointConstants
import org.argus.amandroid.core.model.ApkModel
import org.argus.amandroid.core.parser.{ComponentInfo, ComponentType}
import org.argus.jawa.alir.dataDependenceAnalysis.InterProceduralDataDependenceInfo
import org.argus.jawa.alir.dataFlowAnalysis.InterProceduralDataFlowGraph
import org.argus.jawa.alir.taintAnalysis.TaintAnalysisResult
import org.argus.jawa.core._

object ApkGlobal {
  def isValidApk(nameUri: FileResourceUri): Boolean = {
    class ValidApk extends Exception
    val file = FileUtil.toFile(nameUri)
    file match {
      case dir if dir.isDirectory => false
      case _ => 
        var valid: Boolean = false
        var foundManifest: Boolean = false
        var foundDex: Boolean = false
        var archive: ZipInputStream = null
        try {
          archive = new ZipInputStream(new FileInputStream(file))
          var entry: ZipEntry = null
          entry = archive.getNextEntry
          while (entry != null) {
            val entryName = entry.getName
            if(entryName == "AndroidManifest.xml"){
              foundManifest = true
            } else if(entryName == "classes.dex"){
              foundDex = true
            }
            if(foundManifest && foundDex) {
              valid = true
              throw new ValidApk
            }
            entry = archive.getNextEntry
          }
        } catch {
          case _: Exception =>
        } finally {
          if (archive != null)
            archive.close()
        }
        valid
    }
  }
  def isDecompilable(nameUri: FileResourceUri): Boolean = {
    class ValidJar extends Exception
    val file = FileUtil.toFile(nameUri)
    file match {
      case dir if dir.isDirectory => false
      case _ => 
        var valid: Boolean = false
        var archive: ZipInputStream = null
        try {
          archive = new ZipInputStream(new FileInputStream(file))
          var entry: ZipEntry = null
          entry = archive.getNextEntry
          while (entry != null) {
            val entryName = entry.getName
            if(entryName == "classes.dex"){
              valid = true
              throw new ValidJar
            }
            entry = archive.getNextEntry
          }
        } catch {
          case ie: InterruptedException => throw ie
          case _: Exception =>
        } finally {
          if (archive != null)
            archive.close()
        }
        valid
    }
  }
}

case class InvalidApk(fileUri: FileResourceUri) extends Exception

/**
 * this is an object, which hold information of apps. e.g. components, intent-filter database, etc.
 *
 * @author <a href="mailto:fgwei521@gmail.com">Fengguo Wei</a>
 * @author <a href="mailto:sroy@k-state.edu">Sankardas Roy</a> 
 */
class ApkGlobal(val model: ApkModel, reporter: Reporter) extends Global(model.nameUri, reporter) {
  import ApkGlobal._

  if(model.nameUri.endsWith(".apk")) {
    require(isValidApk(model.nameUri), throw InvalidApk(model.nameUri))
  }
  setJavaLib(AndroidGlobalConfig.settings.lib_files)

  def nameUri: FileResourceUri = model.nameUri

  def load(): Unit = {
    val outApkUri = model.layout.outputSrcUri
    model.layout.sourceFolders foreach { src =>
      val fileUri = FileUtil.appendFileName(outApkUri, src)
      if(FileUtil.toFile(fileUri).exists()) {
        load(fileUri, Constants.JAWA_FILE_EXT, _ => false)
      }
    }
    model.layout.libFolders foreach { lib =>
      val fileUri = FileUtil.appendFileName(outApkUri, lib)
      if(FileUtil.toFile(fileUri).exists()) {
        load(fileUri, Constants.JAWA_FILE_EXT, _ => true)
      }
    }
  }

  def resolveEnvInGlobal(): Unit = {
    model.getEnvMap.foreach {
      case (_, (sig, code)) =>
        resolveMethodCode(sig, code)
    }
  }

  private val idfgResults: MMap[JawaType, InterProceduralDataFlowGraph] = mmapEmpty

  def addIDFG(key: JawaType, idfg: InterProceduralDataFlowGraph): Unit = this.synchronized(this.idfgResults += (key -> idfg))
  def hasIDFG(key: JawaType): Boolean = this.synchronized(this.idfgResults.contains(key))
  def getIDFG(key: JawaType): Option[InterProceduralDataFlowGraph] = this.synchronized(this.idfgResults.get(key))
  def getIDFGs: Map[JawaType, InterProceduralDataFlowGraph] = this.idfgResults.toMap

  private val iddaResults: MMap[JawaType, InterProceduralDataDependenceInfo] = mmapEmpty

  def addIDDG(key: JawaType, iddi: InterProceduralDataDependenceInfo): Unit = this.synchronized(this.iddaResults += (key -> iddi))
  def hasIDDG(key: JawaType): Boolean = this.iddaResults.contains(key)
  def getIDDG(key: JawaType): Option[InterProceduralDataDependenceInfo] = this.synchronized(this.iddaResults.get(key))
  def getIDDGs: Map[JawaType, InterProceduralDataDependenceInfo] = this.iddaResults.toMap

  private val summaryTables: MMap[JawaType, ComponentSummaryTable] = mmapEmpty

  def addSummaryTable(key: JawaType, summary: ComponentSummaryTable): Unit = this.synchronized(this.summaryTables += (key -> summary))
  def hasSummaryTable(key: JawaType): Boolean = this.summaryTables.contains(key)
  def getSummaryTable(key: JawaType): Option[ComponentSummaryTable] = this.synchronized(this.summaryTables.get(key))
  def getSummaryTables: Map[JawaType, ComponentSummaryTable] = this.summaryTables.toMap

  private var apkTaintResult: Option[Any] = None

  def addTaintAnalysisResult(tar: TaintAnalysisResult): Unit = this.synchronized(this.apkTaintResult = Some(tar))
  def hasTaintAnalysisResult(fileUri: FileResourceUri): Boolean = this.apkTaintResult.contains(fileUri)
  def getTaintAnalysisResult(fileUri: FileResourceUri): Option[TaintAnalysisResult] = this.apkTaintResult.map(_.asInstanceOf[TaintAnalysisResult])


  override def reset(removeCode: Boolean = true): Unit = {
    super.reset(removeCode)
    model.reset()
    this.idfgResults.clear()
    this.iddaResults.clear()
    this.summaryTables.clear()
    this.apkTaintResult = None
  }

  def getEntryPoints(comp: ComponentInfo): ISet[Signature] = {
    val lifecycle: MSet[Signature] = msetEmpty
    val clazz = getClassOrResolve(comp.compType)
    val subSigs: List[String] = comp.typ match {
      case ComponentType.ACTIVITY =>
        AndroidEntryPointConstants.getActivityLifecycleMethods
      case ComponentType.SERVICE =>
        AndroidEntryPointConstants.getServiceLifecycleMethods
      case ComponentType.RECEIVER =>
        AndroidEntryPointConstants.getBroadcastLifecycleMethods
      case ComponentType.PROVIDER =>
        AndroidEntryPointConstants.getContentproviderLifecycleMethods
    }
    subSigs.foreach { subSig =>
      clazz.getDeclaredMethod(subSig) match {
        case Some(method) =>
          lifecycle += method.getSignature
        case None =>
      }
    }
    model.getCallbackMethods(comp.compType) ++ lifecycle
  }

  override def toString: String = FileUtil.toFile(nameUri).getName
}
