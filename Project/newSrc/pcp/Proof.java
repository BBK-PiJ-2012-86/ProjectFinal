package pcp;

import java.util.BitSet;

public class Proof extends structure.Sized {
	private BitSet assEnc;
	private BitSet crossEnc;
	
	public Proof(int numVars, BitSet assEnc, BitSet crossEnc) {
		super(numVars);
		this.assEnc = assEnc;
		this.crossEnc = crossEnc;
	}
	
	public BitSet getAssEnc() {
		return assEnc;
	}

	public BitSet getCrossEnc() {
		return crossEnc;
	}
	
	public void setAssEnc(BitSet assEnc) {
		this.assEnc = assEnc;
	}

	public void setCrossEnc(BitSet crossEnc) {
		this.crossEnc = crossEnc;
	}
	
}
