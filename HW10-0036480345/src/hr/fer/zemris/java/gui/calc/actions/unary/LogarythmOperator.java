package hr.fer.zemris.java.gui.calc.actions.unary;

/**
 * An implementation of the {@linkplain UnaryOperator} interface, calculates the
 * logarythm function of the given argument.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class LogarythmOperator implements UnaryOperator {

	@Override
	public double compute(double value) {
		return Math.log10(value);
	}
}
