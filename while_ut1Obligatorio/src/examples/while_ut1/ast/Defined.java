package examples.while_ut1.ast;

import java.util.*;

/** Representación de conjunciones booleanas (AND).
*/
public class Defined extends BExp {
	public final String id;

	public Defined(String id) {
		this.id = id;
	}

	@Override public String unparse() {
		return "defined("+ id +")";
	}

	@Override public String toString() {
		return "Defined("+ id +")";
	}

	@Override public int hashCode() {
		int result = 1;
		result = result * 31 + (this.id == null ? 0 : this.id.hashCode());
		return result;
	}

	@Override public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		Defined other = (Defined)obj;
		return (this.id == null ? other.id == null : this.id.equals(other.id));
	}

//	public static Defined generate(Random random, int min, int max) {
//		BExp left; BExp right; 
//		left = BExp.generate(random, min-1, max-1);
//		right = BExp.generate(random, min-1, max-1);
//		return new Defined(left, right);
//	}

	@Override
	public Object evaluate(State state) {
		Object variable;
		variable = state.mapaValores.get(id);
		return variable != null ? true : false;
	}

	@Override
	public String check(CheckState s) {		
		return "Boolean";
	}

	@Override
	public String checkLinter(CheckStateLinter s) {
		return "Boolean";
	}

	@Override
	public Exp optimize() {
		return this;
	}
}
