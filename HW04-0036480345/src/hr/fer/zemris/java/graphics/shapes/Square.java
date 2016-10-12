package hr.fer.zemris.java.graphics.shapes;

/**
 * Represents an implementation of the {@link AbstractRectangle} interface. This
 * shape is a square, meaning its sides are of equal length.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class Square extends AbstractRectangle {
	/** The length of the side of this square. */
	private int size;

	/**
	 * Creates an instance of this class with the desired parameters.
	 * 
	 * @param x
	 *            the x coordinate of this square.
	 * @param y
	 *            the y coordinate of this square.
	 * 
	 * @param size
	 *            the length of the side of this square.
	 */
	public Square(int x, int y, int size) {
		super(x, y, size, size);
	}

	/**
	 * Gets the length of the side of this square.
	 * 
	 * @return the length of the side of this square.
	 */
	public int getSize() {
		return size;
	}

	/**
	 * Sets the length of the side of this square.
	 * 
	 * @param size
	 *            the desired length of the side of this square.
	 */
	public void setSize(int size) {
		this.size = size;
	}

}
