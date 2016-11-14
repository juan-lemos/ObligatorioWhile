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
		if (exp.countOperators() > 7) CheckStateLinter.addError20(exp.countOperators(), line, column);

		String tipoExp=exp.checkLinter(s);
		//		if (!this.idFunction.equals("")){
		if (s.mapa.get(this.idFunction).tipo.equals("Void")){
			CheckStateLinter.addError12A(this.idFunction, line, column);
		}
		else if (tipoExp.equals(s.mapa.get(this.idFunction).tipo)){
			CheckStateLinter.addError12B(this.idFunction, line, column);
		}
		//		}

		ArrayList <String> tiposAceptados=new ArrayList<String>();
		tiposAceptados.add(s.mapa.get(this.idFunction).tipo);
		CheckStateLinter.evaluarRegla9(this.exp, s, tiposAceptados);



		///ver que hago con idFunction de stmt
		return s;
	}

	@Override
	public int getLine() {
		return 0;
	}

	@Override
	public int getColumn() {
		return 0;
	}

	@Override
	public int countNestingLevels() {
		return 0;
	}
}
