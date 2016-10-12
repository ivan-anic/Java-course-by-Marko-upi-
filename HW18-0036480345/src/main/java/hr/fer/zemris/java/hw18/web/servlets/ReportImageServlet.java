package hr.fer.zemris.java.hw18.web.servlets;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw18.web.util.ImageUtil;
import hr.fer.zemris.java.hw18.web.util.ImageWrapper;

/**
 * A simple web servlet which writes the desired image to the output stream.
 *
 * @author Ivan Anić
 * @version 1.0
 */

@WebServlet(name = "reportImage", urlPatterns = { "/servlets/fullImage" })
public class ReportImageServlet extends HttpServlet {

	/** The generated serial user ID. */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		resp.setContentType("image/png; charset=UTF-8");

		String name = req.getParameter("name");
		if (name != null) {
			// returns the image
			ImageWrapper i = ImageUtil.getImageForName(name);

			String imgName = req.getSession()
					.getServletContext().getRealPath("WEB-INF/slike/" + i.getName());

			BufferedImage img = ImageIO.read(new File(imgName));
			ImageIO.write(img, "png", resp.getOutputStream());

			return;
		}
	}
}
