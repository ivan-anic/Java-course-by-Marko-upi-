package hr.fer.zemris.java.webserver;

/**
 * An interface representing an abstraction for a web worker.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public interface IWebWorker {

	/**
	 * Process the specific request by either the default worker parameters or
	 * the given parameters received from the {@linkplain RequestContext}
	 *
	 * @param context
	 *            the context
	 */
	public void processRequest(RequestContext context);

}
