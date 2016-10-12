package hr.fer.zemris.java.graphics.shapes;

import java.awt.image.Raster;

import hr.fer.zemris.java.graphics.raster.BWRaster;

/**
 * An abstract class representing an abstraction for all geometric shapes.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public abstract class GeometricShape {

	/**
	 * A default implementation of the method which draws the desired shape onto
	 * the {@link Raster}.
	 * 
	 * @param r
	 *            the raster onto which the shape is being drawn.
	 */
	public void draw(BWRaster r) {
		int xmax = r.getWidth();
		int ymax = r.getHeight();
		for (int y = 0; y < ymax; y++) {
			for (int x = 0; x < xmax; x++) {
				if (this.containsPoint(x, y)) {
					r.turnOn(x, y);
				}
			}
		}
	}

	/**
	 * Checks whether the raster contains a certain pixel.
	 * 
	 * @param x
	 *            the x coordinate of the pixel
	 * @param y
	 *            the y coordinate of the pixel
	 * @return true if this raster contains the given pixel.
	 */
	public abstract boolean containsPoint(int x, int y);

}
