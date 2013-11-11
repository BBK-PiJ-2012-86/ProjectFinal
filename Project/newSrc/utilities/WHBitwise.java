package utilities;

import java.util.BitSet;

import trace.BitSetUtils;

public class WHBitwise {
	
	public static BitSet encode(int input, int inLength) {
		if(inLength < 1)  {
			throw new IllegalArgumentException("Length to be encoded must be at least 1 bit, but tried encoding " + inLength);
		}
		System.out.println("...Encoding length " + inLength + " integer bitmask: " + leftPad( Integer.toBinaryString( input ), 32, "0" ));
		int outLength = (int)Math.pow(2, inLength);
		BitSet output = new BitSet(outLength);
		
		for(int i = 0; i < outLength; i++) {
			int x = input & i;
			if (parity(x) == 1)
				output.set(i);
		}
		
		System.out.println("...Encoded to: " + BitSetUtils.printBitSet(output, outLength));
		return output;
	}
	
	public static int binlog( int bits ) // returns 0 for bits=0
	{
	    int log = 0;
	    if( ( bits & 0xffff0000 ) != 0 ) { bits >>>= 16; log = 16; }
	    if( bits >= 256 ) { bits >>>= 8; log += 8; }
	    if( bits >= 16  ) { bits >>>= 4; log += 4; }
	    if( bits >= 4   ) { bits >>>= 2; log += 2; }
	    return log + ( bits >>> 1 );
	}
	
	public static int parity(int v) {
		v ^= v >> 16;
		v ^= v >> 8;
		v ^= v >> 4;
		v &= 0xf;
		return (0x6996 >> v) & 1;
	}
	
	private static String leftPad(String binaryString, int size, String string) {
		String out = "";
		for(int i = 0; i < size - binaryString.length(); i++) 
			out += string;
		
		out += binaryString;
		return out;
	}

}
