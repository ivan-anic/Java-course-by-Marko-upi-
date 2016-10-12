package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * Enumeration of the <i>{@link Lexer}<i> state used by the <i>{@link Lexer}
 * <i> class.
 * 
 * <ul>
 * <li>{@link #BASIC}</li>
 * <li>{@link #TAG}</li>
 * </ul>
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public enum LexerState {
	/** Represents a basic state of the lexer. */
	BASIC,
	/**
	 * Represents an extended state, using different rules for lexical analysis.
	 * Such blocks are surrounded with {{@code $} and }{@code $} symbols.
	 */
	TAG
}
