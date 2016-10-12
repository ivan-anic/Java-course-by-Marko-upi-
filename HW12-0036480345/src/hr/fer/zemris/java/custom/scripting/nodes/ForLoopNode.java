package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;

/**
 * A node representing a single for-loop construct.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class ForLoopNode extends Node {

	/**
	 * The variable defined in the for loop which changes as the loop continues.
	 */
	private ElementVariable variable;

	/** The first expression of the loop. */
	private Element startExpression;

	/** The last expression of the loop. */
	private Element endExpression;

	/** Defines how the <code>variable</code> acts while the loop continues. */
	private Element stepExpression; // which can be null)

	/**
	 * Creates an instance of this class with the desired parameter.
	 * 
	 * @param elements
	 *            the element array contained in this {@link EchoNode}.
	 */
	public ForLoopNode(Element[] elements) {
		this.variable = (ElementVariable) elements[0];
		this.startExpression = elements[1];
		this.endExpression = elements[2];
		this.stepExpression = elements[3];
	}

	/**
	 * Returns the variable defined in this for loop.
	 * 
	 * @return the variable defined in this for loop.
	 */
	public ElementVariable getVariable() {
		return variable;
	}

	/**
	 * Returns the first expression of the loop.
	 * 
	 * @return the first expression of the loop.
	 */
	public Element getStartExpression() {
		return startExpression;
	}

	/**
	 * Returns the last expression of the loop.
	 * 
	 * @return the last expression of the loop.
	 */
	public Element getEndExpression() {
		return endExpression;
	}

	/**
	 * Returns the way how the variable acts while the loop continues.
	 * 
	 * @return expression executed on each step of the for loop.
	 */
	public Element getStepExpression() {
		return stepExpression;
	}

	@Override
	public void accept(INodeVisitor visitor) {
		visitor.visitForLoopNode(this);
	}

}
