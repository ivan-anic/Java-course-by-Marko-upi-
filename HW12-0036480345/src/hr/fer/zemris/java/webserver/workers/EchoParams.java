package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * An implementation of the {@linkplain IWebWorker} interface. Places the given
 * parameters into a table and forwards it to the output stream.
 *
 * @author Ivan Anić
 * @version 1.0
 */
public class EchoParams implements IWebWorker {

	@Override
	public void processRequest(RequestContext context) {
		context.setMimeType("text/html");

		context.write("<html><body>");
		context.write("<h1>Given parameters: </h1>");

		context.write("<table border='1' style=\"width:100%\">");
		context.getParameterNames().forEach(c -> {
			context.write(" <tr>");
			context.write("  <td>" + c + "</td>");
			context.write("  <td>" + context.getParameter(c) + "</td>");
			context.write(" </tr>");
		});
		context.write("</table>");

		context.write("</body></html>");
	}
}