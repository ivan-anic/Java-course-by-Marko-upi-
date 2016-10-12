package hr.fer.zemris.java.custom.scripting.parser;

import hr.fer.zemris.java.custom.scripting.lexer.Lexer;

/**
 * An exception being thrown when an error in the {@link Lexer} class in encountered.
 * @author Ivan Anić
 * @version 1.0
 */
public class SmartScriptParserException extends java.lang.RuntimeException {
	/** version UID */
	private static final long serialVersionUID = 1L;

	/**
	 * A default, empty constructor.
	 */
	public SmartScriptParserException() {
	}

	/**
	 * Creates a new exception, and displays the desired message to the user.
	 * 
	 * @param message
	 *            - a message displayed to the user.
	 */
	public SmartScriptParserException(String message) {
		super(message);
	}

}
