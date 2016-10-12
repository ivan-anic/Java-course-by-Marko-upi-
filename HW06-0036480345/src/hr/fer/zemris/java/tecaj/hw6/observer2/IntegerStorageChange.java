package hr.fer.zemris.java.tecaj.hw6.observer2;

/**
 * A class which encapsulates (as read-only properties) the following
 * information: 
 * <ul>
 * <li> a reference to {@linkplain IntegerStorage} </li>
 * <li> the value of stored integer before the change has occurred </li>
 * <li> the new value of currently stored integer.</li>
 * </ul>
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class IntegerStorageChange {
	/** An instance of the {@linkplain IntegerStorage} storage. */
	private IntegerStorage storage;
	/** The old value which was stored in the storage. */
	int value;
	/** The new value which will be placed into the storage. */
	int newValue;

	/**
	 * Instantiates this integer storage. Initialises the integer value to the
	 * desired value.
	 * 
	 * @param storage
	 *            an instance of the {@linkplain IntegerStorage} storage.
	 * @param value
	 *            the old value which was stored in the storage
	 * @param newValue
	 *            the new value which will be placed into the storage
	 */
	public IntegerStorageChange(IntegerStorage storage, int value, int newValue) {
		super();
		this.storage = storage;
		this.value = value;
		this.newValue = newValue;
	}

	/**
	 * Gets the {@linkplain IntegerStorage} storage.
	 *
	 * @return the storage
	 */
	public IntegerStorage getStorage() {
		return storage;
	}

	/**
	 * Gets the old value.
	 *
	 * @return the old value
	 */
	public int getValue() {
		return value;
	}

	/**
	 * Gets the new value.
	 *
	 * @return the new value
	 */
	public int getNewValue() {
		return value;
	}

}
