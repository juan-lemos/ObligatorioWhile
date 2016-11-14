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

/**
  * Regla número 4
  * Detectar variables declaradas sin usar
  */
class rule4 extends Specification {

  val state = new CheckStateLinter()
  val case1 = "{int a=1; int b=2; int c=3; a=1; b=a;}"


  val error = "Offense detected - variable definida sin usar"

  s"The '$case1' string" should {
    s"not contain message $error" in {
      CheckStateLinter.errores.clear()
      val check = Parser.parse(case1).value.asInstanceOf[Stmt].checkLinter(state)
      check.toString must contain (error)
    }
  }


}

