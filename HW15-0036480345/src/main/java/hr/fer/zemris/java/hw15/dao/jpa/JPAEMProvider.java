package hr.fer.zemris.java.hw15.dao.jpa;

import javax.persistence.EntityManager;

import hr.fer.zemris.java.hw15.dao.DAOException;

/**
 * An {@linkplain EntityManager} provider implementation. Stores the data in in
 * stances of {@linkplain LocalData} inside a {@linkplain ThreadLocal} map
 * 
 * @author marcupic
 */
public class JPAEMProvider {

	/** The local data map. */
	private static ThreadLocal<LocalData> locals = new ThreadLocal<>();

	/**
	 * Gets the entity manager.
	 *
	 * @return the entity manager
	 */
	public static EntityManager getEntityManager() {
		LocalData ldata = locals.get();
		if (ldata == null) {
			ldata = new LocalData();
			ldata.em = JPAEMFProvider.getEmf().createEntityManager();
			ldata.em.getTransaction().begin();
			locals.set(ldata);
		}
		return ldata.em;
	}

	/**
	 * Closes the entitymanager and removes it from the locals data.
	 *
	 * @throws DAOException
	 *             the DAO exception
	 */
	public static void close() throws DAOException {
		LocalData ldata = locals.get();
		if (ldata == null) {
			return;
		}
		DAOException dex = null;
		try {
			ldata.em.getTransaction().commit();
		} catch (Exception ex) {
			dex = new DAOException("Unable to commit transaction.", ex);
		}
		try {
			ldata.em.close();
		} catch (Exception ex) {
			if (dex != null) {
				dex = new DAOException("Unable to close entity manager.", ex);
			}
		}
		locals.remove();
		if (dex != null)
			throw dex;
	}

	/**
	 * The inner class which holds an instance of {@linkplain EntityManager}
	 * 
	 * @author Marko Čupić
	 * @version 1.0
	 */
	private static class LocalData {

		/** The entity manager. */
		EntityManager em;
	}

}