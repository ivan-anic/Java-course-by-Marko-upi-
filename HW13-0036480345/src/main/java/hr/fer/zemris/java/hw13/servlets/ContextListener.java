package hr.fer.zemris.java.hw13.servlets;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * The listener interface for receiving context events. The class that is
 * interested in processing a context event implements this interface, and the
 * object created with that class is registered with a component using the
 * component's <code>addContextListener<code> method. When the context event
 * occurs, that object's appropriate method is invoked.
 *
 * @author Ivan Anić
 * @version 1.0
 */
@WebListener
public class ContextListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		sce.getServletContext().setAttribute("time", System.currentTimeMillis());
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
	}

}