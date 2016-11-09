package examples.while_ut1.ast;

import java.util.*;

/** Representación de constantes numéricas o numerales.
 */
public class Numeral extends AExp {
	public final Object number;

	public Numeral(Double number, int line, int column) {
		this.number = number;
		this.line = line;
		this.column = column;
	}

	public Numeral(Integer number) {
		this.number = number;
	}

	@Override public String unparse() {
		return number.toString();
	}

	@Override public String toString() {
		return "Numeral("+ number +")";
	}

	@Override public int hashCode() {
		int result = 1;
		result = result * 31 + (this.number == null ? 0 : this.number.hashCode());
		return result;
	}

	@Override public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		try {
			Double self = (Double) (this.number);
			Double other = ((Numeral)obj).number == null ? null : (Double) (((Numeral)obj).number);
			return (self == null ? other == null : self.equals(other));
		} catch(Exception e) {
			return false;
		}
	}

	@Override
	public Object evaluate(State state) {
		return number;
	}

	@Override
	public String check(CheckState s){
		if (this.number instanceof Integer){
			return "Integer";
		}
		else if (this.number instanceof Double){
			return "Double";
		}else{
			throw new IllegalStateException(this.unparse());
		} 
		
	}

	@Override
	public String checkLinter(CheckStateLinter s) {
		if (getIsInsideParenthesis()) CheckStateLinter.addError16(line, column);
		if (this.number instanceof Integer){
			return "Integer";
		}
		else if (this.number instanceof Double){
			return "Double";
		}else{
			throw new IllegalStateException(this.unparse());
		} 
	}

	@Override
	public Exp optimize() {
		// TODO Auto-generated method stub
		return this;
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
