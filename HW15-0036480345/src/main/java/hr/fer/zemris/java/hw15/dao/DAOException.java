package hr.fer.zemris.java.hw15.dao;

/**
 * An exception being thrown when an error in the data persistency subsystem is
 * encountered.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class DAOException extends RuntimeException {
	/** version UID */
	private static final long serialVersionUID = 1L;

	/**
	 * A default, empty constructor.
	 */
	public DAOException() {
	}

	/**
	 * Creates a new exception, with the specified detail message, cause,
	 * suppression enabled or disabled, and writable stack trace enabled or
	 * disabled.
	 * 
	 * @param message
	 *            - a message displayed to the user.
	 * @param cause
	 *            - the cause of the exception occurrence
	 * @param enableSuppression
	 *            - whether or not suppression is enabled or disabled
	 * @param writableStackTrace
	 *            - whether or not the stack trace should be writable
	 *
	 */
	public DAOException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
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
	public DAOException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Creates a new exception, and displays the desired message to the user.
	 * 
	 * @param message
	 *            - a message displayed to the user.
	 */
	public DAOException(String message) {
		super(message);
	}

	/**
	 * Creates a new exception, displaying the cause of the exception
	 * occurrence.
	 * 
	 * @param cause
	 *            - the cause of the exception occurrence
	 */
	public DAOException(Throwable cause) {
		super(cause);
	}
}