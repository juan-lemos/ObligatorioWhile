package examples.while_ut1.ast;

import java.util.*;


/** Representación de las comparaciones por igual.
 */
public class CompareEqual extends BExp {
	public final Exp left;
	public final Exp right;

	public CompareEqual(Exp left, Exp right) {
		this.left = left;
		this.right = right;
	}

	@Override public String unparse() {
		return "("+ left.unparse() +" == "+ right.unparse() +")";
	}

	@Override public String toString() {
		return "CompareEqual("+ left +", "+ right +")";
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
		CompareEqual other = (CompareEqual)obj;
		return (this.left == null ? other.left == null : this.left.equals(other.left))
				&& (this.right == null ? other.right == null : this.right.equals(other.right));
	}

	public static CompareEqual generate(Random random, int min, int max) {
		AExp left; AExp right; 
		left = AExp.generate(random, min-1, max-1);
		right = AExp.generate(random, min-1, max-1);
		return new CompareEqual(left, right);
	}

	@Override
	public Object evaluate(State state) {
		return left.evaluate(state).equals(right.evaluate(state));
	}

	@Override
	public String check(CheckState s) {
		String right = this.right.check(s);
		String left = this.left.check(s);
		if (right.equals(left)){
			return right;
		}
		else{
			s.errores.add("Error los tipos no son iguales:"+this.toString());
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
		}else if(leftOptimized instanceof Str  && rightOptimized instanceof Str) {
			String leftString=(String)(((Str)leftOptimized).value);
			String rightString=(String)(((Str)rightOptimized).value);
			return new TruthValue(leftString.equals(rightString));
		}else if(leftOptimized instanceof TruthValue  && rightOptimized instanceof TruthValue) {
			return new TruthValue (((TruthValue)leftOptimized).value == ((TruthValue)rightOptimized).value);
		}
		else{
			return this;
		}
	}

}
