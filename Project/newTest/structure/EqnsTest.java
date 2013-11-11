package structure;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import utilities.BSM;

public class EqnsTest {

	@Test
	public void test() {
		Eqns eqns = new Eqns(3);
		eqns.add(new Eqn(3, BSM.make(1,5), false));
		eqns.add(new Eqn(3, BSM.make(0,1,5), false));
		String result = eqns.toString();
		
		assertTrue(result.contains("x1x2 + x2x3 = 0"));
		assertTrue(result.contains("x1x1 + x1x2 + x2x3 = 0"));
		assertEquals(41,result.length());
	}



}
