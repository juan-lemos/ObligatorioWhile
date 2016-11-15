package rules

object Errores {

  val error01 = "Offense detected - 1: Existe mas de un salto de linea consecutivo"
  val error02 = "Offense detected - 2: No debe haber mas de un statement en la misma linea."
  val error3 = "Offense detected - 3: Funcion declarada sin llamar."
  val error06 = "Offense detected - 6: Las variables deben comenzar con minuscula y sin guiones bajos"
  val error07 = "Offense detected - 7: Los nombres de metodos deben comenzar con minuscula."
  val error08 = "Offense detected - 8: Variable d no declarada."
  val error11 = "Offense detected - 11: Parametro sin usar."
  def error13(str:String):String = s"Offense detected - 13: La funcion $str ya se encuentra definida."
  val error1419 = "Offense detected - 14-19: La variable a ya se encuentra declarada."
  val error15 = "Offense detected - 15: El tipo de la variable b y la expresion no coinciden." // TODO sacar de aca
  val error17 = "Offense detected - 17: Existen llaves superfluas."
}
