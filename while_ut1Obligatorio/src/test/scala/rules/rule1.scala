package rules

import examples.while_ut1.Parser
import examples.while_ut1.ast.{CheckStateLinter, Stmt}
import org.specs2.mutable._
import scala.collection.JavaConversions._

/**
  * Regla número 1
  * Que no exista más de un salto de línea entre dos líneas de código consecutivas.
  */
class rule1 extends Specification {

   val state = new CheckStateLinter()
   val case1 = "{y=2;\ny=3;}"
   val case2 = "{y=2;\n\ny=3;}"
   val case3 = "{y=2;\n\n\ny=3;}"
   val case4 = "{y=2;\n\n\n\ny=3;}"

  val error = "Offense detected - 1: existe mas de un salto de linea consecutivo."

    s"The '$case1' string" should {
      s"not contain message $error" in {
        CheckStateLinter.errores.clear()
        Parser.parse(case1).value.asInstanceOf[Stmt].checkLinter(state)
        error must not be CheckStateLinter.errores
      }
    }

    s"The '$case2' string" should {
      s"not contain message $error" in {
        CheckStateLinter.errores.clear()
        Parser.parse(case2).value.asInstanceOf[Stmt].checkLinter(state)
        error must not be CheckStateLinter.errores
      }
    }

    s"The '$case3' string" should {
      s"contain message $error" in {
        CheckStateLinter.errores.clear()
        Parser.parse(case3).value.asInstanceOf[Stmt].checkLinter(state)
        CheckStateLinter.errores.toIndexedSeq must contain(be_>=(error)).atLeastOnce
        }
    }

    s"The '$case4' string" should {
      s"contain message $error" in {
        CheckStateLinter.errores.clear()
        Parser.parse(case4).value.asInstanceOf[Stmt].checkLinter(state)
        CheckStateLinter.errores.toIndexedSeq must contain(be_>=(error)).atLeastOnce
      }
    }
  }

