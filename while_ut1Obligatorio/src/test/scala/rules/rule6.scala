package rules

import examples.while_ut1.Parser
import examples.while_ut1.ast.{CheckStateLinter, Stmt}
import org.specs2.mutable._
import rules.Errores._
import scala.collection.JavaConverters._

/**
  * Regla número 6
  * Los nombres de la variables deben comenzar con minúsculas y sin guiones bajos
  */
class rule6 extends Specification {

  sequential

  val case1 = "{int a=1; int Ba=2; }"

  val case2 =  "{int a=1; int _ad=2; }"

  val case3 =  "function int myfuncion(int _a, int A){skip;}"  // TODO si se controla

  s"The case1: '$case1' string" should {
    s"contain message $error06" in {
      CheckStateLinter.errores.clear()
      val newState = Parser.parse(case1).value.asInstanceOf[Stmt].checkLinter(new CheckStateLinter())
      CheckStateLinter.generateErrors(newState)
      atLeastOnce (CheckStateLinter.errores.asScala) ((_:String) must be startWith error06)
    }
  }

  s"The case2: '$case2' string" should {
    s"contain message $error06" in {
      CheckStateLinter.errores.clear()
      val newState = Parser.parse(case2).value.asInstanceOf[Stmt].checkLinter(new CheckStateLinter())
      CheckStateLinter.generateErrors(newState)
      atLeastOnce (CheckStateLinter.errores.asScala) ((_:String) must be startWith error06)
    }
  }


}

