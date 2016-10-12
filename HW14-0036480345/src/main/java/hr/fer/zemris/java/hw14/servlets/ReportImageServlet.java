package hr.fer.zemris.java.hw14.servlets;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.util.Rotation;

import hr.fer.zemris.java.hw14.dao.DAOProvider;
import hr.fer.zemris.java.hw14.models.PollOptions;

/**
 * A simple web servlet which displays a simple chart with the desired data.
 * Uses the {@linkplain JFreeChart} class.
 *
 * @author Ivan Anić
 * @version 1.0
 */
@WebServlet(name = "reportImage", urlPatterns = { "/reportImage" })
public class ReportImageServlet extends HttpServlet {

	/** The generated serial user ID. */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		resp.setContentType("image/png; charset=UTF-8");

		List<PollOptions> optionsList = DAOProvider.getDao()
				.getPollOptions((int) req.getSession().getAttribute("pollID"));

		PieDataset dataset = createDataset(optionsList);
		JFreeChart chart = createChart(dataset, "");

		try {
			BufferedImage img = chart.createBufferedImage(500, 270);
			ImageIO.write(img, "png", resp.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Creates a sample dataset.
	 *
	 * @param optionsList
	 *            the map
	 * @return the pie dataset
	 */
	private PieDataset createDataset(List<PollOptions> optionsList) {
		DefaultPieDataset result = new DefaultPieDataset();
		if (!optionsList.isEmpty()) {
			optionsList.forEach(z -> {
				result.setValue(z.getOptionTitle(), z.getVotesCount());
			});
		}
		return result;
	}

	/**
	 * Creates the chart.
	 *
	 * @param dataset
	 *            the dataset
	 * @param title
	 *            the title
	 * @return the desired instance of {@linkplain JFreeChart}
	 */
	private JFreeChart createChart(PieDataset dataset, String title) {

		JFreeChart chart = ChartFactory.createPieChart3D(title,
				dataset,
				true,
				true,
				false);

		PiePlot3D plot = (PiePlot3D) chart.getPlot();
		plot.setStartAngle(290);
		plot.setDirection(Rotation.CLOCKWISE);
		plot.setForegroundAlpha(0.5f);
		return chart;

	}
}