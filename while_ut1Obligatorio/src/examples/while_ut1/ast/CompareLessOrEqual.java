package examples.while_ut1.ast;

import java.util.*;

/** Representación de las comparaciones por menor o igual.
 */
public class CompareLessOrEqual extends BExp {
	public final Exp left;
	public final Exp right;

	public CompareLessOrEqual(Exp left, Exp right) {
		this.left = left;
		this.right = right;
	}

	@Override public String unparse() {
		return "("+ left.unparse() +" <= "+ right.unparse() +")";
	}

	@Override public String toString() {
		return "CompareLessOrEqual("+ left +", "+ right +")";
	}

	@Override public int hashCode() {
		int result = 1;
		result = result * 31 + (this.left == null ? 0 : this.left.hashCode());
		result = result * 31 + (this.right == null ? 0 : this.right.hashCode());
		return result;
	}

	@Override public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		CompareLessOrEqual other = (CompareLessOrEqual)obj;
		return (this.left == null ? other.left == null : this.left.equals(other.left))
				&& (this.right == null ? other.right == null : this.right.equals(other.right));
	}

	public static CompareLessOrEqual generate(Random random, int min, int max) {
		AExp left; AExp right; 
		left = AExp.generate(random, min-1, max-1);
		right = AExp.generate(random, min-1, max-1);
		return new CompareLessOrEqual(left, right);
	}

	@Override
	public Object evaluate(State state) {
		Object left; Object right;
		left =this.left.evaluate(state);
		right =this.right.evaluate(state);
		if ((left instanceof Double && right instanceof Double) ||
				(left instanceof Integer && right instanceof Double)||
				(left instanceof Double && right instanceof Integer)||
				(left instanceof Integer && right instanceof Integer)){
			return toDouble(left,unparse()) <= toDouble(right,unparse());	
		}else {
			throw new IllegalStateException(this.unparse());
		}
	}


	@Override
	public String check(CheckState s) {
		String right = this.right.check(s);
		String left = this.left.check(s);
		if (right.equals(left) && right.equals("Numeral")){
			return right;
		}
		else{
			s.errores.add("Error los tipos no son numérico:"+this.toString());
			return right;
		}
	}

	@Override
	public String checkLinter(CheckStateLinter s) {
		left.checkLinter(s);
		right.checkLinter(s);
		return "Boolean";
	}

	@Override
	public Exp optimize() {
		Exp leftOptimized=left.optimize();
		Exp rightOptimized=right.optimize();
		if (leftOptimized instanceof Numeral  && rightOptimized instanceof Numeral){
			Double leftNumberValue=0.0;
			Double rightNumberValue=0.0;
			if (((Numeral)leftOptimized).number instanceof Integer){
				leftNumberValue =((Integer)((Numeral)leftOptimized).number).doubleValue();
			}else{
				leftNumberValue =((Double)((Numeral)leftOptimized).number);
			}
			if (((Numeral)rightOptimized).number instanceof Integer){
				rightNumberValue =((Integer)((Numeral)rightOptimized).number).doubleValue();
			}else{
				rightNumberValue =((Double)((Numeral)rightOptimized).number);
			}
			return new TruthValue(leftNumberValue == rightNumberValue);
		}
		else{
			return this;
		}
	}
}
