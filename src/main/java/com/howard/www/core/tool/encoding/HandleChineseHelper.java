package com.howard.www.core.tool.encoding;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HandleChineseHelper {
	private static boolean stringContainsChiness(char processingChar) {
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(processingChar);
		if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
				|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
				|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
				|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
			return true;
		}
		return false;
	}

	public static boolean isMessyCode(String processingStr) {
		Pattern p = Pattern.compile("\\s*|\t*|\r*|\n*");
		Matcher m = p.matcher(processingStr);
		String after = m.replaceAll("");
		String temp = after.replaceAll("\\p{P}", "");
		char[] ch = temp.trim().toCharArray();
		float chLength = 0;
		float count = 0;
		for (int i = 0; i < ch.length; i++) {
			char c = ch[i];
			if (!Character.isLetterOrDigit(c)) {
				if (!stringContainsChiness(c)) {
					count = count + 1;
				}
				chLength++;
			}
		}
		float result = count / chLength;
		if (result > 0.4) {
			return true;
		} else {
			return false;
		}
	}

	public static String toChinese(String processingStr) {
		if (isMessyCode(processingStr)) {
			try {
				return new String(processingStr.getBytes("ISO8859-1"), "UTF-8");
			} catch (Exception e) {
			}
		}
		return processingStr;
	}
}
