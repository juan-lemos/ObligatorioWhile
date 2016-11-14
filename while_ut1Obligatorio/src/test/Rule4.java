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
 * Regla número 4
 * Detectar variables declaradas sin usar
 */
public class Rule4 extends TestCase {

	Map<Integer, String> datosPruebas = new HashMap<Integer, String>();
	Logger logger = Logger.getAnonymousLogger();
	CheckStateLinter state;

	protected void setUp() throws Exception {
		state = new CheckStateLinter();
		loadData1();
		super.setUp();
	}

	protected void loadData1() {
		datosPruebas.put(1, "{y=2;\ny=3;}"); // ok
		datosPruebas.put(2, "{y=2;\n\ny=3;}");  // MAL
		datosPruebas.put(3, "{y=2;\n\n\ny=3;}"); // ok
		datosPruebas.put(4, "{y=2;\n\n\n\ny=3;}"); // ok
	}

	public void testData1() {
		try {
			Integer numTest =4;
//			CheckStateLinter.addError("dsdf", "sdf", 1, 1);
			Object obj = Parse.parse(datosPruebas.get(numTest));
			logger.log(Level.INFO, obj.toString());
			if (obj instanceof Stmt) {
				CheckStateLinter check = ((Stmt) obj).checkLinter(state); 
				logger.log(Level.INFO, check.toString());
			} else {
				logger.log(Level.WARNING,"No es instacia de Stmt");
			}
			
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e.getCause());
		}
	}

}
