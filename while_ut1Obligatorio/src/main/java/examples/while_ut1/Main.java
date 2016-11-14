package examples.while_ut1;



import java.io.*;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import examples.while_ut1.ast.*;

public class Main {
	public static String variable="";
	
	public static void main(String[] args) throws Exception {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		System.out.print("> ");
		for (String line; (line = in.readLine()) != null;) {
			line = line.trim();
			try {
				if (line.length() > 0) {
					Stmt prog = (Stmt) (Parser.parse(line).value);
					CheckStateLinter cslint = new CheckStateLinter();
					cslint = prog.checkLinter(cslint);
					CheckStateLinter.generateErrors(cslint);
//					for (String error : CheckStateLinter.errores) {
//						System.out.println(error);
//					}
				}
			} catch (Exception err) {
				System.err.print(err);
				err.printStackTrace();
			}
		}
	}


	public static State evaluateInput(String inPut){
		String testString = inPut;
		State state = new State();
		testString = testString.trim();
		try {
			if (testString.length() > 0) {
				Stmt prog = (Stmt) (Parser.parse(testString).value);
				System.out.print("\t" + prog.evaluate(state) + "\n> ");
			}
		} catch (Exception err) {
			System.err.print(err);
			err.printStackTrace();
		}
		return state;
	}

	public static CheckState checkInput(String inPut){
		String testString = inPut;
		CheckState state = new CheckState();
		testString = testString.trim();
		try {
			if (testString.length() > 0) {
				Stmt prog = (Stmt) (Parser.parse(testString).value);
				state=prog.check(state);
				CheckState.printErrorList(state);
			}
		} catch (Exception err) {
			System.err.print(err);
			err.printStackTrace();
		}
		return state;
	}
}
