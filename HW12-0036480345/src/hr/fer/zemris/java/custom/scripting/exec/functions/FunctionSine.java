package hr.fer.zemris.java.custom.scripting.exec.functions;

import java.util.Stack;

/**
 * A class derived from {@linkplain AbstractFunction}. Represents a function
 * which calculates the sine of the item on top of the stack.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class FunctionSine extends AbstractFunction {

	@Override
	public void execute(Stack<String> stack) {
		stack.push(String.valueOf(Math.sin(Double.parseDouble(stack.pop()))));
	}
}