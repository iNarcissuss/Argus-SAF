/*
 * Copyright (c) 2017. Fengguo Wei and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Detailed contributors are listed in the CONTRIBUTOR.md
 */

package org.argus.jawa.core.frontend.classfile

import org.argus.jawa.core.AccessFlag.FlagKind
import org.argus.jawa.core._
import org.argus.jawa.core.frontend.{MyClass, MyField, MyMethod}
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.FieldVisitor
import org.objectweb.asm.MethodVisitor
import org.argus.jawa.core.util._

/**
 * @author <a href="mailto:fgwei521@gmail.com">Fengguo Wei</a>
 */
class MyClassVisitor(api: Int) extends ClassVisitor(api) {
  private val classes: MMap[JawaType, MyClass] = mmapEmpty
  private var currentClass: MyClass = _
  def getClasses: IMap[JawaType, MyClass] = classes.toMap

  private def getClassName(name: String): String = {
    name.replaceAll("/", ".")
  }
  
  override def visit(
      version: Int,
      access: Int,
      name: String,
      signature: String,
      superName: String,
      interfaces: scala.Array[String]): Unit = {
    val accessFlag: Int = AccessFlag.getJawaFlags(access, FlagKind.CLASS, isConstructor = false)
    val typ: JawaType = JavaKnowledge.getTypeFromJawaName(getClassName(name))
    val superType: Option[JawaType] = {
      if (superName == null) {
        None
      } else {
        Some(JavaKnowledge.getTypeFromJawaName(getClassName(superName)))
      }
    }
    val ifs: MList[JawaType] = mlistEmpty
    for(interface <- interfaces) {
      ifs += JavaKnowledge.getTypeFromJawaName(getClassName(interface))
    }
    val c = MyClass(accessFlag, typ, superType, ifs.toList)
    classes(typ) = c
    currentClass = c
  }
  
  override def visitOuterClass(owner: String, name: String, desc: String): Unit = {
    val o: JawaType = JavaKnowledge.getTypeFromJawaName(getClassName(name))
    currentClass.setOuter(o)
  }
  
  override def visitField(access: Int, name: String, desc: String,
                 signature: String, value: Object): FieldVisitor = {
    val accessFlag: Int = AccessFlag.getJawaFlags(access, FlagKind.FIELD, isConstructor = false)
    val typ: JawaType = JavaKnowledge.formatSignatureToType(desc)
    val FQN: FieldFQN = FieldFQN(currentClass.typ, name, typ)
    val f = MyField(accessFlag, FQN)
    currentClass.addField(f)
    null
  }
  
  override def visitMethod(access: Int, name: String, desc: String,
                  signature: String, exceptions: scala.Array[String]): MethodVisitor = {
    val accessFlag: Int = AccessFlag.getJawaFlags(access, FlagKind.METHOD, if(name == "<init>" || name == "<clinit>") true else false)
    val signature: Signature = JavaKnowledge.genSignature(JavaKnowledge.formatTypeToSignature(currentClass.typ), name, desc)
    val thisParam: Option[String] = if(!AccessFlag.isStatic(accessFlag) && !AccessFlag.isAbstract(accessFlag)) Some("this_v") else None
    val paramNames: IList[String] = (0 until signature.getParameterNum).map(i => "v" + i).toList
    val m = MyMethod(accessFlag, signature, thisParam, paramNames)
    currentClass.addMethod(m)
    null
  }
}
