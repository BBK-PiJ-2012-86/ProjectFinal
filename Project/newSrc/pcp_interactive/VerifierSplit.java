package pcp_interactive;

import java.util.BitSet;

import structure.Eqn;
import structure.Eqns;
import trace.BitSetUtils;
import utilities.Tensor;
import utilities.Rand;

public class VerifierSplit {

	private boolean[] assTestRhs = new boolean[2];
	private BitSet[] retain = new BitSet[3];
	private boolean[] retPro = new boolean[3];
	private long time;
	private Rand rand;
	
	public VerifierSplit(Rand rand) {
		this.rand = rand;
	}
	
	public void log(String message) {
		System.out.println(message+": "+(System.currentTimeMillis()-time));
		time = System.currentTimeMillis();
		
	}
	
	
	public BitSet[] generateFirstPartOfFerificationRequest(Eqns eqns) {
		BitSet[] result = new BitSet[306];
		int assSize = eqns.getNumVars();
		for (int i = 0; i<300; i+=3) {
			result[i] = rand.makeRandomBitSet(assSize);
			result[i+1] = rand.makeRandomBitSet(assSize);
			result[i+2] = (BitSet) result[i].clone();
			result[i+2].xor(result[i+1]);
		}
		for (int i = 0; i<3; i++) {
			result[300+2*i] = rand.makeRandomBitSet(assSize);
			result[300+2*i+1] = rand.makeRandomBitSet(assSize);
			retain[i] = Tensor.compute(result[300+2*i],result[300+2*i+1], assSize);
		}
		return result;
	}
	
	public BitSet[] generateSecondPartOfVerificationRequest(Eqns eqns) {
		int crossSize = eqns.getNumVars()*eqns.getNumVars();
		BitSet[] result = new BitSet[305];
		for (int i = 0; i<300; i+=3) {
			result[i] = rand.makeRandomBitSet(crossSize);
			result[i+1] = rand.makeRandomBitSet(crossSize);
			result[i+2] = (BitSet) result[i].clone();
			result[i+2].xor(result[i+1]);
		}
		for (int i = 0; i<3; i++) {
			result[300+i] = retain[i];
		}
		
		// ass testing
		for (int i = 0; i<2; i++) {
			int numEqns = eqns.size();
			BitSet random = rand.makeRandomBitSet(numEqns);
			BitSet newCoeffs = new BitSet(crossSize);
			Boolean rhs = false;
			int k = 0;
			for (Eqn eqn : eqns) {
				if (random.get(k)) {
					newCoeffs.xor( eqn.getCoeffs());
					rhs = rhs^eqn.getRhs();
				}
				k++;
			}
			result[303+i] = newCoeffs;
			assTestRhs[i]=rhs;
		}
		return result;
	}

	
	public boolean verifyFirstPartOfRequest(BitSet pInf) {
		for (int i = 0; i<100; i++) {
			if(  (pInf.get(3*i)^pInf.get(3*i+1))  != pInf.get(3*i+2) ) {
				System.out.println(": fail");
				return false;
			}
		}
		for (int i = 0; i<3; i++) {
			retPro[i] = pInf.get(300+2*i) && pInf.get(300+2*i+1);
		}
		System.out.println(": pass");
		return true;
		
	}
	
	public boolean verifySeondPartOfRequest(BitSet pInf) {
		for (int i = 0; i<100; i++) {
			if(  (pInf.get(3*i)^pInf.get(3*i+1))  != pInf.get(3*i+2) ) {
				System.out.println(": fail(linearity)");
				return false;
			}
		}
		for (int i = 0; i<3; i++) {
			if( retPro[i] != pInf.get(300+i)) {
				System.out.println(": fail(tensor)");
				return false;
			}
		}
		for (int i = 0; i<2; i++) {
			if( pInf.get(303+i)!= assTestRhs[i]) {
				System.out.println(": fail(assignment)");
				return false;
			}
		}
		System.out.println(": pass");
		return true;
		
	}
}
