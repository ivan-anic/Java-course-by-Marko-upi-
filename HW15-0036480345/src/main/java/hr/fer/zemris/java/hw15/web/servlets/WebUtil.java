package hr.fer.zemris.java.hw15.web.servlets;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Hex;

/**
 * A utility class used by the servlets in this package.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class WebUtil {

	/**
	 * Process the SHA1 encryption of the given string and returns it.
	 *
	 * @param password
	 *            the password
	 * @return the string
	 */
	public static String processSha1Encrypt(String password) {
		String encrypted = null;

		try {
			MessageDigest sha = MessageDigest.getInstance("SHA-256");
			sha.update(password.getBytes("UTF-8"));
			encrypted = Hex.encodeHexString(sha.digest());
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return encrypted;
	}
}
