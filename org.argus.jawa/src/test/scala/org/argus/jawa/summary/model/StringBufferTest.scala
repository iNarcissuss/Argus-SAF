/*
 * Copyright (c) 2017. Fengguo Wei and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Detailed contributors are listed in the CONTRIBUTOR.md
 */

package org.argus.jawa.summary.model

import org.argus.jawa.alir.pta._
import org.argus.jawa.alir.pta.rfa.RFAFact
import org.argus.jawa.core.JawaType

/**
  * Created by fgwei on 6/22/17.
  */
class StringBufferTest extends SuTestBase("StringBuffer.safsu") {

  val thisInstance = PTAInstance(new JawaType("java.lang.StringBuffer"), defContext)
  val thisFact = new RFAFact(VarSlot("v0"), thisInstance)
  val thisValueInstance = PTAConcreteStringInstance("StringBuffer", defContext)
  val thisValueFact = new RFAFact(FieldSlot(thisInstance, "value"), thisValueInstance)

  "Ljava/lang/StringBuffer;.<init>:()V" with_input (
    thisFact,
  ) produce (
    thisFact,
    new RFAFact(FieldSlot(thisInstance, "value"), PTAConcreteStringInstance("", currentContext))
  )

  "Ljava/lang/StringBuffer;.<init>:(I)V" with_input (
    thisFact,
  ) produce (
    thisFact,
    new RFAFact(FieldSlot(thisInstance, "value"), PTAPointStringInstance(currentContext))
  )

  "Ljava/lang/StringBuffer;.<init>:(Ljava/lang/CharSequence;)V" with_input (
    thisFact,
    new RFAFact(VarSlot("v1"), PTAConcreteStringInstance("NewString", defContext2))
  ) produce (
    thisFact,
    new RFAFact(VarSlot("v1"), PTAConcreteStringInstance("NewString", defContext2)),
    new RFAFact(FieldSlot(thisInstance, "value"), PTAConcreteStringInstance("NewString", defContext2))
  )

  "Ljava/lang/StringBuffer;.<init>:(Ljava/lang/String;)V" with_input (
    thisFact,
    new RFAFact(VarSlot("v1"), PTAConcreteStringInstance("NewString", defContext2))
  ) produce (
    thisFact,
    new RFAFact(VarSlot("v1"), PTAConcreteStringInstance("NewString", defContext2)),
    new RFAFact(FieldSlot(thisInstance, "value"), PTAConcreteStringInstance("NewString", defContext2))
  )

  "Ljava/lang/StringBuffer;.append:(C)Ljava/lang/Appendable;" with_input (
    thisFact,
    thisValueFact
  ) produce (
    thisFact,
    thisValueFact,
    new RFAFact(FieldSlot(thisInstance, "value"), PTAPointStringInstance(currentContext)),
    new RFAFact(VarSlot("temp"), thisInstance)
  )

  "Ljava/lang/StringBuffer;.append:(C)Ljava/lang/StringBuffer;" with_input (
    thisFact,
    thisValueFact
  ) produce (
    thisFact,
    thisValueFact,
    new RFAFact(FieldSlot(thisInstance, "value"), PTAPointStringInstance(currentContext)),
    new RFAFact(VarSlot("temp"), thisInstance)
  )

  "Ljava/lang/StringBuffer;.append:(D)Ljava/lang/StringBuffer;" with_input (
    thisFact,
    thisValueFact
  ) produce (
    thisFact,
    thisValueFact,
    new RFAFact(FieldSlot(thisInstance, "value"), PTAPointStringInstance(currentContext)),
    new RFAFact(VarSlot("temp"), thisInstance)
  )

  "Ljava/lang/StringBuffer;.append:(F)Ljava/lang/StringBuffer;" with_input (
    thisFact,
    thisValueFact
  ) produce (
    thisFact,
    thisValueFact,
    new RFAFact(FieldSlot(thisInstance, "value"), PTAPointStringInstance(currentContext)),
    new RFAFact(VarSlot("temp"), thisInstance)
  )

  "Ljava/lang/StringBuffer;.append:(I)Ljava/lang/StringBuffer;" with_input (
    thisFact,
    thisValueFact
  ) produce (
    thisFact,
    thisValueFact,
    new RFAFact(FieldSlot(thisInstance, "value"), PTAPointStringInstance(currentContext)),
    new RFAFact(VarSlot("temp"), thisInstance)
  )

  "Ljava/lang/StringBuffer;.append:(J)Ljava/lang/StringBuffer;" with_input (
    thisFact,
    thisValueFact
  ) produce (
    thisFact,
    thisValueFact,
    new RFAFact(FieldSlot(thisInstance, "value"), PTAPointStringInstance(currentContext)),
    new RFAFact(VarSlot("temp"), thisInstance)
  )

