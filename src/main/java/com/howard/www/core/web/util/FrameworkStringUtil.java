package com.howard.www.core.web.util;

/**
 * 
 * @author howard
 * 
 */
public class FrameworkStringUtil {
	public static String asString(Object obj) {
		return ((obj != null) ? obj.toString() : "");
	}

	public static boolean isNullOfString(String parameter) {
		return "".equals(asString(parameter));
	}

	public static String splitString(String originalStr, int beginIndex,
			int endIndex) {
		if (!"".equals(asString(originalStr))) {
			if (beginIndex >= 0 && endIndex > 0 && beginIndex < endIndex
					&& endIndex <= originalStr.length()) {
				return new String(originalStr.substring(beginIndex, endIndex));
			}

		}
		return asString(null);
	}

}
