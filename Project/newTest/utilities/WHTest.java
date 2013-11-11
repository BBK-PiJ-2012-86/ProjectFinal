package utilities;

import static org.junit.Assert.*;

import java.util.BitSet;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

/*
 * Deterministic testing of the WH encoding
 */
public class WHTest {

	private BitSet test1;
	private BitSet test2;
	private BitSet test3;
	private BitSet test4;

	@Before
	public void setUp() throws Exception {
		test1 = BSM.make(1);
		test2 = BSM.make();
		test3 = BSM.make(0,2,3);
		test4 = BSM.make(1,2,4);
	}

	@Test
	public void testEncode() {
		BitSet result1 = BSM.make(1,3);
		BitSet result2 = BSM.make();
		BitSet result3 = BSM.make(1, 2, 5, 6, 8, 11, 12, 15);
		BitSet result4 = BSM.make(1, 3, 4, 6, 8, 10, 13, 15, 17, 19, 20, 22, 24, 26, 29, 31);
		assertEquals(result1, WH.encode(test1, 2));
		assertEquals(result2, WH.encode(test2, 3));
		assertEquals(result3, WH.encode(test3, 4));
		assertEquals(result4, WH.encode(test4, 5));
		
		try {
			assertEquals(result2, WH.encode(test1, 0));
			fail("should throw here");
		}
		catch(IllegalArgumentException ex) {
			//correct
		}
		
	}

	@Test 
	public void testDecode() {
		Random rand = new Random();
		assertEquals(test1, WH.decode(WH.encode(test1, 2), 4, rand));
		assertEquals(test2, WH.decode(WH.encode(test2, 3), 8, rand));
		assertEquals(test3, WH.decode(WH.encode(test3, 4), 16, rand));
		assertEquals(test4, WH.decode(WH.encode(test4, 5), 32, rand));
	}
}
