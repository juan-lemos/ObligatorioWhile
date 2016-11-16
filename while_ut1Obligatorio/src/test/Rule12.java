package test;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import examples.while_ut1.ast.CheckStateLinter;
//import examples.while_ut1.Parser;
import examples.while_ut1.ast.Stmt;
import junit.framework.TestCase;

/**
 * Regla n�mero 12 - Chequear que las funciones que deben devolver algo lo hagan y lo hagan en el tipo definido en la firma así como las que no devuelven nada no lo hagan
 */
public class Rule12 extends TestCase {

	Map<Integer, String> datosPruebas = new HashMap<Integer, String>();
	Logger logger = Logger.getAnonymousLogger();
	CheckStateLinter state;

	protected void setUp() throws Exception {
		state = new CheckStateLinter();
		loadData();
		super.setUp();
	}

	protected void loadData() {
		//12A
		datosPruebas.put(1,"function Void a(){ return 3;}");
		datosPruebas.put(2,"function Void a(){ int j=3; if (j==4)then{return 2;}}");
		datosPruebas.put(3,"function Void a(){ int j=3; if (j==4)then{while (j==3)do{return 2;}}}");

		//12B
		datosPruebas.put(4,"function num a(){ return 3;});");
		datosPruebas.put(5,"function str a(){ int j=3; if (j==4)then{return 2;}}");
		datosPruebas.put(6,"function bool a(){ int j=3; if (j==4)then{while (j==3)do{return 2;}}}");

		//12c
		datosPruebas.put(7,"function num a(){}");
		datosPruebas.put(8,"{int a=3;function num a(){if (a==2)then{return 3.3;} else{}}}");
		
//		datosPruebas.put(9,"function int a(){ return 3;}");
//		datosPruebas.put(10, "{int a=3;function num a(){if (a==2)then{return 3.3;} else{return 2.2;}}}");


	}

	public void testData() {
		try {
			int numTest =2;   // Setear este valor

			Object obj = Parse.parse(datosPruebas.get(numTest));
			logger.log(Level.INFO, obj.toString());

			CheckStateLinter check = ((Stmt) obj).checkLinter(state); 
			CheckStateLinter.generateErrors(check);

			String actual = check.toString();
			//logger.log(Level.INFO, actual); //borrar
			String expected = "";

			switch (numTest){
				case 1: expected = "Offense detected - 12A: La funcion a no devuelve nada segun su definicion. Line: 0, Column: 19";break;
				case 2: expected = "Offense detected - 12A: La funcion a no devuelve nada segun su definicion. Line: 0, Column: 42";break;
				case 3: expected = "Offense detected - 12A: La funcion a no devuelve nada segun su definicion. Line: 0, Column: 57";break;
				case 4: expected = "12B: El tipo de la expresion del return no coincide con el la funcion a. Line: 0, Column: 18";break;
				case 5: expected = "Offense detected - 12B: El tipo de la expresion del return no coincide con el la funcion a. Line: 0, Column: 41";break;
				case 6: expected = "Offense detected - 12B: El tipo de la expresion del return no coincide con el la funcion a. Line: 0, Column: 57";break;
				case 7: expected = "Offense detected - 12C: Falta return en funcion a. Line: 0, Column: 0";break;
				case 8: expected = "Offense detected - 12C: Falta return en funcion a. Line: 0, Column: 9";break;
			}

			
			assertTrue("Se esperaba " + expected + "pero el resultado fue " + actual, actual.contains(expected));

		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e.getCause());
		}
	}

}