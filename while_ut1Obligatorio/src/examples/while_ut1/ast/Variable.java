package examples.while_ut1.ast;

import java.util.*;

/** Representación de usos de variable en expresiones.
*/
public class Variable extends AExp {
	public final String id;

	
	public Variable(String id,int line, int column) {
		this.id = id;
		this.line=line;
		this.column=column;
	}

	@Override public String unparse() {
		return id;
	}

	@Override public String toString() {
		return "Variable("+ id +")";
	}

	@Override public int hashCode() {
		int result = 1;
		result = result * 31 + (this.id == null ? 0 : this.id.hashCode());
		return result;
	}

	@Override public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		Variable other = (Variable)obj;
		return (this.id == null ? other.id == null : this.id.equals(other.id));
	}

	public static Variable generate(Random random, int min, int max) {
		String id; 
		id = ""+"abcdefghijklmnopqrstuvwxyz".charAt(random.nextInt(26));
		return null;
	}

	@Override
	public Object evaluate(State state) {
		if (state.mapaValores.containsKey(id)) {
			return state.mapaValores.get(id);
		}
		throw new IllegalStateException(this.unparse());
	}
	
	@Override
	public String check(CheckState s){
		if (s.mapa.containsKey(id)){
			return s.mapa.get(id).tipo;
		}else{
			s.errores.add("Error varible "+id+" no definida:"+this.unparse());
//			ObjectState objectState=new ObjectState();
//			objectState.assigned=false;
//			objectState.tipo="Numeral";
//			s.mapa.put(id, objectState);
			return "Double";
		}
		
	}

	@Override
	public String checkLinter(CheckStateLinter s) {
		if(!s.mapa.containsKey(id)){
			CheckStateLinter.addError8(id, line, column);
			return "Double";
		} else {
			s.mapa.get(id).used=true;
			return s.mapa.get(id).tipo;
		}	
	}

	@Override
	public Exp optimize() {
		return this;
	}
}
