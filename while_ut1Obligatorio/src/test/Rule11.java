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
public class Rule11 extends TestCase {

	Map<Integer, String> datosPruebas = new HashMap<Integer, String>();
	Logger logger = Logger.getAnonymousLogger();
	CheckStateLinter state;

	protected void setUp() throws Exception {
		state = new CheckStateLinter();
		loadData();
		super.setUp();
	}

	protected void loadData() {
		datosPruebas.put(1,"function int fun(int a ,int b){}");
		datosPruebas.put(2,"function int fun(int a ,int b){}");
		datosPruebas.put(3,"function int fun(int a ,int b){int h=a;}");

	}

	public void testData() {
		try {
			int numTest =3;   // Setear este valor

			Object obj = Parse.parse(datosPruebas.get(numTest));
			logger.log(Level.INFO, obj.toString());

			CheckStateLinter check = ((Stmt) obj).checkLinter(state); 
			CheckStateLinter.generateErrors(check);

			String actual = check.toString();
			//logger.log(Level.INFO, actual); //borrar
			String expected = "";

			switch (numTest){
				case 1: expected = "11: Parametro a sin usar. Line: 0, Column: 0";break;
				case 2: expected = "11: Parametro b sin usar. Line: 0, Column: 0";break;
				case 3: expected = "11: Parametro b sin usar. Line: 0, Column: 0";break;
			}

			
			assertTrue("Se esperaba " + expected + "pero el resultado fue " + actual, actual.contains(expected));

		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e.getCause());
		}
	}

}