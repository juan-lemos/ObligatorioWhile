package examples.while_ut1.ast;

import java.util.*;

/** Representación de las comparaciones por igual.
*/
public class Return extends Stmt {
	public final Exp exp;	

	public Return(Exp exp, int line, int column) {
		this.exp = exp;
		this.line = line;
		this.column = column;
	}

	@Override public String unparse() {
		return null;
	}

	@Override public String toString() {
		return "return "+exp.toString() ;
	}

	@Override public int hashCode() {
		return (Integer) null;
	}

	@Override public boolean equals(Object obj) {
		return (Boolean) null;
	}

	public static Return generate(Random random, int min, int max) {
		return null;
	}

	@Override
	public State evaluate(State state) {
		return null;
	}

	@Override
	public CheckState check(CheckState s) {
		return null;
	}

	@Override
	public CheckStateLinter checkLinter(CheckStateLinter s) {
		// TODO Auto-generated method stub
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
