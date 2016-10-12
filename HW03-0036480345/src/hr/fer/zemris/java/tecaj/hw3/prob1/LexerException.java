package hr.fer.zemris.java.tecaj.hw3.prob1;

/**
 * An exception being thrown when an error in the {@link Lexer} class in encountered.
 * @author Ivan Anić
 * @version 1.0
 */
public class LexerException extends java.lang.RuntimeException {
	/** version UID */
	private static final long serialVersionUID = 1L;

	/**
	 * A default, empty constructor.
	 */
	public LexerException() {
	}
	
	/**
	 * Creates a new exception, and displays the desired message to the user.
	 * 
	 * @param message
	 *            - a message displayed to the user.
	 */
	public LexerException(String message) {
		super(message);
	}

}
