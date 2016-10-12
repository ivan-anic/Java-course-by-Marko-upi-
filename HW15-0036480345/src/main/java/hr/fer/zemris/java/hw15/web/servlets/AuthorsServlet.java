package hr.fer.zemris.java.hw15.web.servlets;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw15.dao.DAOProvider;
import hr.fer.zemris.java.hw15.forms.BlogEntryForm;
import hr.fer.zemris.java.hw15.model.BlogComment;
import hr.fer.zemris.java.hw15.model.BlogEntry;
import hr.fer.zemris.java.hw15.model.BlogUser;

/**
 * A simple web servlet which performs various operations, adding or editing new
 * blog entries, adding comments to each blog.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
@WebServlet("/servleti/author/*")
public class AuthorsServlet extends HttpServlet {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		processGet(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String[] path = req.getPathInfo().split("/");
		String author = path[1];
		String type = path[2];

		String method = req.getParameter("metoda");

		if ("Odustani".equals(method)) {
			req.getSession().setAttribute("register", null);
			resp.sendRedirect(req.getServletContext().getContextPath() +
					req.getServletPath() + "/" + author);
		}

		if (null != req.getParameter("metoda")) {
			processEntry(req, resp, author, type);
		} else if (null != req.getParameter("method-comment")) {
			processComment(req, resp);
		}
		req.setAttribute("method", method);
	}

	/**
	 * Processes the GET http request.
	 *
	 * @param req
	 *            the request
	 * @param resp
	 *            the response
	 * @throws ServletException
	 *             if an exception occurs
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	protected void processGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String[] path = req.getPathInfo().split("/");
		String author = path[1];

		BlogEntryForm entry = new BlogEntryForm();

		if (path.length == 2) {
			displayBlogList(req, resp, author);
		} else if (path.length >= 3) {
			switch (path[2]) {
			case ("new"):
				entry.popuniIzHttpRequesta(req);
				req.setAttribute("entry", entry);
				req.getRequestDispatcher("/WEB-INF/pages/addBlogEntry.jsp").forward(req, resp);
				return;
			case ("edit"):
				BlogEntry e = DAOProvider.getDAO().getBlogEntry(Long.parseLong(path[3]));
				entry.popuniIzRecorda(e);

				if (entry != null) {
					req.setAttribute("entry", entry);
					req.getRequestDispatcher("/WEB-INF/pages/addBlogEntry.jsp").forward(req, resp);
					return;
				}
			case ("postComment"):
				processComment(req, resp);
				break;
			default:
				if (path[2].matches("\\d+")) {
					entry.popuniIzRecorda(DAOProvider.getDAO().getBlogEntry(Long.parseLong(path[2])));
					req.setAttribute("author", author);
					req.setAttribute("entry", entry);
					req.getRequestDispatcher("/WEB-INF/pages/displayBlogEntry.jsp").forward(req, resp);
				}
			}
		}

		req.setAttribute("path", req.getServletPath());
		req.setAttribute("path2", req.getPathInfo());
		req.setAttribute("author", author);
		req.getRequestDispatcher("/WEB-INF/pages/listBlogEntries.jsp").forward(req, resp);

	}

	/**
	 * Processes the comment and saves it to the database.
	 *
	 * @param req
	 *            the request
	 * @param resp
	 *            the response
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws ServletException
	 *             if an exception occurs.
	 */
	private void processComment(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		String commentText = req.getParameter("comment");
		BlogComment comment = new BlogComment();
		Long id = Long.valueOf(req.getParameter("id"));
		BlogEntry entry = DAOProvider.getDAO().getBlogEntry(id);
		String nick = (String) req.getSession().getAttribute("current.user.nick");
		BlogUser usr = DAOProvider.getDAO().getBlogUser(nick);

		comment.setMessage(commentText);
		comment.setBlogEntry(entry);
		comment.setPostedOn(new Date());
		comment.setUsersEMail((usr == null) ? "anon@anon.com" : usr.getEmail());
		DAOProvider.getDAO().addComment(comment);

		String[] pathArgs = req.getPathInfo().split("/");
		String path = pathArgs[0] + "/" + pathArgs[1] + "/";

		req.setAttribute("blogentry", entry);
		resp.sendRedirect(req.getContextPath() + req.getServletPath() + path + id);
		return;
	}

	/**
	 * Process the entry, either adding a new user or editing the existing one
	 * from the database.
	 *
	 * @param req
	 *            the request
	 * @param resp
	 *            the response
	 * @param author
	 *            the author id
	 * @param type
	 *            the type of the operation being executed
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws ServletException
	 *             if an exception occurs
	 */
	private void processEntry(HttpServletRequest req, HttpServletResponse resp, String author, String type)
			throws IOException, ServletException {
		BlogEntry rec = new BlogEntry();
		BlogEntryForm f = new BlogEntryForm();

		f.popuniIzHttpRequesta(req);
		f.validiraj();

		if (f.imaPogresaka()) {
			req.setAttribute("entry", f);
			req.getRequestDispatcher("/WEB-INF/pages/addBlogEntry.jsp").forward(req, resp);
			return;
		}

		f.popuniURecord(rec);
		rec.setLastModifiedAt(new Date());
		rec.setCreatedAt(new Date());
		rec.setAuthor(
				DAOProvider.getDAO().getBlogUser((String) req.getSession().getAttribute("current.user.nick")));

		if ("edit".equals(type)) {
			Long id = Long.valueOf(req.getParameter("id"));
			BlogEntry recOld = DAOProvider.getDAO().getBlogEntry(id);
			recOld.setLastModifiedAt(rec.getLastModifiedAt());
			recOld.setTitle(rec.getTitle());
			recOld.setText(rec.getText());

			DAOProvider.getDAO().updateBlogEntry(recOld);
		} else {
			DAOProvider.getDAO().addBlogEntry(rec);
		}
		resp.sendRedirect(req.getContextPath() + "/servleti/author/"
				+ author);
	}

	/**
	 * Displays the blog list from the database.
	 *
	 * @param req
	 *            the request
	 * @param resp
	 *            the response
	 * @param author
	 *            the author id
	 */
	private void displayBlogList(HttpServletRequest req, HttpServletResponse resp, String author) {
		List<BlogEntry> entries = DAOProvider.getDAO().getBlogEntriesByAuthor(author);
		req.setAttribute("entries", entries);
	}

}