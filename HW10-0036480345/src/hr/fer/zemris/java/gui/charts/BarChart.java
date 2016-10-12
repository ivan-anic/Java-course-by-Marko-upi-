package hr.fer.zemris.java.gui.charts;

import java.util.List;

/**
 * Represents a single bar chart presented in this package.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class BarChart {

	/**
	 * Keeps the references to the {@linkplain XYValue}s presented in this
	 * barchart.
	 */
	private List<XYValue> values;

	/** The description located near the x Axis. */
	private String xAxisDesc;

	/** The description located near the y Axis. */
	private String yAxisDesc;

	/** The minimum y value displayed on this chart. */
	private int yMin;

	/** The maximum y value displayed on this chart. */
	private int yMax;

	/** The desired distance between bars. */
	private int yMargin;

	/**
	 * Instantiates a new bar chart with the desired values.
	 *
	 * @param values
	 *            the XYValues
	 * @param xAxisDesc
	 *            the x axis desc description located near the x Axis
	 * @param yAxisDesc
	 *            the y axis desc description located near the y Axis
	 * @param yMin
	 *            the y min minimum y value displayed on this chart
	 * @param yMax
	 *            the y max maximum y value displayed on this chart
	 * @param yMargin
	 *            the y margin desired distance between bars
	 */
	public BarChart(List<XYValue> values, String xAxisDesc, String yAxisDesc, int yMin, int yMax, int yMargin) {
		this.values = values;
		this.xAxisDesc = xAxisDesc;
		this.yAxisDesc = yAxisDesc;
		this.yMin = yMin;
		this.yMax = yMax;
		this.yMargin = yMargin;
	}

	/**
	 * Gets the values.
	 *
	 * @return the values
	 */
	public List<XYValue> getValues() {
		return values;
	}

	/**
	 * Gets the description located near the x Axis.
	 *
	 * @return the x axis desc description located near the x Axis
	 */
	public String getxAxisDesc() {
		return xAxisDesc;
	}

	/**
	 * Gets the description located near the y Axis.
	 *
	 * @return the y axis desc description located near the y Axis
	 */
	public String getyAxisDesc() {
		return yAxisDesc;
	}

	/**
	 * Gets the minimum y value displayed on this chart
	 *
	 * @return the minimum y value displayed on this chart
	 */
	public int getyMin() {
		return yMin;
	}

	/**
	 * Gets the maximum y value displayed on this chart
	 *
	 * @return the maximum y value displayed on this chart
	 */
	public int getyMax() {
		return yMax;
	}

	/**
	 * Gets the desired distance between bars.
	 *
	 * @return the desired distance between bars
	 */
	public int getyMargin() {
		return yMargin;
	}

}
