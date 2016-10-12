package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Inherits {@link Element}, has a single read-only {@code String} property.
 * @author Ivan Anić
 * @version 1.0
 */
public class ElementOperator extends Element{
	/** The symbol value contained in this element. */
	private String symbol; 
	
	/**
	 * Creates an instance of this class with the desired parameter.
	 * 
	 * @param symbol
	 *            the value of the element being created.
	 */
	public ElementOperator(String symbol) {
		this.symbol = symbol;
	}

	@Override
	public String asText(){
		return symbol;
	}
}
