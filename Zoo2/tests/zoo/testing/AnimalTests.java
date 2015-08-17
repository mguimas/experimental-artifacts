package zoo.testing;

import java.util.Date;

import junit.framework.TestCase;
import zoo.Area;
import zoo.Zone;
import zoo.Zoo;
import zoo.ZooException;
import zoo.animals.Animal;
import zoo.animals.Crocodile;
import zoo.utils.Dates;
import zoo.utils.Money;
import zoo.utils.Point;

public class AnimalTests extends TestCase {

	private Zoo zoo;
	private Point sw;
	private Point ne;
	private Area area;
	private Zone zone;
	private Animal animal;
	
	public void setUp() throws ZooException {
		zoo = new Zoo(Zone.PRICE);
		sw = new Point(1,1);
		ne = new Point(randomInteger(5, 100),randomInteger(5, 100));
		area = new Area(sw, ne);
		zone = zoo.addZone(area, Crocodile.class);
		animal = zone.addAnimal();
	}
	
	public void testValidMove() throws ZooException {
		Point oldLocation = animal.getLocation();
		int dx = randomInteger(1, ne.x - sw.x);
		int dy = randomInteger(1, ne.y - sw.y);
		// MAKE CALL TO MOVE THE ANIMAL DX AND DY ALONG THE X-AXIS AND Y-AXIS
		Point newLocation = animal.getLocation();
		assertTrue(zone.isInside(newLocation));
		assertEquals(oldLocation.x + dx, newLocation.x);
		assertEquals(oldLocation.y + dy, newLocation.y);
	}

	/**
	 * @return a random number n such that from <= n <= to
	 */
	private int randomInteger(int from, int to) {
		return from + (int) (Math.random() * (to - from + 1));
	}
	
	public void testInvalidMove() throws ZooException {
		Point oldLocation = animal.getLocation();
		int dx = ne.x - sw.x + 1;
		int dy = ne.y - sw.y + 1;
		try {
			// MAKE CALL TO MOVE THE ANIMAL DX AND DY ALONG THE X-AXIS AND Y-AXIS
			fail("Invalid move outside zone not detected");
		} catch (Exception e) {
			assertEquals(ZooException.class, e.getClass());
			Point newLocation = animal.getLocation();
			assertEquals(oldLocation, newLocation);
		}
	}

	public void testFelineTax() {
		// UNCOMMENT THESE TWO LINES
		// Zone zone = new Zone(area, Lion.class);
		// Feline feline = new Lion(zone);
		Date start = Dates.newDate(2011, 1, 1);
		Date end = Dates.newDate(2011, 1, 11);
		Money tax = null;
		// MAKE CALL TO COMPUTE THE TAX FOR THE FELINE BETWEEN THE TWO DATES
		assertEquals(new Money("50.00"), tax);		
	}
	
}
