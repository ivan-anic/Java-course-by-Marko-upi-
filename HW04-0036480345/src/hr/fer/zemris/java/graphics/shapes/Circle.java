package hr.fer.zemris.java.graphics.shapes;

import hr.fer.zemris.java.graphics.raster.BWRaster;

/**
 * @author Ivan Anić
 * @version 1.0
 */
public class Circle extends GeometricShape {

	/** The radius of this ellipse. */
	private int radius;
	/** The x coordinate of the centre of this circle. */
	private int x;
	/** The y coordinate of the centre of this circle. */
	private int y;

	/**
	 * Gets the vertical radius of this circle.
	 * 
	 * @return radius the vertical radius of this circle.
	 */
	public int getRadius() {
		return radius;
	}

	/**
	 * Sets the radius of this circle.
	 * 
	 * @param radius
	 *            the radius of this circle.
	 */
	public void setRadius(int radius) {
		this.radius = radius;
	}

	/**
	 * Gets the x coordinate of the centre of this circle.
	 * 
	 * @return x the x coordinate of the centre of this circle.
	 */
	public int getX() {
		return x;
	}

	/**
	 * Sets the the x coordinate of the centre of this circle.
	 * 
	 * @param x
	 *            the the x coordinate of the centre of this circle.
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * Gets the y coordinate of the centre of this circle.
	 * 
	 * @return y the y coordinate of the centre of this circle.
	 */
	public int getY() {
		return y;
	}

	/**
	 * Sets the y coordinate of the centre of this circle.
	 * 
	 * @param y
	 *            the coordinate of the centre of this circle.
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * Creates an instance of this class with the desired parameters.
	 * 
	 * @param radius
	 *            the radius of this circle.
	 * @param x
	 *            the x coordinate of the centre of this circle.
	 * @param y
	 *            the x coordinate of the centre of this circle.
	 * @throws IllegalArgumentException if arguments are invalid
	 */
	public Circle(int radius, int x, int y) {
		if (radius < 1) {
			throw new IllegalArgumentException("Invalid radius parameter. Must be at least 1.");
		}
		this.radius = radius;
		this.x = x;
		this.y = y;
	}

	@Override
	public boolean containsPoint(int x, int y) {
		return (x * x + y * y <= radius * radius);
	}

	@Override
	public void draw(BWRaster r) {
		for (int y = -radius; y <= radius; y++) {
			for (int x = -radius; x <= radius; x++) {
				if (x * x + y * y <= radius * radius && r.containsPoint(this.x + x, this.y + y)) {
					r.turnOn(this.x + x, this.y + y);
				}
			}
		}
	}
}
