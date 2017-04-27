package com.howard.www.core.web.security.authorizing.saltencryption.impl;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import org.springframework.stereotype.Repository;
import com.howard.www.core.web.security.authorizing.saltencryption.IFrameworkSaltEncryption;

/**
 * 
 * @author howard
 * 
 */
@Repository("frameworkBaseSaltEncryption")
public class FrameworkBaseSaltEncryptionImpl implements
		IFrameworkSaltEncryption {
	public static final String PBKDF2_ALGORITHM = "PBKDF2WithHmacSHA1";
	// The following constants may be changed without breaking existing hashes.
	public static final String SALT_ENCRYPTION="framework";
	public static final int HASH_BYTE_SIZE = 16;
	public static final int PBKDF2_ITERATIONS = 1000;

	public static final int ITERATION_INDEX = 0;
	public static final int SALT_INDEX = 1;
	public static final int PBKDF2_INDEX = 2;

	public static final String SEPARATOR = ":";

	@Override
	public String encryptionThroughAlgorithm(String originalText)
			throws Exception {
		// TODO Auto-generated method stub
		return createHash(originalText);
	}

	/**
	 * 加盐处理密码,返回处理后的hash
	 * 
	 * @param password
	 * @return 加盐处理后的hash
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 */
	private String createHash(String password) throws NoSuchAlgorithmException,
			InvalidKeySpecException {
		return createHash(password.toCharArray());
	}

	/**
	 * 加盐处理密码,返回处理后的hash
	 * 
	 * @param password
	 * @return 加盐处理后的hash
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 */
	private String createHash(char[] password) throws NoSuchAlgorithmException,
			InvalidKeySpecException {
		// Generate a random salt
		// SecureRandom random = new SecureRandom();
		// byte[] salt = new byte[SALT_BYTE_SIZE];
		// random.nextBytes(salt);
		byte[] salt=SALT_ENCRYPTION.getBytes();
		// Hash the password
		byte[] hash = pbkdf2(password, salt, PBKDF2_ITERATIONS, HASH_BYTE_SIZE);
		// format iterations:salt:hash
		return toHex(hash);
	}

	/**
	 * 验证密码与 盐渍hash 是否匹配
	 * <p>
	 * return true 表示匹配,反之则false
	 * </p>
	 * 
	 * @param password
	 * @param correctHash
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 */
	@SuppressWarnings("unused")
	private boolean validatePassword(String password, String correctHash)
			throws NoSuchAlgorithmException, InvalidKeySpecException {
		return validatePassword(password.toCharArray(), correctHash);
	}

	/**
	 * 验证密码与 盐渍hash 是否匹配
	 * <p>
	 * return true 表示匹配,反之则false
	 * </p>
	 * 
	 * @param password
	 * @param correctHash
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 */
	private boolean validatePassword(char[] password, String correctHash)
			throws NoSuchAlgorithmException, InvalidKeySpecException {
		// Decode the hash into its parameters
		String[] params = correctHash.split(SEPARATOR);
		int iterations = Integer.parseInt(params[ITERATION_INDEX]);
		byte[] salt = fromHex(params[SALT_INDEX]);
		byte[] hash = fromHex(params[PBKDF2_INDEX]);
		// Compute the hash of the provided password, using the same salt,
		// iteration count, and hash length
		byte[] testHash = pbkdf2(password, salt, iterations, hash.length);
		// Compare the hashes in constant time. The password is correct if
		// both hashes match.
		return slowEquals(hash, testHash);
	}

	/**
	 * Compares two byte arrays in length-constant time. This comparison method
	 * is used so that password hashes cannot be extracted from an on-line
	 * system using a timing attack and then attacked off-line.
	 * 
	 * @param a
	 *            the first byte array
	 * @param b
	 *            the second byte array
	 * @return true if both byte arrays are the same, false if not
	 */
	private boolean slowEquals(byte[] a, byte[] b) {
		int diff = a.length ^ b.length;
		for (int i = 0; i < a.length && i < b.length; i++)
			diff |= a[i] ^ b[i];
		return diff == 0;
	}

	/**
	 * Computes the PBKDF2 hash of a password.
	 * 
	 * @param password
	 *            the password to hash.
	 * @param salt
	 *            the salt
	 * @param iterations
	 *            the iteration count (slowness factor)
	 * @param bytes
	 *            the length of the hash to compute in bytes
	 * @return the PBDKF2 hash of the password
	 */
	private byte[] pbkdf2(char[] password, byte[] salt, int iterations,
			int bytes) throws NoSuchAlgorithmException, InvalidKeySpecException {
		PBEKeySpec spec = new PBEKeySpec(password, salt, iterations, bytes * 8);
		SecretKeyFactory skf = SecretKeyFactory.getInstance(PBKDF2_ALGORITHM);
		return skf.generateSecret(spec).getEncoded();
	}

	/**
	 * Converts a string of hexadecimal characters into a byte array.
	 * 
	 * @param hex
	 *            the hex string
	 * @return the hex string decoded into a byte array
	 */
	private byte[] fromHex(String hex) {
		byte[] binary = new byte[hex.length() / 2];
		for (int i = 0; i < binary.length; i++) {
			binary[i] = (byte) Integer.parseInt(
					hex.substring(2 * i, 2 * i + 2), 16);
		}
		return binary;
	}

	/**
	 * Converts a byte array into a hexadecimal string.
	 * 
	 * @param array
	 *            the byte array to convert
	 * @return a length*2 character string encoding the byte array
	 */
	private String toHex(byte[] array) {
		BigInteger bi = new BigInteger(1, array);
		String hex = bi.toString(16);
		int paddingLength = (array.length * 2) - hex.length();
		if (paddingLength > 0)
			return String.format("%0" + paddingLength + "d", 0) + hex;
		else
			return hex;
	}
	public static void main(String[] args) throws Exception{
		System.out.println(new FrameworkBaseSaltEncryptionImpl().encryptionThroughAlgorithm("admin123"));
	}
}
