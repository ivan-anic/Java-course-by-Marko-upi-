package hr.fer.zemris.java.tecaj.hw3.prob1;

/**
 * Enumeration of the token types used by the <i>{@link Lexer}<i> class.
 * 
 * <ul>
 * <li>{@link #EOF}</li>
 * <li>{@link #WORD}</li>
 * <li>{@link #NUMBER}</li>
 * <li>{@link #SYMBOL}</li>
 * </ul>
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public enum TokenType {
	/** No more tokens. */
	EOF,
	/** A word token. */
	WORD,
	/** A number token. */
	NUMBER,
	/** Represents a symbol. */
	SYMBOL
}
