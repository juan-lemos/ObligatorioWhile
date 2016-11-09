package examples.while_ut1.ast;

public abstract class Exp extends AstNode {
	abstract public String unparse();
	abstract public Object evaluate(State state);
	abstract public boolean equals(Object obj);
	abstract public String check(CheckState s);
	abstract public String toString();
	public boolean isInsideParenthesis;
	
	public static double toDouble(Object obj,String unparse) {
		return Stmt.toDouble(obj, unparse);
	}
	
	abstract public String checkLinter(CheckStateLinter s);
	abstract public Exp optimize();
	
	public boolean getIsInsideParenthesis() {
		return isInsideParenthesis;
	}
	
	public void setIsInsideParenthesis(boolean isInsideParenthesis) {
		this.isInsideParenthesis = isInsideParenthesis;
	}
}
