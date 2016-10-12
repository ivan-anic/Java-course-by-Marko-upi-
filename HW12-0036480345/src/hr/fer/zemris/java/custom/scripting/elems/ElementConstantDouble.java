package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Inherits {@link Element}, has a single read-only {@code Double} property.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class ElementConstantDouble extends Element {
	/** The value contained in this element. */
	private double value;

	/**
	 * Creates an instance of this class with the desired parameter. Parses the
	 * input value to the appropriate one.
	 * 
	 * @param value
	 *            the value of the element being created.
	 */
	public ElementConstantDouble(String value) {
		this.value = Double.parseDouble(value);
	}

	@Override
	public String getName() {
		return String.valueOf(value);
	}
}
