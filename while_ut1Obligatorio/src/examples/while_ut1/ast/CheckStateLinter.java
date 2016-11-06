package examples.while_ut1.ast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class CheckStateLinter {
	public static ArrayList<String> errores = new ArrayList<String>();
	public Map<String,ObjectState> mapa = new HashMap<String,ObjectState>();
	
	public static void addError(String code, String msg, int line, int column) {
		errores.add(createErrorMsg(code, msg, line, column));
	}
	
	public static String createErrorMsg(String code, String msg, int line, int column) {
		return "Error " + code + ": " + msg + "." + " Line: " + line + ", Column: " + column;
	}
	
	public static void generateErrors(CheckStateLinter cslint) {
		for (Map.Entry<String, ObjectState> element : cslint.mapa.entrySet()) {
			ObjectState objState = element.getValue();
			if (!objState.used) {
				if (objState.isFunction()) {
					addError("3", "funcion declarada sin llamar", objState.getLine(), objState.getColumn());
				} else {
					addError("4", "variable asignada sin usar", objState.getLine(), objState.getColumn());
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
	
	public static void addError8(String variableId, int line, int column) {
		addError("8", "variable " + variableId + " no declarada", line, column);
	}
}
