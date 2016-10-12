package hr.fer.zemris.java.tecaj.hw07.cryptography;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.MessageDigest;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Arrays;
import java.util.Scanner;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.annotation.adapters.HexBinaryAdapter;

/**
 * This program offers various cryptographic operations which the user can
 * perform on certain input data.<br>
 * Features available:
 * <ul>
 * <li>Encryption/decryption of a given file using the AES-128 cryptoalgorythm.
 * </li>
 * <li>Calculating or checking the SHA-256 file digest.</li>
 * 
 * </ul>
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class Crypto {

	/**
	 * The main method, first one to be executed when the program starts.
	 * 
	 * @param args
	 *            - the input arguments, to be entered as:<br>
	 *            <ul>
	 *            <li>The command to be executed. [checksha, encrypt, decrypt]
	 *            </li>
	 *            <li>The file on which the command will be executed. File path
	 *            is relative to the root project directory.</li>
	 *            <li>[Optional] The desired output file name. (for encrypt and
	 *            decrypt commands.</li>
	 */
	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		String password;
		String initvector;
		boolean encrypt = false;
		checkArgs(args, 2, 3);

		switch (args[0]) {
		case ("checksha"):

			checkArgs(args, 2);

			System.out.printf("Please provide expected sha-256 digest for %s:%n", args[1]);
			String digest = sc.nextLine();

			processSha256(digest, args[1]);

			break;
		case ("encrypt"):
			encrypt = true;
		case ("decrypt"):

			checkArgs(args, 3);

			System.out.println("Please provide password as hex-encoded text (16 bytes, i.e. 32 hex-digits):");
			password = sc.nextLine();

			System.out.printf("Please provide initialization vector as hex-encoded text (32 hex-digits):");
			initvector = sc.nextLine();

			processDecrypt(password, args[1], args[2], initvector, encrypt);

			break;
		default:
			System.out.println("Invalid command.");
		}

		sc.close();
	}

	/**
	 * Processes the encryption and decryption commands. Uses the AES
	 * cryptoalgorithm with the 128-bit encryption key.
	 *
	 * @param password
	 *            - the password, given as hex-encoded text (16 bytes, i.e. 32
	 *            hex-digits)
	 * @param file
	 *            - name of the locally stored file
	 * @param outfile
	 *            - name of the file to be generated
	 * @param initvector
	 *            - the initialization vector, given as hex-encoded text (32
	 *            hex-digits)
	 * @param encrypt
	 *            - signals whether the file is being encrypted or decrypted
	 */
	private static void processDecrypt(String password, String file, String outfile, String initvector,
			boolean encrypt) {

		byte[] cipherdata = new byte[4096];

		Cipher cipher = null;

		SecretKeySpec keySpec = new SecretKeySpec(hexToByte(password), "AES");
		AlgorithmParameterSpec paramSpec = new IvParameterSpec(hexToByte(initvector));
		try {
			cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

			cipher.init(encrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, keySpec, paramSpec);
		} catch (Exception ignorable) {
		}

		byte[] buffer = null;

		try (InputStream is = new BufferedInputStream(Files.newInputStream(Paths.get(file), StandardOpenOption.READ));
				OutputStream os = new BufferedOutputStream(new FileOutputStream(new File(outfile)))) {

			int size;
			while ((size = is.read(cipherdata)) > 0) {
				buffer = cipher.update(cipherdata, 0, size);
				if (is != null) {
					os.write(buffer);
				}
			}
			buffer = cipher.doFinal();
			os.write(buffer);

		} catch (IOException ex) {
			System.out.println(ex.getMessage());

		} catch (Exception ignorable) {
		}

		System.out.printf(((encrypt) ? "Encryption" : "Decryption") +
				" completed. Generated file %s based on %s.%n", file, outfile);
	}

	/**
	 * Processes the message digest calculation.
	 *
	 * @param digest
	 *            - the given message digest to be compared with the file
	 * @param file
	 *            - name of the locally stored file
	 */
	private static void processSha256(String digest, String file) {

		byte[] mddata = new byte[4096];
		byte[] hash = null;

		try (InputStream is = new BufferedInputStream(
				Files.newInputStream(Paths.get(file), StandardOpenOption.READ))) {
			MessageDigest sha = MessageDigest.getInstance("SHA-256");

			int size;
			while ((size = is.read(mddata)) > 0) {
				sha.update(mddata, 0, size);
			}
			hash = sha.digest();

		} catch (IOException ex) {
			System.out.println(ex.getMessage());

		} catch (Exception ignorable) {
		}

		boolean eq = Arrays.equals(hexToByte(digest),
				hash);
		System.out.println("Digesting completed. Digest of " + file
				+ ((eq) ? " matches expected digest." : " does not match the expected digest. Digest was: " + digest));
	}

	/**
	 * Converts the input text into an array of bytes.
	 *
	 * @param keyText
	 *            - the input text to be converted
	 * @return the desired byte array
	 */
	public static byte[] hexToByte(String keyText) {
		if (keyText == null) {
			throw new IllegalArgumentException("Key can't be null.");
		}
		HexBinaryAdapter adapter = new HexBinaryAdapter();
		byte[] bytes = adapter.unmarshal(keyText);
		return bytes;
	}

	/**
	 * Validates the number of input arguments. If the number of arguments is
	 * invalid, throws an appropriate message.
	 *
	 * @param args
	 *            - the actual arguments
	 * @param startrange
	 *            - the minimum number of arguments
	 * @param endrange
	 *            - the maximum number of arguments
	 */
	private static void checkArgs(String[] args, int startrange, int endrange) {
		if (args.length < startrange || args.length > endrange) {
			System.out.println("Invalid command syntax. Please provide appropriate number of arguments.");
			System.exit(1);
		}
	}

	/**
	 * Validates the number of input arguments by calling the method
	 * {@link #checkArgs(String[], int, int)}.
	 *
	 * @param args
	 *            - the actual
	 * @param expected
	 *            - the expected argument
	 */
	private static void checkArgs(String[] args, int expected) {
		checkArgs(args, expected, expected);
	}
}