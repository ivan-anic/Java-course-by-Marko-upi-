package hr.fer.zemris.java.hw14.dao;

import hr.fer.zemris.java.hw14.dao.sql.SQLDAO;

/**
 * A singleton class which knows whcih instance to return as the access provider
 * to the da persistency subsystem.
 * 
 * @author Marko Čupić
 */
public class DAOProvider {

	/** The singleton {@code DAO} instance. */
	private static DAO dao = new SQLDAO();

	/**
	 * Gets the {@linkplain DAO} instance.}
	 * 
	 * @return object which encapsulates the access to the da persistency
	 *         subsystem
	 */
	public static DAO getDao() {
		return dao;
	}

}