package hr.fer.zemris.java.gui.calc.actions.unary;

import hr.fer.zemris.java.gui.calc.actions.Operator;

/**
 * An interface representing an abstraction for all unary operators.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public interface UnaryOperator extends Operator {

	/**
	 * Computes the desired value using this operator on the given value.
	 * 
	 * @param value
	 *            the given value
	 * @return the desired result
	 */
	double compute(double value);

}
