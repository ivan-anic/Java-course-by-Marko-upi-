package hr.fer.zemris.java.hw15.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw15.dao.DAO;
import hr.fer.zemris.java.hw15.dao.DAOProvider;
import hr.fer.zemris.java.hw15.forms.BlogUserForm;
import hr.fer.zemris.java.hw15.model.BlogUser;

/**
 * A simple web servlet which performs the registration operation. Stores the
 * new user into the database via {@linkplain DAO} implementation.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
@WebServlet("/servleti/register")
public class RegistrationServlet extends HttpServlet {

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
	 * Processes the operation.
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
	protected void process(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");

		String metoda = req.getParameter("metoda");

		if ("Odustani".equals(metoda)) {
			req.getSession().setAttribute("register", null);
			resp.sendRedirect(req.getServletContext().getContextPath()
					+ "/servleti/main");
			return;
		} else if (!"Pohrani".equals(metoda)) {
			req.getRequestDispatcher("/WEB-INF/pages/registration.jsp").forward(req, resp);
			return;
		}

		BlogUserForm f = new BlogUserForm();
		f.popuniIzHttpRequesta(req);
		f.validiraj();

		if (f.imaPogresaka()) {
			req.setAttribute("entry", f);
			req.getRequestDispatcher("/WEB-INF/pages/registration.jsp").forward(req, resp);
			return;
		}

		BlogUser r = new BlogUser();
		f.popuniURecord(r);

		r.setPasswordHash(WebUtil.processSha1Encrypt(r.getPasswordHash()));

		DAOProvider.getDAO().addBlogUser(r);

		req.getRequestDispatcher("/WEB-INF/pages/RegSuccess.jsp").forward(req, resp);
	}
}