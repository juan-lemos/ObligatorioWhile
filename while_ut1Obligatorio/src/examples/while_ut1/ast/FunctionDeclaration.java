package examples.while_ut1.ast;

import java.util.*;

/** RepresentaciÃ³n de las asignaciones de valores a variables.
 */
public class FunctionDeclaration extends Stmt {
	public final String id;
	public final String type;
	public final LinkedHashMap<String, String> parameters;
	public final Stmt body;

	//hay que clonar el estado para guardar las variables locales de la funicón

	public FunctionDeclaration(String id, String type,
			LinkedHashMap<String, String> parameters,Stmt body,
			int line, int column) {
		this.id = id;
		this.type = type;
		this.parameters=parameters;
		this.body=body;
		this.line = line;
		this.column = column;
	}


	@Override public String toString() {
		String devolver="function "+type+" "+id+" (";
		List <String>keys=(List) parameters.values();
		int i=0;
		for (String parameter:keys){
			devolver+=parameter+" "+parameters.get(parameter);
			i++;
			if (i!=keys.size()){
				devolver+=",";
			}
		}
		devolver+=");";
		return devolver;
	}

	@Override public int hashCode() {
		return (Integer) null;
	}

	@Override public boolean equals(Object obj) {
		return (Boolean) null;
	}

	public static FunctionDeclaration generate(Random random, int min, int max) {
		return null;
	}

	@Override
	public State evaluate(State state) {
		return null;

	}

	@Override
	public CheckState check(CheckState s) {
		return null;
	}


	@Override
	public String unparse() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public CheckStateLinter checkLinter(CheckStateLinter s) {
		body.idFunction=id;//regla 12
		if (Character.isUpperCase(id.charAt(0))) CheckStateLinter.addError7(line, column);
		if (body.countNestingLevels() > 5) CheckStateLinter.addError21(body.countNestingLevels(), line, column);
		if (s.mapa.containsKey(id) && s.mapa.get(id).isFunction()) CheckStateLinter.addError13(id, line, column);
		s.mapa.keySet().forEach((key) -> {
			if (key.toLowerCase().equals(id.toLowerCase()) && !key.equals(id) && s.mapa.get(key).isFunction())
				CheckStateLinter.addError18A(id, key, line, column);
		});
		
		s.mapa.put(id, new ObjectState(type, false, 1, this));//revisar
		
		
		CheckStateLinter cslForOutsideVariables = new CheckStateLinter();
		Map<String,ObjectState> clonedMap = CheckState.clonarMapa(s.mapa);
		cslForOutsideVariables.mapa = clonedMap;
		for (Map.Entry<String, String> parameter : parameters.entrySet()) {
			clonedMap.put(parameter.getKey(), new ObjectState(parameter.getValue(), true, 3, this));
		}
		cslForOutsideVariables = body.checkLinter(cslForOutsideVariables);
		

		CheckStateLinter.generateErrors(CheckStateLinter.variablesNuevas(s, cslForOutsideVariables));
		CheckStateLinter.evaluarRegla11(cslForOutsideVariables);
		
		CheckStateLinter.setVariableUsedIfUsedInside(s, cslForOutsideVariables);
		return s;
	}

	@Override
	public int getLine() {
		return line;
	}

	@Override
	public int getColumn() {
		return column;
	}
	
	@Override
	public int countNestingLevels() {
		return body.countNestingLevels();
	}
}
