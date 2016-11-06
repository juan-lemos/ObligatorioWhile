# ObligatorioWhile

El obligatorio tiene como objetivo agregar funcionalidades al lenguaje While dictado en el curso. 

Integrantes: Juan Lemos, Mauricio Coniglio, Cynthia Duhalde, Fernando Torterolo

[NIELSON1999] (http://www.daimi.au.dk/~bra8130/Wiley_book/wiley.html)
H.R. Nielson & F. Nielson: "Semantic with Applications: A formal introduction". John Wiley & Sons, revised edition ISBN 0-471-92980-8.


## Reglas

| Numero | Descripción Regla                                                                                                                                     | Asignado | Des | Test |
|--------|-------------------------------------------------------------------------------------------------------------------------------------------------------|----------|-----|------|
| 1      | Que no exista más de un salto de línea entre dos líneas de código consecutivas.                                                                       | MC       | x   |      |
| 2      | Que haya solo un statement por línea                                                                                                                  |JP       | x   |      |
| 3      | Detectar funciones declaradas sin llamar                                                                                                              | MC       | x   |      |
| 4      | Detectar variables declaradas sin usar                                                                                                                | MC       | x   |      |
| 5      | Detectar código que no se va a ejecutar. ej: '' if (15>10) { /\*\*/ } else { /\* esto no se ejecuta nunca \*/ }''                                     | CD - JPL   | x   |  CD  |
| 6      | Los nombres de la variables deben comenzar con minúsculas y sin guiones bajos                                                                         | MC       | x   |      |
| 7      | Los nombres de los métodos deben comenzar con minúsculas                                                                                              | MC       | x   |      |
| 8      | Uso de variables no definidas                                                                                                                         | MC - JPL | x   |      |
| 9      | Chequear llamado a funciones que en el caso que se quiera asignar su retorno retornen algo y el tipo coincida.                                        |          |     |      |
| 10     | Chequear que la cantidad de variables que se le pasa a una función sea igual a la definición así como los tipos.                                      | MC       | x   |      |
| 11     | Detectar parámetros de funciones que no son utilizados.                                                                                               |          |     |      |
| 12     | Chequear que las funciones que deben devolver algo lo hagan y lo hagan en el tipo definido en la firma así como las que no devuelven nada no lo hagan |          |     |      |
| 13     | No se puede redefinir funciones                                                                                                                       | MC       | x   |      |
| 14     | No se puede redefinir variables                                                                                                                       | MC       | x   |      |
| 15     | Comprobar que el tipo de la variable y la expresión coincidan al momento de asignar                                                                   |          |     |      |
| 16     | No se puede tener paréntesis superfluos                                                                                                               |          |     |      |
| 17     | No se puede tener llaves superfluas                                                                                                                   |          |     |      |
| 18     | No escribir nombres de variables o métodos iguales pero que se diferencien en solamente en mayúsculas y minúsculas Ej: ''numeric a=23; numeric A=23'' | MC       | x   |      |
| 19     | No permitir redefinición de variables con el mismo nombre dentro de las funciones como variables locales                                              |          |     |      |
| 20     | Warning expresiones con más de 7 operadores.                                                                                                          |          |     |      |
| 21     | Warning sentencias con más de 5 anidaciones.                                                                                                          |          |     |      |
