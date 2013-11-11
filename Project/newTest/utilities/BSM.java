package utilities;

import java.util.BitSet;

public class BSM {
	public static BitSet make(int... ones) {
		BitSet result = new BitSet();
		for (int i : ones) {
			result.set(i);
		}
		return result;
	}

}
