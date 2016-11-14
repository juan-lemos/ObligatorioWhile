package examples.while_ut1.ast;

public class ObjectState {

	public String tipo = "";
	public boolean assigned = false;
	public boolean used = false;
	public int queEs = 0; // 1: function, 2: variable, 3:parametro
	public Object astNode = null;
	
	public ObjectState(String tipo, boolean assigned, int queEs, Object astNode){
		this.tipo = tipo;
		this.assigned = assigned;
		this.queEs = queEs;
		this.astNode = astNode;
	}
	
	public ObjectState() {}
	
	@Override
	public ObjectState clone(){
		ObjectState objectState=new ObjectState();
		objectState.tipo=new String(this.tipo);
		objectState.assigned=new Boolean(this.assigned);
		objectState.used=new Boolean(this.used);
		objectState.queEs=new Integer(this.queEs);
		objectState.astNode=this.astNode;
		return objectState;
	}
	
	public int getLine() {
		return ((AstNode) this.astNode).line;
	}
	
	public int getColumn() {
		return ((AstNode) this.astNode).column;
	}
	
	public boolean isFunction() {
		return this.queEs == 1;
	}
	
	public boolean isVariable() {
		return this.queEs == 2;
	}
	
	public boolean isParameter() {
		return this.queEs == 3;
	}
}




