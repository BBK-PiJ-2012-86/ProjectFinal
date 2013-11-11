package utilities;

import java.util.BitSet;
import java.util.Random;

import trace.BitSetUtils;

public class WH {
	
	public static BitSet encode(BitSet input, int inLength) {
		if(inLength < 1)  {
			throw new IllegalArgumentException("Length to be encoded must be at least 1 bit, but tried encoding " + inLength);
		}
		int outLength = (int)Math.pow(2, inLength);
		BitSet output = new BitSet(outLength);
		int bitsDone = 2;
		
		if(input.get(inLength-1)) {
			output.set(1);
		}
		for(int i = inLength-2; i >= 0; i--) {	//remaining bits of input
			boolean flip = input.get(i);
			for(int j = 0; j <bitsDone ; j++) {	//bits of output already done
				boolean val = output.get(j);
				output.set(j + bitsDone, val ^ flip);
			}
			
			bitsDone *= 2;
		}
		return output;
	}
	
	public static BitSet decode(BitSet input, int length, Random rand) {
		if(length < 2)  {
			throw new IllegalArgumentException("Length to be 2 bits, but tried decoding " + length);
		}
		
		int decodedLength = (int) (Math.log(length)/Math.log(2));
		BitSet result = new BitSet(decodedLength);
		
		for(int i = decodedLength; i > 0 ; i--) {
			int firstBit = rand.nextInt(length);
			int secondBit = firstBit ^ (1 << (i - 1));
			
			result.set(decodedLength - i, input.get(firstBit) != input.get(secondBit));
		}
		
		return result;
	}

}
