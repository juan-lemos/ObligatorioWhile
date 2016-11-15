package rules

import examples.while_ut1.Parser
import examples.while_ut1.ast.{CheckStateLinter, Stmt}
import org.specs2.mutable._
import rules.Errores._

import scala.collection.JavaConverters._

/**
  * Regla número 1
  * Que no exista más de un salto de línea entre dos líneas de código consecutivas.
  */
class rule1 extends Specification {

  sequential

  val state = new CheckStateLinter()
  val case1 = "{y=2;\ny=3;}"
  val case2 = "{y=2;\n\ny=3;}"
  val case3 = "{y=2;\n\n\ny=3;}"
  val case4 = "{y=2;\n\n\n\ny=3;}"

  s"The '$case1' string" should {
    s"not contain message $error01" in {
      CheckStateLinter.clear()
      val newState = Parser.parse(case1).value.asInstanceOf[Stmt].checkLinter(state)
      CheckStateLinter.generateErrors(newState)
      forall (CheckStateLinter.errores.asScala) ((_:String) must not startWith(error01))
    }
  }

  s"The '$case2' string" should {
    s"not contain message $error01" in {
      CheckStateLinter.clear()
      val newState = Parser.parse(case2).value.asInstanceOf[Stmt].checkLinter(state)
      CheckStateLinter.generateErrors(newState)
      forall (CheckStateLinter.errores.asScala) ((_:String) must not startWith(error01))
    }
  }

  s"The '$case3' string" should {
    s"contain message $error01" in {
      CheckStateLinter.clear()
      val newState = Parser.parse(case3).value.asInstanceOf[Stmt].checkLinter(state)
      CheckStateLinter.generateErrors(newState)
      atLeastOnce (CheckStateLinter.errores.asScala) ((_:String) must be startWith error01)
      }
  }

  s"The '$case4' string" should {
    s"contain message $error01" in {
      CheckStateLinter.clear()
      val newState = Parser.parse(case4).value.asInstanceOf[Stmt].checkLinter(state)
      CheckStateLinter.generateErrors(newState)
      atLeastOnce (CheckStateLinter.errores.asScala) ((_:String) must be startWith error01)
    }
  }

}

