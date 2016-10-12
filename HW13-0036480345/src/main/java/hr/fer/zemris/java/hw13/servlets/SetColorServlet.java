package hr.fer.zemris.java.hw13.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * A simple web servlet which sets the current session color. Every web page in
 * this application will be be coloured with the desired colour.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
@WebServlet(name = "setcolor", urlPatterns = { "/setcolor" })
public class SetColorServlet extends HttpServlet {

	/** The generated serial user ID */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		req.getSession().setAttribute("pickedBgCol", (String) req.getParameter("pickedBgCol"));
		resp.sendRedirect("index.jsp");
	}
}
