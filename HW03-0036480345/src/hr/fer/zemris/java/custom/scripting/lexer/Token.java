package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * A class which represents a single instance of a token used in lexic analysis.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class Token {
	/** The type of this token. */
	private TokenType type;
	/** The value which this token contains. */
	private Object value;

	/**
	 * Creates an instance of this class with the desired parameters.
	 * 
	 * @param type
	 *            the type of the token being created
	 * @param value
	 *            the value of the token being created
	 */
	public Token(TokenType type, Object value) {
		this.type = type;
		this.value = value;
	}

	/**
	 * Returns the value of this token instance.
	 * 
	 * @return value of this token.
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * Returns the type of this token instance.
	 * 
	 * @return type of this token
	 */
	public TokenType getType() {
		return type;
	}
}
