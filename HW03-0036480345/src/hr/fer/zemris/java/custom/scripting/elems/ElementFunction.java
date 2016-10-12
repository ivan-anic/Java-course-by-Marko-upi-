package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Inherits {@link Element}, has a single read-only {@code String} property.
 * @author Ivan Anić
 * @version 1.0
 */
public class ElementFunction extends Element{
	/** The name value contained in this element. */
	private String name; 
	
	/**
	 * Creates an instance of this class with the desired parameter. 
	 * 
	 * @param name
	 *            the name of the element being created.
	 */
	public ElementFunction(String name) {
		this.name = name;
	}
	
	@Override
	public String asText(){
		return "@"+name;
	}

}
