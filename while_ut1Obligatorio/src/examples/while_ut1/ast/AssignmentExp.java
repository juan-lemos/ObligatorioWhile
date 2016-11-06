package examples.while_ut1.ast;

import java.util.*;

/** Representación de las asignaciones de valores a variables.
 */
public class AssignmentExp extends Exp {
	public final String id;
	public final Exp expression;

	public AssignmentExp(String id, Exp expression, int column, int line) {
		this.id = id;
		this.expression = expression;
		this.column = column;
		this.line = line;
	}

	@Override public String unparse() {
		return id +" = "+ expression.unparse() +"; ";
	}

	@Override public String toString() {
		return "Assignment("+ id +", "+ expression +")";
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
		AssignmentExp other = (AssignmentExp)obj;
		return (this.id == null ? other.id == null : this.id.equals(other.id))
				&& (this.expression == null ? other.expression == null : this.expression.equals(other.expression));
	}

	public static AssignmentExp generate(Random random, int min, int max) {
		String id; AExp expression; 
		id = ""+"abcdefghijklmnopqrstuvwxyz".charAt(random.nextInt(26));
		expression = AExp.generate(random, min-1, max-1);
		return new AssignmentExp(id, expression, 1, 1);
	}

	@Override
	public Object evaluate(State state) {
		return asignarValor(expression,state,id,this.unparse());
	}

	@Override
	public String check(CheckState s) {
		if (!s.mapa.containsKey(id)){
			s.errores.add("Variable"+id+"no definida: "+this.unparse());
		}else{
			if (s.mapa.get(id).tipo.equals(expression.check(s))){
				s.mapa.get(id).assigned=true;
			}else if(s.mapa.get(id).tipo.equals("Double") && expression.check(s).equals("Double")){
				s.mapa.get(id).assigned=true;
			}else{
				s.errores.add("El tipo de la expresion no coincide con la variable "+id+":"+this.unparse());
			}
		}
		return expression.check(s);
	}
	
	public static Object asignarValor(Exp expression,State state,String id,String unparse2){
		Object valor = expression.evaluate(state);
		if (valor instanceof String){
			if (state.mapaTipo.get(id).equals("String")){
				state.mapaValores.put(id,(String)valor);
				return  valor;
			}else{
				throw new IllegalStateException("Error tipo variable expresión:"+unparse2);
			}
		}
		else if (valor instanceof Integer){
			if (state.mapaTipo.get(id).equals("Integer")){
				state.mapaValores.put(id,valor);
				return  valor;
			}else if (state.mapaTipo.get(id).equals("Double")){
				state.mapaValores.put(id,toDouble(valor,unparse2));
				return  valor;
			}else{
				throw new IllegalStateException("Error tipo variable expresión:"+unparse2);
			}
		}
		else if (valor instanceof Double){
			if (state.mapaTipo.get(id).equals("Double")){
				state.mapaValores.put(id,(Double)valor);
				return  valor;
			}else{
				throw new IllegalStateException("Error tipo variable expresión:"+unparse2);
			}
		}
		else if (valor instanceof Boolean){
			if (state.mapaTipo.get(id).equals("Boolean")){
				state.mapaValores.put(id,(Boolean)valor);
				return  valor;
			}else{
				throw new IllegalStateException("Error tipo variable expresión:"+unparse2);
			}
		}
		else {
			throw new IllegalStateException(unparse2);
		}
	}

	@Override
	public String checkLinter(CheckStateLinter s) {
		String expressionType = this.expression.checkLinter(s);
		if (s.mapa.containsKey(id)) {
			s.mapa.get(id).used = true;
		} else {
			CheckStateLinter.addError8(id, line, column);
			ObjectState objState = new ObjectState("Double", true, 4, this);
			s.mapa.put(this.id, objState);
		}
		return expressionType;
	}

	@Override
	public Exp optimize() {
		Exp optimizedExpression = expression.optimize();
		if (optimizedExpression instanceof Numeral){
			return optimizedExpression;
		}else if(optimizedExpression instanceof Str){
			return optimizedExpression;
		}else if(optimizedExpression instanceof TruthValue){
			return optimizedExpression;
		}else{
			return this;
		}
	}


}
