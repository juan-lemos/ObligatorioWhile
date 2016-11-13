
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
				}
				else if(objState.isVariable()){
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
		addError("4", "variable definida sin usar", line, column); 
	}

	public static void addError5A(int line, int column) {
		addError("5A", "La condici�n no es necesaria", line, column);
	}

	public static void addError5B(int line, int column) {
		addError("5B", "El codigo interno no se ejecutar� nunca", line, column);
	}

	public static void addError5C(int line, int column) {
		addError("5C", "El codigo del else no ejecutar� nunca", line, column);
	}

	public static void addError5D(int line, int column) {
		addError("5D", "El codigo del then no ejecutar� nunca", line, column);
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

	public static void addError9(int line, int column){
		CheckStateLinter.addError("9", "Función no definida", line, column);
	}

	public static void addError9A(int line, int column){
		CheckStateLinter.addError("9A", "La funcion no devuelve valor alguno", line,column);
	}

	public static void addError9B(int line, int column){
		CheckStateLinter.addError("9B", "La funcion no devuelve el tipo esperado", line,column);
	}

	public static void addError10A(int line, int column) {
		addError("10A", "cantidad de parametros de funcion incorrectos", line, column);
	}

	public static void addError10B(String expectedType, String parameterType, int line, int column) {
		addError("10B", "parametro de funcion de tipo incorrecto. Esperado: " + expectedType + ", actual: " + parameterType, line, column);
	}

	public static void addError11(int line, int column) {
		addError("11", "parametro sin usar", line, column); 
	}

	public static void addError12A(String functionName, int line, int column) {
		addError("12A", "la funcion " + functionName + " no devuelve nada segun su definicion", line, column); 
	}

	public static void addError12B(String functionName, int line, int column) {
		addError("12B", "El tipo de la expresion del return no coincide con el la funci�n " + functionName, line, column); 
	}

	public static void addError13(String functionName, int line, int column) {
		addError("13", "la funcion " + functionName + " ya se encuentra definida", line, column); 
	}

	public static void addError14_19(String variableId, int line, int column) {
		addError("14-19", "la variable " + variableId + " ya se encuentra declarada", line, column);
	}

	public static void addError15(int line, int column, String idVariable) {
		addError("15", "El tipo de la variable "+idVariable+" y la expresión no coinciden", line, column);
	}

	public static void addError16(int line, int column) {
		addError("16", "existen parentesis superfluos", line, column);
	}

	public static void addError17(int line, int column) {
		addError("17", "existen llaves superfluas", line, column);
	}

	public static void addError18A(String functionName, String oldFunctionName, int line, int column) {
		addError("18A",  "la funcion " + functionName + " se encuentra definida como " + oldFunctionName, line, column);
	}

	public static void addError18B(String variableId, String oldVariableId, int line, int column) {
		addError("18B", "la variable " + variableId + " se encuentra definida como " + oldVariableId, line, column);
	}

	public static void addError20(int operators, int line, int column) {
		addError("20", "existe una expresion con " + operators + " operadores", line, column);
	}

	public static void addError21(int nestingLevels, int line, int column) {
		addError("21", "existe un statement con " + nestingLevels + " niveles de anidacion", line, column);
	}

	private static void addError(String code, String msg, int line, int column) {
		errores.add(createErrorMsg(code, msg, line, column));
	}

	private static String createErrorMsg(String code, String msg, int line, int column) {
		return "Offense detected - " + code + ": " + msg + "." + " Line: " + line + ", Column: " + column;
	}

	public static void evaluarRegla9(Exp expression,CheckStateLinter s, ArrayList<String> tiposAceptados){
		if (expression!=null && expression instanceof FunctionCall && s.mapa.containsKey(((FunctionCall) expression).id)){
			String functionId=((FunctionCall) expression).id;
			if ( s.mapa.get(functionId).tipo.equals("Void")){
				CheckStateLinter.addError9A(((FunctionCall)expression).line,((FunctionCall)expression).column);
			}else if(!tiposAceptados.contains(s.mapa.get(functionId).tipo)){
				CheckStateLinter.addError9B(((FunctionCall)expression).line, ((FunctionCall)expression).column);
			}
		}
		else if (expression!=null && expression instanceof FunctionCall && !s.mapa.containsKey(((FunctionCall) expression).id)) {
			CheckStateLinter.addError9(((FunctionCall)expression).line, ((FunctionCall)expression).column);
		}
	}

	public static void evaluarRegla11(CheckStateLinter cslint) {
		for (Map.Entry<String, ObjectState> element : cslint.mapa.entrySet()) {
			ObjectState objState = element.getValue();
			if (!objState.used && objState.isParameter()) {
				addError11(objState.getLine(), objState.getColumn());
			}
		}
	}

	public static Map<String,ObjectState> clonarMapa(Map<String,ObjectState> mapaOriginal){
		Map <String,ObjectState>mapaClonado=new HashMap<String,ObjectState>();
		Iterator<String> it = mapaOriginal.keySet().iterator();
		while(it.hasNext()){
			String key = it.next();
			ObjectState osClone=mapaOriginal.get(key).clone();
			mapaClonado.put(key, osClone);
		}
		return mapaClonado;
	}

	public static void setVariableUsedIfUsedInside(CheckStateLinter original, CheckStateLinter inside){
		for (Map.Entry<String, ObjectState> element : original.mapa.entrySet()){
			String id=element.getKey();
			if (inside.mapa.containsKey(id) && !inside.mapa.get(id).isParameter()){
				original.mapa.get(id).used=inside.mapa.get(id).used;
			}
		}
	}
	
	public static CheckStateLinter variablesNuevas(CheckStateLinter viejo, CheckStateLinter nuevo){
		CheckStateLinter newCheckStateLinter=new CheckStateLinter();
		for (Map.Entry<String, ObjectState> element : nuevo.mapa.entrySet()){
			String id=element.getKey();
			if (!viejo.mapa.containsKey(id)){
				newCheckStateLinter.mapa.put(id, element.getValue());
			}
		}
		return newCheckStateLinter;
	}
}
