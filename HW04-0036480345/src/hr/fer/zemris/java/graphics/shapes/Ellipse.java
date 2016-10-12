package hr.fer.zemris.java.graphics.shapes;

import hr.fer.zemris.java.graphics.raster.BWRaster;

/**
 * @author Ivan Anić
 * @version 1.0
 */
public class Ellipse extends GeometricShape {
	/** The horizontal radius of this ellipse. */
	private int radiusHorizontal;
	/** The horizontal radius of this ellipse. */
	private int radiusVertical;
	/** The x coordinate of the centre of this ellipse. */
	private int x;
	/** The y coordinate of the centre of this ellipse. */
	private int y;

	/**
	 * Gets the horizontal radius of this ellipse.
	 * 
	 * @return radiusHorizontal the horizontal radius of this ellipse.
	 */
	public int getRadiusHorizontal() {
		return radiusHorizontal;
	}

	/**
	 * Sets the horizontal radius of this ellipse.
	 * 
	 * @param radiusHorizontal
	 *            the horizontal radius of this ellipse.
	 */
	public void setRadiusHorizontal(int radiusHorizontal) {
		this.radiusHorizontal = radiusHorizontal;
	}

	/**
	 * Gets the vertical radius of this ellipse.
	 * 
	 * @return radiusVertical the vertical radius of this ellipse.
	 * 
	 */
	public int getRadiusVertical() {
		return radiusVertical;
	}

	/**
	 * Sets the vertical radius of this ellipse.
	 * 
	 * @param radiusVertical
	 *            the vertical radius of this ellipse.
	 */
	public void setRadiusVertical(int radiusVertical) {
		this.radiusVertical = radiusVertical;
	}

	/**
	 * Gets the x coordinate of the centre of this ellipse.
	 * 
	 * @return x the x coordinate of the centre of this ellipse.
	 */
	public int getX() {
		return x;
	}

	/**
	 * Sets the the x coordinate of the centre of this ellipse.
	 * 
	 * @param x
	 *            the the x coordinate of the centre of this ellipse.
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * Gets the y coordinate of the centre of this ellipse.
	 * 
	 * @return y the y coordinate of the centre of this ellipse.
	 */
	public int getY() {
		return y;
	}

	/**
	 * Sets the y coordinate of the centre of this ellipse.
	 * 
	 * @param y
	 *            the coordinate of the centre of this ellipse.
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * Creates an instance of this class with the desired parameters.
	 * 
	 * @param radiusHorizontal
	 *            the horizontal radius of this ellipse.
	 * @param radiusVertical
	 *            the vertical radius of this ellipse.
	 * @param x
	 *            the x coordinate of the centre of this ellipse.
	 * @param y
	 *            the y coordinate of the centre of this ellipse.
	 * @throws IllegalArgumentException
	 *             if arguments are invalid
	 */
	public Ellipse(int radiusHorizontal, int radiusVertical, int x, int y) {

		if (radiusHorizontal < 1 || radiusVertical < 1) {
			throw new IllegalArgumentException("Invalid radius parameters. Must be at least 1.");
		}

		this.radiusHorizontal = radiusHorizontal;
		this.radiusVertical = radiusVertical;
		this.x = x;
		this.y = y;
	}

	@Override
	public boolean containsPoint(int x, int y) {
		return (x * x * radiusVertical * radiusVertical + y * y * radiusHorizontal * radiusHorizontal <= radiusVertical
				* radiusVertical * radiusHorizontal * radiusHorizontal);
	}

	@Override
	public void draw(BWRaster r) {
		for (int y = -radiusVertical; y <= radiusVertical; y++) {
			for (int x = -radiusHorizontal; x <= radiusHorizontal; x++) {
				if (r.containsPoint(this.x + x, this.y + y) && x * x * radiusVertical * radiusVertical
						+ y * y * radiusHorizontal * radiusHorizontal <= radiusVertical * radiusVertical
								* radiusHorizontal * radiusHorizontal) {

					r.turnOn(this.x + x, this.y + y);
				}
			}
		}
	}
}
