package com.howard.www.core.web.security.authorizing.test;

public class StringUtil {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String str = "helloworld";
		System.out.println((str.split(" ")).length);
		System.out.println(UrlPage("/analysisadmin/analysis/doLogin.html?demo=1&hello=2"));
	}

	public static String UrlPage(String strURL) {
		String strPage = null;
		String[] arrSplit = null;

		strURL = strURL.trim().toLowerCase();

		arrSplit = strURL.split("[?]");
		if (strURL.length() > 0) {
			if (arrSplit.length > 1) {
				if (arrSplit[0] != null) {
					strPage = arrSplit[0];
				}
			}
		}

		return strPage;
	}
}
