package hr.fer.zemris.java.graphics.shapes;

import hr.fer.zemris.java.graphics.raster.BWRaster;

/**
 * An abstract class representing an abstraction for all rectangular geometric
 * shapes.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class AbstractRectangle extends GeometricShape {

	/** The rectangle width. */
	private int width;
	/** The rectangle height. */
	private int height;
	/** The x coordinate of the upper left pixel of this shape. */
	private int x;
	/** The y coordinate of the pixel. */
	private int y;

	/**
	 * Creates an instance of this rectangle with the desired parameters.
	 * 
	 * @param x
	 *            the x coordinate of this rectangular shape.
	 * @param y
	 *            the y coordinate of this rectangular shape.
	 * 
	 * @param width
	 *            the width of this rectangular shape.
	 * @param height
	 *            the height of this rectangular shape.
	 * 
	 * @throws IllegalArgumentException
	 *             if the width or height is zero or lower
	 */
	public AbstractRectangle(int x, int y, int width, int height) {
		if (width < 1 || height < 1) {
			throw new IllegalArgumentException("Invalid width/height parameters. Can't be less than or equal to 0.");
		}
		this.width = width;
		this.height = height;
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Gets the width of this rectangular shape.
	 * 
	 * @return width the width of this rectangular shape.
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Sets the width of this rectangular shape.
	 * 
	 * @param width
	 *            the width of this rectangular shape.
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * Gets the height of this rectangular shape.
	 * 
	 * @return height the height of this rectangular shape.
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Sets the width of this rectangular shape.
	 * 
	 * @param height
	 *            the height of this rectangular shape.
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	/**
	 * Gets the upper left x coordinate of this rectangular shape.
	 * 
	 * @return the x coordinate of this rectangular shape.
	 */
	public int getX() {
		return x;
	}

	/**
	 * Sets the upper left x coordinate of this rectangular shape.
	 * 
	 * @param x
	 *            the x coordinate of this rectangular shape.
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * Gets the upper left y coordinate of this rectangular shape.
	 * 
	 * @return the y coordinate of this rectangular shape.
	 */
	public int getY() {
		return y;
	}

	/**
	 * Sets the upper left y coordinate of this rectangular shape.
	 * 
	 * @param y
	 *            the y coordinate of this rectangular shape.
	 */
	public void setY(int y) {
		this.y = y;
	}

	@Override
	public boolean containsPoint(int x, int y) {
		if (this.x >= x && this.x <= x + width && this.y >= y && this.y <= y + height) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void draw(BWRaster r) {
		int xmax = x + width;
		int ymax = y + height;
		for (int y = this.y; y < ymax; y++) {
			for (int x = this.x; x < xmax; x++) {
				if (r.containsPoint(x, y)) {
					r.turnOn(x, y);
				}
			}
		}
	}
}
