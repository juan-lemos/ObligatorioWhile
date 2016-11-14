package rules

import examples.while_ut1.Parser
import examples.while_ut1.ast.{CheckStateLinter, Stmt}
import org.specs2.mutable._
//import scala.collection.JavaConversions._
import scala.collection.JavaConverters._
import Errores._

/**
  * Regla número2
  * Que haya solo un statement por línea
  */
class rule2 extends Specification {

  sequential

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

//  val error = "Offense detected - 2: No debe haber mas de un statement en la misma linea."

  s"The '$case1' string" should {
    s"case1 not contain message $error02" in {
      CheckStateLinter.clear()
      Parser.parse(case1).value.asInstanceOf[Stmt].checkLinter(state)
      forall (CheckStateLinter.errores.asScala) ((_:String) must not startWith(error02))
    }
  }

  s"The '$case2' string" should {
    s"case2 not contain message $error02" in {
      CheckStateLinter.clear()
      Parser.parse(case2).value.asInstanceOf[Stmt].checkLinter(state)
      forall (CheckStateLinter.errores.asScala) ((_:String) must not startWith(error02))
    }
  }

  s"The '$case3' string" should {
    s"case3 not contain message $error02" in {
      CheckStateLinter.clear()
      Parser.parse(case3).value.asInstanceOf[Stmt].checkLinter(state)
      forall (CheckStateLinter.errores.asScala) ((_:String) must not startWith(error02))
    }
  }

  s"The '$case4' string" should {
    s"case4 not contain message $error02" in {
      CheckStateLinter.errores.clear()
      Parser.parse(case5).value.asInstanceOf[Stmt].checkLinter(state)
      forall (CheckStateLinter.errores.asScala) ((_:String) must not startWith(error02))
    }
  }

  s"The '$case5' string" should {
    s"case5 not contain message $error02" in {
      CheckStateLinter.errores.clear()
      Parser.parse(case5).value.asInstanceOf[Stmt].checkLinter(state)
      forall (CheckStateLinter.errores.asScala) ((_:String) must not startWith(error02))
    }
  }

  s"The '$case6' string" should {
    s"case6 contain message $error02" in {
      CheckStateLinter.errores.clear()
      Parser.parse(case6).value.asInstanceOf[Stmt].checkLinter(state)
      println(CheckStateLinter.errores)
      atLeastOnce (CheckStateLinter.errores.asScala) ((_:String) must startWith(error02))
    }
  }

}

