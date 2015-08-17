package zoo.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * USE ONLY IN THIS EXPERIENCE.
 * Real applications should use {@link http://joda-time.sourceforge.net}
 */
public class Dates {
	
	public static Date today() {
		return new Date();
	}
	
	/**
	 * @param year
	 * @param month - between 1 and 12
	 * @param day - between 1 and 31
	 * @return the date corresponding to the inputs.
	 */
	public static Date newDate(int year, int month, int day) {
		return new GregorianCalendar(year, month - 1, day).getTime();
	}

	public static long daysBetween(Date start, Date end) {
		long millisBetween = Math.abs(end.getTime() - start.getTime());
		return (int) Math.round(millisBetween / (1000 * 60 * 60 * 24));
	}

	public static int yearsBetween(Date start, Date end) {
		Calendar s = Calendar.getInstance();
		s.setTime(start);
		Calendar e = Calendar.getInstance();
		e.setTime(end);
		return yearsBetween(s, e);
	}

	public static int yearsBetween(Calendar start, Calendar end) {
		int years = end.get(Calendar.YEAR) - start.get(Calendar.YEAR);
		if ((start.get(Calendar.MONTH) > end.get(Calendar.MONTH))
				|| (start.get(Calendar.MONTH) == end.get(Calendar.MONTH) && start
						.get(Calendar.DAY_OF_MONTH) > end
						.get(Calendar.DAY_OF_MONTH))) {
			years--;
		}
		return years;
	}

	public static Date parseDate(String s) throws ParseException {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		df.setLenient(false);
		return df.parse(s);
	}

}
