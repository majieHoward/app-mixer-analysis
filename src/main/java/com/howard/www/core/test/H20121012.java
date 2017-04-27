package com.howard.www.core.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import com.howard.www.core.web.security.service.impl.ThroughGzipModifyTheStringServiceImpl;

public class H20121012 {
	public static void readTxtFile(String filePath) {
		try {
			String encoding = "UTF-8";
			File file = new File(filePath);
			StringBuffer sb = new StringBuffer();
			if (file.isFile() && file.exists()) { // 判断文件是否存在
				InputStreamReader read = new InputStreamReader(
						new FileInputStream(file), encoding);// 考虑到编码格式
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				while ((lineTxt = bufferedReader.readLine()) != null) {
					sb.append(lineTxt);
				}
				read.close();
			} else {
				System.out.println("找不到指定的文件");
			}
			ThroughGzipModifyTheStringServiceImpl a = new ThroughGzipModifyTheStringServiceImpl();
			String str = a.compressionOriginalString(sb.toString());
			System.out.println(str);
			String str1 = a
					.decompressionOriginalString(str);
			System.out.println(str1);
		} catch (Exception e) {
			System.out.println("读取文件内容出错");
			e.printStackTrace();
		}

	}

	public static void main(String argv[]) {
		String filePath = "F:\\workhard\\spareTime\\login\\Airo-login\\login.html";
		// "res/";
		readTxtFile(filePath);
	}
}
