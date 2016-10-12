package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Inherits {@link Element}, has a single read-only {@code String} property.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class ElementString extends Element {
	/** The value contained in this element. */
	private String value;

	/**
	 * Creates an instance of this class with the desired parameter.
	 * 
	 * @param value
	 *            the value of the element being created.
	 */
	public ElementString(String value) {
		this.value = value;
	}

	@Override
	public String getName() {
		return value;
	}
}
