package hr.fer.zemris.java.custom.scripting.exec.functions;

import java.util.Stack;

import hr.fer.zemris.java.webserver.RequestContext;

/**
 * A class derived from {@linkplain AbstractFunction}. Represents a function
 * which sets the mimetype of the {@linkplain RequestContext} instance from the
 * item on top of the stack.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class FunctionSetMimeType extends AbstractContextFunction {

	@Override
	public void execute(Stack<String> stack, RequestContext context) {
		context.setMimeType(stack.pop());
	}
}