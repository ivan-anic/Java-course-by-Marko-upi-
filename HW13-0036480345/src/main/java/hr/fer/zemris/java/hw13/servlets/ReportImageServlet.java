package hr.fer.zemris.java.hw13.servlets;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.util.Rotation;

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

		PieDataset dataset = createDataset(req.getParameterMap());
		JFreeChart chart = createChart(dataset, "");
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));

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
	 * @param map
	 *            the map
	 * @return the pie dataset
	 */
	private PieDataset createDataset(Map<String, String[]> map) {
		DefaultPieDataset result = new DefaultPieDataset();

		if (!map.isEmpty()) {
			map.keySet().forEach(z -> {
				result.setValue(z, Integer.parseInt(map.get(z)[0]));

			});
		} else {
			result.setValue("Linux", 29);
			result.setValue("Mac", 20);
			result.setValue("Windows", 51);
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

	/**
	 * Gets the screen shot.
	 *
	 * @param panel
	 *            the panel
	 * @return the screen shot
	 */
	@SuppressWarnings("unused")
	private BufferedImage getScreenShot(JPanel panel) {
		BufferedImage bi = new BufferedImage(
				500, 270, BufferedImage.TYPE_INT_ARGB);
		panel.paint(bi.getGraphics());
		return bi;
	}
}