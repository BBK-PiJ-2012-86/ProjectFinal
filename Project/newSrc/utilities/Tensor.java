package utilities;

import java.util.BitSet;

import trace.BitSetUtils;

public class Tensor {

	public static BitSet compute(BitSet input1, BitSet input2, int size) {
		BitSet result = new BitSet(size*size);
		for (int i = 0; i< size; i++) {
			if(input1.get(i)) {
				for (int j = 0; j<size; j++) {
					if (input2.get(j)) {
						result.set(i*size+j);
					}
				}
			}
		}
		return result;
	}
}
