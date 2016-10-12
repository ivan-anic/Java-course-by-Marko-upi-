package hr.fer.zemris.java.graphics.views;

import hr.fer.zemris.java.graphics.raster.BWRaster;

/**
 * Represents an implementation of the {@link AbstractRasterView} class. This
 * implementation displays the raster data on the screen, and returns it as a
 * String.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class StringRasterView extends AbstractRasterView implements RasterView {

	/** A default constructor. Calls the constructor of the superclass. */
	public StringRasterView() {
		super();
	}

	/**
	 * Creates an instance of this class with the desired parameters. Calls the
	 * constructor of the superclass.
	 * 
	 * @param pixelOn
	 *            the character representing the pixel which is visually turned
	 *            off.
	 * @param pixelOff
	 *            the character representing the pixel which is visually turned
	 *            off.
	 */
	public StringRasterView(char pixelOn, char pixelOff) {
		super(pixelOn, pixelOff);
	}

	public Object produce(BWRaster raster) {
		return super.produce(raster);
	}

}
