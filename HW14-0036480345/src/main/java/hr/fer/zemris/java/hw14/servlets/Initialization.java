package hr.fer.zemris.java.hw14.servlets;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;

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

		Properties properties = new Properties();

		try {
			properties.load(Files
					.newInputStream(Paths.get(sce.getServletContext().getRealPath("/WEB-INF/dbsettings.properties"))));
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e.getCause());
		}

		String connectionURL;
		try {
			connectionURL = "jdbc:derby://" +
					properties.getProperty("host").toString() + ":" +
					properties.getProperty("port").toString() + "/" +
					properties.getProperty("name").toString() + ";user=" +
					properties.getProperty("user").toString() + ";password=" +
					properties.getProperty("password").toString();
		} catch (NullPointerException e) {
			throw new RuntimeException(e.getMessage(), e.getCause());
		}

		ComboPooledDataSource cpds = new ComboPooledDataSource();
		try {
			cpds.setDriverClass("org.apache.derby.jdbc.ClientDriver");
		} catch (PropertyVetoException e1) {
			throw new RuntimeException("Error during pool initialisation.", e1);
		}
		cpds.setJdbcUrl(connectionURL);

		sce.getServletContext().setAttribute("hr.fer.zemris.dbpool", cpds);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		ComboPooledDataSource cpds = (ComboPooledDataSource) sce.getServletContext()
				.getAttribute("hr.fer.zemris.dbpool");
		if (cpds != null) {
			try {
				DataSources.destroy(cpds);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}