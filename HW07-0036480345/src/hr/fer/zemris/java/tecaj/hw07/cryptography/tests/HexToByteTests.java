package hr.fer.zemris.java.tecaj.hw07.cryptography.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import hr.fer.zemris.java.tecaj.hw07.cryptography.Crypto;

/**
 * The Class HexToByteTests.
 */
public class HexToByteTests {

	/**
	 * Hextobyte1.
	 */
	@Test
	public void hextobyte1() {

		byte[] bytes = Crypto.hexToByte("a52217e3ee213ef1ffdee3a192e2ac7e");
		byte[] expected = { (byte) 165, (byte) 34, (byte) 23, (byte) 227,
				(byte) 238, (byte) 33, (byte) 62, (byte) 241,
				(byte) 255, (byte) 222, (byte) 227, (byte) 161,
				(byte) 146, (byte) 226, (byte) 172, (byte) 126 };
		assertArrayEquals(expected, bytes);
	}

	/**
	 * Hextobyte2.
	 */
	@Test
	public void hextobyte2() {
		byte[] bytes = Crypto.hexToByte("00000a");
		byte[] expected = { (byte) 0,(byte) 0,(byte) 10};
		assertArrayEquals(expected, bytes);
	}

	/**
	 * Hextobyte3.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void hextobyte3() {
		@SuppressWarnings("unused")
		byte[] bytes = Crypto.hexToByte(null);
	}

}
