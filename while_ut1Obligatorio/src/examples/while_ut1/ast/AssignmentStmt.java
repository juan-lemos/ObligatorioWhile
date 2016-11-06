package examples.while_ut1.ast;

import java.util.*;

/** Representación de las asignaciones de valores a variables.
 */
public class AssignmentStmt extends Stmt {
	public final String id;
	public final Exp expression;
	
	
	public AssignmentStmt(String id, Exp expression,int line, int column) {
		this.id = id;
		this.expression = expression;
		this.line=line;
		this.column=column;
	}

	@Override public String unparse() {
		return id +" = "+ expression.unparse() +"; ";
	}

	@Override public String toString() {
		return "Assignment("+ id +", "+ expression +")";
	}

	@Override public int hashCode() {
		int result = 1;
		result = result * 31 + (this.id == null ? 0 : this.id.hashCode());
		result = result * 31 + (this.expression == null ? 0 : this.expression.hashCode());
		return result;
	}

	@Override public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		AssignmentStmt other = (AssignmentStmt)obj;
		return (this.id == null ? other.id == null : this.id.equals(other.id))
				&& (this.expression == null ? other.expression == null : this.expression.equals(other.expression));
	}

	public static AssignmentStmt generate(Random random, int min, int max) {
		return null; 
	}

	@Override
	public State evaluate(State state) {
		AssignmentExp.asignarValor(expression,state,id,this.unparse());
		return state;
				
	}

	@Override
	public CheckState check(CheckState s) {
		if (!s.mapa.containsKey(id)){
			s.errores.add("Variable "+id+" no definida: "+this.unparse());
		}else{
			if (s.mapa.get(id).tipo.equals(expression.check(s))){
				s.mapa.get(id).assigned=true;
			}else if(s.mapa.get(id).tipo.equals("Double") && expression.check(s).equals("Double")){
				s.mapa.get(id).assigned=true;
			}else{
				s.errores.add("El tipo de la expresion no coincide con la variable "+id+":"+this.unparse());
			}
		}
		return s;
	}

	@Override
	public CheckStateLinter checkLinter(CheckStateLinter s) {
		String expressionType = this.expression.checkLinter(s);
		if (s.mapa.containsKey(id)) {
			s.mapa.get(id).used = true;
		} else {
			CheckStateLinter.addError8(id, line, column);
			ObjectState objState = new ObjectState("Double", true, 3, this);
			s.mapa.put(this.id, objState);
		}
		return s;
	}

		
}
