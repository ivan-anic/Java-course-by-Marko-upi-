package hr.fer.zemris.java.hw13.servlets;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

		String fileName = req.getServletContext().getRealPath("/WEB-INF/voting-results.txt");

		List<String> lines = Files.readAllLines(Paths.get(fileName));
		Map<String, Integer> mapResults = new HashMap<>();

		Integer argId = Integer.parseInt((String) req.getParameter("id"));
		StringBuilder sb = new StringBuilder();

		String fileNameDef = req.getServletContext().getRealPath("/WEB-INF/voting-definition.txt");

		List<String> linesDef = Files.readAllLines(Paths.get(fileNameDef));
		List<String> listResults = new ArrayList<>();

		Map<String, String> mapUrls = new HashMap<>();

		linesDef.forEach(z -> {
			listResults.add(z.split("\t")[1]);
			mapUrls.put(z.split("\t")[1], z.split("\t")[2]);
		});

		for (int i = 0; i < lines.size(); i++) {
			String z = lines.get(i);
			String[] linesSplit = z.split("\t");
			sb.append(linesSplit[0] + "\t");

			if ((Integer.parseInt(linesSplit[0]) == argId)) {
				linesSplit[1] = String.valueOf(Integer.parseInt(linesSplit[1]) + 1);
			}

			sb.append(Integer.parseInt(linesSplit[1]) + "\n");
			mapResults.put(listResults.get(i), Integer.parseInt(linesSplit[1]));
		}

		HashMap<Integer, String> sortedMap = new HashMap<>();
		for (Map.Entry<String, Integer> entry : mapResults.entrySet()) {
			sortedMap.put(entry.getValue(), entry.getKey());
		}

		int tmpMax = 0;
		Map<String, String> mapWinners = new HashMap<>();

		for (Integer num : sortedMap.keySet()) {
			if (num < tmpMax) {
				continue;
			}
			mapWinners.put(sortedMap.get(num), mapUrls.get(sortedMap.get(num)));
			if (num > tmpMax) {
				tmpMax = num;
				mapWinners.clear();
				mapWinners.put(sortedMap.get(num), mapUrls.get(sortedMap.get(num)));
			} else if (num == tmpMax) {
				mapWinners.put(sortedMap.get(num), mapUrls.get(sortedMap.get(num)));
			}
		}

		Files.write(Paths.get(fileName), sb.toString().getBytes(), StandardOpenOption.TRUNCATE_EXISTING);

		req.setAttribute("mapResults", mapResults);
		req.setAttribute("mapWinners", mapWinners);
		req.getRequestDispatcher("/WEB-INF/pages/votingResults.jsp").forward(req, resp);
	}
}
