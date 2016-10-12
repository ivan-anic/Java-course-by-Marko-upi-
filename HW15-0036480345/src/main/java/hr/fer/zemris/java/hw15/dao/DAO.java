package hr.fer.zemris.java.hw15.dao;

import java.util.List;

import hr.fer.zemris.java.hw15.dao.jpa.JPADAOImpl;
import hr.fer.zemris.java.hw15.model.BlogComment;
import hr.fer.zemris.java.hw15.model.BlogEntry;
import hr.fer.zemris.java.hw15.model.BlogUser;

/**
 * An interface implemented by the {@linkplain JPADAOImpl}. Its purpose is the
 * communication with the data persistency subsystem.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public interface DAO {

	/**
	 * Dohvaća entry sa zadanim <code>id</code>-em. Ako takav entry ne postoji,
	 * vraća <code>null</code>.
	 * 
	 * @param id
	 *            ključ zapisa
	 * @return entry ili <code>null</code> ako entry ne postoji
	 * @throws DAOException
	 *             ako dođe do pogreške pri dohvatu podataka
	 */
	public BlogEntry getBlogEntry(Long id) throws DAOException;

	/**
	 * Inserts a new {@linkplain BlogEntry} instance to the database.
	 *
	 * @param e
	 *            the entry to be saved
	 */
	public void addBlogEntry(BlogEntry e);

	/**
	 * Gets the blog entries by author nick.
	 *
	 * @param author
	 *            the author nickname
	 * @return the desired blog entry list by author
	 */
	public List<BlogEntry> getBlogEntriesByAuthor(String author);

	/**
	 * Inserts a new {@linkplain BlogUser} instance to the database.
	 *
	 * @param u
	 *            the blog user
	 * @throws DAOException
	 *             the DAO exception
	 */
	public void addBlogUser(BlogUser u) throws DAOException;

	/**
	 * Gets the desired {@linkplain BlogUser} instance from the database.
	 *
	 * @param id
	 *            the user id
	 * @return the desired blog user
	 * @throws DAOException
	 *             the DAO exception if an exception occurs
	 */
	public BlogUser getBlogUser(String id) throws DAOException;

	/**
	 * Gets the list of all {@linkplain BlogUser}s.
	 *
	 * @return the blog authors list
	 */
	public List<BlogUser> getBlogAuthorsList();

	/**
	 * Updates the given {@linkplain BlogEntry}.
	 *
	 * @param e
	 *            the e
	 */
	void updateBlogEntry(BlogEntry e);

	/**
	 * Adds the comment.
	 *
	 * @param b
	 *            the b
	 * @throws DAOException
	 *             the DAO exception
	 */
	void addComment(BlogComment b) throws DAOException;

	/**
	 * Gets the blog comments list.
	 *
	 * @return the blog comments list
	 */
	List<BlogComment> getBlogCommentsList();
}