package hr.fer.zemris.java.tecaj.hw6.observer1;

import java.util.ArrayList;
import java.util.List;

/**
 * The subject class, used fot testing the {@linkplain IntegerStorageObserver}s.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class IntegerStorage {
	/** The integer value held in this storage. */
	private int value;
	/**
	 * The list of registered {@linkplain IntegerStorageObserver}s which listen
	 * to this storage changes.
	 */
	private List<IntegerStorageObserver> observers;
	/**
	 * The list of registered {@linkplain IntegerStorageObserver}s which should
	 * be removed after the iteration is complete.
	 */
	private List<IntegerStorageObserver> toberemoved;

	/**
	 * Instantiates this integer storage. Initialises the integer value to the
	 * desired value.
	 * 
	 * @param initialValue
	 *            the initial value
	 */
	public IntegerStorage(int initialValue) {
		this.value = initialValue;
		this.observers = new ArrayList<>(value);
		this.toberemoved = new ArrayList<>(value);
	}

	/**
	 * Registers the desired {@linkplain IntegerStorageObserver} to this
	 * storage.
	 *
	 * @param observer
	 *            the observer
	 */
	public void addObserver(IntegerStorageObserver observer) {
		if (observer == null) {
			throw new IllegalArgumentException("Observer can't be null!");
		} else {
			observers.add(observer);
		}
	}

	/**
	 * Adds the desired {@linkplain IntegerStorageObserver} to the list of
	 * observers to be removed after the iteration.
	 *
	 * @param observer
	 *            the desired observer to be registered
	 */
	public void addForRemoval(IntegerStorageObserver observer) {
		if (observer == null) {
			throw new IllegalArgumentException("Observer can't be null!");
		} else {
			toberemoved.add(observer);
		}
	}

	/**
	 * Removes the desired {@linkplain IntegerStorageObserver} from this
	 * storage.
	 *
	 * @param observer
	 *            the desired observer to be removed
	 */
	public void removeObserver(IntegerStorageObserver observer) {
		observers.remove(observer);
	}

	/**
	 * Removes all registered observers from this storage.
	 */
	public void clearObservers() {
		observers.removeAll(observers);
	}

	/**
	 * Gets the value kept in this storage.
	 *
	 * @return the value kept in this storage.
	 */
	public int getValue() {
		return value;
	}

	/**
	 * Places the desired value into this storage
	 *
	 * @param value
	 *            the new value to be placed into this storage
	 */
	public void setValue(int value) {
		// Only if new value is different than the current value:
		if (this.value != value) {
			// Update current value
			this.value = value;
			// Notify all registered observers
			if (observers != null) {
				for (IntegerStorageObserver observer : observers) {
					observer.valueChanged(this);
				}
				toberemoved.forEach(this::removeObserver);
			}
		}
	}
}
