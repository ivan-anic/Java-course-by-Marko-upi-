package hr.fer.zemris.java.gui.calc.actions.binary;

/**
 * An implementation of the {@linkplain BinaryOperator} interface, performs the
 * arithmetical multiplication.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class MultiplicationOperator implements BinaryOperator {

	@Override
	public double compute(double argument1, double argument2) {
		return argument1 * argument2;
	}
}
