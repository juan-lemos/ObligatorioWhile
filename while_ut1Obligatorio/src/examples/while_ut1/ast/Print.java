package examples.while_ut1.ast;

import java.util.*;

/** Representación de las sentencias condicionales.
*/
public class Print extends Stmt {
	public final Exp expression;

	public Print(Exp expression, int line, int column) {
		this.expression = expression;
		this.line = line;
		this.column = column;
	}

	@Override public String unparse() {
		return "print("+ expression.unparse() +")";
	}

	@Override public String toString() {
		return "print("+ expression +")";
	}

	@Override public int hashCode() {
		int result = 1;
		result = result * 31 + (this.expression == null ? 0 : this.expression.hashCode());
		return result;
	}

	@Override public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		Print other = (Print)obj;
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
	public State evaluate(State state) {
		System.out.println(expression.evaluate(state));
		return state;
	}

	@Override
	public CheckState check(CheckState s) {
		expression.check(s);
		return s;
	}

	@Override
	public CheckStateLinter checkLinter(CheckStateLinter s) {
		if (expression.countOperators() > 7) CheckStateLinter.addError20(expression.countOperators(), line, column);
		expression.checkLinter(s);
		return null;
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
