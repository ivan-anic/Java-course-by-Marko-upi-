package hr.fer.zemris.java.graphics.raster;

/**
 * Represents an implementation of the {@link BWRaster} interface.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class BWRasterMem implements BWRaster {

	/** The raster width */
	private int width;
	/** The raster height */
	private int height;

	/** An array representing the pixels of the raster. */
	private int[][] raster;

	/**
	 * A flag which indicates if the flipping mode is on. See
	 * {@link #enableFlipMode()}
	 */
	private boolean flip;

	/**
	 * Creates an instance of this class with the desired parameters.
	 * 
	 * @param width
	 *            the width of the raster, in pixels
	 * @param height
	 *            the height of the raster, in pixels
	 * @throws IllegalArgumentException if parameters are invalid
	 */
	public BWRasterMem(int width, int height) {
		if (width < 1 || height < 1) {
			throw new IllegalArgumentException("Invalid width/height parameters. Must be at least 1.");
		}

		this.width = width;
		this.height = height;
		this.raster = new int[width][height];
		flip = false;
	}

	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public int getHeight() {
		return height;
	}

	@Override
	public void clear() {
		raster = null;
	}

	@Override
	public void turnOn(int x, int y) {
		if (flip) {
			raster[x][y] = (raster[x][y] == 0) ? 1 : 0;
		} else {
			raster[x][y] = 1;
		}
	}

	@Override
	public void turnOff(int x, int y) {
		raster[x][y] = 1;
	}

	@Override
	public void enableFlipMode() {
		flip = true;
	}

	@Override
	public void disableFlipmode() {
		flip = false;
	}

	@Override
	public void toggleFlipMode() {
		if (flip) {
			flip = false;
		} else
			flip = true;
	}

	@Override
	public boolean isTurnedOn(int x, int y) {
		return (raster[x][y] == 1) ? true : false;
	}

	@Override
	public boolean containsPoint(int x, int y) {
		return (x >= 0 && x < width && y >= 0 && y < height);
	}

}
