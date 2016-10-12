package hr.fer.zemris.java.custom.collections;

/**
 * A class which represents a general collection of objects. Its purpose is to
 * later be implemented in various ways, thus offering various different
 * collections a user can work with.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class Collection {
	/**
	 * A default constructor.
	 */
	protected Collection() {
	}

	/**
	 * Checks if the collection is empty.
	 * 
	 * @return - returns {@code true} if the collection contains no elements by
	 *         utilising the {@link #size()} method.
	 */
	public boolean isEmpty() {
		return (this.size() == 0) ? true : false;
	}

	/**
	 * Returns the current size of the collection.
	 * 
	 * @return - size of the collection, as an integer value.
	 */
	public int size() {
		return 0;
	}

	/**
	 * Adds the given value to the collection which the method is called on.
	 * 
	 * @param value
	 *            - the value to be added to the collection
	 */
	public void add(Object value) {
	}

	/**
	 * Checks if the collection contains the given element.
	 * 
	 * @param value
	 *            - the element to be searched for in the collection
	 * @return - returns {@code true} if the collection contains the given
	 *         element.
	 */
	public boolean contains(Object value) {
		return false;
	}

	/**
	 * Removes the given element from the collection.
	 * 
	 * @param value
	 *            - the element to be removed from the collection
	 * @return - returns {@code true} if the element is removed from the
	 *         collection
	 */
	public boolean remove(Object value) {
		return false;
	}

	/**
	 * Converts the collection to an array of objects.
	 * 
	 * @return - an array objects collected from the collection
	 */
	public Object[] toArray() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Calls processor.process(.) for each element of this collection.
	 * 
	 * @param processor
	 *            - the processor whose .process method will be used.
	 */
	public void forEach(Processor processor) {
	}

	/**
	 * Adds into itself all elements from the given collection. The given
	 * collection remains unchanged. It defines a local processor class whose
	 * method process adds each item into the current collection by calling the
	 * method returns {@code true} if the collection contains the given el
	 * {@link #add(Object)}, and then calls {@link #forEach(Processor)} on the
	 * other collection with this processor as the argument.
	 * 
	 * @param other
	 *            - the other collection from which the elemets will be added in
	 *            the current one
	 */
	public void addAll(Collection other) {
		class P extends Processor {
			public void process(Object o) {
				add(o);
			}
		}
		;
		other.forEach(new P());

	}

	/**
	 * Removes all elements from this collection.
	 */
	void clear() {
	}

}