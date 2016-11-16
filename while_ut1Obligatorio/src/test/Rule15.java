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
 * Regla n�mero 15 - Comprobar que el tipo de la variable y la expresión coincidan al momento de asignar
 */
public class Rule15 extends TestCase {

	Map<Integer, String> datosPruebas = new HashMap<Integer, String>();
	Logger logger = Logger.getAnonymousLogger();
	CheckStateLinter state;

	protected void setUp() throws Exception {
		state = new CheckStateLinter();
		loadData();
		super.setUp();
	}

	protected void loadData() {
		
		datosPruebas.put(1,"int a=\"hola\";");
		datosPruebas.put(2,"{str a=3; int b=true;}");
		datosPruebas.put(3,"num a=3/3;");

		datosPruebas.put(4,"int a=3.3;");
		datosPruebas.put(5,"bool a=1;");
		


	}

	public void testData() {
		try {
			int numTest =5;   // Setear este valor

			Object obj = Parse.parse(datosPruebas.get(numTest));
						logger.log(Level.INFO, obj.toString());

			CheckStateLinter check = ((Stmt) obj).checkLinter(state); 
			CheckStateLinter.generateErrors(check);
			
			String actual = check.toString();
			logger.log(Level.INFO, actual); //borrar
			String expected = "";
			
			switch (numTest){
				case 1: expected = "Offense detected - 15: El tipo de la variable a y la expresion no coinciden. Line: 0, Column: 0"; break;
				case 2: expected = "Offense detected - 15: El tipo de la variable b y la expresion no coinciden. Line: 0, Column: 10";break;
				case 3: expected = "Offense detected - 15: El tipo de la variable a y la expresion no coinciden. Line: 0, Column: 0";break;
				case 4: expected = "Offense detected - 15: El tipo de la variable a y la expresion no coinciden. Line: 0, Column: 0";break;
				case 5: expected = "Offense detected - 15: El tipo de la variable a y la expresion no coinciden. Line: 0, Column: 0";break;
			}
			
			assertTrue("Se esperaba " + expected + "pero el resultado fue " + actual, actual.contains(expected));

		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e.getCause());
		}
	}

}