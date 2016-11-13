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
  * Regla número2
  * Que haya solo un statement por línea
  */
class rule2 extends Specification {

  val state = new CheckStateLinter()
  val case1 = "int y=3;"

  val case2 = "{int y=3;}"

  val case3 =
    """{int y=2;
      |
      |
      |y=3;}""".stripMargin

  val case4 =
    """{int y=2;
      |
      |y=3;}""".stripMargin

  val case5 = "{print(2);}"

  val case6 = "{print(2); print(2); print(2);}"

  val error = "Offense detected - 2: No debe haber mas de un statement en la misma linea."

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
    s"not contain message $error" in {
      CheckStateLinter.errores.clear()
      val check = Parser.parse(case3).value.asInstanceOf[Stmt].checkLinter(state)
      check.toString must not contain error
    }
  }


  s"The '$case4' string" should {
    s"not contain message $error" in {
      CheckStateLinter.errores.clear()
      val check = Parser.parse(case5).value.asInstanceOf[Stmt].checkLinter(state)
      check.toString must not contain error
    }
  }

  s"The '$case5' string" should {
    s"not contain message $error" in {
      CheckStateLinter.errores.clear()
      val check = Parser.parse(case5).value.asInstanceOf[Stmt].checkLinter(state)
      check.toString must not contain error
    }
  }

  s"The '$case6' string" should {
    s"not contain message $error" in {
      CheckStateLinter.errores.clear()
      val check = Parser.parse(case6).value.asInstanceOf[Stmt].checkLinter(state)
      check.toString must contain (error)
    }
  }

}

