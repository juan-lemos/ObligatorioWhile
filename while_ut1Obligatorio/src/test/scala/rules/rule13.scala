package rules

import examples.while_ut1.Parser
import examples.while_ut1.ast.{CheckStateLinter, Stmt}
import org.specs2.mutable._
import rules.Errores._

import scala.collection.JavaConverters._

/**
  * Regla número 13
  * No se puede redefinir funciones
  */
class rule13 extends Specification {

  sequential

  val case1 = "{function int fun1(int a) skip; function int fun1(int a) skip;}"

  s"The case1: '$case1' string" should {
    CheckStateLinter.errores.clear()
    val newState = Parser.parse(case1).value.asInstanceOf[Stmt].checkLinter(new CheckStateLinter())
    CheckStateLinter.generateErrors(newState)
    println(CheckStateLinter.errores.asScala.toList.mkString("\n"))
    s"contain messages:}" in {
      atLeastOnce (CheckStateLinter.errores.asScala) ((_:String) must be startWith error3) and
//        atLeastOnce (CheckStateLinter.errores.asScala) ((_:String) must be startWith error02) and
          atLeastOnce (CheckStateLinter.errores.asScala) ((_:String) must be startWith error13("fun1"))
    }
  }

  val case2 =
    """{
      | function int fun1(int a) skip;
      | function int fun2(int b) skip;
      | function int fun3(int b) skip;
      | function int fun1(int c) skip;
      | }""".stripMargin

  s"The case2: '$case2' string" should {
//    println("-----------------------"+ case2)
    CheckStateLinter.errores.clear()
    val newState = Parser.parse(case2).value.asInstanceOf[Stmt].checkLinter(new CheckStateLinter())
    CheckStateLinter.generateErrors(newState)

    s"contain messages: ${error13("fun1")}" in {
      atLeastOnce (CheckStateLinter.errores.asScala) ((_:String) must be startWith error13("fun1"))
    }
  }



}

