package hr.fer.zemris.java.gui.calc.actions.unary;

import hr.fer.zemris.java.gui.calc.actions.binary.PowerOperator;

/**
 * An implementation of the {@linkplain UnaryOperator} interface, calculates the
 * nth power of 10.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class PowerOf10Operator extends PowerOperator implements UnaryOperator {

	@Override
	public double compute(double value) {
		return super.compute(10, value);
	}
}
