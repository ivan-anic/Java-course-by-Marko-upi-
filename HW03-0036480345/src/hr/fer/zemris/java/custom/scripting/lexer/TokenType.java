package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * Enumeration of the token types used by the <i>{@link Lexer}<i> class.
 * 
 * <ul>
 * <li>{@link #EOF}</li>
 * <li>{@link #VARIABLE}</li>
 * <li>{@link #CONSTANTINT}</li>
 * <li>{@link #CONSTANTDOUBLE}</li>
 * <li>{@link #STRING}</li>
 * <li>{@link #FUNCTION}</li>
 * <li>{@link #OPERATOR}</li>
 * <li>{@link #TEXT}</li>
 * <li>{@link #TAG}</li>
 * <li>{@link #STARTTAG}</li>
 * <li>{@link #ENDTAG}</li>
 * </ul>
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public enum TokenType {
	/** No more {@link Token}s. */
	EOF,
	/** A variable {@link Token}. */
	VARIABLE,
	/** A number {@link Token}, of type <code>Integer</code>. */
	CONSTANTINT,
	/** A number {@link Token}, of type <code>Double</code>. */
	CONSTANTDOUBLE,
	/** A <code>String</code> {@link Token}. */
	STRING,
	/** A {@link Token} which represents a function. */
	FUNCTION,
	/** An operation {@link Token}. */
	OPERATOR,
	/** A {@link Token} containing plain text. */
	TEXT,
	/** A {@link Token} representing a tag. */
	TAG,
	/** A {@link Token} representing the tag opening symbol sequence. */
	STARTTAG,
	/**	A {@link Token} representing the tag closing symbol sequence. */
	ENDTAG,
}
