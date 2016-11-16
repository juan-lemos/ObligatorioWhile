package rules

import examples.while_ut1.Parser
import examples.while_ut1.ast.{CheckStateLinter, Stmt}
import org.specs2.mutable._
import rules.Errores._

import scala.collection.JavaConverters._

/**
  * Regla número 14
  * No se puede redefinir variables
  */
class rule14 extends Specification {

  sequential

  val case1 = "{int a=1; int a=3;}"

  s"contain message:" in {
    CheckStateLinter.errores.clear()
    val newState = Parser.parse(case1).value.asInstanceOf[Stmt].checkLinter(new CheckStateLinter())
    CheckStateLinter.generateErrors(newState)

    s"contain message:" in {
      atLeastOnce (CheckStateLinter.errores.asScala) ((_:String) must be startWith error02) and
      atLeastOnce (CheckStateLinter.errores.asScala) ((_:String) must be startWith error1419)
    }
  }

  val case2 = "{int a=1; bool a=3;}"

  s"The case2: '$case2' string" should {
    CheckStateLinter.errores.clear()
    val newState = Parser.parse(case2).value.asInstanceOf[Stmt].checkLinter(new CheckStateLinter())
    CheckStateLinter.generateErrors(newState)

    s"contain message:" in {
      atLeastOnce (CheckStateLinter.errores.asScala) ((_:String) must be startWith error02) and
      atLeastOnce (CheckStateLinter.errores.asScala) ((_:String) must be startWith error1419)
    }
  }


  val case3 = "{int a=1; str a=3;}"

  s"The case3: '$case3' string" should {
    CheckStateLinter.errores.clear()
    val newState = Parser.parse(case3).value.asInstanceOf[Stmt].checkLinter(new CheckStateLinter())
    CheckStateLinter.generateErrors(newState)

    s"contain message:" in {
      atLeastOnce (CheckStateLinter.errores.asScala) ((_:String) must be startWith error02) and
        atLeastOnce (CheckStateLinter.errores.asScala) ((_:String) must be startWith error1419)
    }
  }

  val case4 = "{str a=1; str a=3;}"

  s"The case2: '$case4' string" should {
    CheckStateLinter.errores.clear()
    val newState = Parser.parse(case4).value.asInstanceOf[Stmt].checkLinter(new CheckStateLinter())
    CheckStateLinter.generateErrors(newState)

    s"contain message:" in {
      atLeastOnce (CheckStateLinter.errores.asScala) ((_:String) must be startWith error02) and
        atLeastOnce (CheckStateLinter.errores.asScala) ((_:String) must be startWith error1419)
    }
  }

  val case5 = "{str a=1; bool a=3;}"

  s"The case2: '$case5' string" should {
    CheckStateLinter.errores.clear()
    val newState = Parser.parse(case5).value.asInstanceOf[Stmt].checkLinter(new CheckStateLinter())
    CheckStateLinter.generateErrors(newState)

    s"contain message:" in {
      atLeastOnce (CheckStateLinter.errores.asScala) ((_:String) must be startWith error02) and
        atLeastOnce (CheckStateLinter.errores.asScala) ((_:String) must be startWith error1419)
    }
  }

  val case6 = "{str a=1; int a=3;}"

  s"The case2: '$case6' string" should {
    CheckStateLinter.errores.clear()
    val newState = Parser.parse(case6).value.asInstanceOf[Stmt].checkLinter(new CheckStateLinter())
    CheckStateLinter.generateErrors(newState)

    s"contain message:" in {
      atLeastOnce (CheckStateLinter.errores.asScala) ((_:String) must be startWith error02) and
        atLeastOnce (CheckStateLinter.errores.asScala) ((_:String) must be startWith error1419)
    }
  }


}

