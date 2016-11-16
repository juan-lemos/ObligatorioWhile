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
public class Rule18 extends TestCase {

	Map<Integer, String> datosPruebas = new HashMap<Integer, String>();
	Logger logger = Logger.getAnonymousLogger();
	CheckStateLinter state;

	protected void setUp() throws Exception {
		state = new CheckStateLinter();
		loadData();
		super.setUp();
	}

	protected void loadData() {
		datosPruebas.put(1, "{int c=10; int C=11;}");//ok
		datosPruebas.put(2, "{while(15<=10)do{ int yYY=2;\n int yyy=3; }}"); //ok
		datosPruebas.put(3, "{while(15<=10)do{int y=2;\n int x=3; }}"); //ok 
		datosPruebas.put(4, "{function Void Hola(int x){} \n function Void hola(int x){}}"); //

		
	}

	public void testData() {
		try {
			Integer numTest =5;   // Setear este valor
			
			Object obj = Parse.parse(datosPruebas.get(numTest));
			logger.log(Level.INFO, obj.toString());
			
			CheckStateLinter check = ((Stmt) obj).checkLinter(state);
			CheckStateLinter.generateErrors(check);
			
			String actual = check.toString();
			
			
//			System.out.println(actual);
//			logger.log(Level.INFO, actual); //borrar
			String expected = "";
			
 
				if(numTest ==1)
					expected = "Offense detected - 18B: La variable C se encuentra definida como c.";
				
				if(numTest ==2){
					expected = "Offense detected - 18B: La variable yyy se encuentra definida como yYY.";
				}
				if(numTest ==4){
					expected = "Offense detected - 18A: La funcion hola se encuentra definida como Hola.";
				}
			
				
			
			assertTrue("Se esperaba " + expected + "pero el resultado fue " + actual, actual.contains(expected));
			
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e.getCause());
		}
	}

}