package trace;

import java.util.BitSet;

import utilities.WH;
import utilities.WHBitwise;

public class WHBitwiseTrace {

	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		int x = 13;
		
		System.out.println("Producing Walsh-Hadamard encoding of " + x + " by bitwise encoding");
		
		BitSet encoding = WHBitwise.encode(x, 4);
		
		System.out.println("Result: " + BitSetUtils.printBitSet(encoding, (int) Math.pow(2, 4)));
	}
}
