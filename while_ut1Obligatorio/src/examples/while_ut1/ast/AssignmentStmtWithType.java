package examples.while_ut1.ast;

import java.util.*;

import examples.while_ut1.Main;

/** Representación de las asignaciones de valores a variables.
 */
public class AssignmentStmtWithType extends Stmt {
	public final String type;
	public final String id;
	public Exp expression;

	public AssignmentStmtWithType(String type, String id, Exp expression, int line, int column) {
		this.type = type;
		this.id = id;
		this.expression = expression;
		this.line = line;
		this.column = column;
	}


	@Override public String unparse() {
		if (type != null) {
			return type + " " + id +" = "+ expression.unparse() +"; ";
		} else {
			return type + " " + id +"; ";
		}
	}

	@Override public String toString() {
		return "AssignmentWithType("+ id +", "+ expression +")";
	}

	@Override public int hashCode() {
		int result = 1;
		result = result * 31 + (this.id == null ? 0 : this.id.hashCode());
		result = result * 31 + (this.expression == null ? 0 : this.expression.hashCode());
		return result;
	}

	@Override public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		AssignmentStmtWithType other = (AssignmentStmtWithType)obj;
		return (this.id == null ? other.id == null : this.id.equals(other.id))
				&& (this.expression == null ? other.expression == null : this.expression.equals(other.expression));
	}

	//	public static AssignmentStmtWithType generate(Random random, int min, int max) {
	//		String id; AExp expression;
	//		id = ""+"abcdefghijklmnopqrstuvwxyz".charAt(random.nextInt(26));
	//		expression = AExp.generate(random, min-1, max-1);
	//		return new AssignmentStmtWithType(id, expression);
	//	}

	@Override
	public State evaluate(State state) {
		if (state.mapaValores.containsKey(id)){
			throw new IllegalStateException("variable ya definida:"+this.unparse());
		}
		else{
			if (expression!=null){
				Object valor = expression.evaluate(state);
				meterTipo(state);
				if (valor instanceof String){
					if (state.mapaTipo.get(id).equals("String")){
						state.mapaValores.put(id,(String)valor);
						return  state;
					}else{
						throw new IllegalStateException("Error tipo variable expresión:"+this.unparse());
					}
				}
				else if (valor instanceof Integer){
					if (state.mapaTipo.get(id).equals("Integer")){
						state.mapaValores.put(id,(Integer)valor);
						return  state;
					}else if (state.mapaTipo.get(id).equals("Double")){
						state.mapaValores.put(id,toDouble(valor,this.unparse()));
						return  state;
					}else{
						throw new IllegalStateException("Error tipo variable expresión:"+this.unparse());
					}
				}
				else if (valor instanceof Double){
					if (state.mapaTipo.get(id).equals("Double")){
						state.mapaValores.put(id,(Double)valor);
						return  state;
					}else{
						throw new IllegalStateException("Error tipo variable expresión:"+this.unparse());
					}
				}
				else if (valor instanceof Boolean){
					if (state.mapaTipo.get(id).equals("Boolean")){
						state.mapaValores.put(id,(Boolean)valor);
						return  state;
					}else{
						throw new IllegalStateException("Error tipo variable expresión:"+this.unparse());
					}
				}
				else {
					throw new IllegalStateException(this.unparse());
				}
			}
			meterTipo(state);
			return null;
		}
	}




	@Override
	public CheckState check(CheckState s) {
		ObjectState objectState=new ObjectState();
		if (expression==null){
			objectState.assigned=false;
		}else{
			if(type.equals(expression.check(s))){
				objectState.assigned=true;
			}else if(type.equals("Double")&& expression.check(s).equals("Integer")){
				objectState.assigned=true;
			}else {
				s.errores.add("El tipo de la expresion no coincide con la variable "+id+":"+this.unparse());
				objectState.assigned=false;
			}
		}
		if (!s.mapa.containsKey(id)){//variable definida???
			objectState.tipo=type;
			s.mapa.put(id, objectState);
		}else{
			s.errores.add("Error la variable ya estaba definida: "+this.unparse());
		}
		return s;
	}



	public void meterTipo(State state){
		state.mapaTipo.put(id,type);
		state.mapaValores.put(id,null);
	}


	@Override
	public CheckStateLinter checkLinter(CheckStateLinter s) {
		String expressionType = this.expression.checkLinter(s);
		if (s.mapa.containsKey(id)) CheckStateLinter.addError("14", "la variable " + id + " ya se encuentra declarada", line, column);
		s.mapa.keySet().forEach((key) -> {
			if (key.toLowerCase().equals(id.toLowerCase()))
				CheckStateLinter.addError("18B", "la variable " + id + " se encuentra definida como " + key, line, column);
		});
		ObjectState objState = new ObjectState(this.type, true, 2, this);
		s.mapa.put(this.id, objState);
		return s;
	}


}
