package hr.fer.zemris.java.hw18.web.servlets;

import java.io.IOException;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import hr.fer.zemris.java.hw18.web.util.ImageUtil;

/**
 * A simple web servlet which processes the files on the disk and returns the
 * list of all occuring tags to the browser.
 * 
 * @author Ivan Anić
 * @version 1.0
 */

@WebServlet(urlPatterns = { "/servlets/tags" })
public class TagsServlet extends HttpServlet {

	/** The generated serial user ID. */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		Set<String> list = ImageUtil.getTags(req);

		String[] array = new String[list.size()];
		list.toArray(array);

		resp.setContentType("application/json;charset=UTF-8");

		Gson gson = new Gson();
		String jsonText = gson.toJson(array);

		resp.getWriter().write(jsonText);

		resp.getWriter().flush();
	}

}
