package hr.fer.zemris.java.gui.calc.actions.unary;

/**
 * An implementation of the {@linkplain UnaryOperator} interface, calculates the
 * natural logarythm function of the given argument.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class NaturalLogarythmOperator implements UnaryOperator {

	@Override
	public double compute(double value) {
		return Math.log(value);
	}
}
