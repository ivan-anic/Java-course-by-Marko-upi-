package hr.fer.zemris.java.custom.scripting.exec.functions;

import java.util.Stack;

import hr.fer.zemris.java.webserver.RequestContext;

/**
 * A class derived from {@linkplain AbstractFunction}. Represents a function
 * which removes a parameter from the temporary parameter list from the
 * {@linkplain RequestContext} instance.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class FunctionTParamDel extends AbstractContextFunction {

	@Override
	public void execute(Stack<String> stack, RequestContext context) {
		context.removeTemporaryParameter(stack.pop());
	}
}