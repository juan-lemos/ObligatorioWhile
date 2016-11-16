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
 * Regla numero 21 - Warning sentencias con más de 5 anidaciones. 
 */
public class Rule21 extends TestCase {

    Map<Integer, String> datosPruebas = new HashMap<Integer, String>();
    Logger logger = Logger.getAnonymousLogger();
    CheckStateLinter state;

    protected void setUp() throws Exception {
        state = new CheckStateLinter();
        loadData();
        super.setUp();
    }

    protected void loadData() {
        datosPruebas.put(1, "{if(1<=2)then{if(2<=3)then{if(3<=4)then{if(4<=5)then{if(5<=6)then{if(6<=7)then{int c=10;} else{int z=16;}} else{int z=15;}} else{int z=14;}} else{int z=13;}} else{int z=12;}} else{int z=11;}}");//hay 6 anidaciones a traves del then, deberia reportarlo
        datosPruebas.put(2, "{if(2<=1)then{int c=10;} else{if(3<=2)then{int c=9;} else{if(4<=3)then{int c=8;} else{if(5<=4)then{int c=7;} else{if(6<=5)then{int c=6;} else{if(7<=6)then{int c=5;} else{int z=11;}}}}}}}"); // hay 6 anidaciones a traves del else, deberia reportarlo
        datosPruebas.put(3, "{while(1<=2)do{ if(1<=2)then{if(2<=3)then{if(3<=4)then{if(4<=5)then{if(5<=6)then{if(6<=7)then{int c=10;} else{int z=16;}} else{int z=15;}} else{int z=14;}} else{int z=13;}} else{int z=12;}} else{int z=11;} x=3;}}"); // hay 7 anidaciones desde el while y a traves del if then, deberia reportarlo 
        datosPruebas.put(4, "{if(1<=2)then{if(2<=3)then{if(3<=4)then{if(5<=6)then{if(6<=7)then{int c=10;} else{int z=15;}} else{int z=14;}} else{int z=13;}} else{int z=12;}} else{int z=11;}}"); //Hay 5 anidaciones a traves del then, no deberia reportarlo, esta justo
        datosPruebas.put(5, "{int z=4;int y=10;if(1<=2)then{if(2<=3)then{if(3<=4)then{if(5<=6)then{if(6<=7)then{if(7<=8)then{int c=10;} else{int z=9;}} else{int z=15;}} else{int z=14;}} else{int z=13;}} else{int z=12;}} else{int z=11;}}"); //es una secuencia de stmt donde la ultima tien 6 anidaciones a traves del if then, deberia reportarlo 
        datosPruebas.put(6, "{while(5<=10)do{ while(2<=3)do{ y=2;\n x=3;}}}"); //2 anidaciones, no deberia reportarlo
    }

    public void testData() {
        try {
            Integer numTest =3;   // Setear este valor
            
            Object obj = Parse.parse(datosPruebas.get(numTest));
            logger.log(Level.INFO, obj.toString());
            
            CheckStateLinter check = ((Stmt) obj).checkLinter(state); 
            CheckStateLinter.generateErrors(check);
            
            String actual = check.toString();
            logger.log(Level.INFO, actual); //borrar
            String expected = "";
            
            if (numTest == 4 || numTest ==6)
                expected = "";
            else{ 
                if(numTest ==1 || numTest ==2 || numTest ==5){                    
                    expected = "Offense detected - 21: Existe un statement con 6 niveles de anidacion";
                }
                if(numTest ==3){                    
                    expected = "Offense detected - 21: Existe un statement con 7 niveles de anidacion";
                }
            }
            assertTrue("Se esperaba " + expected + "pero el resultado fue " + actual, actual.contains(expected));
            
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e.getCause());
        }
    }

}