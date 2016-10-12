package hr.fer.zemris.java.gui.calc.actions.unary;

import hr.fer.zemris.java.gui.calc.actions.binary.PowerOperator;

/**
 * An implementation of the {@linkplain UnaryOperator} interface, calculates the
 * inverse value of the given argument.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class InverseOperator extends PowerOperator implements UnaryOperator {

	@Override
	public double compute(double value) {
		return super.compute(value, -1);
	}
}
