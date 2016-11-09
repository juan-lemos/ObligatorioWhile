package examples.while_ut1.ast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class CheckStateLinter {
	public static ArrayList<String> errores = new ArrayList<String>();
	public Map<String,ObjectState> mapa = new HashMap<String,ObjectState>();
	
	public static void generateErrors(CheckStateLinter cslint) {
		for (Map.Entry<String, ObjectState> element : cslint.mapa.entrySet()) {
			ObjectState objState = element.getValue();
			if (!objState.used) {
				if (objState.isFunction()) {
					addError3(objState.getLine(), objState.getColumn());
				} else {
					addError4(objState.getLine(), objState.getColumn());
				}
			}
		}
	}

	@Override
	public String toString() {
		String resultado = "";
			
		for (String value : errores) {
			resultado = resultado + "\n" + value;
		}
		
		return resultado;
	}
	
	public static void addError1(int line, int column) {
		addError("1", "existe mas de un salto de linea consecutivo", line, column); 
	}
	
	public static void addError2(int line, int column) {
		addError("2", "No debe haber mas de un statement en la misma linea", line, column);
	}
	
	public static void addError3(int line, int column) {
		addError("3", "funcion declarada sin llamar", line, column); 
	}
	
	public static void addError4(int line, int column) {
		addError("4", "variable asignada sin usar", line, column); 
	}
	
	public static void addError5A(int line, int column) {
		addError("5A", "La condición no es necesaria", line, column);
	}

	public static void addError5B(int line, int column) {
		addError("5B", "El codigo interno no se ejecutará nunca", line, column);
	}
	
	public static void addError5C(int line, int column) {
		addError("5C", "El codigo del else no ejecutará nunca", line, column);
	}
	
	public static void addError5D(int line, int column) {
		addError("5D", "El codigo del then no ejecutará nunca", line, column);
	}
	
	public static void addError6(int line, int column) {
		addError("6", "las variables deben comenzar con minuscula y sin guiones bajos", line, column); 
	}
	
	public static void addError7(int line, int column) {
		addError("7", "los nombres de metodos deben comenzar con minuscula", line, column); 
	}
	
	public static void addError8(String variableId, int line, int column) {
		addError("8", "variable " + variableId + " no declarada", line, column);
	}
	
	public static void addError10A(int line, int column) {
		addError("10A", "cantidad de parametros de funcion incorrectos", line, column);
	}
	
	public static void addError10B(String expectedType, String parameterType, int line, int column) {
		addError("10B", "parametro de funcion de tipo incorrecto. Esperado: " + expectedType + ", actual: " + parameterType, line, column);
	}
	
	public static void addError13(String functionName, int line, int column) {
		addError("13", "la funcion " + functionName + " ya se encuentra definida", line, column); 
	}
	
	public static void addError14(String variableId, int line, int column) {
		addError("14", "la variable " + variableId + " ya se encuentra declarada", line, column);
	}
	
	public static void addError16(int line, int column) {
		addError("16", "existen parentesis superfluos", line, column);
	}
	
	public static void addError17(int line, int column) {
		addError("17", "existen llaves superfluas", line, column);
	}
	
	public static void addError18A(String functionName, String oldFunctionName, int line, int column) {
		addError("18B",  "la funcion " + functionName + " se encuentra definida como " + oldFunctionName, line, column);
	}
	
	public static void addError18B(String variableId, String oldVariableId, int line, int column) {
		addError("18B", "la variable " + variableId + " se encuentra definida como " + oldVariableId, line, column);
	}
	
	private static void addError(String code, String msg, int line, int column) {
		errores.add(createErrorMsg(code, msg, line, column));
	}
	
	private static String createErrorMsg(String code, String msg, int line, int column) {
		return "Error " + code + ": " + msg + "." + " Line: " + line + ", Column: " + column;
	}
}
