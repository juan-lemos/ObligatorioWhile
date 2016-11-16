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
 * Regla n�mero 10 - Chequear que la cantidad de variables que se le pasa a una función sea igual a la definición así como los tipos.
 */
public class Rule10 extends TestCase {

	Map<Integer, String> datosPruebas = new HashMap<Integer, String>();
	Logger logger = Logger.getAnonymousLogger();
	CheckStateLinter state;

	protected void setUp() throws Exception {
		state = new CheckStateLinter();
		loadData();
		super.setUp();
	}

	protected void loadData() {
		datosPruebas.put(1,"{function int fun(int a ,int b){} fun(3,2.3);}");
		datosPruebas.put(2,"{function int fun(int a ,int b){} fun(3);}");
		datosPruebas.put(3,"{function int fun(int a ,int b){} fun();}");

		datosPruebas.put(4,"{function int fun(int a ,int b){} int a=fun(3,2.3);}");
		datosPruebas.put(5,"{function int fun(int a ,int b){} int a=fun(3);}");
		datosPruebas.put(6,"{function int fun(int a ,int b){} int a=fun();}");


	}

	public void testData() {
		try {
			int numTest =6;   // Setear este valor

			Object obj = Parse.parse(datosPruebas.get(numTest));
			logger.log(Level.INFO, obj.toString());

			CheckStateLinter check = ((Stmt) obj).checkLinter(state); 
			CheckStateLinter.generateErrors(check);

			String actual = check.toString();
//			logger.log(Level.INFO, actual); //borrar
			String expected = "";

			switch (numTest){
				case 1: expected = "10B: Parametro de funcion de tipo incorrecto. Esperado: int, actual: num. Line: 0, Column: 34";break;
				case 2: expected = "10A: Cantidad de parametros de funcion incorrectos. Line: 0, Column: 34";break;
				case 3: expected = "10A: Cantidad de parametros de funcion incorrectos. Line: 0, Column: 34";break;
				case 4: expected = "10B: Parametro de funcion de tipo incorrecto. Esperado: int, actual: num. Line: 0, Column: 40";break;
				case 5: expected = "10A: Cantidad de parametros de funcion incorrectos. Line: 0, Column: 40";break;
				case 6: expected = "10A: Cantidad de parametros de funcion incorrectos. Line: 0, Column: 40";break;
			}

			
			assertTrue("Se esperaba " + expected + "pero el resultado fue " + actual, actual.contains(expected));

		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e.getCause());
		}
	}

}