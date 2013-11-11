package utilities;

import java.util.BitSet;
import java.util.Random;

import trace.BitSetUtils;

import com.sun.xml.internal.ws.util.StringUtils;

public class Rand {
	private Random random;
	
	public Rand(Random rand) {
		this.random = rand;
	}

	public BitSet makeRandomBitSet(int n) {
		int numLongs = n/64+1;
		long[] longs = new long[numLongs];
		for (int i = 0; i< numLongs; i++) {
			longs[i]=random.nextLong();
		}
		BitSet result = BitSet.valueOf(longs);
		result.clear(n,n+64);
		return result;
	}
	
	String leftPad(String binaryString, int size, String string) {
		String out = "";
		for(int i = 0; i < size - binaryString.length(); i++) 
			out += string;
		
		out += binaryString;
		return out;
	}

	public boolean getNextBoolean() {
		boolean res =  random.nextBoolean();
		return res;
	}
}
