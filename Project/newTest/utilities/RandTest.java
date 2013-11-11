package utilities;

import static org.junit.Assert.*;

import java.util.BitSet;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

public class RandTest {

	private Random random;
	private Rand rand;

	@Before
	public void setUp() throws Exception {
		random = new Random(123456);
		rand = new Rand(random);
	}

	@Test
	public void testMakeRandomBitset() {
		BitSet result1 = BSM.make(1,2,3);
		BitSet result2 = BSM.make(1, 3, 6, 7, 11, 12, 14, 15, 17, 20, 22, 27, 28, 29, 30, 33, 34, 36, 38, 39, 40, 41, 43, 46, 47, 48, 49, 51, 55, 56, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 72, 73, 74, 75, 76, 77, 79);
		assertEquals(result1, rand.makeRandomBitSet(4));
		assertEquals(result2, rand.makeRandomBitSet(80));
	}

	@Test
	public void testGetNextBoolean() {
		assertFalse(rand.getNextBoolean());
		assertTrue(rand.getNextBoolean());
	}

}
