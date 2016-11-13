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

class rule1 extends Specification {

   val state = new CheckStateLinter()
   val case1 = "{y=2;\ny=3;}"
   val case2 = "{y=2;\n\ny=3;}"
   val case3 = "{y=2;\n\n\ny=3;}"
   val case4 = "{y=2;\n\n\n\ny=3;}"

  val error = "Offense detected - 1: existe mas de un salto de linea consecutivo."

    s"The '$case1' string" should {
      s"not contain message $error" in {
        CheckStateLinter.errores.clear()
        val check = Parser.parse(case1).value.asInstanceOf[Stmt].checkLinter(state)
        check.toString must not contain error
      }
    }

    s"The '$case2' string" should {
      s"not contain message $error" in {
        CheckStateLinter.errores.clear()
        val check = Parser.parse(case2).value.asInstanceOf[Stmt].checkLinter(state)
        check.toString must not contain error
      }
    }

    s"The '$case3' string" should {
      s"contain message $error" in {
        CheckStateLinter.errores.clear()
        val check = Parser.parse(case3).value.asInstanceOf[Stmt].checkLinter(state)
        check.toString must contain(error)
      }
    }
    s"The '$case4' string" should {
      s"contain message $error" in {
        CheckStateLinter.errores.clear()
        val check = Parser.parse(case4).value.asInstanceOf[Stmt].checkLinter(state)
        check.toString must contain(error)
      }
    }
  }

