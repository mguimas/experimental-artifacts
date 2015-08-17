package zoo.utils;

public class Strings {

	public static String repeatChar(char c, int num) {
		if (num <= 0)
			return "";
		else {
			char[] buf = new char[num];
			for (int i = 0; i < num; ++i)
				buf[i] = c;
			return new String(buf);
		}
	}

}
