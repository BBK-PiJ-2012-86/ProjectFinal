package structure;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import utilities.BSM;

public class EqnTest {

	@Test
	public void testToString() {

		Eqn eqn = new Eqn(2, BSM.make(1,3), true);
		assertEquals("x1x2 + x2x2 = 1", eqn.toString());
		
		eqn = new Eqn(3, BSM.make(1,3,5), false);
		assertEquals("x1x2 + x2x1 + x2x3 = 0", eqn.toString());
		
		eqn = new Eqn(3, BSM.make(0,3,5), false);
		assertEquals("x1x1 + x2x1 + x2x3 = 0", eqn.toString());
	
	}

}
