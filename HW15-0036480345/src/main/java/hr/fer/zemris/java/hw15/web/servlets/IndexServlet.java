package hr.fer.zemris.java.hw15.web.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw15.dao.DAOProvider;
import hr.fer.zemris.java.hw15.model.BlogUser;

/**
 * A servlet which redirects the user to the main page.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
@WebServlet("/servleti/main")
public class IndexServlet extends HttpServlet {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<BlogUser> authors = DAOProvider.getDAO().getBlogAuthorsList();
		req.setAttribute("authors", authors);

		req.getRequestDispatcher("/WEB-INF/pages/index.jsp").forward(req, resp);
	}
}