
package hr.fer.zemris.java.hw15.dao.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import hr.fer.zemris.java.hw15.dao.DAO;
import hr.fer.zemris.java.hw15.dao.DAOException;
import hr.fer.zemris.java.hw15.model.BlogComment;
import hr.fer.zemris.java.hw15.model.BlogEntry;
import hr.fer.zemris.java.hw15.model.BlogUser;

/**
 * An implementation of {@linkplain DAO} interface using JPA technology. This
 * implementation uses the entity manager instance provided by the
 * {@linkplain JPAEMFProvider}.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class JPADAOImpl implements DAO {

	@Override
	public BlogEntry getBlogEntry(Long id) throws DAOException {
		BlogEntry blogEntry = JPAEMProvider.getEntityManager().find(BlogEntry.class, id);
		return blogEntry;
	}

	@Override
	public void addBlogEntry(BlogEntry e) {
		EntityManager em = JPAEMProvider.getEntityManager();
		em.persist(e);
	}

	@Override
	public void updateBlogEntry(BlogEntry e) {
		EntityManager em = JPAEMProvider.getEntityManager();
		em.merge(e);
	}

	public List<BlogEntry> getBlogEntriesByAuthor(String author) {
		EntityManager em = JPAEMProvider.getEntityManager();

		BlogUser user = getBlogUser(author);
		if (user == null) {
			return new ArrayList<BlogEntry>();
		}
		@SuppressWarnings("unchecked")
		List<BlogEntry> blogs = (List<BlogEntry>) em
				.createQuery(
						"select b from BlogEntry as b where b.author=:author")
				.setParameter("author", user).getResultList();

		return blogs;
	}

	@Override
	public BlogUser getBlogUser(String nick) throws DAOException {
		EntityManager em = JPAEMProvider.getEntityManager();

		@SuppressWarnings("unchecked")
		List<BlogUser> users = (List<BlogUser>) em
				.createQuery("select b from BlogUser as b where b.nick=:nick")
				.setParameter("nick", nick).getResultList();
		if (users.size() == 0) {
			return null;
		}
		return users.get(0);
	}

	@Override
	public void addBlogUser(BlogUser u) throws DAOException {
		EntityManager em = JPAEMProvider.getEntityManager();

		em.persist(u);
	}

	@Override
	public void addComment(BlogComment b) throws DAOException {
		EntityManager em = JPAEMProvider.getEntityManager();

		em.persist(b);
	}

	@Override
	public List<BlogComment> getBlogCommentsList() {
		EntityManager em = JPAEMProvider.getEntityManager();

		@SuppressWarnings("unchecked")
		List<BlogComment> comments = (List<BlogComment>) em.createQuery("from BlogComment")
				.getResultList();

		return comments;
	}

	@Override
	public List<BlogUser> getBlogAuthorsList() {
		EntityManager em = JPAEMProvider.getEntityManager();

		@SuppressWarnings("unchecked")
		List<BlogUser> authors = (List<BlogUser>) em.createQuery("from BlogUser")
				.getResultList();

		return authors;
	}
}