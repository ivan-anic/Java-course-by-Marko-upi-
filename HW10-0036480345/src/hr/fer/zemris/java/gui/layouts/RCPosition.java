package hr.fer.zemris.java.gui.layouts;

/**
 * A read-only class which represents a position for instances of object which
 * will be store in a {@linkplain CalcLayout}.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class RCPosition {

	/** The row of this position object. */
	private int row;
	/** The column of this position object. */
	private int column;

	/**
	 * Instantiates a new {@linkplain RCPosition} object.
	 *
	 * @param row
	 *            the desired row position of this object
	 * @param column
	 *            the desired column position of this object
	 */
	public RCPosition(int row, int column) {
		super();
		this.row = row;
		this.column = column;
	}

	/**
	 * Gets the row of this position object.
	 *
	 * @return the row
	 */
	public int getRow() {
		return row;
	}

	/**
	 * Gets the column of this position object.
	 *
	 * @return the column
	 */
	public int getColumn() {
		return column;
	}

}
