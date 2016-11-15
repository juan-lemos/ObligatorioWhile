package rules

import examples.while_ut1.Parser
import examples.while_ut1.ast.{CheckStateLinter, Stmt}
import org.specs2.mutable._
import rules.Errores._
import scala.collection.JavaConverters._

/**
  * Regla número 8
  * Uso de variables no definidas
  */
class rule8 extends Specification {

  sequential

  val case1 = "{int a=1; int b=2; int c=3; a=1; b=d;}"

  val errorLocal08 = "Offense detected - 8: Variable d no declarada."

  s"The case3: '$case1' string" should {
//    s"contain messages:\n $error02\n $error08\n $error15" in {

    CheckStateLinter.errores.clear()
    val newState = Parser.parse(case1).value.asInstanceOf[Stmt].checkLinter(new CheckStateLinter())
    CheckStateLinter.generateErrors(newState)
//    println(CheckStateLinter.errores.asScala.mkString("\n"))

    s"contain messages: $error02" in {
      atLeastOnce (CheckStateLinter.errores.asScala) ((_:String) must be startWith error02)
    }

    s"contain messages: $errorLocal08" in {
      atLeastOnce (CheckStateLinter.errores.asScala) ((_:String) must be startWith errorLocal08)
    }

    s"contain messages: $error15" in {
      atLeastOnce (CheckStateLinter.errores.asScala) ((_:String) must be startWith error15)
    }
  }

}

