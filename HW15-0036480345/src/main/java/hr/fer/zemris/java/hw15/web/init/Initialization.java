package hr.fer.zemris.java.hw15.web.init;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import hr.fer.zemris.java.hw15.dao.jpa.JPAEMFProvider;

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
public class Initialization implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("baza.podataka.za.blog");
		sce.getServletContext().setAttribute("my.application.emf", emf);
		JPAEMFProvider.setEmf(emf);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		JPAEMFProvider.setEmf(null);
		EntityManagerFactory emf = (EntityManagerFactory) sce.getServletContext().getAttribute("my.application.emf");
		if (emf != null) {
			emf.close();
		}
	}
}