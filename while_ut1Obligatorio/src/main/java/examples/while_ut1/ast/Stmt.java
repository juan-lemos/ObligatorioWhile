package examples.while_ut1.ast;

import java.util.*;

/** Categoría sintáctica de las sentencias (statements) de While, las 
	construcciones del lenguaje que modifican (potencialmente) los 
	valores de las variables en el estado del programa.
*/
public abstract class Stmt extends AstNode {

	public abstract String unparse();

	@Override public abstract String toString();

	@Override public abstract int hashCode();

	@Override public abstract boolean equals(Object obj);
	public String idFunction="";
	
	public void putIntoLineColumn(CheckStateLinter s,int line,int column){
		if (s.filaColumnaRegla2.containsKey(line)){
			s.filaColumnaRegla2.get(line).add(column);
		}else{
			ArrayList<Integer> a=new ArrayList<Integer>();
			a.add(column);
			s.filaColumnaRegla2.put(line, a);
		}
	}
	
	
	public static Stmt generate(Random random, int min, int max) {
		final int TERMINAL_COUNT = 0;
		final int NONTERMINAL_COUNT = 4;
		int i = random.nextInt(TERMINAL_COUNT + NONTERMINAL_COUNT);
		switch (i) {
		//Terminals
		//Non terminals
			case 0: return AssignmentStmt.generate(random, min-1, max-1);
			case 1: return Sequence.generate(random, min-1, max-1);
			case 2: return IfThenElse.generate(random, min-1, max-1);
			case 3: return WhileDo.generate(random, min-1, max-1);
			default: throw new Error("Unexpected error at Stmt.generate()!");
		}
	}

	public abstract State evaluate(State state);
	
	abstract public CheckState check(CheckState s);
	
	abstract public CheckStateLinter checkLinter(CheckStateLinter s);
	
	public static double toDouble(Object obj,String unparse) {
		if (obj instanceof Integer){
			return ((Integer) obj).doubleValue();
		}else if (obj instanceof Double){
			return ((Double) obj).doubleValue();
		}else{
			throw new IllegalStateException(unparse);
		}
	}
	
	abstract public int countNestingLevels();
}
