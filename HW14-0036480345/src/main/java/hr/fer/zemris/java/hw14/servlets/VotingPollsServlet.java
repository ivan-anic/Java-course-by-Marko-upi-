package hr.fer.zemris.java.hw14.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw14.dao.DAOProvider;
import hr.fer.zemris.java.hw14.models.Poll;

/**
 * A simple web servlet which displays the availible polls acquired from the
 * database
 *
 * @author Ivan Anić
 * @version 1.0
 */
@WebServlet(name = "index", urlPatterns = { "/index.html", "" })
public class VotingPollsServlet extends HttpServlet {

	/** The generated serial user ID */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		DAOProvider.getDao().createTablesIfInexistent(req.getServletContext());

		List<Poll> listPolls = DAOProvider.getDao().getPolls();

		req.setAttribute("listPolls", listPolls);
		req.getRequestDispatcher("/WEB-INF/pages/index.jsp").forward(req, resp);
	}
}