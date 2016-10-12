package hr.fer.zemris.java.hw14.models;

/**
 * A class which represents an object implementation of a POLLOPTION instance
 * from the database.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class PollOptions {

	/** The poll option id. */
	private long id;
	/** The poll option title. */
	private String optionTitle;
	/** The poll option link. */
	private String optionLink;
	/** The poll option vote count. */
	private long votesCount;

	/**
	 * Instantiates a new poll option.
	 *
	 * @param id
	 *            the id
	 * @param optionTitle
	 *            the option title
	 * @param optionLink
	 *            the option link
	 * @param votesCount
	 *            the votes count
	 */
	public PollOptions(long id, String optionTitle, String optionLink, long votesCount) {
		super();
		this.id = id;
		this.optionTitle = optionTitle;
		this.optionLink = optionLink;
		this.votesCount = votesCount;
	}

	/**
	 * Gets the option title.
	 *
	 * @return the option title
	 */
	public String getOptionTitle() {
		return optionTitle;
	}

	/**
	 * Sets the option title.
	 *
	 * @param optionTitle
	 *            the new option title
	 */
	public void setOptionTitle(String optionTitle) {
		this.optionTitle = optionTitle;
	}

	/**
	 * Gets the option link.
	 *
	 * @return the option link
	 */
	public String getOptionLink() {
		return optionLink;
	}

	/**
	 * Sets the option link.
	 *
	 * @param optionLink
	 *            the new option link
	 */
	public void setOptionLink(String optionLink) {
		this.optionLink = optionLink;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * Gets the votes count.
	 *
	 * @return the votes count
	 */
	public long getVotesCount() {
		return votesCount;
	}
}
