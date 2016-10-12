package hr.fer.zemris.java.gui.calc.actions.unary;

/**
 * An implementation of the {@linkplain UnaryOperator} interface, calculates the
 * nth power of {@code e}.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class ExpOperator implements UnaryOperator {

	@Override
	public double compute(double value) {
		return Math.exp(value);
	}
}
