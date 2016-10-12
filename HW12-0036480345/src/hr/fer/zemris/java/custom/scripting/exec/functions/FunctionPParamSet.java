package hr.fer.zemris.java.custom.scripting.exec.functions;

import java.util.Stack;

import hr.fer.zemris.java.webserver.RequestContext;

/**
 * A class derived from {@linkplain AbstractFunction}. Represents a function
 * which sets a parameter into the persistent parameter list from the
 * {@linkplain RequestContext} instance.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class FunctionPParamSet extends AbstractContextFunction {

	@Override
	public void execute(Stack<String> stack, RequestContext context) {
		String name = stack.pop();
		String value = stack.pop();
		context.setPersistentParameter(name, value);
	}
}