package hr.fer.zemris.java.tecaj.hw6.observer2;

/**
 * This interface defines a pattern of observing a subject and notifying all
 * registered observers.
 *
 * @author Ivan Anić
 * @version 1.0
 */
public interface IntegerStorageObserver {

	/**
	 * This method is called when information about an
	 * {@linkplain IntegerStorage} which was previously requested using an
	 * asynchronous interface becomes available.
	 *
	 * @param storage
	 *            the storage
	 */
	public void valueChanged(IntegerStorageChange storage);

}
