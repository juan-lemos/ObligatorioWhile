# ObligatorioWhile

El obligatorio tiene como objetivo agregar funcionalidades al lenguaje While dictado en el curso. 

Integrantes: Juan Lemos, Mauricio Coniglio, Cynthia Duhalde, Fernando Torterolo, Sebastian Caredio

[NIELSON1999] (http://www.daimi.au.dk/~bra8130/Wiley_book/wiley.html)
H.R. Nielson & F. Nielson: "Semantic with Applications: A formal introduction". John Wiley & Sons, revised edition ISBN 0-471-92980-8.


## Reglas

| Numero | Descripción Regla | Asignado | Des | Test |
|--------|-------------------------------------------------------------------------------------------------------------------------------------------------------|----------|-----|-------|
| 1 | Que no exista más de un salto de línea entre dos líneas de código consecutivas. | MC | x | x |
| 2 | Que haya solo un statement por línea | JP | x | x |
| 3 | Detectar funciones declaradas sin llamar | MC | x |  |
| 4 | Detectar variables declaradas sin usar | MC | x |  x|
| 5 | Detectar código que no se va a ejecutar. ej: '' if (15>10) { /\*\*/ } else { /\* esto no se ejecuta nunca \*/ }'' | CD - JPL | x | CD |
| 6 | Los nombres de la variables deben comenzar con minúsculas y sin guiones bajos | MC | x | x |
| 7 | Los nombres de los métodos deben comenzar con minúsculas | MC | x | x |
| 8 | Uso de variables no definidas | MC - JPL | x | x |
| 9 | Chequear llamado a funciones que en el caso que se quiera asignar su retorno retornen algo y el tipo coincida. | JP | X |  |
| 10 | Chequear que la cantidad de variables que se le pasa a una función sea igual a la definición así como los tipos. | MC | x |  |
| 11 | Detectar parámetros de funciones que no son utilizados. | MC | x |  |
| 12 | Chequear que las funciones que deben devolver algo lo hagan y lo hagan en el tipo definido en la firma así como las que no devuelven nada no lo hagan | JP | X | JP |
| 13 | No se puede redefinir funciones | MC | x | x |
| 14 | No se puede redefinir variables | MC | x | x |
| 15 | Comprobar que el tipo de la variable y la expresión coincidan al momento de asignar | JP | x |  |
| 16 | No se puede tener paréntesis superfluos | JP - MC | x | CD-SC |
| 17 | No se puede tener llaves superfluas | JP - MC | x | CD-SC |
| 18 | No escribir nombres de variables o métodos iguales pero que se diferencien en solamente en mayúsculas y minúsculas Ej: ''numeric a=23; numeric A=23'' | MC | x | CD-SC |
| 19 | No permitir redefinición de variables con el mismo nombre dentro de las funciones como variables locales | MC | x | x |
| 20 | Warning expresiones con más de 7 operadores. | MC | x | CD-SC |
| 21 | Warning sentencias con más de 5 anidaciones. | MC | x |  |
