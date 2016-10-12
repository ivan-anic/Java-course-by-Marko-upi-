package hr.fer.zemris.java.tecaj.hw3.prob1;

/**
 * Represents an implementation of a lexical analyser.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class Lexer {

	/** The input text. */
	private char[] data;

	/** The current token. */
	private Token token;

	/** The index of the next unprocessed character. */
	private int currentIndex;

	/** Current state of the lexer. {@link LexerState} */
	private LexerState state;

	/**
	 * A flag used by the escape method, which allows only a single number to be
	 * parsed into a {@link TokenType} WORD.
	 */
	private boolean escapeFlag;

	/**
	 * Creates an instance of this class with the input text as
	 * <code>String</code>.
	 * 
	 * @param text
	 *            - the text being transformed to tokens.
	 * @throws IllegalArgumentException
	 *             - if the input text is an empty string.
	 */
	public Lexer(String text) {
		if (text == null) {
			throw new IllegalArgumentException("Text can't be empty!");
		}
		this.data = text.toCharArray();
		this.state = LexerState.BASIC;
		this.escapeFlag = false;
	}

	/**
	 * Returns the previous generated token. Does not initiate the generation of
	 * the next token; can be called multiple times.
	 * 
	 * @return the previously generated token.
	 */
	public Token getToken() {
		return token;
	}

	/**
	 * Sets the lexer to a new desired {@link LexerState}.
	 * 
	 * 
	 * @param state
	 */
	public void setState(LexerState state) {
		if (state == null) {
			throw new IllegalArgumentException("Text can't be empty!");
		}
		this.state = state;
	}

	/**
	 * Generates and returns the next token.
	 * 
	 * @return the next token
	 * @throws LexerException
	 *             if an error occurs.
	 */
	public Token nextToken() {
		// if previous is the last token in the file
		if (token != null && token.getType() == TokenType.EOF) {
			throw new LexerException("No tokens available.");
		}
		
		skipBlanks();
		
		// if there are no characters, generate and EOF token
		if (currentIndex >= data.length) {
			token = new Token(TokenType.EOF, null);
			return token;
		}

		//if state is extended, launch a separate method
		if (state == LexerState.EXTENDED) {
			token = extendedToken();
			return token;
		}

		// if a character is a token on it's own
		char charSymbol = data[currentIndex];
		if (!Character.isDigit(charSymbol) && !Character.isLetter(charSymbol) && !(charSymbol == '\\')) {
			token = new Token(TokenType.SYMBOL, charSymbol);
			currentIndex++;
			return token;
		}

		// if it's a word
		if (Character.isLetter(data[currentIndex]) || checkEscape()) {
			int startIndex = currentIndex;
			currentIndex++;

			while (currentIndex < data.length && (Character.isLetter(data[currentIndex])
					|| (Character.isDigit((data[currentIndex])) && escapeFlag) || checkEscape())) {
				currentIndex++;
				escapeFlag = false;
			}
			int endIndex = currentIndex;

			String value = new String(data, startIndex, endIndex - startIndex);
			token = new Token(TokenType.WORD, value);
			return token;
		}

		// if it's a number
		if (Character.isDigit(data[currentIndex])) {
			int startIndex = currentIndex;
			currentIndex++;

			while (currentIndex < data.length && Character.isDigit((data[currentIndex]))) {
				currentIndex++;
			}
			int endIndex = currentIndex;

			String value = new String(data, startIndex, endIndex - startIndex);
			try {
				token = new Token(TokenType.NUMBER, Long.parseLong(value));
			} catch (NumberFormatException ex) {
				throw new LexerException("Number too big. Only 64-bit two's complement integers are allowed. ");
			}

			return token;
		}

		throw new LexerException("Invalid character found: '" + data[currentIndex] + "'.");
	}

	/**
	 * A method which processes the input in an extended way, as described in
	 * {@link LexerState}.
	 * 
	 * @return the next token
	 */
	private Token extendedToken() {

		if ((data[currentIndex]) == '#') {
			token = new Token(TokenType.SYMBOL, data[currentIndex]);
			currentIndex++;
			return token;
		}

		int startIndex = currentIndex;
		while (!Character.isWhitespace(data[currentIndex])&&!((data[currentIndex]) == '#')) {
			currentIndex++;
		}
		int endIndex = currentIndex;
		
		String value = new String(data, startIndex, endIndex - startIndex);
		token = new Token(TokenType.WORD, value);
		return token;
	}

	/**
	 * A method which skips the blanks in the input character sequence, if they
	 * exist.
	 */
	private void skipBlanks() {
		while (currentIndex < data.length) {
			char c = data[currentIndex];
			if (c == ' ' || c == '\t' || c == '\r' || c == '\n') {
				currentIndex++;
				continue;
			}
			break;
		}
	}

	/**
	 * Checks if the next character is an escape, and if it's correctly escaped.
	 * 
	 * @return returns {@code true} if the following sequence if a correct
	 *         escape.
	 * @throws LexerException
	 *             if an invalid escape sequence if encountered.
	 */
	private boolean checkEscape() {
		if ((data[currentIndex]) == '\\') {
			if (currentIndex + 1 < data.length
					&& (data[currentIndex + 1] == '\\' || Character.isDigit(data[currentIndex + 1]))) {
				data = removeChar(data, currentIndex);
				escapeFlag = true;
				return true;
			}
			throw new LexerException("Invalid escape sequence: '" + data[currentIndex - 1] + data[currentIndex] + "'.");
		}
		return false;
	}

	/**
	 * Removes the unwanted character from the character sequence.
	 * 
	 * @param array
	 *            the character array
	 * @param removePosition
	 *            the character to be removed
	 * @return the wanted sequence of characters, without the {@code remove}.
	 */
	public static char[] removeChar(char[] array, int removePosition) {
		StringBuilder sb = new StringBuilder();
		int len = array.length;
		for (int i = 0; i < len; i++) {
			if (i != removePosition) {
				sb.append(array[i]);
			}
		}
		return sb.toString().toCharArray();
	}

}
