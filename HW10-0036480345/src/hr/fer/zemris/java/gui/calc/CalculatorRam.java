package hr.fer.zemris.java.gui.calc;

import java.util.Stack;

import hr.fer.zemris.java.gui.calc.actions.Operator;
import hr.fer.zemris.java.gui.calc.actions.binary.BinaryOperator;
import hr.fer.zemris.java.gui.calc.actions.unary.UnaryOperator;

/**
 * An implementation of a calculator Random Access Memory. Its purpose is to
 * calculate the results and to return them to the calculator.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class CalculatorRam {

	/**
	 * Keeps the information about whether the calculator is set to inverse mode
	 * or not.
	 */
	private boolean inverse;

	/** Indicates whether a new number is about to be entered. */
	private boolean newNumber;

	/** Indicates whether the calculator state is cleared. */
	private boolean isEmpty;

	/** A reference to the previous display content. */
	private String prevDisplayContent;

	/** A reference to the current display content. */
	private String currDisplayContent;

	/** A stack which offers push and pop operations in the calculator. */
	private Stack<String> stack;

	/** A reference to the previous {@linkplain Operator } */
	private Operator prevOp;

	/**
	 * A reference to the result which will be returned and displayed to the
	 * screen.
	 */
	private double result;

	/**
	 * Instantiates a new RAM of the calculator.
	 */
	public CalculatorRam() {
		super();
		currDisplayContent = "0";
		prevDisplayContent = "0";
		result = 0;

		isEmpty = true;
		stack = new Stack<>();
	}

	/**
	 * Gets the current display content as a double value.
	 * 
	 * @return the current display content
	 */
	public double getCurrDisplayContent() {
		return ((currDisplayContent.charAt(currDisplayContent.length() - 1) == '.')
				? Double.valueOf(currDisplayContent.substring(0, currDisplayContent.length() - 1))
				: Double.valueOf(currDisplayContent));
	}

	/**
	 * Gets the current display content as a string value.
	 * 
	 * @return the current display content
	 */
	public String getCurrDisplayContentValue() {
		return currDisplayContent;
	}

	/**
	 * Sets the current display content as a double value.
	 * 
	 * @param newDisplayContent
	 *            the new display content
	 */
	protected void setCurrDisplayContent(double newDisplayContent) {
		setCurrDisplayContent(String.valueOf(isEmpty ? newDisplayContent
				: getCurrDisplayContent() * 10 + newDisplayContent));
	}

	/**
	 * Gets the current display content as a string value.
	 * 
	 * @param newDisplayContent
	 *            new display content
	 */
	protected void setCurrDisplayContent(String newDisplayContent) {
		try {
			if (currDisplayContent.equals("0") || currDisplayContent.equals("0.0") || newNumber) {
				currDisplayContent = newDisplayContent;
				newNumber = false;
			} else {
				currDisplayContent += newDisplayContent;
			}
			// setCurrDisplayContent(Double.parseDouble(newDisplayContent));
		} catch (NumberFormatException ex) {
			// dialog neki
		}
	}

	/**
	 * Indicates that a decimal number will be entered.
	 */
	protected void convertToDecimal() {
		currDisplayContent += '.';
	}

	/**
	 * Invers the operators used in the {@linkplain Calculator}
	 */
	protected void invert() {
		inverse = !inverse;
	}

	/**
	 * Checks whether the calculator is in the inverted state.
	 * 
	 * @return true, if the calculator is in the inverted state.
	 */
	public boolean isInverted() {
		return !inverse;
	}

	/**
	 * Clears the display, setting its content to zero.
	 */
	public void clearDisplay() {
		currDisplayContent = "0";
	}

	/**
	 * Resets the calculator memory.
	 */
	public void reset() {
		inverse = false;
		isEmpty = true;
		stack.clear();
	}

	/**
	 * Executes the desired {@linkplain #stack} operation.
	 * 
	 * @param key
	 *            the pressed key, indicating the desired operation
	 */
	public void executeStackOperation(String key) {
		switch (key) {
		case ("push"):
			stack.push(getCurrDisplayContentValue());
			break;
		case ("pop"):
			setCurrDisplayContent(stack.pop());
			break;
		default:
			throw new IllegalArgumentException("");
		}
	}

	/**
	 * Executes the desired {@linkplain Calculator} operation.
	 * 
	 * @param op
	 *            the operator to be executed
	 */
	public void executeOperation(Operator op) {
		result = Double.parseDouble(prevDisplayContent);

		if (op instanceof UnaryOperator) {
			result = ((UnaryOperator) op).compute(getCurrDisplayContent());
		}

		else if (isEmpty) {
			// repeated parse to get rid of the decimal sign
			prevDisplayContent = currDisplayContent;
			prevOp = op;
			clearDisplay();

		} else if (prevOp instanceof BinaryOperator) {
			result = ((BinaryOperator) prevOp).compute(Double.parseDouble(prevDisplayContent), getCurrDisplayContent());
		}

		currDisplayContent = String.valueOf(result);
		newNumber = true;
		isEmpty = false;

		if (op == null) {
			isEmpty = true;
			prevOp = op;
		}
	}

}
