package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Inherits {@link Element}, has a single read-only {@code String} property.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class ElementVariable extends Element {
	/** The name value contained in this element. */
	private String name;

	/**
	 * Creates an instance of this class with the desired parameter.
	 * 
	 * @param name
	 *            the name of the element being created.
	 */
	public ElementVariable(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

	/**
	 * Returns the name as plain text.
	 * 
	 * @return the value of the element as a <code> string </code>
	 */
	public String getFunctionName() {
		return name;
	}
}
