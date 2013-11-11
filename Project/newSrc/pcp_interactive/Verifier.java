package pcp_interactive;

import java.util.BitSet;
import java.util.Random;

import structure.Eqn;
import structure.Eqns;
import utilities.Tensor;
import utilities.Rand;

public class Verifier {
	private boolean[] assTestRhs = new boolean[2];
	private long time;
	private final Rand rand;
	
	public Verifier(Rand rand) {
		this.rand = rand;
	}
	
	public void log(String message) {
		System.out.println(message+": "+(System.currentTimeMillis()-time));
		time = System.currentTimeMillis();
		
	}
	
	
	public BitSet[][] request(Eqns eqns) {
		log("BEGIN REQUEST");
		BitSet[][] result = new BitSet[2][];
		result[0] = new BitSet[306];
		result[1] = new BitSet[305];
		int assSize = eqns.getNumVars();
		int crossSize = assSize* assSize;
		// for linearity of ass encoding
		linear(result[0], assSize);
		log("FIRST LINEARITY");
		// for linearity of cross encoding
		linear(result[1], crossSize);
		log("LINEARITY DONE");
		// for rel testing
		for (int i = 0; i<3; i++) {
			result[0][300+2*i] = rand.makeRandomBitSet(assSize);
			result[0][300+2*i+1] = rand.makeRandomBitSet(assSize);
			result[1][300+i] = Tensor.compute(result[0][300+2*i],result[0][300+2*i+1], assSize);
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
			result[1][303+i] = newCoeffs;
			assTestRhs[i]=rhs;
		}
		log("END REQUEST");
		return result;
	}


	private void linear(BitSet[] amend, int size) {
		for (int i = 0; i<300; i+=3) {
			amend[i] = rand.makeRandomBitSet(size);
			amend[i+1] = rand.makeRandomBitSet(size);
			amend[i+2] = (BitSet) amend[i].clone();
			amend[i+2].xor(amend[i+1]);
		}
		
	}
	
	public boolean verify(BitSet[] pInf) {
		for (int j = 0; j<2; j++) {
			for (int i = 0; i<100; i++) {
				if(  (pInf[j].get(3*i)^pInf[j].get(3*i+1))  != pInf[j].get(3*i+2) ) {
					return false;
				}
			}
		}
		for (int i = 0; i<3; i++) {
			if( (pInf[0].get(300+2*i)&&pInf[0].get(300+2*i+1)) != pInf[1].get(300+i)) {
				return false;
			}
		}
		for (int i = 0; i<2; i++) {
			if( pInf[1].get(303+i)!= assTestRhs[i]) {
				return false;
			}
		}
		return true;
		
	}

}
