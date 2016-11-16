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
 * Regla n�mero 9 - Chequear llamado a funciones que en el caso que se quiera asignar su retorno retornen algo y el tipo coincida.
 */
public class Rule9 extends TestCase {

	Map<Integer, String> datosPruebas = new HashMap<Integer, String>();
	Logger logger = Logger.getAnonymousLogger();
	CheckStateLinter state;

	protected void setUp() throws Exception {
		state = new CheckStateLinter();
		loadData();
		super.setUp();
	}

	protected void loadData() {
		datosPruebas.put(1,"{function Void a(int ds){} int se=a(3);}");
		datosPruebas.put(2,"{function num a(int ds){} int se=a(3);}");
		datosPruebas.put(3,"str se=a(3);");
		datosPruebas.put(4,"{function str a(){return \"hol\";} num se=a(3)+3.3;}");

	}

	public void testData() {
		try {
			Integer numTest =4;   // Setear este valor

			Object obj = Parse.parse(datosPruebas.get(numTest));
						logger.log(Level.INFO, obj.toString());

			CheckStateLinter check = ((Stmt) obj).checkLinter(state); 
			CheckStateLinter.generateErrors(check);
			
			String actual = check.toString();
//			logger.log(Level.INFO, actual); //borrar
			String expected = "";
			
			switch (numTest){
				case 1: expected = "9B: La funcion no devuelve el tipo esperado. Line: 0, Column: 33";break;
				case 2: expected = "9A: La funcion no devuelve valor alguno. Line: 0, Column: 34";break;
				case 3: expected = "9: Funcion no definida. Line: 0, Column: 7";break;
				case 4: expected = "9B: La funcion no devuelve el tipo esperado. Line: 0, Column: 40";break;
			}
			System.out.println(actual);

			assertTrue("Se esperaba " + expected + "pero el resultado fue " + actual, actual.contains(expected));

		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e.getCause());
		}
	}

}