package hr.fer.zemris.java.hw14.dao.sql;

import java.sql.Connection;

/**
 * Stores the connections to the data persistency subsystem in a
 * {@linkplain ThreadLocal} object instance. This object acts as a map whose
 * keys are the id-s of the thread which performs a map operation.
 * 
 * @author marcupic
 */
public class SQLConnectionProvider {

	/** The connections map. */
	private static ThreadLocal<Connection> connections = new ThreadLocal<>();

	/**
	 * Sets the connection to the current thread. If argument is null, removes
	 * the entry from the thread map.
	 * 
	 * @param con
	 *            connection to the database
	 */
	public static void setConnection(Connection con) {
		if (con == null) {
			connections.remove();
		} else {
			connections.set(con);
		}
	}

	/**
	 * Gets the connection which the current thread may use.
	 * 
	 * @return vezu connection to the database
	 */
	public static Connection getConnection() {
		return connections.get();
	}

}