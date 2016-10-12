package hr.fer.zemris.java.cstr;

/**
 * A class which offers help with creating character arrays. 
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class CharArrayBuilder {

	/** The data. */
	private char[] data;
	/** The capacity of the current character array. */
	private int capacity;

	/**
	 * Instantiates a new char array builder.
	 */
	public CharArrayBuilder() {
		data = new char[16];
		this.capacity = 16;
	}

	/**
	 * Appends a character to the array.
	 *
	 * @param c
	 *            the character being appended.
	 */
	public void append(char c) {
		checkCapacity(1);
		data[length() + 1] = c;
	}

	/**
	 * Appends a {@link CString} to the array.
	 *
	 * @param c
	 *            the string being appended.
	 */
	public void append(CString c) {
		int len = c.length();
		while (checkCapacity(len)) {
			continue;
		}

		char[] tmp = new char[capacity];

		for (int i = 0; i < c.length(); i++) {
			tmp[i + length()] = c.charAt(i);
		}
		data = tmp;
	}

	/**
	 * Checks if maximum capacity is reached if it is reallocate, the array.
	 * 
	 * @param len
	 *            the length of the array trying to be added.
	 * @return true if the array has been reallocated
	 */
	private boolean checkCapacity(int len) {
		if (length() + len > capacity) {
			this.capacity *= 2;
			char[] pom = new char[capacity];

			for (int i = 0; i < length(); i++) {
				pom[i] = data[i];
			}
			data = pom;
			return true;
		} else
			return false;
	}

	/**
	 * Returns the current array length
	 * @return the length of the current array.
	 */
	private int length(){
		int cnt = 0;
		for (char c:data){
			if (c==0){
				return cnt;
			} else {
				cnt++;
			}
		}
		return cnt;
	}
	
	/**
	 * Returns the character array built by this class.
	 *
	 * @return the character array built by this class.
	 */
	public char[] toCharArray() {
		return data;
	}
}
