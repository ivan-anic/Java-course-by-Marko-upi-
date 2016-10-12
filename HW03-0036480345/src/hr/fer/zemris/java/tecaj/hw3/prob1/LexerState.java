package hr.fer.zemris.java.tecaj.hw3.prob1;

/**
 * Enumeration of the <i>{@link Lexer}<i> state used by the <i>{@link Lexer}
 * <i> class.
 * 
 * <ul>
 * <li>{@link #BASIC}</li>
 * <li>{@link #EXTENDED}</li>
 * </ul>
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public enum LexerState {
	/** Represents a basic state of the lexer. */
	BASIC,
	/**
	 * Represents an extended state, using different parsing rules. Such blocks
	 * are surrounded with {@code #} symbols. Escapes do not exist, and every
	 * strings {@link TokenType} is WORD, except for blanks and '#' which
	 * produces a SYMBOL token.
	 */
	EXTENDED
}
