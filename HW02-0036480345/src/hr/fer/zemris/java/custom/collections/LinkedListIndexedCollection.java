package hr.fer.zemris.java.custom.collections;

/**
 * An linked list-backed collection of objects which extends the class
 * {@link Collection}.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class LinkedListIndexedCollection extends Collection {
	/** A single node of the list. */
	private static class ListNode {
		/** a reference to the previous node in the list */
		ListNode previous;
		/** a reference to the previous node in the list */
		ListNode next;
		/** the value stored in the node */
		Object value;
	}

	/** the current size of the collection */
	private int size;
	/** a reference on the first node of the collection */
	private ListNode first;
	/** a reference on the last node of the collection */
	private ListNode last;

	/**
	 * A default constructor, initialises an empty collection with null
	 * references on first and last pointers.
	 */
	public LinkedListIndexedCollection() {
		this.first = null;
		this.last = null;
	}

	/**
	 * Creates an instance of the collection and copies elements from the
	 * argument into this new collection.
	 * 
	 * @param collection
	 *            - a collection from which elements are to be copied into this
	 *            new collection.
	 */
	public LinkedListIndexedCollection(Collection collection) {
		addAll(collection);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see hr.fer.zemris.java.custom.collections.Collection#size()
	 */
	@Override
	public int size() {
		return size;
	}

	/**
	 * Adds the given value to the collection which the method is called on.
	 * Reference is added into first empty place in the local array. If it's
	 * full, it is reallocated by doubling its size. If a {@code null} elements
	 * is to be added, the method throws an {@code IllegalArgumentException}.
	 * 
	 * @param value
	 *            - the value to be added to the collection
	 * @throws IllegalArgumentException
	 *             - if a null reference is being added
	 */
	@Override
	public void add(Object value) {
		if (value == null) {
			throw new IllegalArgumentException();
		}
		insert(value, size);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see hr.fer.zemris.java.custom.collections.Collection#contains(java.lang.
	 * Object)
	 */
	@Override
	public boolean contains(Object value) {
		return (indexOf(value) < 0 ? false : true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * hr.fer.zemris.java.custom.collections.Collection#remove(java.lang.Object)
	 */
	@Override
	public boolean remove(Object value) {
		if (this.contains(value)) {
			remove(indexOf(value));
			return true;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see hr.fer.zemris.java.custom.collections.Collection#toArray()
	 */
	@Override
	public Object[] toArray() {
		Object[] array = new Object[size];
		ListNode pom = first;
		for (int i = 0; pom != null; pom = pom.next, i++) {
			array[i] = pom.value;
		}
		return array;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * hr.fer.zemris.java.custom.collections.Collection#forEach(hr.fer.zemris.
	 * java.custom.collections.Processor)
	 */
	@Override
	public void forEach(Processor processor) {
		for (ListNode pom = first; pom != null; pom = pom.next) {
			processor.process(pom.value);
		}
	}

	/**
	 * Returns the object that is stored in the linked list at position index.
	 * Valid indexes are 0 to size-1. If index is invalid, the method throws an
	 * {@code IndexOutOfBoundsException}.
	 * 
	 * @param index
	 *            - the index from which the object is fetched from
	 * @return - the object from the desired index
	 * @throws IndexOutOfBoundsException
	 *             - if the argument is invalid
	 */
	public Object get(int index) {
		if (index < 0 || index >= size - 1) {
			throw new IndexOutOfBoundsException();
		}
		ListNode pom;
		for (pom = first; index > 0; pom = pom.next, index--) {
		}
		return pom.value;
	}

	/**
	 * Removes all elements from the collection. The pointers to the first and
	 * last node are dereferenced, so the garbage collector will free the
	 * allocated memory.
	 */
	@Override
	void clear() {
		this.first = null;
		this.last = null;
		this.size = 0;
	}

	/**
	 * Inserts the given value at the given position in the linked list. The
	 * legal positions are 0 to size. If position is invalid, an
	 * {@code IllegalArgumentException} is thrown.
	 * 
	 * @param value
	 *            - the value to be inserted into the collection
	 * @param position
	 *            - the position where the given value is supposed to be
	 *            inserted
	 * @throws IndexOutOfBoundsException
	 *             - if the position is invalid
	 */
	public void insert(Object value, int position) {
		if (position < 0 || position > size) {
			throw new IllegalArgumentException();
		}
		ListNode newNode = new ListNode();
		newNode.value = value;
		if (size == 0) {
			first = newNode;
			last = newNode;
		} else if (position == 0) {
			newNode.next = first;
			first.previous = newNode;
			first = newNode;
		} else {
			ListNode pom;
			for (pom = first; position > 1; pom = pom.next, position--) {
			}
			newNode.previous = pom;
			pom.next = newNode;
		}
		size++;
	}

	/**
	 * Searches the collection and returns the index of the first occurrence of
	 * the given value or -1 if the value is not found. The equality is
	 * determined using the {@code #equals(Object)} method.
	 * 
	 * @param value
	 *            - the object which is searched for
	 * @return - index of the first occurrence of the given value
	 */
	public int indexOf(Object value) {
		ListNode pom = first;
		for (int i = 0; i < size; pom = pom.next, i++) {
			if (pom.value.equals(value)) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Removes element at specified index from the collection. Legal indexes are
	 * 0 to size-1.
	 * 
	 * @param index
	 *            - the index from where an element should be removed
	 * @throws IndexOutOfBoundsException
	 *             - if the index is invalid
	 */
	void remove(int index) {
		if (index < 0 || index > size - 1) {
			throw new IndexOutOfBoundsException();
		}
		ListNode pom;
		if (index == 0) {
			first.next.previous = null;
			first = first.next;
		} else if (index == size - 1) {
			last.previous.next = null;
			last = last.previous;
		} else {
			for (pom = first; index > 0; pom = pom.next, index--) {
			}
			pom.previous.next = pom.next;
			pom.next.previous = pom.previous;
		}
		size--;
	}

}
