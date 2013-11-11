package structure;

import static org.junit.Assert.*;

import org.junit.Test;

import utilities.BSM;


public class EqnPlusTest {
	
	@Test
	public void testConstructor() {
		
		EqnPlus eqn1 = new EqnPlus(3, BSM.make(1,3,5), false);
		EqnPlus eqn2 = new EqnPlus(3, BSM.make(5), false);
		assertEquals(eqn1, eqn2);
		
		eqn1 = new EqnPlus(3, BSM.make(0,3,5), false);
		eqn2 = new EqnPlus(3, BSM.make(0,1,5), false);
		assertEquals(eqn1, eqn2);

	}

	@Test
	public void testToStrings() {
		EqnPlus eqn = new EqnPlus(2, BSM.make(1,3), true);
		assertEquals("x1x2 + x2x2 = 1", eqn.toString());
		
		eqn = new EqnPlus(3, BSM.make(1,3,5), false);
		assertEquals("x2x3 = 0", eqn.toString());
		
		eqn = new EqnPlus(3, BSM.make(0,3,5), false);
		assertEquals("x1x1 + x1x2 + x2x3 = 0", eqn.toString());
	}
	
	@Test
	public void testSetCoeffs() {
		EqnPlus eqn1 = new EqnPlus(3, null, false);
		eqn1.setCoeffs(BSM.make(1,3,5));
		EqnPlus eqn2 = new EqnPlus(3, BSM.make(5), false);
		assertEquals(eqn1, eqn2);
		
	}
	

}
