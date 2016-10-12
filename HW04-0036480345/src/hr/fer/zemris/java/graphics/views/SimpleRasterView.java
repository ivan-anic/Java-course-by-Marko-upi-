package hr.fer.zemris.java.graphics.views;

import hr.fer.zemris.java.graphics.raster.BWRaster;

/**
 * Represents an implementation of the {@link AbstractRasterView} class. This
 * implementation displays the raster data on the screen.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class SimpleRasterView extends AbstractRasterView implements RasterView {

	/** A default constructor. Calls the constructor of the superclass. */
	public SimpleRasterView() {
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
	public SimpleRasterView(char pixelOn, char pixelOff) {
		super(pixelOn, pixelOff);
	}

	/*
	 * {@inheritDoc) Returns null instead of the String representation of the
	 * raster data.
	 */
	@Override
	public Object produce(BWRaster raster) {
		System.out.println(super.produce(raster));
		return null;
	}

}
