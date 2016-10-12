package hr.fer.zemris.java.gui.prim;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 * An implementation of the {@linkplain ListModel} interface.
 * 
 * @author Ivan Anić
 * @version 1.0
 * @param <Integer>
 */
@SuppressWarnings({ "rawtypes", "hiding" })
public class PrimListModel<Integer> implements ListModel {
	/** An array which holds references to the elements in this model. */
	private List<Integer> elementi = new ArrayList<>();
	/** An array which holds references to the observers in this model. */
	private List<ListDataListener> promatraci = new ArrayList<>();

	/**
	 * Instantiates a new prim list model.
	 *
	 * @param argument
	 *            the argument to be initially stored in the model
	 */
	public PrimListModel(Integer argument) {
		elementi.add(argument);
	}

	/** Remembers the last calculated prime. Initially set to 1. */
	private int currPrime = 2;

	@Override
	public void addListDataListener(ListDataListener l) {
		promatraci.add(l);
	}

	@Override
	public void removeListDataListener(ListDataListener l) {
		promatraci.remove(l);
	}

	@Override
	public int getSize() {
		return elementi.size();
	}

	@Override
	public Integer getElementAt(int index) {
		return elementi.get(index);
	}

	/**
	 * Adds a new element to the observers array.
	 * 
	 * @param element
	 *            the element to be added.
	 */
	public void add(Integer element) {
		int pos = elementi.size();
		elementi.add(element);

		ListDataEvent event = new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, pos, pos);
		for (ListDataListener l : promatraci) {
			l.intervalAdded(event);
		}
	}

	/**
	 * Removes an element from the observers array.
	 * 
	 * @param pos
	 *            the position of the element to be removed
	 */
	public void remove(int pos) {
		elementi.remove(pos);
		ListDataEvent event = new ListDataEvent(this, ListDataEvent.INTERVAL_REMOVED, pos, pos);
		for (ListDataListener l : promatraci) {
			l.intervalRemoved(event);
		}
	}

	/**
	 * Returns the next element in the iteration.
	 *
	 * @return the next element in the iteration
	 */
	public int next() {
		if (currPrime < 3) {
			return currPrime++;
		} else if (currPrime == 3) {
			int ret = currPrime;
			currPrime += 2;
			return ret;
		}
		while (true) {
			if (isPrime(currPrime)) {
				int ret = currPrime;
				currPrime += 2;
				return ret;
			}
			currPrime += 2;
		}
	}

	/**
	 * Checks whether the given number is prime.
	 *
	 * @param number
	 *            the given number
	 * @return true, if the argument number is prime
	 */
	private boolean isPrime(int number) {
		int limit = (int) Math.sqrt(number);
		for (int i = 2; i <= limit; ++i) {
			if (number % i == 0) {
				return false;
			}
		}
		return true;
	}
}