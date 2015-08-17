package zoo.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.Date;
import java.util.regex.Pattern;

public class Console {

	public static String readLine(String fmt, Object... args) {
		java.io.Console console = System.console();
		if (console != null) {
			// Running from the command line.
			return console.readLine(fmt, args);
		} else {
			// Running from Eclipse.
			/*
			 * . Note, do not type CTRL+Z since it will
			 * break Eclipse's console. If you do you will have to restart the
			 * application.
			 */
			System.out.format(fmt, args);
			BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(System.in));
			String line;
			try {
				line = bufferedReader.readLine();
				if (line == null) {
					/* 
					 * BUG: the user type CTRL+Z thus breaking the Eclipse console,
					 * which we cannot recover from. 
					 */
					System.out.println("\nHave a nice day, bye!");
					System.exit(-1);
				}
			} catch (IOException e) {
				e.printStackTrace(System.err);
				line = "";
			}
			return line;
		}
	}

	public static int readInteger(String prompt) {
		return Integer.parseInt(readLine(prompt));
	}

	public static boolean readYesOrNo(String prompt) {
		String line = readLine(prompt + " (yes or no) > ");
		Pattern pattern = Pattern.compile("yes|no", Pattern.CASE_INSENSITIVE);
		if (!pattern.matcher(line.trim()).matches())
			return false;
		return line.equals("yes");
	}

	public static Money readMoney(String prompt) {
		Money money = new Money("0");
		String symbol = money.getCurrency().getSymbol();
		int decimals = money.getNumDecimalsForCurrency();
		return new Money(readLine("%s (type \"10.%s\" for 10.00%s) > ", 
				prompt, Strings.repeatChar('0', decimals).trim(), symbol));
	}
	
	public static Point readPoint(String prompt) {
		return Point.parsePoint(readLine(prompt).trim());
	}

	public static Date readDate(String prompt) {
		try {
			return Dates.parseDate(readLine("%s (given as \"DD/MM/YYYY\") > ", prompt).trim());
		} catch (ParseException e) { }
		return null;
	}

}
