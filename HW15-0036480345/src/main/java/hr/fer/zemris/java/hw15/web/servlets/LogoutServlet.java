package hr.fer.zemris.java.hw15.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// TODO: Auto-generated Javadoc
/**
 * A simple web servlet which performs the logout operation.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
@WebServlet("/servleti/logout")
public class LogoutServlet extends HttpServlet {

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
	 * Processes the logout operation.
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

		req.getSession().invalidate();
		resp.sendRedirect(req.getServletContext().getContextPath()
				+ "/servleti/main");
	}
}