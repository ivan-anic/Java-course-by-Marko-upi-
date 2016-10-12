package hr.fer.zemris.java.custom.scripting.exec.functions;

import java.util.Stack;

/**
 * A class derived from {@linkplain AbstractFunction}. Represents a function
 * which duplicates the item on the top of the stack.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class FunctionDuplicate extends AbstractFunction {

	@Override
	public void execute(Stack<String> stack) {
		stack.push(stack.peek());
	}
}