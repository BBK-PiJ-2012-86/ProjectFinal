package structure;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Eqns extends Sized implements Iterable<Eqn> {
	private Set<Eqn> eqnSet;

	public Eqns(int numVars) {
		super(numVars);
		eqnSet = new HashSet<>();
	}
	
	public boolean add(Eqn eqn) {
		if(eqn.getNumVars()!= getNumVars()) {
			throw new IllegalArgumentException("Number of variables do not match");
		}
		return eqnSet.add(eqn);
	}
	
	public int size() {
		return eqnSet.size();
	}

	@Override
	public Iterator<Eqn> iterator() {
		return eqnSet.iterator();
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for (Eqn eqn: eqnSet) {
			builder.append(eqn);
			builder.append("\r\n");
		}
		return builder.toString();
	}
	
	

}
