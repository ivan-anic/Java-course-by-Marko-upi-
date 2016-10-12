package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.Element;

/**
 * A node representing a piece of textual data.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class EchoNode extends Node {

	/** An array of elements this document contains. */
	private Element[] elements;
	
	/**
	 * Creates an instance of this class with the desired parameter.
	 * 
	 * @param elements
	 *            the element array contained in this {@link EchoNode}.
	 */
	public EchoNode(Element[] elements) {
		this.elements = elements;
	}

	/**
	 * Returns an array of the elements this document contains.
	 * 
	 * @return the elements contained in this document node.
	 */
	public Element[] getElements() {
		return elements;
	}
}
