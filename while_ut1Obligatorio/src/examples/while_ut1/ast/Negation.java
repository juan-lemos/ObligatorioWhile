package examples.while_ut1.ast;

import java.util.*;

/** Representación de las negaciones de expresiones booleanas.
 */
public class Negation extends BExp {
	public final Exp condition;

	public Negation(Exp condition, int line, int column) {
		this.condition = condition;
		this.line = line;
		this.column = column;
	}

	@Override public String unparse() {
		return "(!"+ condition.unparse() +")";
	}

	@Override public String toString() {
		return "Negation("+ condition +")";
	}

	@Override public int hashCode() {
		int result = 1;
		result = result * 31 + (this.condition == null ? 0 : this.condition.hashCode());
		return result;
	}

	@Override public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		Negation other = (Negation)obj;
		return (this.condition == null ? other.condition == null : this.condition.equals(other.condition));
	}

	@Override
	public Object evaluate(State state) {
		Object expression=condition.evaluate(state);
		if (expression instanceof Boolean){
			return !((Boolean) expression);
		} else {
			throw new IllegalStateException(this.unparse());
		}
	}

	@Override
	public String check(CheckState s) {
		String condition=this.condition.check(s);
		if (condition.equals("Boolean")){
			return "Boolean";
		}
		else{
			s.errores.add("Solo booleanos:"+ this.toString());
			return "Boolean";
		}

	}

	@Override
	public String checkLinter(CheckStateLinter s) {
		
		ArrayList <String> tiposAceptados=new ArrayList<String>();
		tiposAceptados.add("Boolean");
		CheckStateLinter.evaluarRegla9(this.condition, s, tiposAceptados);

		this.condition.checkLinter(s);
		return "Boolean";
	}

	@Override
	public Exp optimize() {
		Exp optimizedCondition=condition.optimize();
		if (optimizedCondition instanceof TruthValue){
			return new TruthValue(!((TruthValue)optimizedCondition).value, condition.line, condition.column);
		}else{
			return this;
		}
	}

	@Override
	public int getLine() {
		return 0;
	}

	@Override
	public int getColumn() {
		return 0;
	}


}
