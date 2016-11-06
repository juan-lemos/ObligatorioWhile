package examples.while_ut1.ast;

import java.util.Map;

public class TernaryOperator extends Exp {
	public final Exp condition;
	public final Exp thenExp;
	public final Exp elseExp;
	
	public TernaryOperator(Exp condition, Exp thenExp, Exp elseExp) {
		this.condition = condition;
		this.thenExp = thenExp;
		this.elseExp = elseExp;
	}

	@Override
	public String unparse() {
		return "("+ condition.unparse() +") ? "+ thenExp.unparse() +" : "+ elseExp.unparse();
	}

	@Override
	public Object evaluate(State state) {
		Object evaluatedCondition=condition.evaluate(state);
		if (evaluatedCondition instanceof Boolean){
			Boolean truthValue=(Boolean) evaluatedCondition;
			Object result;
			State statein=state.clone();
			if (truthValue){
				result = thenExp.evaluate(statein);
			} else {
				result =  elseExp.evaluate(statein);
			}
			state.mapaValores=State.actualizarValoresMapViejo(state.mapaValores,statein.mapaValores);
			return result;
		}
		else {
			throw new IllegalStateException(this.unparse());
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		TernaryOperator other = (TernaryOperator)obj;
		return (this.condition == null ? other.condition == null : this.condition.equals(other.condition))
				&& (this.thenExp == null ? other.thenExp == null : this.thenExp.equals(other.thenExp))
				&& (this.elseExp == null ? other.elseExp == null : this.elseExp.equals(other.elseExp));
	}

	@Override
	public String check(CheckState s) {
		String checkCondition=condition.check(s);
		if (!checkCondition.equals("Boolean")){
			s.errores.add("Error en el if debe poner condicion boolean:"+this.toString());
		} //asumimos para que en el caso de que la condicion no sea boolean lo es
		
		String checkThenExp=thenExp.check(s);
		if (!(checkThenExp.equals("Boolean") || checkThenExp.equals("Integer") || 
				checkThenExp.equals("String")  || checkThenExp.equals("Double"))){
			s.errores.add("Se debe devolver un valor:"+this.toString());
		}
		
		String checkElseExp=elseExp.check(s);
		if (!(checkElseExp.equals("Boolean") || checkElseExp.equals("Integer") || 
				checkElseExp.equals("String")  || checkElseExp.equals("Double"))){
			s.errores.add("Se debe devolver un valor:"+this.toString());
		}
		if (checkThenExp.equals(checkElseExp)) {
			if (checkThenExp.equals("Void")){
				return "Integer";
			}
			return checkThenExp;
		} else {
			s.errores.add("Los tipos asignados son distintos:"+this.toString());
			return "Double";
		}
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String checkLinter(CheckStateLinter s) {
		Exp optimizado=condition.optimize();
		if (optimizado instanceof TruthValue){
			if (((TruthValue) optimizado).value){
				CheckStateLinter.addError("5", "El codigo del else no ejecutará nunca", line, column);
			}else{
				CheckStateLinter.addError("5", "El codigo del then no ejecutará nunca", line, column);
			}
		}
		
		
		this.thenExp.checkLinter(s);
		this.elseExp.checkLinter(s);
		return null;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public Exp optimize() {
		Exp optimizedCondition=condition.optimize();
		Exp optimizedThen=thenExp.optimize();
		Exp optimizedElse=elseExp.optimize();
		if (optimizedCondition instanceof TruthValue){
			if (((TruthValue) optimizedCondition).value){
				return optimizedThen;
			}else{
				return optimizedElse;
			}
		}
		return this;
	}

}
