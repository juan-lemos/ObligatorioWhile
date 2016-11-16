package rules

import examples.while_ut1.Parser
import examples.while_ut1.ast.{CheckStateLinter, Stmt}
import org.specs2.mutable._
import rules.Errores._

import scala.collection.JavaConverters._

/**
  * Regla número 10
  * Chequear que la cantidad de variables que se le pasa a una función
  * sea igual a la definición así como los tipos
  */
class rule10 extends Specification {

  sequential

  val case1 =
    """{
      | function int identidad(int a, bool b, num c, str d) {
      |   return 1;
      | }
      | bool a = true;
      | int b = 4;
      | num c = 2;
      | str p = pepe;
      | int b = identidad(a,b,c,d);
      | print(b);
      |}""".stripMargin

  s"The case2: '$case1' string" should {
    //    println("-----------------------"+ case2)
    CheckStateLinter.errores.clear()
    val newState = Parser.parse(case1).value.asInstanceOf[Stmt].checkLinter(new CheckStateLinter())
    CheckStateLinter.generateErrors(newState)


    s"contain messages:\n${getAll()}" in {
//      atLeastOnce (CheckStateLinter.errores.asScala) ((_:String) must be startWith error08("a")) and
//        atLeastOnce (CheckStateLinter.errores.asScala) ((_:String) must be startWith error9B) and
//          atLeastOnce (CheckStateLinter.errores.asScala) ((_:String) must be startWith error10B) and
//            atLeastOnce (CheckStateLinter.errores.asScala) ((_:String) must not startWith error15("b")) and
              atLeastOnce (CheckStateLinter.errores.asScala) ((_:String) must be startWith error11)
    }
  }

}

