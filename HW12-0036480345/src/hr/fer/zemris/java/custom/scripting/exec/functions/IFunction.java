package hr.fer.zemris.java.custom.scripting.exec.functions;

import java.util.Stack;

import hr.fer.zemris.java.webserver.RequestContext;

/**
 * An interface representing an abstraction for a function. It uses items
 * located on the stack, and executes the function( {@link #execute(Stack)}).
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public interface IFunction {

	/**
	 * Executes the function.
	 *
	 * @param stack
	 *            the stack
	 */
	public void execute(Stack<String> stack);

	/**
	 * Executes the function.
	 *
	 * @param stack
	 *            the stack
	 * @param context
	 *            the {@linkplain RequestContext} instance
	 */

	public void execute(Stack<String> stack, RequestContext context);

}
