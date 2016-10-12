package hr.fer.zemris.java.hw13.servlets;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

		String fileName = req.getServletContext().getRealPath("/WEB-INF/voting-definition.txt");

		List<String> lines = Files.readAllLines(Paths.get(fileName));
		List<String> listResults = new ArrayList<>();
		lines.forEach(z -> {
			listResults.add(z.split("\t")[1]);
		});

		req.setAttribute("listResults", listResults);
		req.getRequestDispatcher("/WEB-INF/pages/votingIndex.jsp").forward(req, resp);
	}
}
