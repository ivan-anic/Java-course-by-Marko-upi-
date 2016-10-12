package hr.fer.zemris.java.hw13.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * A simple web servlet which generates trigonometrical sine and cosine values
 * for the given parameter range.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
@WebServlet(name = "trigonometry", urlPatterns = { "/trigonometry" })
public class TrigonometryServlet extends HttpServlet {

	/** The generated serial user ID */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		int startRange = 0;
		try {
			startRange = Integer.parseInt((String) req.getParameter("a"));
		} catch (NumberFormatException ex) {
			startRange = 5;
		}
		int endRange = 0;
		try {
			endRange = Integer.parseInt((String) req.getParameter("b"));
		} catch (NumberFormatException ex) {
			endRange = 360;
		}

		if (startRange > endRange) {
			endRange += startRange;
			startRange = endRange - startRange;
			endRange -= startRange;
		}
		if (endRange > startRange + 720) {
			endRange = startRange + 720;
		}

		Map<Integer, String[]> mapSin = new HashMap<>();

		for (; startRange <= endRange; startRange++) {
			mapSin.put(startRange,
					new String[] { String.format("%.4f", (Math.sin(Math.toRadians(startRange)))),
							String.format("%.4f", (Math.cos(Math.toRadians(startRange)))) });

		}
		req.setAttribute("mapSin", mapSin);

		req.getRequestDispatcher("WEB-INF/pages/trigonometry.jsp").forward(req, resp);

	}
}