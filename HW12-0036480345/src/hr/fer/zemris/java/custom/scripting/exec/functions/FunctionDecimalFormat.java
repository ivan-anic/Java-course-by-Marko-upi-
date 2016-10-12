package hr.fer.zemris.java.custom.scripting.exec.functions;

import java.text.DecimalFormat;
import java.util.Stack;

/**
 * A class derived from {@linkplain AbstractFunction}. Represents a function
 * which formats the decimal number on top of the stack with the given format.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class FunctionDecimalFormat extends AbstractFunction {

	@Override
	public void execute(Stack<String> stack) {
		DecimalFormat format = new DecimalFormat(stack.pop());
		String num = stack.pop();
		stack.push(format.format(Double.valueOf(num)));
	}
}
