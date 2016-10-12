package hr.fer.zemris.java.hw15.web.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw15.dao.DAOProvider;
import hr.fer.zemris.java.hw15.forms.BlogUserForm;
import hr.fer.zemris.java.hw15.model.BlogUser;

/**
 * A simple web servlet which performs the login operation. If successful,
 * stores the user data into the session data.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
@WebServlet("/servleti/login")
public class LoginServlet extends HttpServlet {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		process(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		process(req, resp);
	}

	/**
	 * Processes the request.
	 *
	 * @param req
	 *            the request
	 * @param resp
	 *            the response
	 * @throws ServletException
	 *             if an exception ocurs
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	protected void process(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		List<BlogUser> authors = DAOProvider.getDAO().getBlogAuthorsList();
		req.setAttribute("authors", authors);

		req.setCharacterEncoding("UTF-8");

		String metoda = req.getParameter("metoda");
		if (!"Prijava".equals(metoda)) {
			resp.sendRedirect(req.getServletContext().getContextPath()
					+ "/servleti/main");
			return;
		}

		BlogUserForm f = new BlogUserForm();
		f.popuniIzHttpRequesta(req);

		BlogUser user = DAOProvider.getDAO().getBlogUser(f.getNick());

		if (user == null) {
			f.dodajGresku("nick", "Nick does not exist in the database!");
		} else if (!WebUtil.processSha1Encrypt(f.getPassword()).equals(user.getPasswordHash())) {
			f.dodajGresku("password", "Password does not match!");
		}

		if (f.imaPogresaka()) {
			req.setAttribute("entry", f);
			req.getRequestDispatcher("/WEB-INF/pages/index.jsp").forward(req, resp);
			return;
		}

		req.getSession().setAttribute("current.user.fn", user.getFirstName());
		req.getSession().setAttribute("current.user.ln", user.getLastName());
		req.getSession().setAttribute("current.user.id", user.getId());
		req.getSession().setAttribute("current.user.nick", user.getNick());

		req.getRequestDispatcher("/WEB-INF/pages/index.jsp").forward(req, resp);
	}
}