package hr.fer.zemris.java.custom.collections;

/**
 * An exception being thrown when an empty stack is encountered.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class EmptyStackException extends java.lang.RuntimeException {
	/** version UID */
	private static final long serialVersionUID = 1L;

	/**
	 * A default, empty constructor.
	 */
	public EmptyStackException() {
	}

	/**
	 * Creates a new exception, and displays the desired message to the user.
	 * 
	 * @param message
	 *            - a message displayed to the user.
	 */
	public EmptyStackException(String message) {
		super(message);
	}

	/**
	 * Creates a new exception, displaying the cause of the exception
	 * occurrence.
	 * 
	 * @param cause
	 *            - the cause of the exception occurrence
	 */
	public EmptyStackException(Throwable cause) {
		super(cause);
	}

	/**
	 * Creates a new exception, and displays the desired message to the user,
	 * also displaying the cause of the exception occurence.
	 * 
	 * @param message
	 *            - a message displayed to the user.
	 * @param cause
	 *            - the cause of the exception occurrence
	 */
	public EmptyStackException(String message, Throwable cause) {
		super(message, cause);
	}
}