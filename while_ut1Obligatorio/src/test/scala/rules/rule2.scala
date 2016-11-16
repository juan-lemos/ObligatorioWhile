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

//  val state = new CheckStateLinter()

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
    s"contain message:" in {
      CheckStateLinter.clear()
      val newState = Parser.parse(case1).value.asInstanceOf[Stmt].checkLinter(new CheckStateLinter())
      CheckStateLinter.generateErrors(newState)
      forall (CheckStateLinter.errores.asScala) ((_:String) must not startWith error02)
    }
  }

  s"The '$case2' string" should {
    s"contain message:" in {
      CheckStateLinter.clear()
      val newState = Parser.parse(case2).value.asInstanceOf[Stmt].checkLinter(new CheckStateLinter())
      CheckStateLinter.generateErrors(newState)
      forall (CheckStateLinter.errores.asScala) ((_:String) must not startWith error02)
    }
  }

  s"The '$case3' string" should {
    s"contain message:" in {
      CheckStateLinter.clear()
      val newState = Parser.parse(case3).value.asInstanceOf[Stmt].checkLinter(new CheckStateLinter())
      CheckStateLinter.generateErrors(newState)
      forall (CheckStateLinter.errores.asScala) ((_:String) must not startWith error02)
    }
  }

  s"The '$case4' string" should {
    s"contain message:" in {
      CheckStateLinter.errores.clear()
      val newState = Parser.parse(case5).value.asInstanceOf[Stmt].checkLinter(new CheckStateLinter())
      CheckStateLinter.generateErrors(newState)
      forall (CheckStateLinter.errores.asScala) ((_:String) must not startWith error02)
    }
  }

  s"The '$case5' string" should {
    s"contain message:" in {
      CheckStateLinter.errores.clear()
      val newState = Parser.parse(case5).value.asInstanceOf[Stmt].checkLinter(new CheckStateLinter())
      CheckStateLinter.generateErrors(newState)
      forall (CheckStateLinter.errores.asScala) ((_:String) must not startWith error02)
    }
  }

  s"The '$case6' string" should {
    s"contain message:" in {
      CheckStateLinter.errores.clear()
      val newState = Parser.parse(case6).value.asInstanceOf[Stmt].checkLinter(new CheckStateLinter())
      CheckStateLinter.generateErrors(newState)
      atLeastOnce (CheckStateLinter.errores.asScala) ((_:String) must be startWith error02)
    }
  }

}

