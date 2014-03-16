import junit.framework.*;
import org.junit.After;
import org.junit.Before;

/* PassagerTest.java
 * Par Guillaume Lahaie
 * 
 * Cette classe teste certaines méthodes de la classe passager pour s'assurer qu'elle
 * retourne bien les résultats attendus.
 * 
 * Dernière modification: 5 décembre 2011.
 * 
 */

public class PassagerTest extends TestCase {
	
	private Passager p1 = new Passager("Tremblay", "William");
	private Passager p2 = new Passager("Daoust", "Thomas");
	private Passager p3 = new Passager("Ouellette", "Camille");
	private Passager p4 = new Passager("Ouellette", "Camille");
	private Passager p5 = new Passager("Ouellette", "Camille");
	

	@Before
	public void setUp() throws Exception {
		super.setUp();
	}

	@After
	public void tearDown() throws Exception {
		super.tearDown();
	}

	
	//On vérifie la symétrie, la transivitié et l'associativité.
	public void testEquals() {
		assertEquals(true, p1.equals(p1));
		assertEquals(false, p1.equals(p2));
		assertEquals(false, p1.equals(null));
		assertEquals(true, p3.equals(p4)&& p4.equals(p3));
		assertTrue((p3.equals(p4)&& p4.equals(p5)) == (p3.equals(p5)));
	}
	
	public void testHashCode() {
		assertEquals(p1.hashCode(), (37*"Tremblay".hashCode() + "William".hashCode()));
		assertFalse( p2.hashCode() == ("Daoust".hashCode() + "Thomas".hashCode()));
		assertTrue((p3.hashCode() == p4.hashCode()) == p3.equals(p4));
		
	}
	
	public void testCompareTo() {
		assertTrue(p1.compareTo(p2) > 0);
		assertTrue(p2.compareTo(p1) < 0);
		assertTrue(p3.compareTo(p4) == 0);
		assertTrue((p3.compareTo(p4) == 0) == p3.equals(p4));
		assertFalse(p2.compareTo(p3) > 0);
	}

}
