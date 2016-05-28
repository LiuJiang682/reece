package au.com.reece.addressbook.utils;

/**
 * This class partially mirrors the apache's StringUtils
 * class.
 *
 */
public class StringUtils {

	private static final int ZERO = 0;

	/**
	 * Returns true if str is
	 * 	1. Null
	 * 	2. Empty String
	 * 	3. Space String
	 * 	4. String with only whitespace character. E.g. \t\n\f\r
	 * @param str
	 * @return
	 */
	public static boolean isBlank(String str) {
		return ((null == str) || (ZERO == str.trim().length()));
	}

}
