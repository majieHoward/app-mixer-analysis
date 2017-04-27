package com.howard.www.core.web.security.service.impl;

import org.springframework.stereotype.Repository;

import com.howard.www.core.web.security.service.IByteArrayConversionDecimalService;
import com.howard.www.core.web.util.FrameworkStringUtil;

/**
 * 
 * @author majie
 * 
 */
@Repository("byteArrayConversionHexaDecimal")
public class ByteArrayConversionHexaDecimalServiceImpl implements
		IByteArrayConversionDecimalService {

	public byte[] decimalStringConversionBytes(String originalDecimalString) {
		if ("".equals(FrameworkStringUtil.asString(originalDecimalString))) {
			return null;
		}
		originalDecimalString = originalDecimalString.toUpperCase();
		int length = originalDecimalString.length() / 2;
		char[] hexChars = originalDecimalString.toCharArray();
		byte[] dytes = new byte[length];
		for (int i = 0; i < length; i++) {
			int pos = i * 2;
			dytes[i] = (byte) (charConversionByte(hexChars[pos]) << 4 | charConversionByte(hexChars[pos + 1]));
		}
		return dytes;
	}

	public String bytesConversionDecimalString(byte[] originalBytes) {
		if (originalBytes.length > 0) {
			StringBuffer decimalString = new StringBuffer();
			for (int i = 0; i < originalBytes.length; i++) {
				String hex = Integer.toHexString(originalBytes[i] & 0xFF);
				if (hex.length() == 1) {
					hex = '0' + hex;
				}
				decimalString.append(hex.toUpperCase());
			}
			return decimalString.toString();
		}
		return null;
	}

	public byte charConversionByte(char originalChar) {
		return (byte) "0123456789ABCDEF".indexOf(originalChar);
	}

}
