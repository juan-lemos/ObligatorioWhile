package examples.while_ut1.ast;

public abstract class AstNode {
	public int line;
	public int column;
	
	abstract public int getLine();
	abstract public int getColumn();
}
