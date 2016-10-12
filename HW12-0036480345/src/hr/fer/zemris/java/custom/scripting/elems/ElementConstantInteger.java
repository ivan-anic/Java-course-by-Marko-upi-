package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Inherits {@link Element}, has a single read-only {@code int} property.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class ElementConstantInteger extends Element {
	/** The value contained in this element. */
	private int value;

	/**
	 * Creates an instance of this class with the desired parameter. Parses the
	 * input value to the appropriate one.
	 * 
	 * @param value
	 *            the value of the element being created.
	 */
	public ElementConstantInteger(String value) {
		this.value = Integer.parseInt(value);
	}

	@Override
	public String getName() {
		return String.valueOf(value);
	}
}