  "Ljava/lang/StringBuffer;.append:(Ljava/lang/CharSequence;)Ljava/lang/Appendable;" with_input (
    thisFact,
    thisValueFact
  ) produce (
    thisFact,
    thisValueFact,
    new RFAFact(FieldSlot(thisInstance, "value"), PTAPointStringInstance(currentContext)),
    new RFAFact(VarSlot("temp"), thisInstance)
  )

  "Ljava/lang/StringBuffer;.append:(Ljava/lang/CharSequence;)Ljava/lang/StringBuffer;" with_input (
    thisFact,
    thisValueFact
  ) produce (
    thisFact,
    thisValueFact,
    new RFAFact(FieldSlot(thisInstance, "value"), PTAPointStringInstance(currentContext)),
    new RFAFact(VarSlot("temp"), thisInstance)
  )

  "Ljava/lang/StringBuffer;.append:(Ljava/lang/CharSequence;II)Ljava/lang/Appendable;" with_input (
    thisFact,
    thisValueFact
  ) produce (
    thisFact,
    thisValueFact,
    new RFAFact(FieldSlot(thisInstance, "value"), PTAPointStringInstance(currentContext)),
    new RFAFact(VarSlot("temp"), thisInstance)
  )

  "Ljava/lang/StringBuffer;.append:(Ljava/lang/CharSequence;II)Ljava/lang/StringBuffer;" with_input (
    thisFact,
    thisValueFact
  ) produce (
    thisFact,
    thisValueFact,
    new RFAFact(FieldSlot(thisInstance, "value"), PTAPointStringInstance(currentContext)),
    new RFAFact(VarSlot("temp"), thisInstance)
  )

  "Ljava/lang/StringBuffer;.append:(Ljava/lang/Object;)Ljava/lang/StringBuffer;" with_input (
    thisFact,
    thisValueFact
  ) produce (
    thisFact,
    thisValueFact,
    new RFAFact(FieldSlot(thisInstance, "value"), PTAPointStringInstance(currentContext)),
    new RFAFact(VarSlot("temp"), thisInstance)
  )

  "Ljava/lang/StringBuffer;.append:(Ljava/lang/String;)Ljava/lang/StringBuffer;" with_input (
    thisFact,
    thisValueFact
  ) produce (
    thisFact,
    thisValueFact,
    new RFAFact(FieldSlot(thisInstance, "value"), PTAPointStringInstance(currentContext)),
    new RFAFact(VarSlot("temp"), thisInstance)
  )

  "Ljava/lang/StringBuffer;.append:(Ljava/lang/StringBuffer;)Ljava/lang/StringBuffer;" with_input (
    thisFact,
    thisValueFact
  ) produce (
    thisFact,
    thisValueFact,
    new RFAFact(FieldSlot(thisInstance, "value"), PTAPointStringInstance(currentContext)),
    new RFAFact(VarSlot("temp"), thisInstance)
  )

  "Ljava/lang/StringBuffer;.append:(Z)Ljava/lang/StringBuffer;" with_input (
    thisFact,
    thisValueFact
  ) produce (
    thisFact,
    thisValueFact,
    new RFAFact(FieldSlot(thisInstance, "value"), PTAPointStringInstance(currentContext)),
    new RFAFact(VarSlot("temp"), thisInstance)
  )

  "Ljava/lang/StringBuffer;.append:([C)Ljava/lang/StringBuffer;" with_input (
    thisFact,
    thisValueFact
  ) produce (
    thisFact,
    thisValueFact,
    new RFAFact(FieldSlot(thisInstance, "value"), PTAPointStringInstance(currentContext)),
    new RFAFact(VarSlot("temp"), thisInstance)
  )

  "Ljava/lang/StringBuffer;.append:([CII)Ljava/lang/StringBuffer;" with_input (
    thisFact,
    thisValueFact
  ) produce (
    thisFact,
    thisValueFact,
    new RFAFact(FieldSlot(thisInstance, "value"), PTAPointStringInstance(currentContext)),
    new RFAFact(VarSlot("temp"), thisInstance)
  )

  "Ljava/lang/StringBuffer;.appendCodePoint:(I)Ljava/lang/StringBuffer;" with_input (
    thisFact,
    thisValueFact
  ) produce (
    thisFact,
    thisValueFact,
    new RFAFact(FieldSlot(thisInstance, "value"), PTAPointStringInstance(currentContext)),
    new RFAFact(VarSlot("temp"), thisInstance)
  )

  "Ljava/lang/StringBuffer;.capacity:()I" with_input () produce ()

