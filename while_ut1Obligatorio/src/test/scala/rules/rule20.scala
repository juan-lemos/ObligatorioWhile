package rules

import examples.while_ut1.Parser
import examples.while_ut1.ast.{CheckStateLinter, Stmt}
import org.specs2.mutable._
import rules.Errores._

import scala.collection.JavaConverters._

/**
  * Regla número 20
  * Warning expresiones con más de 7 operadores.
  */
class rule20 extends Specification {

  sequential

  val case1 =
    """{
      | int a = 1;
      | int b = 1;
      | int c = 1;
      | int d = 1;
      | int e = 1;
      | int f = 1;
      | int g = 1;
      | int h = 1;
      | int i = a + b + c + d + e + f + g + h + 1;
      | print(i);
      | }""".stripMargin

  s"$case1" should {

    s"contain message:${error20("8")}" in {
      CheckStateLinter.errores.clear()
      val newState = Parser.parse(case1).value.asInstanceOf[Stmt].checkLinter(new CheckStateLinter())
      CheckStateLinter.generateErrors(newState)
//      print(getAll())
      atLeastOnce (CheckStateLinter.errores.asScala) ((_:String) must be startWith error20("8"))
    }
  }


  val case2 =
    """{
      | int a = 1;
      | int b = 1;
      | int c = 1;
      | int d = 1;
      | int e = 1;
      | int f = 1;
      | int g = 1;
      | int h = 1;
      | int i = a + b + c + d + e + f + g + h ;
      | print(i);
      | }""".stripMargin

  s"$case2" should {
    s"contain message:${error20("7")}" in {
      CheckStateLinter.errores.clear()
      val newState = Parser.parse(case2).value.asInstanceOf[Stmt].checkLinter(new CheckStateLinter())
      CheckStateLinter.generateErrors(newState)
//      print(getAll())

      (CheckStateLinter.errores.asScala) must beEmpty
    }
  }


  val case3 =
    """{
      |  function num suma(num a, num b) {
      |    num i = (a + b *( a + b * ( a + b *( a + b *( a + b )))));
      |    return i;
      |  }
      |
      |  num i = suma(1.0, 1.0);
      |  print(i);
      |}""".stripMargin

  s"$case3" should {
    s"contain message:${error20("9")}" in {
      CheckStateLinter.errores.clear()
      val newState = Parser.parse(case3).value.asInstanceOf[Stmt].checkLinter(new CheckStateLinter())
      CheckStateLinter.generateErrors(newState)
            print(getAll())

      atLeastOnce (CheckStateLinter.errores.asScala) ((_:String) must be startWith error20("9"))
    }
  }


}

