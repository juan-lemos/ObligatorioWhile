package examples.while_ut1.ast;

import java.util.ArrayList;

public class FunctionCallStmt extends Stmt{

	public final String id;
	public final ArrayList <Exp> parameters;

	public FunctionCallStmt(String id, ArrayList<Exp> p, int line, int column) {
		this.id=id;
		this.parameters = p;
		this.line = line;
		this.column = column;
	}
	
	@Override
	public String unparse() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public State evaluate(State state) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CheckState check(CheckState s) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CheckStateLinter checkLinter(CheckStateLinter s) {
		// COPIADO DE FUNCTIONCALL REVISAR!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
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
			
//			return functionDeclaration.type;
		}
		return s;
	}

}
