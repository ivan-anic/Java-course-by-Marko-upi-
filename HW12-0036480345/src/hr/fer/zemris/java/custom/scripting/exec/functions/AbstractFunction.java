package hr.fer.zemris.java.custom.scripting.exec.functions;

import java.util.Stack;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * An implementation of the {@linkplain IFunction} interface. Serves as an
 * abstraction for functions used by the {@linkplain SmartScriptEngine}.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public abstract class AbstractFunction implements IFunction {

	public void execute(Stack<String> stack) {
	}

	public void execute(Stack<String> stack, RequestContext context) {
	}
}
