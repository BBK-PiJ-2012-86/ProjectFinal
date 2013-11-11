package structure;

public abstract class Sized {
	private int numVars;
	
	public Sized(int numVars) {
		if (numVars<1) {
			throw new IllegalArgumentException("Must have at least one variable");
		}
		this.numVars = numVars;
	}
	
	public int getNumVars() {
		return numVars;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + numVars;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Sized))
			return false;
		Sized other = (Sized) obj;
		if (numVars != other.numVars)
			return false;
		return true;
	}

	
}
