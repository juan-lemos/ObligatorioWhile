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
 * Regla nï¿½mero 20 - Warning expresiones con más de 7 operadores.
 */
public class Rule20 extends TestCase {

	Map<Integer, String> datosPruebas = new HashMap<Integer, String>();
	Logger logger = Logger.getAnonymousLogger();
	CheckStateLinter state;

	protected void setUp() throws Exception {
		state = new CheckStateLinter();
		loadData();
		super.setUp();
	}

	protected void loadData() {
		datosPruebas.put(1, "{int x=(((5-4)+(4-2))*((7*8)+(3-1)))+(2*5);}");//(ok)9 operadores, deberia reportarlo
		datosPruebas.put(2, "{int x=(5+6)+8;}"); //(ok) 2 operadores, esta bien
		datosPruebas.put(3, "{int x=((3-2)*(9*2)+(3+7)-(4/2))+1;}"); //(ok)8 operadores, deberia reportarlo 
		datosPruebas.put(4, "{int y=3+((5+5)*(6+4)-((5+2)+(2*2)));}");//(ok)8 operadores contando el =, deberia reportarlo
		datosPruebas.put(5, "{int x=4+2;int y=(1+7+8+4+9)*(10/2);}"); //La segunda asignacion tiene 7 operadores incluido el =, esta bien 
		datosPruebas.put(6, "{int x=((8-7+8+9+8+7)+(6/6))*2*2;}"); //9 operadores, deberia reportar
	}

	public void testData() {
		try {
			Integer numTest =6;   // Setear este valor
			
			Object obj = Parse.parse(datosPruebas.get(numTest));
			logger.log(Level.INFO, obj.toString());
			
			CheckStateLinter check = ((Stmt) obj).checkLinter(state); 
			CheckStateLinter.generateErrors(check);
			
			String actual = check.toString();
			logger.log(Level.INFO, actual); //borrar
			String expected = "";
			
			if (numTest == 2 || numTest ==5)
				expected = "";
			else{ 
				if(numTest ==1||numTest ==6){					
					expected = "Offense detected - 20: Existe una expresion con 9 operadores";
				}
				if(numTest ==3||numTest ==4){					
					expected = "Offense detected - 20: Existe una expresion con 8 operadores";
				}
			}
			assertTrue("Se esperaba " + expected + "pero el resultado fue " + actual, actual.contains(expected));
			
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e.getCause());
		}
	}

}