package trace;

import java.util.BitSet;

public class BitSetUtils {

	public static String printBitSet(BitSet input, int length) {
		StringBuffer sb = new StringBuffer(length*3);
		sb.append('{');
		for(int i = 0; i < length; i++) {
			if(i > 0) {
				sb.append(',');
			}
			sb.append(input.get(i) ? '1' : '0');
		}
		sb.append('}');
		
		return sb.toString();
	}

}
