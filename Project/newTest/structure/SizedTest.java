package structure;

import static org.junit.Assert.*;

import org.junit.Test;

public class SizedTest {

	@Test (expected = IllegalArgumentException.class)
	public void testSizedIllegal() {
		@SuppressWarnings("unused")
		Sized sized = new Sized(0){};
	}

	@Test
	public void testGetNumVars() {
		Sized sized = new Sized(3){};
		int actual = sized.getNumVars();
		int expected = 3;
		
		assertEquals(expected,actual);
	}

}
