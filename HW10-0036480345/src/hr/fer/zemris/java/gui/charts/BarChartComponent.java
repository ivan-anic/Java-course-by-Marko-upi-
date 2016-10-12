package hr.fer.zemris.java.gui.charts;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.util.List;

import javax.swing.JComponent;

/**
 * Represents a single bar chart {@linkplain Component}.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class BarChartComponent extends JComponent {

	/** The version user ID. */
	private static final long serialVersionUID = 1L;
	/** A reference to the barChart being displayed. */
	private BarChart barChart;

	/**
	 * Instantiates a new bar chart component.
	 *
	 * @param barChart
	 *            the bar chart which contains the elements being displayed.
	 */
	public BarChartComponent(BarChart barChart) {
		this.barChart = barChart;
	}

	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;

		draw(g2);
	}

	/**
	 * Draws the neccesary parts of this component.
	 *
	 * @param g2
	 *            the g2
	 */
	public void draw(Graphics2D g2) {
		int i = 1;
		double max = 0;

		Dimension dim = getSize();
		int height = dim.height;
		int width = dim.width;

		List<XYValue> values = barChart.getValues();

		for (XYValue value : values) {
			if (max < value.getY())
				max = value.getY();
		}
		int xwidth = width - 100;
		int yheight = height - 50;

		int xleft = 50;

		int m = barChart.getyMargin();

		int numLines = (int) Math.ceil((double) (barChart.getyMax() -
				barChart.getyMin()) / barChart.getyMargin());

		int yHeightSc = (int) Math.ceil((yheight * 0.9));

		int margin = yHeightSc / numLines;

		int z = yheight;

		FontMetrics fm = g2.getFontMetrics();
		int w = fm.stringWidth(String.valueOf(barChart.getyMax()));

		for (int x = 0; x <= numLines * 2; x += m) {
			g2.drawLine(xleft - 2, z, xwidth + 50, z);

			if (x % m == 0) {
				g2.drawString(String.valueOf(x), xleft - w - 5, z);
			}

			z -= margin;
		}
		int n = 0;
		w = fm.stringWidth(String.valueOf(n));

		for (XYValue value : values) {

			int xright = 50 + xwidth * (i++) / values.size();
			int barWidth = xwidth / values.size();
			int barHeight = (int) Math.round(yheight * value.getY() / max * (yHeightSc) / yheight);

			Rectangle bar = new Rectangle(xleft, yheight - barHeight,
					barWidth, barHeight);

			g2.setColor(Color.BLACK);
			g2.drawLine(xright, yheight, xright, yheight + 2);
			g2.drawString(String.valueOf(barChart.getValues().get(n++).x),
					xright - (xright - xleft) / 2 - w / 2,
					yheight + 20);

			g2.setColor(Color.ORANGE);
			g2.draw(bar);
			g2.fill(bar);

			xleft = xright;
		}

		xleft = 50;

		String xAxisDesc = barChart.getxAxisDesc();
		String yAxisDesc = barChart.getyAxisDesc();

		w = fm.stringWidth(xAxisDesc);

		g2.setColor(Color.BLACK);

		g2.drawString(xAxisDesc, width / 2 - w / 2, (height - 20));

		g2.drawLine(xleft - 2, yheight, xwidth + xleft + 2, yheight);
		g2.drawLine(xleft, yheight + 2, xleft, yheight - yHeightSc);

		AffineTransform at = new AffineTransform();
		at.rotate(Math.PI * 3 / 2);

		g2.setTransform(at);

		w = fm.stringWidth(yAxisDesc);

		g2.drawString(yAxisDesc, (-height / 2 + (-w / 2)), 25);

	}

}
