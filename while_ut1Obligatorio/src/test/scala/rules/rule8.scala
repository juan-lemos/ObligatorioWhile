/*
 * Copyright 2012 Damian Helme

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.

   */
package rules

import examples.while_ut1.Parser
import examples.while_ut1.ast.{CheckStateLinter, Stmt}
import org.specs2.mutable._
import rules.Errores._
import scala.collection.JavaConverters._

/**
  * Regla número 8
  * Uso de variables no definidas
  */
class rule8 extends Specification {

  sequential

  val state = new CheckStateLinter()

  val case1 =   "{int a=1; int b=2; int c=3; a=1; b=d;}"

  s"The case3: '$case1' string" should {
    s"contain messages:\n $error02\n $error08\n $error15" in {
      CheckStateLinter.errores.clear()
      Parser.parse(case1).value.asInstanceOf[Stmt].checkLinter(state)
      atLeastOnce (CheckStateLinter.errores.asScala) ((_:String) must be startWith error02) and
        atLeastOnce (CheckStateLinter.errores.asScala) ((_:String) must be startWith error08) and
          atLeastOnce (CheckStateLinter.errores.asScala) ((_:String) must be startWith error15)
    }
  }


}

