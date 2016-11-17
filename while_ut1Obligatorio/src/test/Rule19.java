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
 * Regla nï¿½mero 19 - No permitir redefinición de variables con el mismo nombre dentro de las funciones como variables locales
 */
public class Rule19 extends TestCase {

	Map<Integer, String> datosPruebas = new HashMap<Integer, String>();
	Logger logger = Logger.getAnonymousLogger();
	CheckStateLinter state;

	protected void setUp() throws Exception {
		state = new CheckStateLinter();
		loadData();
		super.setUp();
	}

	protected void loadData() {
		datosPruebas.put(1, "{int j; function int a(){int j=3;} j=2;}");//ok
		datosPruebas.put(2, "{int x; function int a(){int j=3;int x=35;} x=2;}"); //ok

		
	}

	public void testData() {
		try {
			Integer numTest =2;   // Setear este valor
			
			Object obj = Parse.parse(datosPruebas.get(numTest));
			logger.log(Level.INFO, obj.toString());
			
			CheckStateLinter check = ((Stmt) obj).checkLinter(state); 
			CheckStateLinter.generateErrors(check);
			
			String actual = check.toString();
			logger.log(Level.INFO, actual); //borrar
			String expected = "";
			
			if (numTest==5||numTest==3)
				expected = "";
			else{ 
				if(numTest ==1)
					expected = "14-19: La variable j ya se encuentra declarada. Line: 0, Column: 25";
				if(numTest ==2)
					expected = "14-19: La variable x ya se encuentra declarada. ";
				
			}
			
			
			
			
			assertTrue("Se esperaba " + expected + "pero el resultado fue " + actual, actual.contains(expected));
			
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e.getCause());
		}
	}

}