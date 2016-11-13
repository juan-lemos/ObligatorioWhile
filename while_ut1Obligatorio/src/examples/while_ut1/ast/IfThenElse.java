package examples.while_ut1.ast;

import java.util.*;

/** Representación de las sentencias condicionales.
 */
public class IfThenElse extends Stmt {
	public final Exp condition;
	public final Stmt thenBody;
	public final Stmt elseBody;

	public IfThenElse(Exp condition, Stmt thenBody, Stmt elseBody, int line, int column) {
		this.condition = condition;
		this.thenBody = thenBody;
		this.elseBody = elseBody;
		this.line = line;
		this.column = column;
	}

	@Override public String unparse() {
		return "if "+ condition.unparse() +" then { "+ thenBody.unparse() +" } else { "+ elseBody.unparse() +" }";
	}

	@Override public String toString() {
		return "IfThenElse("+ condition +", "+ thenBody +", "+ elseBody +")";
	}

	@Override public int hashCode() {
		int result = 1;
		result = result * 31 + (this.condition == null ? 0 : this.condition.hashCode());
		result = result * 31 + (this.thenBody == null ? 0 : this.thenBody.hashCode());
		result = result * 31 + (this.elseBody == null ? 0 : this.elseBody.hashCode());
		return result;
	}

	@Override public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		IfThenElse other = (IfThenElse)obj;
		return (this.condition == null ? other.condition == null : this.condition.equals(other.condition))
				&& (this.thenBody == null ? other.thenBody == null : this.thenBody.equals(other.thenBody))
				&& (this.elseBody == null ? other.elseBody == null : this.elseBody.equals(other.elseBody));
	}

	@Override
	public State evaluate(State state) {
		if (condition.evaluate(state) instanceof Boolean){
			Boolean truthValue=(Boolean) condition.evaluate(state);
			State statein=state.clone();
			if (truthValue){
				thenBody.evaluate(statein);
			} else {
				elseBody.evaluate(statein);
			}
			state.mapaValores=State.actualizarValoresMapViejo(state.mapaValores,statein.mapaValores);
			return state;
		}
		else {
			throw new IllegalStateException(this.unparse());
		}
		
	}

	@Override
	public CheckState check(CheckState s){
		Map mapaAntesIf= CheckState.clonarMapa(s.mapa);
		CheckState checkStateIfIn=new CheckState();
		checkStateIfIn.mapa=mapaAntesIf;
		Map mapaAntesIf2= CheckState.clonarMapa(s.mapa);
		CheckState checkStateElseIn=new CheckState();
		checkStateElseIn.mapa=mapaAntesIf2;
		if (condition.check(s).equals("Boolean")){
			checkStateIfIn=thenBody.check(checkStateIfIn);
			checkStateElseIn=elseBody.check(checkStateElseIn);
			s.errores.addAll(checkStateIfIn.errores);
			s.errores.addAll(checkStateElseIn.errores);
			s=CheckState.intersect(checkStateElseIn, checkStateIfIn);
			return s;
		}else{
			s.errores.add("Error en el if debe poner condicion boolean:"+this.toString());
			return s;
		}
	}


	public void compareIf(final CheckState checkState1, final CheckState checkState2) {
		for (String id : checkState1.mapa.keySet()) {
			ObjectState firstEntry = checkState1.mapa.get(id);
			if (checkState2.mapa.containsKey(id) && !checkState2.mapa.get(id).tipo.equals(firstEntry.tipo)){
				checkState1.errores.add("Error la variable "+id+" se le cambio el tipo en el IF:"+this.toString());
			}
		}
	}

	@Override
	public CheckStateLinter checkLinter(CheckStateLinter s) {
		if (countNestingLevels() > 5) CheckStateLinter.addError21(countNestingLevels(), line, column);
		if (condition.countOperators() > 7) CheckStateLinter.addError20(condition.countOperators(), line, column);
		
		Exp optimizado=condition.optimize();
		if (optimizado instanceof TruthValue){
			if (((TruthValue) optimizado).value){
				CheckStateLinter.addError5C(line, column);
			}else{
				CheckStateLinter.addError5D(line, column);
			}
		}
		
		
		ArrayList <String> tiposAceptados=new ArrayList<String>();
		tiposAceptados.add("Boolean");
		CheckStateLinter.evaluarRegla9(this.condition, s, tiposAceptados);
		
		
		condition.checkLinter(s);
		Map<String,ObjectState> clonMapaThen= CheckState.clonarMapa(s.mapa);
		CheckStateLinter cslThen=new CheckStateLinter();
		cslThen.mapa=clonMapaThen;
		thenBody.idFunction=this.idFunction;//regla 12
		thenBody.checkLinter(cslThen);
		CheckStateLinter.generateErrors(CheckStateLinter.variablesNuevas(s, cslThen));
		Map<String,ObjectState> clonMapaElse= CheckState.clonarMapa(s.mapa);
		CheckStateLinter cslElse=new CheckStateLinter();
		cslElse.mapa=clonMapaElse;
		elseBody.idFunction=this.idFunction;//regla 12
		elseBody.checkLinter(cslElse);
		CheckStateLinter.generateErrors(CheckStateLinter.variablesNuevas(s, cslElse));
		
		
		
		CheckStateLinter.setVariableUsedIfUsedInside(s, cslThen);
		CheckStateLinter.setVariableUsedIfUsedInside(s, cslElse);
		return s;
	}

	@Override
	public int getLine() {
		return line;
	}

	@Override
	public int getColumn() {
		return column;
	}

	@Override
	public int countNestingLevels() {
		int thenNestingLevels = thenBody.countNestingLevels();
		int elseNestingLevels = elseBody.countNestingLevels(); 
		return 1 + (thenNestingLevels > elseNestingLevels ? thenNestingLevels : elseNestingLevels);
	}
}
