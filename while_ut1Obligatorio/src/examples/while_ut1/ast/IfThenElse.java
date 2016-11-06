package examples.while_ut1.ast;

import java.util.*;

/** Representaci√≥n de las sentencias condicionales.
 */
public class IfThenElse extends Stmt {
	public final Exp condition;
	public final Stmt thenBody;
	public final Stmt elseBody;

	public IfThenElse(Exp condition, Stmt thenBody, Stmt elseBody) {
		this.condition = condition;
		this.thenBody = thenBody;
		this.elseBody = elseBody;
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

	public static IfThenElse generate(Random random, int min, int max) {
		BExp condition; Stmt thenBody; Stmt elseBody; 
		condition = BExp.generate(random, min-1, max-1);
		thenBody = Stmt.generate(random, min-1, max-1);
		elseBody = Stmt.generate(random, min-1, max-1);
		return new IfThenElse(condition, thenBody, elseBody);
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
		Exp optimizado=condition.optimize();
		if (optimizado instanceof TruthValue){
			if (((TruthValue) optimizado).value){
				CheckStateLinter.addError("5", "El codigo del else no ejecutar· nunca", line, column);
			}else{
				CheckStateLinter.addError("5", "El codigo del then no ejecutar· nunca", line, column);
			}
		}
		
		
		condition.checkLinter(s);
		thenBody.checkLinter(s);
		elseBody.checkLinter(s);
		return null;
	}

}
