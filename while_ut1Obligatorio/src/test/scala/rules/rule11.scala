package rules

import examples.while_ut1.Parser
import examples.while_ut1.ast.{CheckStateLinter, Stmt}
import org.specs2.mutable._
import rules.Errores._

import scala.collection.JavaConverters._

/**
  * Regla número 11
  * Detectar parámetros de funciones que no son utilizados.
  */
class rule11 extends Specification {

  sequential

  val case1 =
    """{
      | function int identidad(int a, bool b, num c, str d) {
      |   return 1;
      | }
      |
      | int b = identidad(a);
      | bool c = identidad(a);
      | print(b);
      |}""".stripMargin

  s"The case2: '$case1' string" should {
    //    println("-----------------------"+ case2)
    CheckStateLinter.errores.clear()
    val newState = Parser.parse(case1).value.asInstanceOf[Stmt].checkLinter(new CheckStateLinter())
    CheckStateLinter.generateErrors(newState)


    s"contain messages:\n" in {
//      atLeastOnce (CheckStateLinter.errores.asScala) ((_:String) must be startWith error08("a")) and
//        atLeastOnce (CheckStateLinter.errores.asScala) ((_:String) must be startWith error9B) and
//          atLeastOnce (CheckStateLinter.errores.asScala) ((_:String) must be startWith error10B) and
//            atLeastOnce (CheckStateLinter.errores.asScala) ((_:String) must not startWith error15("b")) and
        atLeastOnce (CheckStateLinter.errores.asScala) ((_:String) must be startWith error11("a")) and
          atLeastOnce (CheckStateLinter.errores.asScala) ((_:String) must be startWith error11("b")) and
            atLeastOnce (CheckStateLinter.errores.asScala) ((_:String) must be startWith error11("c")) and
              atLeastOnce (CheckStateLinter.errores.asScala) ((_:String) must be startWith error11("d"))
    }
  }

}

