package hr.fer.zemris.java.gui.charts.demo;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Arrays;

import javax.swing.JFrame;

import hr.fer.zemris.java.gui.charts.BarChart;
import hr.fer.zemris.java.gui.charts.BarChartComponent;
import hr.fer.zemris.java.gui.charts.XYValue;

/**
 * Demonstration of the {@linkplain BarChart}
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class BarChartDemo {

	/**
	 * The main method, first one to be executed when the program starts.
	 * 
	 * @param args
	 *            - the input arguments, a path to a test file should be
	 *            entered.
	 */
	public static void main(String[] args) {
		JFrame frame = new JFrame();

		final int FRAME_WIDTH = 300;
		final int FRAME_HEIGHT = 400;

		frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		frame.setTitle("BarChart");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		String xAxisDesc;
		String yAxisDesc;
		int yMin;
		int yMax;
		int yMargin;

		BarChart model = null;

		String[] line;
		if (args.length == 1) {
			try (
					InputStream fis = new FileInputStream(args[0]);
					InputStreamReader isr = new InputStreamReader(fis, Charset.forName("UTF-8"));
					BufferedReader br = new BufferedReader(isr);) {
				xAxisDesc = br.readLine().trim();
				yAxisDesc = br.readLine().trim();
				line = br.readLine().trim().split(" ");

				String[] arguments;
				XYValue[] xyvalues = new XYValue[line.length];
				for (int i = 0; i < xyvalues.length; i++) {
					arguments = line[i].split(",");
					xyvalues[i] = new XYValue(Integer.valueOf(arguments[0]), Integer.valueOf(arguments[1]));
				}

				yMin = Integer.valueOf(br.readLine().trim());
				yMax = Integer.valueOf(br.readLine().trim());
				yMargin = Integer.valueOf(br.readLine().trim());

				model = new BarChart(
						Arrays.asList(xyvalues),
						xAxisDesc,
						yAxisDesc,
						yMin,
						yMax,
						yMargin);
			} catch (IOException e) {
				e.printStackTrace();
			}

		} else {
			// demo, if no arguments are given
			model = new BarChart(
					Arrays.asList(
							new XYValue(1, 8), new XYValue(2, 20), new XYValue(3, 22),
							new XYValue(4, 10), new XYValue(5, 4)),
					"Number of people in the car",
					"Frequency",
					0,
					22,
					2);
		}
		BarChartComponent component = new BarChartComponent(model);
		frame.add(component);

		frame.setVisible(true);
	}
}
