package rules

import examples.while_ut1.Parser
import examples.while_ut1.ast.{CheckStateLinter, Stmt}
import org.specs2.mutable._
import scala.collection.JavaConverters._
import Errores._

/**
  * Regla número 4
  * Detectar variables declaradas sin usar
  */
class rule4 extends Specification {

  sequential

  val state = new CheckStateLinter()
  val case1 = "{int a=1; int b=2; int c=3; a=1; b=a;}"


  val error = "Offense detected - variable definida sin usar"

  s"atLeastOnce parsing '$case1'" should {
    s"contain message $error" in {
      CheckStateLinter.errores.clear()
      Parser.parse(case1).value.asInstanceOf[Stmt].checkLinter(state)
      atLeastOnce (CheckStateLinter.errores.asScala) ((_:String) must be startWith error)
    }
  }


}

