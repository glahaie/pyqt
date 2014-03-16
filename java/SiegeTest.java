import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
/* SiegeTest.java
 * Par Guillaume Lahaie
 * 
 * Cette classe effectue des tests sur la classe Siege pour s'assurer 
 * que les méthodes de la classe retourne bien les résultats attendus.
 * 
 * Dernière modification: 7 décembre 2011.
 * 
 */

public class SiegeTest {
	
	private Siege s1 = new Siege(1, "A");
	private Siege s2 = new Siege(1, "A");
	private Siege s3 = new Siege(1, "C");
	private Siege s4 = new Siege(2, "A");
	private Siege s5 = new Siege(10, "A");

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testHashCode() {
		assertTrue(s1.hashCode() == s2.hashCode());
		assertFalse(s1.hashCode() == s3.hashCode());
		assertFalse(s1.hashCode() == s5.hashCode());
	}

	@Test
	public void testCompareTo() {
		assertTrue(s1.compareTo(s2) == 0);
		assertTrue(s1.compareTo(s3) < 0);
		assertTrue(s3.compareTo(s1) > 0);
		assertTrue(s1.compareTo(s4) < 0);
		assertTrue(s1.compareTo(s5) < 0);
	}

	@Test
	public void testEqualsObject() {
		assertEquals(s1.equals(s2), true);
		assertEquals(s1.equals(s3), false);
		assertEquals(s2.equals(s4), false);
	}

}
