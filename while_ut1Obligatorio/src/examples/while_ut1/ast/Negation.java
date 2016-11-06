package examples.while_ut1.ast;

import java.util.*;

/** Representación de las negaciones de expresiones booleanas.
 */
public class Negation extends BExp {
	public final Exp condition;

	public Negation(Exp condition) {
		this.condition = condition;
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

	public static Negation generate(Random random, int min, int max) {
		BExp condition; 
		condition = BExp.generate(random, min-1, max-1);
		return new Negation(condition);
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
		this.condition.checkLinter(s);
		return "Boolean";
	}

	@Override
	public Exp optimize() {
		Exp optimizedCondition=condition.optimize();
		if (optimizedCondition instanceof TruthValue){
			return new TruthValue(!((TruthValue)optimizedCondition).value);
		}else{
			return this;
		}
	}


}
