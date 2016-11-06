package examples.while_ut1.ast;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

public class Str extends Exp{
	public final String value;
	
	public Str(String value) {
		this.value = value;
	}

	@Override public String unparse() {
		return value;
	}

	@Override public String toString() {
		return "String("+ value +")";
	}

	@Override public int hashCode() {
		int result = 1;
		result = result * 31 + (this.value == null ? 0 : this.value.hashCode());
		return result;
	}

	@Override public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		Str other = (Str)obj;
		return (this.value == null ? other.value == null : this.value.equals(other.value));
	}

	public static Str generate(Random random, int min, int max) {
		SecureRandom random1 = new SecureRandom();
		return new Str(new BigInteger(130, random1).toString(32));
	}

	@Override
	public Object evaluate(State state) {
		return value;
	}
	
	
	@Override
	public String check(CheckState s){
		return "String";
	}

	@Override
	public String checkLinter(CheckStateLinter s) {
		return "String";
	}

	@Override
	public Exp optimize() {
		return this;
	}
}
