package com.howard.www.core.tool.encoding;

public class EncodingTest {
	public static void main(String argc[]) throws Exception {
		ParseEncoding parse;

		parse = new ParseEncoding();
        String testStr="ä½ å¥½";
        String strEncoding=parse.getEncoding(testStr.getBytes());
		System.out.println(new String(testStr.getBytes("iso-8859-1"),strEncoding));

	}
}
