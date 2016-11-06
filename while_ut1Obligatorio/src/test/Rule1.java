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
 * Regla numeo 1
 * Que no exista más de un salto de línea entre dos líneas de código consecutivas.
 */
public class Rule1 extends TestCase {

	Map<Integer, String> datosPruebas = new HashMap<Integer, String>();
	Logger logger = Logger.getAnonymousLogger();
	CheckStateLinter state;

	protected void setUp() throws Exception {
		state = new CheckStateLinter();
		loadData();
		super.setUp();
	}

	protected void loadData() {
		datosPruebas.put(1, "{y=2;\ny=3;}"); // ok
		datosPruebas.put(2, "{y=2;\n\ny=3;}");  // ok
		datosPruebas.put(3, "{y=2;\n\n\ny=3;}"); // ok, deberia dar un mensaje de error
		datosPruebas.put(4, "{y=2;\n\n\n\ny=3;}"); // ok, deberia dar un mensaje de error
	}

	public void testData() {
		try {
			Integer numTest =4;   // Setear este valor
			
			Object obj = Parse.parse(datosPruebas.get(numTest));
			logger.log(Level.INFO, obj.toString());
			
			CheckStateLinter check = ((Stmt) obj).checkLinter(state); 
			
			String actual = check.toString();
			String expected = "Error 1: existe mas de un salto de linea consecutivo.";
			
			if (numTest == 1 || numTest ==2)
				expected = "";
			else 
				expected = "Error 1: existe mas de un salto de linea consecutivo.";
				
			assertTrue("Se esperaba " + expected + "pero el resultado fue " + actual, actual.contains(expected));
			
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e.getCause());
		}
	}
	
	public void testAll() {
        datosPruebas.forEach((numTest,strTest) -> {
            logger.log(Level.INFO, "test" + numTest + " : " + strTest);
            try {
            	Object obj = Parse.parse(datosPruebas.get(numTest));
                logger.log(Level.INFO,obj.toString());
    			CheckStateLinter check = ((Stmt) obj).checkLinter(state); 
    			logger.log(Level.INFO, check.toString());
            }catch (Exception e){
                logger.log(Level.SEVERE, e.getMessage(), e.getCause());
            }
        });
	}

}