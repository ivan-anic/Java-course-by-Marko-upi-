package hr.fer.zemris.java.custom.collections;

/**
 * An array-backed resizable collection of objects which extends the class
 * {@link Collection}.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class ArrayIndexedCollection extends Collection {

	/** the current size of the collection */
	private int size;
	/** the capacity of the collection */
	private int capacity;
	/** an array of objects in which the elements will be stored */
	private Object[] elements;

	/**
	 * A default constructor, creates an instance of the collection with
	 * capacity set to 16.
	 */
	public ArrayIndexedCollection() {
		this(16);
	}

	/**
	 * Creates an instance of the collection with capacity set to the given
	 * argument.
	 * 
	 * @param initialCapacity
	 *            - the initial capacity of the collection
	 * @throws IllegalArgumentException
	 *             - if initialCapacity is less than 1
	 */
	public ArrayIndexedCollection(int initialCapacity) {
		if (initialCapacity < 1) {
			throw new IllegalArgumentException();
		}
		this.capacity = initialCapacity;
		this.elements = new Object[this.capacity];
	}

	/**
	 * Creates an instance of the collection with capacity set to 16.
	 * 
	 * @param collection
	 *            - a collection from which elements are to be copied into this
	 *            new collection.
	 */
	public ArrayIndexedCollection(Collection collection) {
		this(collection, 16);
	}

	/**
	 * Creates an instance of the collection with capacity set to the given
	 * argument.
	 * 
	 * @param collection
	 *            - a collection from which elements are to be copied into this
	 *            new collection.
	 * @param initialCapacity
	 *            - the initial capacity of the collection
	 */
	public ArrayIndexedCollection(Collection collection, int initialCapacity) {
		this(initialCapacity);
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
		Object[] pom = new Object[size];
		for (int i = 0; i < size; i++) {
			pom[i] = elements[i];
		}
		return pom;
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
		for (int i = 0; i < size; i++) {
			processor.process(elements[i]);
		}
	}

	/**
	 * Removes all elements from the collection. The allocated array if left at
	 * current capacity, and null references are written in to the backing
	 * array.
	 */
	@Override
	void clear() {
		this.size = 0;
		this.elements = null;
	}

	/**
	 * Returns the object that is stored in the backing array at position index.
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
		if (index < 0 || index > size - 1) {
			throw new IndexOutOfBoundsException();
		} else {
			return elements[index];
		}
	}

	/**
	 * Inserts the given value at the given position in the array. The legal
	 * positions are 0 to size. If position is invalid, an
	 * {@code IllegalArgumentException} is thrown.
	 * 
	 * @param value
	 *            - the value to be inserted into the collection
	 * @param position
	 *            - the position where the given value is supposed to be inserted
	 * @throws IndexOutOfBoundsException
	 *             - if the position is invalid
	 */
	public void insert(Object value, int position) {
		if (position < 0 || position > size) {
			throw new IllegalArgumentException();
		}
		for (int i = size; i < position; i--) {
			elements[i] = elements[i - 1];
		}
		if (size == capacity) {
			this.capacity *= 2;
			Object[] pom = new Object[capacity];
			for (int i = 0; i < size; i++) {
				pom[i] = elements[i];
			}
			elements = pom;
		}
		elements[position] = value;
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
		for (int i = 0; i < this.size; i++) {
			if (elements[i].equals(value)) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Removes element at specified index from the collection. Legal indexes are 0
	 * to size-1.
	 * 
	 * @param index
	 *            - the index from where an element should be removed
	 * @throws IndexOutOfBoundsException
	 *             - if the index is invalid
	 */
	public void remove(int index) {
		if (index < 0 || index > size - 1) {
			throw new IndexOutOfBoundsException();
		}
		if (index == size - 1) {
			elements[size - 1] = null;
		} else {
			for (int i = index; i < size; i++) {
				elements[i] = elements[i + 1];
			}
		}
		size--;
	}

}