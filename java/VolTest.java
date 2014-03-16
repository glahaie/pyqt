import static org.junit.Assert.*;
import java.util.Date;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/* VolTest.java
 * Par Guillaume Lahaie
 * 
 * Cette classe effectue des tests sur la classe Vol pour s'assurer que les méthodes de la classe
 * retourne bien les résultats attendus.
 * 
 * Dernière modification: 5 décembre 2011.
 * 
 */

public class VolTest {
	
	//Objets sur lesquels on teste.
	private Vol v1 = new Vol("Montreal", "Quebec", new Date(2011, 11, 20, 23, 00, 00), 30, 6);
	private Vol v2 = new Vol("Montreal", "Quebec", new Date(2011, 11, 20, 23, 00, 00), 30, 6);
	private Vol v3 = new Vol("New York", "Chicoutimi", new Date(2011, 12, 25, 00, 00, 00), 10, 2);
	private Vol v4 = new Vol("Montreal", "Quebec", new Date(2011, 11, 20, 23, 00, 00), 30, 6);

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testHashCode() {
		assertTrue(v1.hashCode() == v2.hashCode());
		assertFalse(v1.hashCode() == v3.hashCode());
		assertTrue((v1.hashCode() == v2.hashCode()) == v1.equals(v2));
	}

	@Test
	public void testCompareTo() {
		assertTrue(v1.compareTo(v2) == 0);
		assertTrue(v1.compareTo(v2) == v2.compareTo(v1));
		assertTrue(v1.compareTo(v3) < 0);
		assertTrue(v3.compareTo(v1) > 0);
	}

	@Test
	public void testEqualsObject() {
		assertEquals(true, v1.equals(v2));
		assertEquals(false, v1.equals(v3));
		assertTrue(v1.equals(v2) == v2.equals(v1));
		assertTrue((v1.equals(v2) && v2.equals(v3)) == v1.equals(v3));
	}

	@Test
	public void testCreerCle() {
		assertFalse(v1.creerCle().equals(v2.creerCle()));
		assertTrue(v1.creerCle().equals(v1.creerCle()));
	}

}
