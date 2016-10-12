package hr.fer.zemris.java.hw14.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw14.dao.DAOProvider;
import hr.fer.zemris.java.hw14.models.PollOptions;

/**
 * A simple web servlet which generates the results of the poll, and updates the
 * files on the disk.
 * 
 * @author Ivan Anić
 * @version 1.0
 */

@WebServlet(name = "votingVote", urlPatterns = { "/voting-vote" })
public class VotingVoteServlet extends HttpServlet {

	/** The generated serial user ID */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		int paramsess = 0;
		long param = 0;
		List<PollOptions> listResults = null;
		try {
			paramsess = (int) req.getSession().getAttribute("pollID");
			param = Integer.parseInt((String) req.getParameter("id"));
			int count = DAOProvider.getDao().getOptionCount(paramsess, param);
			if (count != -1) {
				count++;
				DAOProvider.getDao().updateVoteCount(paramsess, param, count);
			}
			listResults = DAOProvider.getDao().getPollOptions(paramsess);

		} catch (Exception ex) {
			resp.sendError(400, "Bad parameters.");
			return;
		}

		List<PollOptions> listSorted = listResults.stream()
				.sorted((z1, z2) -> Long.compare(z1.getVotesCount(), z2.getVotesCount()))
				.collect(Collectors.toList());

		List<PollOptions> listWinners = new ArrayList<>();

		long max = listSorted.get(listSorted.size() - 1).getVotesCount();
		for (PollOptions p : listSorted) {
			if (p.getVotesCount() == max) {
				listWinners.add(p);
			}
		}

		req.setAttribute("listResults", listSorted);
		req.setAttribute("listWinners", listWinners);
		req.getRequestDispatcher("/WEB-INF/pages/votingResults.jsp").forward(req, resp);
	}
}
