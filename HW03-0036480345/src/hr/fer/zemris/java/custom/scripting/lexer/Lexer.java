package hr.fer.zemris.java.custom.scripting.lexer;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;

/**
 * Represents an implementation of a lexical analyser. Groups the input text
 * into {@link Token}s, which are used by the {@link SmartScriptParser} class
 * which afterwards executes the document parsing.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class Lexer {

	/** The current token. */
	private Token token;

	/** The input text, as a{@link Character} array. */
	private char[] data;

	/**
	 * An {@link ArrayIndexedCollection} which will be populated with the
	 * tokenised text.
	 */
	private ArrayIndexedCollection tokenised;

	/**
	 * A counter used by the {@link #getNextToken()} method for remembering the
	 * token index it needs to return.
	 */
	private int cnt;

	/** A constant used for a quotation mark. */
	public static final char QUOTATION_MARK = '"';

	/** A constant used for an underscore. */
	public static final char UNDERSCORE = '_';

	/** A constant used for an at symbol. */
	public static final char AT_SYMBOL = '@';

	/** A constant used for a hyphen symbol. */
	public static final char HYPHEN_SYMBOL = '-';

	/** A constant used for an equals symbol. */
	public static final char EQUALS_SYMBOL = '=';
	
	/** A constant used for an open curly brace. */
	public static final char CURLY_OPEN = '{';

	/** A constant used for a closed curly brace. */
	public static final char CURLY_CLOSE = '}';

	/** A constant used for a dollar sign. */
	public static final char DOLLAR = '$';
	
	/** A list of allowed operators. */
	private static final ArrayIndexedCollection operators;
	static {
		operators = new ArrayIndexedCollection(5);
		operators.add('+');
		operators.add('-');
		operators.add('*');
		operators.add('/');
		operators.add('^');
	}

	/** A list of allowed escapes in text. */
	private static final ArrayIndexedCollection escapesText;
	static {
		escapesText = new ArrayIndexedCollection(2);
		escapesText.add('{');
		escapesText.add('\\');
	}

	/** A list of allowed escapes in {@code Strings}. */
	private static final ArrayIndexedCollection escapesSpecial;
	static {
		escapesSpecial = new ArrayIndexedCollection(3);
		escapesSpecial.add('n');
		escapesSpecial.add('r');
		escapesSpecial.add('t');
	}

	/** A list of allowed escapes in {@code Strings}. */
	private static final ArrayIndexedCollection escapesString;
	static {
		escapesString = new ArrayIndexedCollection(2);
		escapesString.add('"');
		escapesString.add('\\');
	}

	/** The index of the next unprocessed character. */
	private int currentIndex;

	/** Current state of the lexer. {@link LexerState} */
	private LexerState state;

	/**
	 * Creates an instance of a {@link Lexer} class.
	 * 
	 * @param document
	 *            - the document to be tokenised, saved locally as a
	 *            {@link Character} array
	 */
	public Lexer(String document) {
		if (document == null) {
			throw new IllegalArgumentException("Text can't be empty!");
		}
		this.tokenised = new ArrayIndexedCollection();
		this.state = LexerState.BASIC;
		this.data = document.toCharArray();
		this.cnt = 0;
		tokenise();
		this.token = (Token) tokenised.get(cnt++);
	}

	/**
	 * Returns the previous generated token. Does not initiate the generation of
	 * the next token; can be called multiple times.
	 * 
	 * @return the previously generated token.
	 */
	public Token getCurrentToken() {
		return token;
	}

	/**
	 * Generates and returns the next token.
	 * 
	 * @return the next token
	 */
	public Token getNextToken() {
		if (token.getType() == TokenType.EOF) {
			throw new SmartScriptParserException("EOF already reached.");
		} else {
			token = (Token) tokenised.get(cnt++);
		}
		return token;
	}

	/**
	 * Converts the document body into an array of {@link Token}s, and saves it
	 * locally. They can be fetched using the {@link #getCurrentToken} method.
	 */
	private void tokenise() {
		while (currentIndex < data.length) {
			checkTag(currentIndex);
			if (this.state == LexerState.BASIC) {
				if (!checkEscape(escapesText) && !checkTag(currentIndex)) {
				}
				int startIndex = currentIndex;
				currentIndex++;

				while (currentIndex < data.length && !checkTag(startIndex)) {
					checkEscape(escapesText);
					currentIndex++;
				}
			} else {// tags here
				while (!checkEndTag()) {
					skipBlanks();

					char temp = data[currentIndex];

					int startIndex = currentIndex;

					if (Character.isLetter(temp)) {
						processVariable(startIndex, TokenType.VARIABLE);
					} else if (Character.isDigit(temp)) {
						processNumber(startIndex);
					} else if (temp == QUOTATION_MARK) {
						processString(startIndex);
					} else if (temp == AT_SYMBOL) {
						currentIndex++;
						processVariable(startIndex + 1, TokenType.FUNCTION);
					} else if (operators.contains(temp)) {
						processOperator(startIndex);
					}
				}
			}
		}
		token = new Token(TokenType.EOF, null);
		tokenised.add(token);

	}

	/**
	 * Processes a VARIABLE {@link Token}, defining its borders, and saving it
	 * locally in a {@link ArrayIndexedCollection}. Also used for the FUNCTION
	 * {@link TokenType}s, since both of them are using the same syntax.
	 * 
	 * @param startIndex
	 *            represents the index in the local {@link Character} array
	 *            where the token data begins.
	 * @param type
	 *            the {@link TokenType} of the {@link Token}.
	 */
	private void processVariable(int startIndex, TokenType type) {
		while (currentIndex < data.length && (Character.isLetter(data[currentIndex])
				|| Character.isDigit(data[currentIndex]) || data[currentIndex] == UNDERSCORE)) {
			currentIndex++;
		}

		int endIndex = currentIndex;

		String value = new String(data, startIndex, endIndex - startIndex);
		token = new Token(type, value);
		tokenised.add(token);
	}

	/**
	 * Processes a number {@link Token}, defining its borders, and saving it
	 * locally in a {@link ArrayIndexedCollection}. Saves both integer and
	 * double {@link TokenType}s.
	 * 
	 * @param startIndex
	 *            represents the index in the local {@link Character} array
	 *            where the token data begins.
	 */
	private void processNumber(int startIndex) {
		int numParts = 1;

		// to skip the "-" symbol
		if (data[currentIndex] == HYPHEN_SYMBOL) {
			currentIndex++;
		}
		while (currentIndex < data.length && numParts < 3
				&& ((data[currentIndex] == '.') || Character.isDigit(data[currentIndex]))) {

			currentIndex++;

			if (data[currentIndex] == '.') {
				numParts++;
				if (currentIndex < data.length) {
					currentIndex++;
				}
			}
		}
		int endIndex = currentIndex;

		String value = new String(data, startIndex, endIndex - startIndex);
		if (numParts == 1) {
			token = new Token(TokenType.CONSTANTINT, value);
		} else {
			token = new Token(TokenType.CONSTANTDOUBLE, value);
		}
		tokenised.add(token);
	}

	/**
	 * Processes a STRING {@link Token}, defining its borders, and saving it
	 * locally in a {@link ArrayIndexedCollection}. Checks for valid escaping
	 * using the {@link #checkEscape} method.
	 * 
	 * @param startIndex
	 *            represents the index in the local {@link Character} array
	 *            where the token data begins.
	 */
	private void processString(int startIndex) {
		// to skip the first quotation mark
		currentIndex++;

		while (currentIndex < data.length && (data[currentIndex]) != QUOTATION_MARK) {
			checkEscape(escapesString);
			currentIndex++;
		}

		int endIndex = currentIndex;

		// to skip the first quotation mark
		currentIndex++;

		String value = new String(data, startIndex + 1, endIndex - 1 - startIndex);
		token = new Token(TokenType.STRING, value);

		tokenised.add(token);
	}

	/**
	 * Processes an OPERATOR {@link Token}, defining its borders, and saving it
	 * locally in a {@link ArrayIndexedCollection}.
	 * 
	 * @param startIndex
	 *            represents the index in the local {@link Character} array
	 *            where the token data begins.
	 */
	private void processOperator(int startIndex) {

		if (data[currentIndex] == HYPHEN_SYMBOL && Character.isDigit(data[currentIndex + 1])) {
			processNumber(startIndex);
		} else {
			currentIndex++;
			String value = new String(data, startIndex, 1);
			token = new Token(TokenType.OPERATOR, value);
			tokenised.add(token);
		}

	}

	/**
	 * Processes a TAG {@link Token}, defining its borders, and saving it
	 * locally in a {@link ArrayIndexedCollection}.
	 * 
	 * @param startIndex
	 *            represents the index in the local {@link Character} array
	 *            where the token data begins.
	 */
	private void processTag(int startIndex) {
		startIndex = skipBlanks();

		String value;

		if (data[currentIndex] == EQUALS_SYMBOL) {
			value = new String(data, startIndex, 1);
			currentIndex++;
		} else {

			while (currentIndex < data.length && (Character.isLetter(data[currentIndex])
					|| Character.isDigit(data[currentIndex]) || data[currentIndex] == UNDERSCORE)) {
				currentIndex++;
			}
			int endIndex = currentIndex;

			value = new String(data, startIndex, endIndex - startIndex);
		}

		token = new Token(TokenType.TAG, value);
		tokenised.add(token);

	}

	/**
	 * Checks if the escape sequence defined in the {@link Character} array is
	 * valid, as defined in the project documentation.
	 * 
	 * @param escapes
	 *            an {@link ArrayIndexedCollection}, in which the legal escape
	 *            sequences are stored.
	 * @return returns true if the escape sequence
	 */
	private boolean checkEscape(ArrayIndexedCollection escapes) {
		if ((data[currentIndex]) == '\\') {
			if (currentIndex + 1 < data.length
					&& (escapesSpecial.contains(data[currentIndex + 1]) || escapes.contains(data[currentIndex + 1]))) {
				data = removeChar(data, currentIndex);
				if (currentIndex < data.length && escapesSpecial.contains(data[currentIndex])) {
					int temp = '\\' + data[currentIndex];
					data[currentIndex] = (char) temp;
				}
				return true;
			}
			throw new SmartScriptParserException(
					"Invalid escape sequence: '" + data[currentIndex - 1] + data[currentIndex] + "'.");
		}
		return false;
	}

	/**
	 * Checks if a valid TAG {@link TokenType}is encountered, as defined in the
	 * project documentation.
	 * 
	 * @param startIndex
	 *            represents the index in the local {@link Character} array
	 *            where the token data begins.
	 * @return returns true if a valid TAG {@link TokenType}is encountered.
	 */
	private boolean checkTag(int startIndex) {
		if ((data[currentIndex]) == CURLY_OPEN && (data[currentIndex + 1]) == DOLLAR) {
			this.state = LexerState.TAG;

			String value;
			int endIndex = currentIndex;

			if (endIndex != 0) {
				value = new String(data, startIndex, endIndex - startIndex);
				token = new Token(TokenType.TEXT, value);
				tokenised.add(token);
			}
			value = new StringBuilder().append(data[currentIndex]).append(data[currentIndex + 1]).toString();
			token = new Token(TokenType.STARTTAG, value);
			tokenised.add(token);

			currentIndex += 2;

			processTag(currentIndex);

			return true;
		}
		return false;
	}

	/**
	 * Checks if a valid ENDTAG {@link TokenType}is encountered, as defined in
	 * the project documentation.
	 * 
	 * @return returns true if a valid ENDTAG {@link TokenType}is encountered.
	 */
	private boolean checkEndTag() {
		if ((data[currentIndex]) == DOLLAR && (data[currentIndex + 1]) == CURLY_CLOSE) {
			this.state = LexerState.BASIC;

			String value = new StringBuilder().append(data[currentIndex]).append(data[currentIndex + 1]).toString();
			token = new Token(TokenType.ENDTAG, value);
			tokenised.add(token);

			currentIndex += 2;

			return true;
		}
		return false;
	}

	/**
	 * Removes a character from the specified position from the character array
	 * provided.
	 * 
	 * @param array
	 *            the character array from which the desired character will be
	 *            removed.
	 * @param removePosition
	 *            the position in the array where the unwanted character is
	 *            located.
	 * @return a new {@link Character} array, which does not contain the
	 *         specified character.
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

	/**
	 * Skips all the whitespace, until a character is encountered. This is
	 * performed by increasing the {@link Character} array pointer.
	 * 
	 * @return returns the index of a first character after the whitespace.
	 */
	private int skipBlanks() {
		while (currentIndex < data.length) {
			char c = data[currentIndex];
			if (c == ' ' || c == '\t' || c == '\r' || c == '\n') {
				currentIndex++;
				continue;
			}
			break;
		}
		return currentIndex;
	}
}