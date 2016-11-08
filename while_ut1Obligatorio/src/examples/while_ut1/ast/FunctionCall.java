package examples.while_ut1.ast;

import java.util.*;

/** Representación de multiplicaciones.
 */
public class FunctionCall extends Exp {
	public final String id;
	public final ArrayList <Exp> parameters;

	public FunctionCall(String id, ArrayList<Exp> p, int line, int column) {
		this.id=id;
		this.parameters = p;
		this.line = line;
		this.column = column;
	}


	@Override public String toString() {
		String devolver=id+" (";
		int i=0;
		for (Exp parameter:parameters){
			devolver+=parameter.toString();
			i++;
			if (i!=parameters.size()){
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

	public static FunctionCall generate(Random random, int min, int max) {
		return null;
	}

	@Override
	public Object evaluate(State state) {
		return null;
	}

	@Override
	public String check(CheckState s){
		return null;
	}


	@Override
	public String unparse() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String checkLinter(CheckStateLinter s) {
		if (s.mapa.containsKey(this.id)) {
			ObjectState objState = s.mapa.get(this.id);
			objState.used = true;
			FunctionDeclaration functionDeclaration = (FunctionDeclaration) objState.astNode;
			if (!(this.parameters.size() == functionDeclaration.parameters.size())) {
				CheckStateLinter.addError("10A", "cantidad de parametros de funcion incorrectos", line, column);
			} else {
				for (int i = 0; i < this.parameters.size(); i++){
					String parameterType = this.parameters.get(i).checkLinter(s);
					String expectedType = (String) functionDeclaration.parameters.values().toArray()[i];
				    if (!(parameterType == expectedType)) {
				    	String msg = "parametro de funcion de tipo incorrecto. Esperado: " + expectedType + ", actual: " + parameterType;
				    	CheckStateLinter.addError("10B", msg, line, column);
				    }
				}
			}
			
			return functionDeclaration.type;
		}
		return "Double";
	}


	@Override
	public Exp optimize() {
		return this;
	}

	@Override
	public int getLine() {
		return line;
	}

	@Override
	public int getColumn() {
		return column;
	}
}
