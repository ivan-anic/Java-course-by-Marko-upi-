package hr.fer.zemris.java.gui.calc.actions.unary;

/**
 * An implementation of the {@linkplain UnaryOperator} interface, calculates the
 * arcus cotangent function of the given argument.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class CoTangentOperator extends TangentOperator implements UnaryOperator {

	@Override
	public double compute(double value) {
		return Math.pow(super.compute(value), -1);
	}
}
