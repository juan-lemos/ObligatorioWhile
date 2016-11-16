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
 * Regla n�mero 18 - No escribir nombres de variables o métodos iguales pero que se diferencien en solamente en mayúsculas y minúsculas Ej: ''numeric a=23; numeric A=23''
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
		datosPruebas.put(1, "{function Void hola(int x){int x=10;int x=20;} function Void hola(int x){}}");//ok
		//datosPruebas.put(2, "{while(15<=10)do{ int yYY=2;\n int yyy=3; }}"); //ok

		
	}

	public void testData() {
		try {
			Integer numTest =1;   // Setear este valor
			
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
					expected = "Offense detected - 14-19: La variable c ya se encuentra declarada.";
				}
				
			
			
			assertTrue("Se esperaba " + expected + "pero el resultado fue " + actual, actual.contains(expected));
			
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e.getCause());
		}
	}

}