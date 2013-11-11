package trace;

import java.util.BitSet;

import utilities.WH;

public class WHIncrDTrace {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		BitSet x = new BitSet();

		x.set(1);
		x.set(3);
		
		System.out.println("Producing Walsh-Hadamard encoding of " + BitSetUtils.printBitSet(x, 4) + " by incremenal doubling");
		
		BitSet encoding = WH.encode(x, 4);
		
		System.out.println("Result: " + BitSetUtils.printBitSet(encoding, (int) Math.pow(2, 4)));
	}

}
