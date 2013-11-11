package structure;

import java.util.BitSet;

import utilities.Tensor;
import utilities.Rand;

public class Problem extends Sized {

	private Eqns eqns;
	private Assignment ass;
	
	public Problem(int numVars, Eqns eqns, Assignment ass) {
		super(numVars);
		setEqns(eqns);
		setAss(ass);
	}
	
	public Eqns getEqns() {
		return eqns;
	}
	public void setEqns(Eqns eqns) {
		if(eqns.getNumVars()!=getNumVars()) {
			throw new IllegalArgumentException("Number of variables do not match");
		}
		this.eqns = eqns;
	}
	public Assignment getAss() {
		return ass;
	}
	public void setAss(Assignment ass) {
		if(ass.getNumVars()!=getNumVars()) {
			throw new IllegalArgumentException("Number of variables do not match");
		}
		this.ass = ass;
	}

	@Override
	public String toString() {
		return "---\r\n"+eqns.toString()+"\r\n"+ass.toString()+"\r\n---";
	}
	
	public static Problem makeQuadeq(int numVars, int numEqns, Rand rand) {
		if (numEqns>3*numVars) {
			throw new IllegalArgumentException("Do you really need "+numEqns+" eqns with "+numVars+" vars?");
		}
		int tensorSize = numVars*numVars;
		
		Assignment ass = Assignment.make(numVars, rand);
		BitSet tensor = Tensor.compute(ass.getAssSet(), ass.getAssSet(), numVars);
		
		Eqns eqns = new Eqns(numVars);
		
		int foundEqns = 0;
		BitSet lhs, check;
		Eqn eqn = null;
		while(foundEqns<numEqns) {
			lhs = rand.makeRandomBitSet(tensorSize);
			check = (BitSet) lhs.clone();
			check.and(tensor);
			eqn = new Eqn(numVars, lhs, check.cardinality()%2==1);
			if (eqns.add(eqn)) {foundEqns++;}
		}
		return new Problem(numVars, eqns, ass);
	}
	
	public static Problem makeProblem(int numVars, int numEqns, Rand rand) {
		Eqns eqns = new Eqns(numVars);
		int foundEqns = 0;
		while(foundEqns<numEqns) {
			if (eqns.add(Eqn.make(numVars, rand))) {foundEqns++;}
		}
		return new Problem(numVars, eqns, Assignment.make(numVars));
	}
	
	public boolean isCorrect() {	//naive
		BitSet cross = Tensor.compute(ass.getAssSet(), ass.getAssSet(), getNumVars());
		BitSet check;
		for (Eqn eqn : eqns) {
			check = (BitSet) eqn.getCoeffs().clone();
			check.and(cross);
			if((check.cardinality()%2==1) != eqn.getRhs()) {
				return false;
			}
		}
		return true;
	}

}
