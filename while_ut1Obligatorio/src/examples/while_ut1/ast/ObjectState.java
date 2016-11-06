package examples.while_ut1.ast;

public class ObjectState {

	public String tipo = "";
	public boolean assigned = false;
	public boolean used = false;
	public int queEs = 0; // 1: function, 2: assignmentStmtWithType, 3: assignmentStmtWithoutType, 4: assignmentExp
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
		objectState.assigned=new Boolean(this.assigned);
		objectState.tipo=new String(this.tipo);
		return objectState;
	}
	
	public int getLine() {
		switch (this.queEs){
			case 1:
				return ((FunctionDeclaration) this.astNode).line;
			case 2:
				return ((AssignmentStmtWithType) this.astNode).line;
			case 3:
				return ((AssignmentStmt) this.astNode).line;
			case 4:
				return ((AssignmentExp) this.astNode).line;
			default:
				return -1;
		}
	}
	
	public int getColumn() {
		switch (this.queEs){
			case 1:
				return ((FunctionDeclaration) this.astNode).column;
			case 2:
				return ((AssignmentStmtWithType) this.astNode).column;
			case 3:
				return ((AssignmentStmt) this.astNode).column;
			case 4:
				return ((AssignmentExp) this.astNode).column;
			default:
				return -1;
		}
	}
	
	public boolean isFunction() {
		return this.queEs == 1;
	}
}




