package rules

import examples.while_ut1.Parser
import examples.while_ut1.ast.{CheckStateLinter, Stmt}
import org.specs2.mutable._
import rules.Errores._

import scala.collection.JavaConverters._

/**
  * Regla número 7
  * Los nombres de los métodos deben comenzar con minúsculas
  */
class rule7 extends Specification {

  sequential

  val state = new CheckStateLinter()

  val case1 =  "function int MiFuncion(int _a, int A){skip;}"

  val case2 =  "function int miFuncion(int _a, int A){skip;}"

  val case3 =   "function int _mi_Funcion(int _a, int A) {skip;}"

  s"The case1: '$case1' string" should {
    s"contain message $error07" in {
      CheckStateLinter.errores.clear()
      val newState = Parser.parse(case1).value.asInstanceOf[Stmt].checkLinter(state)
      CheckStateLinter.generateErrors(newState)
      atLeastOnce (CheckStateLinter.errores.asScala) ((_:String) must be startWith error07)
    }
  }

  s"The case2: '$case2' string" should {
    s"not contain message $error07" in {
      CheckStateLinter.errores.clear()
      val newState = Parser.parse(case2).value.asInstanceOf[Stmt].checkLinter(state)
      CheckStateLinter.generateErrors(newState)
      forall (CheckStateLinter.errores.asScala) ((_:String) must not startWith error07)
    }
  }

  s"The case3: '$case3' string" should {
    s"not contain messages:\n $error07\n and $error11\n and $error17" in {
      CheckStateLinter.errores.clear()
      val newState = Parser.parse(case3).value.asInstanceOf[Stmt].checkLinter(state)
      CheckStateLinter.generateErrors(newState)

      forall (CheckStateLinter.errores.asScala) ((_:String) must not startWith error07) and
        atLeastOnce (CheckStateLinter.errores.asScala) ((_:String) must be startWith error11) and
          atLeastOnce (CheckStateLinter.errores.asScala) ((_:String) must be startWith error17)
    }
  }


}

