package hr.fer.zemris.java.graphics.raster;

/**
 * An interface representing an abstraction for all raster devices of fixed
 * width and height for which each pixel can be painted with only two colors:
 * black (when pixel is turned off) and white (when pixel is turned on).
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public interface BWRaster {

	/**
	 * Gets the raster width.
	 *
	 * @return the width of the raster
	 */
	public int getWidth();

	/**
	 * Gets the raster height.
	 *
	 * @return the height of the raster
	 */
	public int getHeight();

	/** Turns off all pixels in the raster. */
	public void clear();

	/**
	 * Turns on a pixel in the raster.
	 *
	 * @param x
	 *            the x coordinate of the pixel
	 * @param y
	 *            the y coordinate of the pixel
	 */
	public void turnOn(int x, int y);

	/**
	 * Turns off a pixel in the raster.
	 *
	 * @param x
	 *            the x coordinate of the pixel
	 * @param y
	 *            the y coordinate of the pixel
	 */
	public void turnOff(int x, int y);

	/**
	 * Enables the flip mode. When on, if a pixel is turned on, it will actually
	 * be turned off.
	 */
	public void enableFlipMode();

	/**
	 * Disables the flipmode. See {@link #enableFlipMode()}
	 */
	public void disableFlipmode();
	
	/**
	 * Toggles the flip mode. If it was on, it will now be off, vice-versa. See
	 * {@link #enableFlipMode()}
	 */
	public void toggleFlipMode();

	
	/**
	 * Checks if the pixel is turned on.
	 *
	 * @param x
	 *            the x coordinate of the pixel
	 * @param y
	 *            the y coordinate of the pixel
	 * 
	 * @return true if the pixel is turned on
	 */
	public boolean isTurnedOn(int x, int y);

	/**
	 * Checks if the raster contains a pixel.
	 *
	 * @param x
	 *            the x coordinate of the pixel
	 * @param y
	 *            the y coordinate of the pixel
	 * 
	 * @return true, if successful
	 */
	public boolean containsPoint(int x, int y);

}
