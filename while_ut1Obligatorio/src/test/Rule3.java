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
 * Regla n�mero 3 - Detectar funciones declaradas sin llamar
 */
public class Rule3 extends TestCase {

	Map<Integer, String> datosPruebas = new HashMap<Integer, String>();
	Logger logger = Logger.getAnonymousLogger();
	CheckStateLinter state;

	protected void setUp() throws Exception {
		state = new CheckStateLinter();
		loadData();
		super.setUp();
	}

	protected void loadData() {
		datosPruebas.put(1,"function Void Hola(int x){}");
		datosPruebas.put(2,"{function Void Hola(int x){} function int jj(){Hola(3);}}");

	}

	public void testData() {
		try {
			Integer numTest =2;   // Setear este valor

			Object obj = Parse.parse(datosPruebas.get(numTest));
						logger.log(Level.INFO, obj.toString());

			CheckStateLinter check = ((Stmt) obj).checkLinter(state); 
			CheckStateLinter.generateErrors(check);
			
			String actual = check.toString();
//			logger.log(Level.INFO, actual); //borrar
			String expected = "";
			
			switch (numTest){
				case 1: expected = "3: Funcion jj declarada sin llamar. Line: 0, Column: 29";break;
				case 2: expected = "3: Funcion Hola declarada sin llamar. Line: 0, Column: 0";break;
				
			}


			assertTrue("Se esperaba " + expected + "pero el resultado fue " + actual, actual.contains(expected));

		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e.getCause());
		}
	}

}