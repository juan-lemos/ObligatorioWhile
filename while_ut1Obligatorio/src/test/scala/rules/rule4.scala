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

  val case1 = "{int a=1; int b=2; int c=3; a=1; b=a;}"


  val error = "Offense detected - "

  s"atLeastOnce parsing '$case1'" should {
    s"contain message $error" in {
      CheckStateLinter.errores.clear()
      val newState = Parser.parse(case1).value.asInstanceOf[Stmt].checkLinter(new CheckStateLinter())
      CheckStateLinter.generateErrors(newState)
//      println(CheckStateLinter.errores)
      atLeastOnce (CheckStateLinter.errores.asScala) ((_:String) must be startWith error).setMessage(CheckStateLinter.errores.asScala.mkString(","))
    }
  }


}

