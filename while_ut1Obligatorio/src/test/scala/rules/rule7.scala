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

  val case1 =  "function int MiFuncion(int _a, int A){skip;}"

  s"The case1: '$case1' string" should {
    s"contain message:" in {
      CheckStateLinter.errores.clear()
      val newState = Parser.parse(case1).value.asInstanceOf[Stmt].checkLinter(new CheckStateLinter())
      CheckStateLinter.generateErrors(newState)
      atLeastOnce (CheckStateLinter.errores.asScala) ((_:String) must be startWith error07)
    }
  }

  val case2 =  "function int miFuncion(int _a, int A){skip;}"

  s"The case2: '$case2' string" should {
    s"contain message:\n${getAll()} " in {
      CheckStateLinter.errores.clear()
      val newState = Parser.parse(case2).value.asInstanceOf[Stmt].checkLinter(new CheckStateLinter())
      CheckStateLinter.generateErrors(newState)
      forall (CheckStateLinter.errores.asScala) ((_:String) must not startWith error07)
    }
  }

  val case3 =   "function int _mi_Funcion(int _a, int A) {skip;}"

  s"The case3: '$case3' string" should {
    s"contain messages:\n${getAll()}" in {
      CheckStateLinter.errores.clear()
      val newState = Parser.parse(case3).value.asInstanceOf[Stmt].checkLinter(new CheckStateLinter())
      CheckStateLinter.generateErrors(newState)

      forall (CheckStateLinter.errores.asScala) ((_:String) must not startWith error07) and
        atLeastOnce (CheckStateLinter.errores.asScala) ((_:String) must be startWith error11("_a")) and
          atLeastOnce (CheckStateLinter.errores.asScala) ((_:String) must be startWith error11("A")) and
            atLeastOnce (CheckStateLinter.errores.asScala) ((_:String) must be startWith error17)
    }
  }


}
