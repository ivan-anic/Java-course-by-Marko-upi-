package hr.fer.zemris.java.graphics.views;

import hr.fer.zemris.java.graphics.raster.BWRaster;

/**
 * An interface representing an abstraction for all raster views. Classes which
 * implement this interface will be responsible for visualisation of created
 * images.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public abstract class AbstractRasterView implements RasterView {

	/** The character representing the pixel which is visually turned on. */
	private char pixelOn;
	/** The character representing the pixel which is visually turned off. */
	private char pixelOff;

	/**
	 * A default constructor, setting the {@link #pixelOn} sign to '*', and the
	 * {@link #pixelOn} sign to '.'.
	 */
	public AbstractRasterView() {
		this('*', '.');
	}

	/**
	 * Creates an instance of this class with the desired parameters.
	 * 
	 * @param pixelOn
	 *            the character representing the pixel which is visually turned
	 *            off.
	 * @param pixelOff
	 *            the character representing the pixel which is visually turned
	 *            off.
	 */
	public AbstractRasterView(char pixelOn, char pixelOff) {
		this.pixelOn = pixelOn;
		this.pixelOff = pixelOff;
	}

	@Override
	public Object produce(BWRaster raster) {
		int height = raster.getHeight();
		int width = raster.getWidth();

		StringBuilder sb = new StringBuilder();

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (raster.isTurnedOn(x, y)) {
					sb.append(pixelOn);
				} else {
					sb.append(pixelOff);
				}
			}
			sb.append('\n');
		}
		return sb.toString();
	}

}
