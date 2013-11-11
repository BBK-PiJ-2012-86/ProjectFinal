package structure;

import java.util.BitSet;

import utilities.Rand;

public class EqnPlus extends Eqn {
		
	public EqnPlus(int numVars, BitSet coeffs, boolean rhs) {
		super(numVars, coeffs, rhs);
		niceify();
	}
	public EqnPlus(Eqn eqn) {
		this(eqn.getNumVars(),eqn.getCoeffs(),eqn.getRhs());
	}

	@Override
	public void setCoeffs(BitSet coeffs) {
		super.setCoeffs(coeffs);
		niceify();
	}

	private void niceify() {
		int numVars = getNumVars();
		BitSet coeffs = getCoeffs();
		if (coeffs == null) {return;}
		BitSet newCoeffs = new BitSet(numVars*numVars);
		int n, m, diag;
		for (int i = 0; i<numVars; i++) {
			diag = i*(numVars+1);
			newCoeffs.set(diag, coeffs.get(diag));
			for (int j = i+1; j<numVars; j++) {
				n = i*numVars+j;
				m = j*numVars+i;
				newCoeffs.set(n,coeffs.get(n)^coeffs.get(m));
			}
		}
		super.setCoeffs(newCoeffs);
	}
	
	public static EqnPlus make(int numVars, Rand rand) {
		return new EqnPlus(Eqn.make(numVars, rand));
	}

}
