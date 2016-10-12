package hr.fer.zemris.java.hw14.dao;

import java.util.List;

import javax.servlet.ServletContext;

import hr.fer.zemris.java.hw14.models.Poll;
import hr.fer.zemris.java.hw14.models.PollOptions;

/**
 * An interface implemented by the {@linkplain DAOProvider}. Its purpose is the
 * communication with the data persistency subsystem.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public interface DAO {

	/**
	 * Gets all existing {@linkplain Poll} instances from the database.
	 * 
	 * @return the desired poll list
	 * @throws DAOException
	 *             if an error occurs
	 */
	public List<Poll> getPolls() throws DAOException;

	/**
	 * Gets the desired {@linkplain Poll} instance from the database.
	 * 
	 * @param id
	 *            the id of the desired poll
	 * 
	 * @return the desired poll instance
	 * @throws DAOException
	 *             if an error occurs
	 */
	public Poll getPoll(long id) throws DAOException;

	/**
	 * Gets all existing {@linkplain PollOptions} instances from the database.
	 * 
	 * @param id
	 *            the id of the desired poll
	 * 
	 * @return the desired poll options list
	 * @throws DAOException
	 *             if an error occurs
	 */
	public List<PollOptions> getPollOptions(long id) throws DAOException;

	/**
	 * Creates the sql database tables if they aren't created yet.
	 *
	 * @param sc
	 *            the {{@linkplain ServletContext instance}
	 */
	public void createTablesIfInexistent(ServletContext sc);

	/**
	 * Inserts a new {@linkplain Poll} instance to the database.
	 *
	 * @param id
	 *            the id of the new poll
	 * @param title
	 *            the title of the new poll
	 * @param message
	 *            the message of the new poll
	 */
	public void insertPoll(long id, String title, String message);

	/**
	 * Insert a new {@linkplain PollOptions} instance to the database.
	 *
	 * @param id
	 *            the id of the new poll option
	 * @param optionTitle
	 *            the option title
	 * @param optionLink
	 *            the option link
	 * @param votesCount
	 *            the initial vote count
	 */
	public void insertPollOption(long id, String optionTitle, String optionLink, long votesCount);

	/**
	 * Gets the vote count from the specified {@linkplain PollOptions}.
	 *
	 * @param pollID
	 *            the poll id
	 * @param optionID
	 *            the option id
	 * @return the option count
	 */
	public Integer getOptionCount(long pollID, long optionID);

	/**
	 * Updates the vote count of the specified {@linkplain PollOptions}.
	 *
	 * @param pollID
	 *            the poll id
	 * @param optionID
	 *            the option id
	 * @param votesCount
	 *            the votes count
	 */
	public void updateVoteCount(long pollID, long optionID, int votesCount);
}