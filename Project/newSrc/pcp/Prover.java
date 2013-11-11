package pcp;

import java.util.BitSet;

import structure.Assignment;
import utilities.Tensor;
import utilities.WH;

public class Prover {
	

	public static Proof constructProof(Assignment ass) {
		int numVars = ass.getNumVars();
		BitSet assSet = ass.getAssSet();
		
		BitSet assEnc = WH.encode(assSet, numVars);
		
		BitSet crossEnc = WH.encode(Tensor.compute(assSet, assSet, numVars), numVars*numVars);
		
		return new Proof(numVars,assEnc,crossEnc);
	}

}
