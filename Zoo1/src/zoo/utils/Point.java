package zoo.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Point {

	private static final String INTEGER_POINT_REGEX = "\\(\\s*(-?\\d+)\\s*,\\s*(-?\\d+)\\s*\\)";
	
	public final int x;
	public final int y;
	
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public static Point parsePoint(String s) {
		Pattern pattern = Pattern.compile(INTEGER_POINT_REGEX);
		Matcher m = pattern.matcher(s);
		try {
			if (m.matches()) {
				int x = Integer.parseInt(m.group(1));
				int y = Integer.parseInt(m.group(2));
				return new Point(x, y);
			}
		} catch(NumberFormatException e) { }
		throw new PointFormatException();
	}
	
	@Override
	public int hashCode() {
		return 31 * (x + 1) + y;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Point other = (Point) obj;
		return x == other.x && y == other.y;
	}

	@Override
	public String toString() {
		return "(" + x + ", " + y + ")";
	}

}
