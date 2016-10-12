package hr.fer.zemris.java.custom.scripting.exec.functions;

import java.util.Stack;

import hr.fer.zemris.java.webserver.RequestContext;

/**
 * A class derived from {@linkplain AbstractFunction}. Represents a function
 * which gets a parameter from the parameter list from the
 * {@linkplain RequestContext} instance and pushes it onto the stack.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class FunctionParamGet extends AbstractContextFunction {

	@Override
	public void execute(Stack<String> stack, RequestContext context) {
		String defValue = stack.pop();
		String value = context.getParameter(stack.pop());
		stack.push((value == null) ? defValue : value);
	}
}