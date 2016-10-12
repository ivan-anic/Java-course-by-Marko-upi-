package hr.fer.zemris.java.gui.charts;

/**
 * A point representing a location in (x,y) coordinate space, specified in
 * integer precision.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class XYValue {

	/** The x coordinate of this XYValue. */
	int x;
	/** The y coordinate of this XYValue. */
	int y;

	/**
	 * Constructs and initializes a XYValue at the specified (x,y) location in
	 * the coordinate space.
	 *
	 * @param x
	 *            the x coordinate of this XYValue
	 * @param y
	 *            the y coordinate of this XYValue
	 */
	public XYValue(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}

	/**
	 * Returns the x coordinate of this XYValue.
	 *
	 * @return the x the x coordinate of this XYValue.
	 */
	public int getX() {
		return x;
	}

	/**
	 * Returns the y coordinate of this XYValue.
	 *
	 * @return the y the y coordinate of this XYValue.
	 */
	public int getY() {
		return y;
	}

}
