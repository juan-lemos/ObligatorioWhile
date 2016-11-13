package examples.while_ut1.ast;

import java.util.*;

/** Representación de las iteraciones while-do.
 */
public class WhileDo extends Stmt {
	public final Exp condition;
	public final Stmt body;

	public WhileDo(Exp condition, Stmt body) {
		this.condition = condition;
		this.body = body;
	}

	@Override public String unparse() {
		return "while "+ condition.unparse() +" do { "+ body.unparse() +" }";
	}

	@Override public String toString() {
		return "WhileDo("+ condition +", "+ body +")";
	}

	@Override public int hashCode() {
		int result = 1;
		result = result * 31 + (this.condition == null ? 0 : this.condition.hashCode());
		result = result * 31 + (this.body == null ? 0 : this.body.hashCode());
		return result;
	}

	@Override public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		WhileDo other = (WhileDo)obj;
		return (this.condition == null ? other.condition == null : this.condition.equals(other.condition))
				&& (this.body == null ? other.body == null : this.body.equals(other.body));
	}

	public static WhileDo generate(Random random, int min, int max) {
		BExp condition; Stmt body; 
		condition = BExp.generate(random, min-1, max-1);
		body = Stmt.generate(random, min-1, max-1);
		return new WhileDo(condition, body);
	}

	@Override
	public State evaluate(State state) {
		if (condition.evaluate(state) instanceof Boolean){
			State statein=state.clone();
			while ((Boolean)condition.evaluate(statein)){
				body.evaluate(statein);
			}
			state.mapaValores=State.actualizarValoresMapViejo(state.mapaValores,statein.mapaValores);

			return state;
		}
		else{
			throw new IllegalStateException(this.unparse());
		}
	}

	@Override
	public CheckState check(CheckState s){
		Map mapaAntesWhile= CheckState.clonarMapa(s.mapa);
		CheckState checkStateWhileIn=new CheckState();
		checkStateWhileIn.mapa=mapaAntesWhile;
		if (condition.check(s).equals("Boolean")){
			checkStateWhileIn=body.check(checkStateWhileIn);
			s.errores.addAll(checkStateWhileIn.errores);
			return s;
		}else{
			s.errores.add("Error en el if debe poner condicion boolean:"+this.toString());
			return s;
		}
	}


	public void compare(final CheckState originalCheckState, final CheckState newCheckState) {
		for (String id : originalCheckState.mapa.keySet()) {
			ObjectState firstEntry = originalCheckState.mapa.get(id);
			if (!newCheckState.mapa.containsKey(id)){
				originalCheckState.errores.add("Error la variable "+id+" se le cambio el tipo en el while:"+this.toString());
			}
		}

	}

	@Override
	public CheckStateLinter checkLinter(CheckStateLinter s) {
		if (countNestingLevels() > 5) CheckStateLinter.addError21(countNestingLevels(), line, column);
		if (condition.countOperators() > 7) CheckStateLinter.addError20(condition.countOperators(), line, column);
		
		
		Exp optimizado=condition.optimize();
		if (optimizado instanceof TruthValue){
			if (!((TruthValue) optimizado).value){
				CheckStateLinter.addError5B(line, column);
			}
		}

		ArrayList <String> tiposAceptados=new ArrayList<String>();
		tiposAceptados.add("Boolean");
		CheckStateLinter.evaluarRegla9(this.condition, s, tiposAceptados);

		Map<String,ObjectState> mapaAntesWhile= CheckState.clonarMapa(s.mapa);
		CheckStateLinter checkStateLinterWhileIn=new CheckStateLinter();
		checkStateLinterWhileIn.mapa=mapaAntesWhile;
		
		body.idFunction=this.idFunction; //regla 12
		checkStateLinterWhileIn=body.checkLinter(checkStateLinterWhileIn);
		CheckStateLinter.generateErrors(CheckStateLinter.variablesNuevas(s, checkStateLinterWhileIn));
		
		return s;
		
	}

	@Override
	public int getLine() {
		return 0;
	}

	@Override
	public int getColumn() {
		return 0;
	}
	
	@Override
	public int countNestingLevels() {
		return 1 + body.countNestingLevels();
	}
}
