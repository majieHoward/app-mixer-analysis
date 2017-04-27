package com.howard.www.core.web.security.authorizing.test;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.util.Enumeration;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Base64;

public class ReadP12Cert {
	@SuppressWarnings("rawtypes")
	public static void main(String[] args) {
		final String KEYSTORE_FILE = "\\F:\\developer\\code\\Java\\frame\\weixin\\mix\\mix\\code\\app-mixer-analysis\\src\\main\\resources\\config\\certificate\\basicConfiguration.p12";
		final String KEYSTORE_PASSWORD = "panguopendays";
		try {
			KeyStore ks = KeyStore.getInstance("PKCS12");
	
			FileInputStream fis = new FileInputStream(KEYSTORE_FILE);
			char[] nPassword = null;
			if ((KEYSTORE_PASSWORD == null)
					|| KEYSTORE_PASSWORD.trim().equals("")) {
				nPassword = null;
			} else {
				nPassword = KEYSTORE_PASSWORD.toCharArray();
			}
			ks.load(fis, nPassword);
			fis.close();
			Enumeration enuml = ks.aliases();
			String keyAlias = null;
			if (enuml.hasMoreElements()) {
				keyAlias = (String) enuml.nextElement();
			}
			PrivateKey prikey = (PrivateKey) ks.getKey(keyAlias, nPassword);
			Certificate cert = ks.getCertificate(keyAlias);
			PublicKey pubkey = cert.getPublicKey();
			byte[] msg = "123456"
					.getBytes("UTF8"); // 待加解密的消息
			Cipher c1 = Cipher.getInstance("RSA/ECB/PKCS1Padding"); // 定义算法：RSA
			c1.init(Cipher.ENCRYPT_MODE, pubkey);
			byte[] msg1 = c1.doFinal(msg); // 加密后的数据
			String str = gzip(printHexString(msg1));
			System.out.println("加密后的数据----" + str);
			FileWriter writer;
			try {
				writer = new FileWriter("D:/1.txt");
				writer.write(str);
				writer.flush();
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			Cipher c2 = Cipher.getInstance("RSA/ECB/PKCS1Padding");
			c2.init(Cipher.DECRYPT_MODE, prikey);
			String msgget = readTxtFile("D:/1.txt");
			byte[] msg2 = c2.doFinal(hexStringToBytes(gunzip("H4sIAAAAAAAAABWRuREAMQgDWwLMG5rH/Zd0XGDGAQitYMRJ6DEQVazOJK+5AoQhLpfknAIDEpQjBHn6jKaPGtGWJnLvIDxg2Oisj287IJ4IEpEbGYkxrJmpK0UNZjSpNwPt2gskbTLx/SBK2KBsV0nxfUQ5duApE4v260tHx884mlY90esZ1u9RRwc0wOtjB+GfBWme25ym0JSGc1Vrxx+tvbK44+byNIEuv+owfbfcczPRxVC9d/ZJP5Ihe1Pb0FHMgnQQg3/S4nljHXKcS9jXmPpVQr+s3cKhJ2vaYvcJLfSK+gNJW/lntchjK3MmbnGmU8DoRk51IQFBzDfWvsIMbyMTsLvqyeeMVD57FxzckLiq9ZzAPeWpR2tXvDC7G1E5ISKzWg7UEn8vD0+nAAIAAA=="))); // 解密后的数据
			// 打印解密字符串
			System.out.println("解密后的数据----" + new String(msg2, "UTF8")); // 将解密数据转为字符串
			// System.out.println(prikey.toString());
			// System.out.println(pubkey.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static byte[] hexStringToBytes(String hexString) {
		if (hexString == null || hexString.equals("")) {
			return null;
		}
		hexString = hexString.toUpperCase();
		int length = hexString.length() / 2;
		char[] hexChars = hexString.toCharArray();
		byte[] d = new byte[length];
		for (int i = 0; i < length; i++) {
			int pos = i * 2;
			d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
		}
		return d;
	}

	private static byte charToByte(char c) {
		return (byte) "0123456789ABCDEF".indexOf(c);
	}

	public static String printHexString(byte[] b) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			String hex = Integer.toHexString(b[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			sb.append(hex.toUpperCase());
		}
		return sb.toString();
	}

	public static String readTxtFile(String filePath) {
		try {
			String encoding = "UTF-8";
			StringBuffer sb = new StringBuffer();
			File file = new File(filePath);
			if (file.isFile() && file.exists()) { // 判断文件是否存在
				InputStreamReader read = new InputStreamReader(
						new FileInputStream(file), encoding);// 考虑到编码格式
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				while ((lineTxt = bufferedReader.readLine()) != null) {
					sb.append(lineTxt);
				}
				read.close();
				return sb.toString();
			} else {
				System.out.println("找不到指定的文件");
			}
		} catch (Exception e) {
			System.out.println("读取文件内容出错");
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * 使用gzip进行压缩
	 */
	public static String gzip(String primStr) {
		if (primStr == null || primStr.length() == 0) {
			return primStr;
		}

		ByteArrayOutputStream out = new ByteArrayOutputStream();

		GZIPOutputStream gzip = null;
		try {
			gzip = new GZIPOutputStream(out);
			gzip.write(primStr.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (gzip != null) {
				try {
					gzip.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return new String(new Base64().encode(out.toByteArray()));
	}

	/**
	 * 使用gzip进行解压缩
	 * 
	 * @param compressedStr
	 * @return 解压后的字符串
	 */
	public static String gunzip(String compressedStr) {
		if (compressedStr == null) {
			return null;
		}

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ByteArrayInputStream in = null;
		GZIPInputStream ginzip = null;
		byte[] compressed = null;
		String decompressed = null;
		try {
			compressed = new Base64().decode(compressedStr.getBytes());
			in = new ByteArrayInputStream(compressed);
			ginzip = new GZIPInputStream(in);

			byte[] buffer = new byte[1024];
			int offset = -1;
			while ((offset = ginzip.read(buffer)) != -1) {
				out.write(buffer, 0, offset);
			}
			decompressed = out.toString();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (ginzip != null) {
				try {
					ginzip.close();
				} catch (IOException e) {
				}
			}
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
				}
			}
		}

		return decompressed;
	}

}