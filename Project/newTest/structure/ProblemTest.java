package structure;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import utilities.BSM;

public class ProblemTest {

	@Test
	public void testToString() {
		
		Eqns eqns = new Eqns(3);
		eqns.add(new Eqn(3, BSM.make(1,5), false));
		eqns.add(new Eqn(3, BSM.make(0,1,5), false));

		Assignment ass = new Assignment(3, BSM.make(0,1));
		
		Problem problem = new Problem(3, eqns, ass);
		
		String result = problem.toString();
		
		assertTrue(result.startsWith("---"));
		assertTrue(result.endsWith("---"));
		assertTrue(result.contains(ass.toString()));
		assertEquals(75,result.length());
	}

}
