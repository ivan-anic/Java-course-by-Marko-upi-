package hr.fer.zemris.java.hw15.dao.jpa;

import javax.persistence.EntityManagerFactory;

/**
 * Provides the {@linkplain EntityManagerFactory} to the user when requested.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class JPAEMFProvider {

	/** The factory instance. */
	public static EntityManagerFactory emf;

	/**
	 * Gets the {@linkplain EntityManagerFactory}.
	 *
	 * @return the factory
	 */
	public static EntityManagerFactory getEmf() {
		return emf;
	}

	/**
	 * Sets the {@linkplain EntityManagerFactory}.
	 *
	 * @param emf
	 *            the new factory
	 */
	public static void setEmf(EntityManagerFactory emf) {
		JPAEMFProvider.emf = emf;
	}
}