package hr.fer.zemris.java.gui.calc.actions.unary;

/**
 * An implementation of the {@linkplain UnaryOperator} interface, calculates the
 * tangent function of the given argument.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class TangentOperator implements UnaryOperator {

	@Override
	public double compute(double value) {
		return Math.tan(value);
	}
}
