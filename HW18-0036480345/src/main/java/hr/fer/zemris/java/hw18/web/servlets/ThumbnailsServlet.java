package hr.fer.zemris.java.hw18.web.servlets;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import hr.fer.zemris.java.hw18.web.util.ImageUtil;
import hr.fer.zemris.java.hw18.web.util.ImageWrapper;

/**
 * A simple web servlet which returns the thumbnail list for the specified tag
 * or the full resolution image of the selected thumbnail.
 * 
 * @author Ivan Anić
 * @version 1.0
 */

@WebServlet(urlPatterns = { "/servlets/thumbnail" })
public class ThumbnailsServlet extends HttpServlet {

	/** The generated serial user ID. */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String filter = req.getParameter("filter");

		Set<ImageWrapper> listNames = ImageUtil.getImagesForTag(filter);

		resp.setContentType("application/json;charset=UTF-8");

		Gson gson = new Gson();

		String name = req.getParameter("name");
		if (name != null) {
			// returns a thumbnail
			String path = ImageUtil.getThumbnail(name, req);

			BufferedImage img = ImageIO.read(new File(path));
			ImageIO.write(img, "png", resp.getOutputStream());

			return;
		}

		String jsonText = gson.toJson(listNames);

		resp.getWriter().write(jsonText);

		resp.getWriter().flush();
	}

}
