package examples.while_ut1.ast;

import java.util.*;

import javax.swing.text.html.parser.ParserDelegator;

/** Representación de las sentencias condicionales.
*/
public class Length extends Exp {
	public final Exp expression;

	public Length(Exp expression) {
		this.expression = expression;
	}

	@Override public String unparse() {
		return "length("+ expression.unparse() +")";
	}

	@Override public String toString() {
		return "length("+ expression +")";
	}

	@Override public int hashCode() {
		int result = 1;
		result = result * 31 + (this.expression == null ? 0 : this.expression.hashCode());
		return result;
	}

	@Override public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		Length other = (Length)obj;
		return (this.expression == null ? other.expression == null : this.expression.equals(other.expression));
	}
//
//	public static Print generate(Random random, int min, int max) {
//		BExp condition; Stmt thenBody;  
//		condition = BExp.generate(random, min-1, max-1);
//		thenBody = Stmt.generate(random, min-1, max-1);
//		return new Print(condition, thenBody);
//	}

	@Override
	public Object evaluate(State state) {
		Object expression;
		expression = this.expression.evaluate(state);
		if (expression instanceof String){
			return new Integer(((String) expression).length());
		} else {
			throw new IllegalStateException(this.unparse());
		}
	}

	@Override
	public String check(CheckState s) {
		if (expression.check(s).equals("String")){
			
		}else{
			s.errores.add("Error solo Strings:"+this.toString());
		}
		return "Integer";
	}

	@Override
	public String checkLinter(CheckStateLinter s) {
		expression.checkLinter(s);
		return null;
	}

	@Override
	public Exp optimize() {
		Exp optimizedExpression=expression.optimize();
		if (optimizedExpression instanceof Str){
			return new Numeral(((Str)optimizedExpression).value.length());
		}
		return this;
	}
	
}
