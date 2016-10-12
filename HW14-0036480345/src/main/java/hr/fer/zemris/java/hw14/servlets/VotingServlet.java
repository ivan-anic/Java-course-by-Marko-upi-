package hr.fer.zemris.java.hw14.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw14.dao.DAOProvider;
import hr.fer.zemris.java.hw14.models.Poll;
import hr.fer.zemris.java.hw14.models.PollOptions;

/**
 * @author Ivan Anić
 * @version 1.0
 */
@WebServlet(name = "votingIndex", urlPatterns = { "/votingIndex" })
public class VotingServlet extends HttpServlet {

	/** The generated serial user ID */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		int param = 0;
		Poll poll = null;
		List<PollOptions> listPollOptions = new ArrayList<>();

		try {
			param = Integer.parseInt((String) req.getParameter("pollID"));
			listPollOptions = DAOProvider.getDao().getPollOptions(param);
			poll = DAOProvider.getDao().getPoll(param);
		} catch (Exception ex) {
			resp.sendError(400, "Bad parameters.");
			return;
		} finally {
			if (listPollOptions.isEmpty() || poll == null) {
				resp.sendError(400, "Bad parameters.");
				return;
			}
		}

		req.getSession().setAttribute("pollID", param);
		req.setAttribute("poll", poll);
		req.setAttribute("listPollOptions", listPollOptions);
		req.getRequestDispatcher("/WEB-INF/pages/votingIndex.jsp").forward(req, resp);
	}
}
