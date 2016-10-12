package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * An interface implemented by a concrete visitor class. Its purpose is to visit
 * the document tree
 */
public interface INodeVisitor {

	/**
	 * Visits the given instance of {@linkplain TextNode}
	 * 
	 * @param node
	 *            the given node to visit
	 */
	public void visitTextNode(TextNode node);

	/**
	 * Visits the given instance of {@linkplain ForLoopNode}
	 * 
	 * @param node
	 *            the given node to visit
	 */
	public void visitForLoopNode(ForLoopNode node);

	/**
	 * Visits the given instance of {@linkplain EchoNode}
	 * 
	 * @param node
	 *            the given node to visit
	 */
	public void visitEchoNode(EchoNode node);

	/**
	 * Visits the given instance of {@linkplain DocumentNode}
	 * 
	 * @param node
	 *            the given node to visit
	 */
	public void visitDocumentNode(DocumentNode node);
}
