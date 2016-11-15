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

    s"contain messages: $error02 \n ${error13("fun1")}" in {
      atLeastOnce (CheckStateLinter.errores.asScala) ((_:String) must be startWith error02) and
      atLeastOnce (CheckStateLinter.errores.asScala) ((_:String) must be startWith error13("fun1"))
    }
  }



//  val case2 =
//    """{
//      | function int fun1(int a) skip;
//      | function int fun2(int b) skip;
//      | function int fun3(int c) skip;
//      | function int fun4(int d) skip;
//      | function int fun5(int e) skip;
//      | function int fun6(int f) skip;
//      | function int fun7(int g) skip;
//      | function int fun8(int h) skip;
//      | function int fun9(int i) skip;
//      | function int fun1(int error) skip;
//      | }
//      |"""".stripMargin

//  val case2 =
//    """{
//      | function int fun1(int a) skip;
//      | int a = 1;
//      | function int fun1(int a) skip;
//      | }""".stripMargin.toString
//
//
//  s"The case2: '$case2' string" should {
//    println("-----------------------"+ case2)
//    CheckStateLinter.errores.clear()
//    val newState = Parser.parse(case2).value.asInstanceOf[Stmt].checkLinter(new CheckStateLinter())
//    CheckStateLinter.generateErrors(newState)
//
//    s"contain messages: ${error13("fun1")}" in {
//      atLeastOnce (CheckStateLinter.errores.asScala) ((_:String) must be startWith error13("fun1"))
//    }
//  }

}

