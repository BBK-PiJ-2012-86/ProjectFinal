package pcp_interactive;


import java.util.BitSet;

import structure.Assignment;
import utilities.Tensor;

public class Prover {

	public static BitSet[] proverInfo(Assignment assignment, BitSet[][] request) {
		int numVars = assignment.getNumVars();
		BitSet ass = assignment.getAssSet();
		BitSet cross = Tensor.compute(ass, ass, numVars);
		BitSet[] result = new BitSet[2];
		result[0] = new BitSet(306);
		result[1] = new BitSet(305);
		for (int i = 0; i<306; i++) {
			result[0].set(i,encodeBit(ass, request[0][i]));
		}
		for (int i = 0; i<305; i++) {
			result[1].set(i,encodeBit(cross, request[1][i]));
		}
		return result;
		
	}
	
	private static boolean encodeBit(BitSet input, BitSet position) {
		position.and(input);
		return position.cardinality()%2==1;
	}
}
