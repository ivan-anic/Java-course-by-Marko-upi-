package hr.fer.zemris.java.gui.calc.actions.binary;

/**
 * An implementation of the {@linkplain BinaryOperator} interface, calculates
 * the nth power of the given number.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class PowerOperator implements BinaryOperator {

	@Override
	public double compute(double argument1, double argument2) {
		return Math.pow(argument1, argument2);
	}
}