  "Ljava/lang/StringBuffer;.charAt:(I)C" with_input () produce ()

  "Ljava/lang/StringBuffer;.codePointAt:(I)I" with_input () produce ()

  "Ljava/lang/StringBuffer;.codePointBefore:(I)I" with_input () produce ()

  "Ljava/lang/StringBuffer;.codePointCount:(II)I" with_input () produce ()

  "Ljava/lang/StringBuffer;.delete:(II)Ljava/lang/StringBuffer;" with_input (
    thisFact,
    thisValueFact
  ) produce (
    thisFact,
    new RFAFact(FieldSlot(thisInstance, "value"), PTAPointStringInstance(currentContext)),
    new RFAFact(VarSlot("temp"), thisInstance)
  )

  "Ljava/lang/StringBuffer;.deleteCharAt:(I)Ljava/lang/StringBuffer;" with_input (
    thisFact,
    thisValueFact
  ) produce (
    thisFact,
    new RFAFact(FieldSlot(thisInstance, "value"), PTAPointStringInstance(currentContext)),
    new RFAFact(VarSlot("temp"), thisInstance)
  )

  "Ljava/lang/StringBuffer;.ensureCapacity:(I)V" with_input () produce ()

  "Ljava/lang/StringBuffer;.getChars:(II[CI)V" with_input (
    thisFact,
    thisValueFact
  ) produce (
    thisFact,
    thisValueFact,
    new RFAFact(VarSlot("v3"), PTAInstance(new JawaType("char", 1), currentContext))
  )

  "Ljava/lang/StringBuffer;.indexOf:(Ljava/lang/String;)I" with_input () produce ()

  "Ljava/lang/StringBuffer;.indexOf:(Ljava/lang/String;I)I" with_input () produce ()

  "Ljava/lang/StringBuffer;.insert:(IC)Ljava/lang/StringBuffer;" with_input (
    thisFact,
    thisValueFact
  ) produce (
    thisFact,
    new RFAFact(FieldSlot(thisInstance, "value"), PTAPointStringInstance(currentContext)),
    new RFAFact(VarSlot("temp"), thisInstance)
  )

  "Ljava/lang/StringBuffer;.insert:(ID)Ljava/lang/StringBuffer;" with_input (
    thisFact,
    thisValueFact
  ) produce (
    thisFact,
    new RFAFact(FieldSlot(thisInstance, "value"), PTAPointStringInstance(currentContext)),
    new RFAFact(VarSlot("temp"), thisInstance)
  )

  "Ljava/lang/StringBuffer;.insert:(IF)Ljava/lang/StringBuffer;" with_input (
    thisFact,
    thisValueFact
  ) produce (
    thisFact,
    new RFAFact(FieldSlot(thisInstance, "value"), PTAPointStringInstance(currentContext)),
    new RFAFact(VarSlot("temp"), thisInstance)
  )

  "Ljava/lang/StringBuffer;.insert:(II)Ljava/lang/StringBuffer;" with_input (
    thisFact,
    thisValueFact
  ) produce (
    thisFact,
    new RFAFact(FieldSlot(thisInstance, "value"), PTAPointStringInstance(currentContext)),
    new RFAFact(VarSlot("temp"), thisInstance)
  )

  "Ljava/lang/StringBuffer;.insert:(IJ)Ljava/lang/StringBuffer;" with_input (
    thisFact,
    thisValueFact
  ) produce (
    thisFact,
    new RFAFact(FieldSlot(thisInstance, "value"), PTAPointStringInstance(currentContext)),
    new RFAFact(VarSlot("temp"), thisInstance)
  )

  "Ljava/lang/StringBuffer;.insert:(ILjava/lang/CharSequence;)Ljava/lang/StringBuffer;" with_input (
    thisFact,
    thisValueFact
  ) produce (
    thisFact,
    new RFAFact(FieldSlot(thisInstance, "value"), PTAPointStringInstance(currentContext)),
    new RFAFact(VarSlot("temp"), thisInstance)
  )

  "Ljava/lang/StringBuffer;.insert:(ILjava/lang/CharSequence;II)Ljava/lang/StringBuffer;" with_input (
    thisFact,
    thisValueFact
  ) produce (
    thisFact,
    new RFAFact(FieldSlot(thisInstance, "value"), PTAPointStringInstance(currentContext)),
    new RFAFact(VarSlot("temp"), thisInstance)
  )

  "Ljava/lang/StringBuffer;.insert:(ILjava/lang/Object;)Ljava/lang/StringBuffer;" with_input (
    thisFact,
    thisValueFact
  ) produce (
    thisFact,
    new RFAFact(FieldSlot(thisInstance, "value"), PTAPointStringInstance(currentContext)),
    new RFAFact(VarSlot("temp"), thisInstance)
  )

