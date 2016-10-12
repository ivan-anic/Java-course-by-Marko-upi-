package hr.fer.zemris.java.custom.scripting.exec.functions;

import java.util.Stack;

/**
 * A class derived from {@linkplain AbstractFunction}. Represents a function
 * which swaps the first two items on the stack.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class FunctionSwap extends AbstractFunction {

	@Override
	public void execute(Stack<String> stack) {
		String x = stack.pop();
		String y = stack.pop();
		stack.push(x);
		stack.push(y);
	}
}