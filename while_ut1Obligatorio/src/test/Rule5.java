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
 * Regla n�mero 5 Detectar c�digo que no se va a ejecutar. ej: '' if (15>10) {
 * -- } else { // esto no se ejecuta nunca }
 */
public class Rule5 extends TestCase {

	Map<Integer, String> datosPruebas = new HashMap<Integer, String>();
	Logger logger = Logger.getAnonymousLogger();
	CheckStateLinter state;

	protected void setUp() throws Exception {
		state = new CheckStateLinter();
		loadData();
		super.setUp();
	}

	protected void loadData() {
		datosPruebas.put(1, "{int y=2;int x=3;if(x<=y)then{int z=10;} else {int z=11;}}");
		// if "+ condition.unparse() +" then { "+ thenBody.unparse() +" } else
		// { "+ elseBody.unparse() +" }";
		datosPruebas.put(2, "{if(15<=10)then{y=2;}else{x=3;}}"); //
		datosPruebas.put(3, "{while(15<=10)do{ y=2;\n x=3; }}"); //
		datosPruebas.put(4, "{while(true)do{ y=2;\n x=3; }}"); //
		datosPruebas.put(5, "{while(15<=10)do{ y=2;\nx=3; }}"); //
		datosPruebas.put(6, "{y=2; x=3; }"); //
	}

	public void testData() {
		try {
			Integer numTest =1;   // Setear este valor
			
			Object obj = Parse.parse(datosPruebas.get(numTest));
			logger.log(Level.INFO, obj.toString());
			
			CheckStateLinter check = ((Stmt) obj).checkLinter(state); 
			//logger.log(Level.INFO, "Despues");
			
			String actual = check.toString();
			//logger.log(Level.INFO, actual); //borrar
			String expected = "";
			
			if (numTest == 1 || numTest ==2)
				expected = "";
			else 
				expected = "Error 5: El codigo interno no se ejecutar� nunca.";
				
			assertTrue("Se esperaba " + expected + "pero el resultado fue " + actual, actual.contains(expected));
			
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e.getCause());
		}
	}

}
