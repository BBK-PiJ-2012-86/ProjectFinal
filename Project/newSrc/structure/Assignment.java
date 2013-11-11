package structure;

import java.util.BitSet;
import java.util.Random;

import utilities.Rand;

public class Assignment extends Sized {
	private BitSet assSet;
	
	public Assignment(int numVars, BitSet assSet) {
		super(numVars);
		this.assSet = assSet;
	}
	
	public BitSet getAssSet() {
		return assSet;
	}
	
	public void setAssSet(BitSet assSet) {
		this.assSet = assSet;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i<getNumVars(); i++) {
			builder.append("x");
			builder.append(i+1);
			builder.append(" = ");
			builder.append(assSet.get(i)?"1":"0");
			if(i<getNumVars()-1)
				builder.append(", ");
		}
		return builder.toString();
	}

	public static Assignment make(int numVars, Rand rand) {
		Assignment ass = new Assignment(numVars, rand.makeRandomBitSet(numVars));
		return ass;
	}
	
	public static Assignment make(int numVars) {
		return make(numVars, new Rand(new Random()));
	}

}
