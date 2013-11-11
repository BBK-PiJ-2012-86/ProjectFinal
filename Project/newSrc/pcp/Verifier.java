package pcp;

import java.util.BitSet;
import java.util.Random;

import structure.Eqn;
import structure.Eqns;
import trace.BitSetUtils;
import utilities.Pair;
import utilities.Rand;

public class Verifier {
	private final Random rand;
	private final Rand randUtil;
	
	public Verifier(Random rand) {
		this.rand = rand;
		randUtil = new Rand(rand);
	}
	
	public boolean verify(Proof proof, Eqns eqns) {
		int numVars = proof.getNumVars();
		BitSet assEnc = proof.getAssEnc();
		BitSet crossEnc = proof.getCrossEnc();
		int assEncSize = (int) Math.pow(2,numVars);
		int crossEncSize = (int) Math.pow(2,numVars*numVars);
				
		
		for (int i = 0; i<100; i++) {
			if(!testLinearity(assEnc, assEncSize)) return false;
		}
		for (int i = 0; i<100; i++) {
			if(!testLinearity(crossEnc, crossEncSize)) return false;
		}
		for (int i = 0; i<3; i++) {
			if(!testCross(numVars, assEnc, crossEnc, assEncSize)) return false;
		}
		
		for (int i = 0; i<2; i++) {
			if(!testAss(eqns, crossEnc, crossEncSize)) return false;
		}
		
		return true;
	}

	boolean testAss(Eqns eqns, BitSet crossEnc, int crossEncSize) {
		int numEqns = eqns.size();
		BitSet random = randUtil.makeRandomBitSet(numEqns);
		BitSet newCoeffs = new BitSet(crossEncSize);
		
		Pair<BitSet, Boolean> testEqns = new Pair<BitSet, Boolean>(newCoeffs, false);
		
		computeCompositeEquationForAssignmentTest(eqns, random, testEqns);
		
		BitSet reversed = reverse(newCoeffs,eqns.getNumVars()*eqns.getNumVars());
		int val = newCoeffs.cardinality() > 0 ? (reversed.cardinality() > 0 ? (int)reversed.toLongArray()[0] : 0) : 0;

		if(crossEnc.get(val) !=testEqns.right) {
			return false;
		}
		else {
			return true;
		}
	}

	private void computeCompositeEquationForAssignmentTest(Eqns eqns,
			BitSet random, Pair<BitSet, Boolean> testEqns) {
		int k = 0;
		for (Eqn eqn : eqns) {
			if (random.get(k)) {
				testEqns.left.xor( eqn.getCoeffs());
				testEqns.right = testEqns.right^eqn.getRhs();
			}
			k++;
		}
	}

	boolean testCross(int numVars, BitSet assEnc, BitSet crossEnc, int assEncSize) {
		int x = rand.nextInt(assEncSize);
		int y = rand.nextInt(assEncSize);
		int xCrossy = 0;	//need a better way of calculating this..
		
		for (int j = 0; j< numVars; j++) {
			xCrossy = (xCrossy << numVars) | (((x & 1<<(numVars - j - 1)) == 0) ? 0 : y); 
		}
		
		return testCrossCheck(assEnc, crossEnc, x, y, xCrossy);
	}

	private boolean testCrossCheck(BitSet assEnc, BitSet crossEnc, int x,
			int y, int xCrossy) {
		if((assEnc.get(x)&&assEnc.get(y)) != crossEnc.get(xCrossy)) {
			return false;
		}
		return true;
	}

	boolean testLinearity(BitSet assEnc, int assEncSize) {
		int x = rand.nextInt(assEncSize);
		int y = rand.nextInt(assEncSize);
		return testLinearityCheck(assEnc, x, y);
	}

	private boolean testLinearityCheck(BitSet assEnc, int x, int y) {
		if( assEnc.get(x)^assEnc.get(y) != assEnc.get(x^y)) {
			return false;
		}
		return true;
	}
	
	private static BitSet reverse(BitSet bs, int numVars) { //needs revisiting
		BitSet result = new BitSet(numVars);
		
		for (int i = 0; i < numVars; i++) {
			result.set(i, bs.get(numVars - i - 1));
		}
		
		return result;
	}

}
