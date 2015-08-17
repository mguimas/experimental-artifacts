package zoo.testing;

import junit.framework.TestCase;
import zoo.Area;
import zoo.utils.Point;

/**
 * The tests of the Zoo application.
 *
 */
public class ZooTests extends TestCase {

	public void testAreaM2() {
		Point sw = new Point(1,1);
		Point ne = new Point(6,7);
		Area area = new Area(sw, ne);
		int m2 = 0;
		// MAKE CALL TO ASSIGN THE AREA IN SQUARE METERS TO VARIABLE m2
		assertEquals(30, m2);
		assertEquals("[SW=(1,1) NE=(6,7) m2=30]", area.toString());
	}
	
}
