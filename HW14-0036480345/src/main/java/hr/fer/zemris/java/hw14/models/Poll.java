package hr.fer.zemris.java.hw14.models;

/**
 * A class which represents an object implementation of a POLL instance from the
 * database.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class Poll {

	/** The poll id. */
	private long id;
	/** The poll title. */
	private String title;
	/** The poll message. */
	private String message;

	/**
	 * Instantiates a new poll.
	 *
	 * @param id
	 *            the id
	 * @param title
	 *            the title
	 * @param message
	 *            the message
	 */
	public Poll(long id, String title, String message) {
		super();
		this.id = id;
		this.title = title;
		this.message = message;
	}

	/**
	 * Gets the title.
	 *
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the title.
	 *
	 * @param title
	 *            the new title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Gets the message.
	 *
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Sets the message.
	 *
	 * @param message
	 *            the new message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public long getId() {
		return id;
	}
}
