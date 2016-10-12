package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * A node representing a piece of textual data.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class TextNode extends Node {
	
	/** A textual node contained in the document.*/
	String text;

	/**
	 * Creates an instance of a text node.
	 * 
	 * @param text
	 *            the text which this node contains.
	 */
	public TextNode(String text) {
		this.text = text;
	}

	/**
	 * Returns the text contained in this node.
	 * 
	 * @return the desired text.
	 */
	public String getText() {
		return text;
	}

}
