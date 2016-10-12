package hr.fer.zemris.java.cstr;

/**
 * An implementation of a class which offers similar functionalities as the old
 * Java String class. Here, multiple instances of strings can share the same
 * {@link Character} array, thus providing methods executable in O(1)
 * complexity, contrary to the modern {@link String} class.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class CString {

	/** The data contained in this string, as an array of characters. */
	private char[] data;

	/** The point in the array where the actual data begins. */
	private int offset;

	/** The length of the data. */
	private int length;

	/**
	 * Creates an instance of this class with the desired parameters
	 * 
	 * @param data
	 *            textual data which will be transformed into a {@link CString}
	 *            object. Sent to this constructor as an array of characters.
	 * @param offset
	 *            the distance from the beginning of the {@link Character}
	 *            sequence; a point from where the {@link CString} object will
	 *            be created.
	 * @param length
	 *            the length of the textual data wanted; the number of
	 *            characters.
	 * @throws IllegalArgumentException
	 *             if arguments are invalid.
	 */

	public CString(char[] data, int offset, int length) {
		if (data.equals(null)) {
			throw new IllegalArgumentException("Can't be null.");
		}
		if (offset < 0 || length < 0) {
			throw new IllegalArgumentException((offset < 0) ? "Offset" : "Length" + " can't be less than zero.");
		}
		if (length() > data.length) {
			throw new IllegalArgumentException("Invalid arguments, array index out of bounds.");
		}
		this.data = data;
		this.offset = offset;
		this.length = length;
	}

	/**
	 * Creates an instance of this class with the desired parameters
	 * 
	 * @param data
	 *            textual data which will be transformed into a {@link CString}
	 *            object. Sent to this constructor as an array of characters.
	 * @throws IllegalArgumentException
	 *             if arguments are invalid.
	 */
	public CString(char[] data) {
		if (data.equals(null)) {
			throw new IllegalArgumentException("Can't be null.");
		} else {
			this.data = data;
			this.length = data.length;
		}
	}

	/**
	 * Creates an instance of this class with the desired parameters. Resizes
	 * the {@link Character} array if needed.
	 * 
	 * @param original
	 *            textual data which will be transformed into a {@link CString}
	 *            object. Sent to this constructor as an array of characters.
	 * @throws IllegalArgumentException
	 *             if arguments are invalid.
	 * 
	 */
	public CString(CString original) {
		if (data.equals(null)) {
			throw new IllegalArgumentException("Can't be null.");
		} else if (this.offset != 0 || this.length != data.length) {
			int cnt = 0;
			char[] temp = new char[original.length];

			for (int i = original.offset; i < original.length; i++) {
				original.data[i] = temp[cnt++];
			}
			this.data = temp;
		}
	}

	/**
	 * Creates an instance of this class with the same value as the given
	 * parameter.
	 * 
	 * @param s
	 *            a {@link String} which will be converted to {@link CString}.
	 * @return an instance of the {@link CString} class, containing the same
	 *         data as the given {@link String} contains.
	 * @throws IllegalArgumentException
	 *             if arguments are invalid.
	 */
	public static CString fromString(String s) {
		if (s.equals(null)) {
			throw new IllegalArgumentException("Can't be null.");
		} else {
			return new CString(s.toCharArray());
		}
	}

	/**
	 * Returns the length of the {@link CString}.
	 * 
	 * @return number of characters in the {@link CString}
	 */
	public int length() {
		return length + offset;
	}

	/**
	 * Returns the character at the given index.
	 * 
	 * @param index
	 *            the desired index of the wanted character
	 * @return the wanted character.
	 * @throws IllegalArgumentException
	 *             if arguments are invalid.
	 */
	public char charAt(int index) {
		if (index < 0 || index > length() - 1) {
			throw new IllegalArgumentException("Array index out of bounds.");
		} else {
			return data[offset + index];
		}
	}

	/**
	 * Converts this object to an array of characters, and returns it.
	 * 
	 * @return an array of characters
	 * @throws IllegalArgumentException
	 *             if arguments are invalid.
	 */
	public char[] toCharArray() {
		if (this.equals(null)) {
			throw new IllegalArgumentException("Can't be null.");
		} else if (this.length() != data.length) {
			{
				return new CString(this).toCharArray();
			}
		} else {
			return this.data;
		}
	}

	/**
	 * Converts this {@link CString} object into a String and returns it.
	 * 
	 * @return the String version of the object
	 */
	public String toString() {
		String res = new String(data, offset, length);
		return res;
	}

	/**
	 * Checks if the string begins with a given string.
	 * 
	 * @param s
	 *            the substring which is being checked.
	 * @return true if this string begins with the given substring.
	 */
	public boolean startsWith(CString s) {
		return ((substring(s.offset, s.offset + s.length).toString().equals(s.toString())));
		/*
		 * for (int i = offset; i < length; i++) { if (s.charAt(cnt) != data[i])
		 * { return false; } } return true;
		 */
	}

	/**
	 * Checks if the string ends with a given string.
	 * 
	 * @param s
	 *            the substring which is being checked.
	 * @return true if this string ends with the given substring.
	 */
	public boolean endsWith(CString s) {
		int start = offset + length - s.length;

		CString str = (substring(start, offset + length));
		return (str.toString().equals(s.toString()));

	}

	/**
	 * Checks if this string contains the given string at any position.
	 * 
	 * @param s
	 *            the given string to check if this string contains is
	 * @return true if this string contains <b>s</b>
	 */
	public boolean contains(CString s) {
		if (s.toString().equals(this.toString())) {
			return true;
		} else {

			int cnt = 0;

			int oldLen = s.length();

			for (int i = offset; i < length; i++) {

				// if a potential beginning is found
				if (data[i] == s.charAt(0)) {
					for (int j = i + 1; j < oldLen; j++) {
						if (data[j] != s.charAt(cnt++) && j < length) {
							break;
						}
						return true;
					}
					cnt = 0;
				}
			}
			return false;
		}
	}

	/**
	 * Returns a new instance of this class with only a part of the textual data
	 * contained in it.
	 * 
	 * @param startIndex
	 *            the desired start of the substring
	 * @param endIndex
	 *            the desired end of the substring
	 * @return the desired substring of this string
	 * @throws IllegalArgumentException
	 *             if arguments are invalid.
	 */
	public CString substring(int startIndex, int endIndex) {
		if (startIndex < 0 || offset + endIndex - startIndex > length()) {
			throw new IllegalArgumentException("String index out of bounds.");
		} else {
			CString tmp = new CString(data, startIndex, endIndex - startIndex);
			return tmp;
		}
	}

	/**
	 * Returns a new instance of this class which represents the starting part
	 * of the original string and is of length n.
	 * 
	 * @param n
	 *            desired length of the substring
	 * @return the desired substring
	 */
	public CString left(int n) {
		paramLengthCheck(n);
		return substring(offset, n);
	}

	/**
	 * Returns a new instance of this class which represents the ending part of
	 * the original string and is of length n.
	 * 
	 * @param n
	 *            desired length of the substring
	 * @return the desired substring
	 */
	public CString right(int n) {
		paramLengthCheck(n);
		return substring(length() - n, length());
	}

	/**
	 * Concatenates the given string to this string, and returns the result.
	 * 
	 * @param s
	 *            the given string to concatenate
	 * @return the concatenation of the given parameters
	 */
	public CString add(CString s) {

		CharArrayBuilder cb = new CharArrayBuilder();
		for (char c : data) {
			cb.append(c);
		}
		for (char c : s.toCharArray()) {
			cb.append(c);
		}
		return new CString(cb.toCharArray());
	}

	/**
	 * Returns a new instance of this class in which in which each occurrence of
	 * the old character is replaced with the new character.
	 * 
	 * @param oldChar
	 *            the character to be replaced
	 * @param newChar
	 *            the character which oldChar if replaced with
	 * @return the edited string
	 */
	public CString replaceAll(char oldChar, char newChar) {
		if (oldChar == newChar)
			return this;
		else {
			for (char c : data) {
				if (c == oldChar) {
					c = newChar;
				}
			}
			return this;
		}
	}

	/**
	 * Returns a new instance of this class in which in which each occurrence of
	 * the old string is replaced with the new string.
	 * 
	 * @param oldStr
	 *            the character to be replaced
	 * @param newStr
	 *            the character which oldChar if replaced with
	 * @return the edited string
	 */
	public CString replaceAll(CString oldStr, CString newStr) {
		if (oldStr.toString().equals(newStr.toString())) {
			return this;
		} else {

			int cnt = 0;
			boolean found = false;
			CharArrayBuilder cb = new CharArrayBuilder();
			int oldLen = oldStr.length();
			int newLen = newStr.length();

			for (int i = offset; i < length; i++) {

				// if a potential beginning is found
				if (data[i] == oldStr.charAt(0)) {
					for (int j = i + 1; j < oldLen; j++) {
						if (data[j] != oldStr.charAt(cnt++) && j < length) {
							break;
						}
						found = true;
					}
					cnt = 0;
				}
				// replace the string
				if (found) {
					found = false;
					cb.append(newStr);
					i += newLen - 1;
				} else {
					cb.append(data[i]);
				}
				cnt = 0;
			}
			return new CString(cb.toString().toCharArray());
		}
	}

	/**
	 * A private method with a purpose to check whether the method parameters
	 * are value.
	 * 
	 * @param n
	 *            the length of the substring
	 * @throws IndexOutOfBoundsException
	 *             if the length is greater than the length of the original
	 *             string.
	 */
	private void paramLengthCheck(int n) {
		if (n < 0 || n > length()) {
			throw new IndexOutOfBoundsException("Index out of bounds.");
		}
	}
}
