package zoo;

import zoo.utils.Point;

public final class Area {
	
	Point sw;
	Point ne;
	
	public Area(Point sw, Point ne) {
		if (sw.x <= 0 || sw.y <= 0 || ne.x <= 0 || ne.y <= 0)
			throw new IllegalArgumentException("Area coordinates must be > 0");

		if (sw.x >= ne.x || sw.y >= ne.y)
			throw new IllegalArgumentException("Invalid area coordinates");
		
		this.sw = sw;
		this.ne = ne;
	}
	
	public boolean isInside(Point p) {
		return sw.x <= p.x && p.x <= ne.x &&
			   sw.y <= p.y && p.y <= ne.y;
	}

	public boolean overlaps(Area area) {
		if (area.isInside(sw)) return true;
		if (area.isInside(ne)) return true;
		if (area.isInside(new Point(sw.x, ne.y))) return true;
		if (area.isInside(new Point(sw.y, ne.x))) return true;
		return false;
	}
	
	@Override
	public String toString() {
		return "[SW=" + sw + " NE=" + ne
			+ "]";
	}

}
