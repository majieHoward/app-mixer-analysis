package com.howard.www.core.tool.encoding;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @author howard
 * 
 */
public class HandleStringHelper {
	/**
	 * Whether the string contains Chinese
	 * 
	 * @param processingStr
	 * @return
	 */
	public static boolean stringContainsChinese(String processingStr) {
		boolean resultsOfHandle = false;
		Pattern paramOfPattern = Pattern.compile("[\u4e00-\u9fa5]");
		Matcher paramOfMatcher = paramOfPattern.matcher(processingStr);
		if (paramOfMatcher.find()) {
			resultsOfHandle = true;
		}
		return resultsOfHandle;
	}
}
