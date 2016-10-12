package hr.fer.zemris.java.graphics.views;

import hr.fer.zemris.java.graphics.raster.BWRaster;
import hr.fer.zemris.java.graphics.shapes.GeometricShape;

/**
 * An interface representing an abstraction for all raster views.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public interface RasterView {
	/**
	 * Parses the raster data, which results in a visual representation of the
	 * {@link GeometricShape}s being drawn.
	 * 
	 * @param raster
	 *            the raster containing the image of the drawn shapes.
	 * @return returns the Object containing the data which this view will print
	 *         out to the screen.
	 */
	Object produce(BWRaster raster);
}
