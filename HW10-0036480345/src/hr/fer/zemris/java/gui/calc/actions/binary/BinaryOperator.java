package hr.fer.zemris.java.gui.calc.actions.binary;

import hr.fer.zemris.java.gui.calc.actions.Operator;

/**
 * An interface representing an abstraction for all binary operators.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public interface BinaryOperator extends Operator {

	/**
	 * Computes the desired value using this operator on the given value.
	 * 
	 * @param argument1
	 *            the first argument
	 * @param argument2
	 *            the second argument
	 * 
	 * @return the desired result
	 */
	double compute(double argument1, double argument2);

}