  "Ljava/lang/StringBuffer;.insert:(ILjava/lang/String;)Ljava/lang/StringBuffer;" with_input (
    thisFact,
    thisValueFact
  ) produce (
    thisFact,
    new RFAFact(FieldSlot(thisInstance, "value"), PTAPointStringInstance(currentContext)),
    new RFAFact(VarSlot("temp"), thisInstance)
  )

  "Ljava/lang/StringBuffer;.insert:(IZ)Ljava/lang/StringBuffer;" with_input (
    thisFact,
    thisValueFact
  ) produce (
    thisFact,
    new RFAFact(FieldSlot(thisInstance, "value"), PTAPointStringInstance(currentContext)),
    new RFAFact(VarSlot("temp"), thisInstance)
  )

  "Ljava/lang/StringBuffer;.insert:(I[C)Ljava/lang/StringBuffer;" with_input (
    thisFact,
    thisValueFact
  ) produce (
    thisFact,
    new RFAFact(FieldSlot(thisInstance, "value"), PTAPointStringInstance(currentContext)),
    new RFAFact(VarSlot("temp"), thisInstance)
  )

  "Ljava/lang/StringBuffer;.insert:(I[CII)Ljava/lang/StringBuffer;" with_input (
    thisFact,
    thisValueFact
  ) produce (
    thisFact,
    new RFAFact(FieldSlot(thisInstance, "value"), PTAPointStringInstance(currentContext)),
    new RFAFact(VarSlot("temp"), thisInstance)
  )

  "Ljava/lang/StringBuffer;.lastIndexOf:(Ljava/lang/String;)I" with_input () produce ()

  "Ljava/lang/StringBuffer;.lastIndexOf:(Ljava/lang/String;I)I" with_input () produce ()

  "Ljava/lang/StringBuffer;.length:()I" with_input () produce ()

  "Ljava/lang/StringBuffer;.offsetByCodePoints:(II)I" with_input () produce ()

  "Ljava/lang/StringBuffer;.replace:(IILjava/lang/String;)Ljava/lang/StringBuffer;" with_input (
    thisFact,
    thisValueFact
  ) produce (
    thisFact,
    new RFAFact(FieldSlot(thisInstance, "value"), PTAPointStringInstance(currentContext)),
    new RFAFact(VarSlot("temp"), thisInstance)
  )

  "Ljava/lang/StringBuffer;.reverse:()Ljava/lang/StringBuffer;" with_input (
    thisFact,
    thisValueFact
  ) produce (
    thisFact,
    new RFAFact(FieldSlot(thisInstance, "value"), PTAPointStringInstance(currentContext)),
    new RFAFact(VarSlot("temp"), thisInstance)
  )

  "Ljava/lang/StringBuffer;.setCharAt:(IC)V" with_input (
    thisFact,
    thisValueFact
  ) produce (
    thisFact,
    new RFAFact(FieldSlot(thisInstance, "value"), PTAPointStringInstance(currentContext))
  )

  "Ljava/lang/StringBuffer;.setLength:(I)V" with_input (
    thisFact,
    thisValueFact
  ) produce (
    thisFact,
    new RFAFact(FieldSlot(thisInstance, "value"), PTAPointStringInstance(currentContext))
  )

  "Ljava/lang/StringBuffer;.subSequence:(II)Ljava/lang/CharSequence;" with_input (
    thisFact,
    thisValueFact
  ) produce (
    thisFact,
    thisValueFact,
    new RFAFact(VarSlot("temp"), PTAPointStringInstance(currentContext))
  )

  "Ljava/lang/StringBuffer;.substring:(I)Ljava/lang/String;" with_input (
    thisFact,
    thisValueFact
  ) produce (
    thisFact,
    thisValueFact,
    new RFAFact(VarSlot("temp"), PTAPointStringInstance(currentContext))
  )

  "Ljava/lang/StringBuffer;.substring:(II)Ljava/lang/String;" with_input (
    thisFact,
    thisValueFact
  ) produce (
    thisFact,
    thisValueFact,
    new RFAFact(VarSlot("temp"), PTAPointStringInstance(currentContext))
  )

  "Ljava/lang/StringBuffer;.toString:()Ljava/lang/String;" with_input (
    thisFact,
    thisValueFact
  ) produce (
    thisFact,
    thisValueFact,
    new RFAFact(VarSlot("temp"), thisValueInstance)
  )

  "Ljava/lang/StringBuffer;.trimToSize:()V" with_input () produce ()
}
