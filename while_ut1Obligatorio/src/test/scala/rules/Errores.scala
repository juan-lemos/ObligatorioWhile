package rules

import examples.while_ut1.ast.CheckStateLinter
import scala.collection.JavaConverters._

object Errores {

  val error01 = "Offense detected - 1: Existe mas de un salto de linea consecutivo"
  val error02 = "Offense detected - 2: No debe haber mas de un statement en la misma linea."
  def error3(v:String) = s"Offense detected - 3: Funcion $v declarada sin llamar."
  val error06 = "Offense detected - 6: Las variables deben comenzar con minuscula y sin guiones bajos"
  val error07 = "Offense detected - 7: Los nombres de metodos deben comenzar con minuscula."
  def error08 (v:String) = s"Offense detected - 8: Variable $v no declarada."
  val error9A = "Offense detected - 9A: La funcion no devuelve valor alguno"
  val error9B = "Offense detected - 9B: La funcion no devuelve el tipo esperado."
  def error10B(esperado:String, actual:String) = s"Offense detected - 10B: Parametro de funcion de tipo incorrecto. Esperado: $esperado, actual: $actual."
  def error11(v:String) = s"Offense detected - 11: Parametro $v sin usar."
  def error13(str:String) = s"Offense detected - 13: La funcion $str ya se encuentra definida."
  val error1419 = "Offense detected - 14-19: La variable a ya se encuentra declarada."
  def error15(v:String) = s"Offense detected - 15: El tipo de la variable $v y la expresion no coinciden."
  val error17 = "Offense detected - 17: Existen llaves superfluas."
  def error20(v:String) = s"Offense detected - 20: Existe una expresion con $v operadores."


  def getAll() = CheckStateLinter.errores.asScala.toList.mkString("\n")
}
