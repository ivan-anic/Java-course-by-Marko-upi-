package hr.fer.zemris.java.custom.scripting.nodes;

import java.util.ArrayList;
import java.util.List;

/**
 * A base node class, with a purpose to be inherited, thus providing various
 * Implementations of various elements.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class Node {
	/** An array of the direct children this node has. */
	List<Node> children;

	/**
	 * Adds a new child node to the internally kept collection of children.
	 * 
	 * @param child
	 *            the new child node to be added
	 */
	public void addChildNode(Node child) {
		if (children == null) {
			children = new ArrayList<>();
		}
		children.add(child);
	}

	/**
	 * Returns a number of direct children this node has.
	 * 
	 * @return the number of direct children of this node
	 */
	public int numberOfChildren() {
		return children.size();
	}

	/**
	 * Returns the selected child.
	 * 
	 * @param index
	 * @return the child at the desired index.
	 * @throws IndexOutOfBoundsException
	 *             if the index is invalid.
	 */
	public Node getChild(int index) {
		return (Node) children.get(index);
	}

	/**
	 * Accepts this node; printing its contents to the standard output.
	 *
	 * @param visitor
	 *            the visitor
	 */
	public void accept(INodeVisitor visitor) {
		throw new RuntimeException("Operation not allowed on this object!");
	}
}
