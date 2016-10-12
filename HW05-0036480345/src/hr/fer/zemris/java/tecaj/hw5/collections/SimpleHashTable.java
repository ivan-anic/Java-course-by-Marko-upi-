package hr.fer.zemris.java.tecaj.hw5.collections;

import java.util.ConcurrentModificationException;
import java.util.Iterator;

/**
 * An implementation of a simple hash table. It is a generic class, parametrised
 * over types K (key) and V (value).
 * 
 * @author Ivan Anić
 * @version 1.0
 * @param <K>
 *            - the key, used for getting the value from a single table entry.
 * @param <V>
 *            - the value assigned to the key
 */
public class SimpleHashTable<K, V> implements Iterable<SimpleHashTable.TableEntry<K, V>> {

	/** An array containing pointers to heads of the table slots. */
	private TableEntry<K, V>[] table;
	/** The initial size of the table. */
	private int size;
	/** Remembers the number of modifications undergone in this collection. */
	private int modificationCount;

	/**
	 * A single slot of the {@link SimpleHashTable}. The data is stored in a
	 * key-value format, where the user can get the desired value, using the
	 * specified key.
	 * 
	 * @author Ivan Anić
	 * @version 1.0
	 * @param <K>
	 *            - the key, used for getting the value from a single table
	 *            entry.
	 * @param <V>
	 *            - the value assigned to the key
	 */
	public static class TableEntry<K, V> {

		/**
		 * A key by which the user can get the associated value from the table.
		 */
		private K key;
		/** The value assigned to the key of the table. */
		private V value;
		/** Points to the next entry of the table. */
		private TableEntry<K, V> next;

		/**
		 * Instantiates a new table entry with the desired parameters.
		 *
		 * @param key
		 *            the key
		 * @param value
		 *            the value
		 * @param next
		 *            the next
		 * @throws IllegalArgumentException
		 *             if the key is null
		 */
		public TableEntry(K key, V value, TableEntry<K, V> next) {
			if (key == null) {
				throw new IllegalArgumentException("Key can't be null!");
			}
			this.key = key;
			this.value = value;
			this.next = next;
		}

		/**
		 * Gets the value.
		 *
		 * @return the value
		 */
		public V getValue() {
			return value;
		}

		/**
		 * Sets the value.
		 *
		 * @param value
		 *            the new value
		 */
		public void setValue(V value) {
			this.value = value;
		}

		/**
		 * Gets the key.
		 *
		 * @return the key
		 */
		public K getKey() {
			return key;
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append(key.toString());
			sb.append("=");
			sb.append(value.toString());
			return sb.toString();
		}

	}

	/** Creates an instance of this class with the default capacity of 16. */
	public SimpleHashTable() {
		this(16);
	}

	/**
	 * Creates an instance of this class with the desired parameters. Rounds the
	 * capacity to the nearest exponent of 2.
	 * 
	 * @param capacity
	 *            - the initial capacity of the hash table.
	 * @throws IllegalArgumentException
	 *             - if the capacity if less than 1.
	 */
	@SuppressWarnings("unchecked")
	public SimpleHashTable(int capacity) {
		size = (int) Math.pow(2, 32 - Integer.numberOfLeadingZeros(capacity - 1));
		table = (TableEntry<K, V>[]) new TableEntry[size];
	}

	/**
	 * Adds a new record to the table.
	 *
	 * @param key
	 *            the key
	 * @param value
	 *            the value
	 * @throws IllegalArgumentException
	 *             if the key is null
	 */
	public void put(K key, V value) {
		if (key == null) {
			throw new IllegalArgumentException("Key can't be null!");
		}

		if (capacityCheck()) {
			resize();
		}

		int slot = calculateSlot(key);
		
		TableEntry<K, V> head = table[slot];
		TableEntry<K, V> entry = new TableEntry<K, V>(key, value, null);
		if (head == null) {
			table[slot] = entry;
			modificationCount++;
			return;
		}

		addToEnd(head, entry, true);
	}

	/**
	 * Gets the value assigned to the given key.
	 *
	 * @param key
	 *            the key
	 * @return the object
	 */
	public V get(K key) {
		int slot = calculateSlot(key);
		TableEntry<K, V> head = table[slot];

		if (head == null) {
			return null;
		}

		while (!head.getKey().equals(key)) {
			head = head.next;
		}
		return head.getValue();
	}

	/**
	 * @return size
	 */
	public int size() {
		int cnt = 0;
		TableEntry<K, V> head;

		for (int i = 0; i < size; i++) {
			head = table[i];

			while (head != null) {
				cnt++;
				head = head.next;
			}
		}
		return cnt;
	}

	/**
	 * Method used for testing. Returns the total capacity of the table.
	 * 
	 * @return the capacity of the table
	 */
	public int capacity() {
		return table.length;
	}

	/**
	 * Checks if the table contains a certain key.
	 *
	 * @param key
	 *            the key
	 * @return true, if the table does contain the given key.
	 */
	public boolean containsKey(Object key) {
		if (key == null) {
			return false;
		}
		int slot = calculateSlot(key);
		for (int i = 0; i < size; i++) {
			TableEntry<K, V> head = table[i];

			while (head != null) {
				if ((head.getKey().equals(key)) && (calculateSlot(head.getKey()) == slot)) {
					return true;
				}
				head = head.next;
			}
		}
		return false;
	}

	/**
	 * Checks if the table contains a certain key. *
	 * 
	 * @param value
	 *            the value
	 * @return true, if the table does contain the given key.
	 */
	public boolean containsValue(Object value) {
		for (int i = 0; i < size; i++) {
			TableEntry<K, V> head = table[i];

			while (head != null) {
				if (head.getValue() == null || head.getValue().equals(value)) {
					return true;
				}
				head = head.next;
			}
		}
		return false;
	}

	/**
	 * Removes a record from the table.
	 *
	 * @param key
	 *            the key of the record which is to be removed.
	 */
	public void remove(Object key) {
		if (key == null) {
			return;
		}

		for (int i = 0; i < size; i++) {
			TableEntry<K, V> head = table[i];
			TableEntry<K, V> prev = null;

			while (head != null) {
				if ((head.getKey().equals(key))) {
					if (prev != null) {
						prev.next = head.next;
					} else {
						table[i] = head.next;
					}
					modificationCount++;
					return;
				}
				prev = head;
				head = head.next;
			}
		}
	}

	/**
	 * Checks if the table is empty.
	 *
	 * @return true, if it is empty
	 */
	public boolean isEmpty() {
		for (int i = 0; i < size; i++) {
			if (table[i] != null) {
				return false;
			}
		}
		return true;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < size; i++) {
			TableEntry<K, V> head = table[i];
			sb.append('[');

			while (head != null) {
				sb.append(head.toString() + ", ");
				head = head.next;
			}
			sb.append(']');
		}
		return sb.toString();
	}

	/**
	 * Removes all content from this table.
	 */
	public void clear() {
		for (int i = 0; i < size; i++) {
			table[i] = null;
		}
	}

	/**
	 * Checks if the table is populated over 75% of its capacity.
	 *
	 * @return true, if the table is populated over 75% of its capacity.
	 */
	private boolean capacityCheck() {
		return size() >= 0.75 * size;
	}

	/**
	 * Resize.
	 */
	private void resize() {

		modificationCount++;
		size *= 2;
		@SuppressWarnings("unchecked")
		TableEntry<K, V>[] temp = (TableEntry<K, V>[]) new TableEntry[size];
		TableEntry<K, V> head;
		int slot;

		for (int i = 0; i < size / 2; i++) {
			head = table[i];

			while (head != null) {
				slot = calculateSlot(head.getKey());
				TableEntry<K, V> entry = new TableEntry<K, V>(head.getKey(), head.getValue(), null);

				if (temp[slot] == null) {
					temp[slot] = entry;
				} else {
					addToEnd(temp[slot], entry, false);
				}
				head = head.next;
			}
		}
		table = temp;
	}

	/**
	 * @param head
	 * @param entry
	 * @param check
	 */
	private void addToEnd(TableEntry<K, V> head, TableEntry<K, V> entry, boolean check) {
		while (head != null) {

			if (check && head.getKey().equals(entry.getKey())) {
				head.setValue(entry.getValue());
				return;
			} else if (head.next == null) {
				head.next = new TableEntry<K, V>(entry.getKey(), entry.getValue(), null);
				modificationCount++;
				return;
			} else {
				head = head.next;
			}
		}
	}

	/**
	 * Calculates the slot in the table where the record will be placed.
	 * 
	 * @param key
	 *            the key of the record.
	 *
	 * @return the desired slot.
	 */
	private int calculateSlot(Object key) {
		return Math.abs(key.hashCode() % size);
	}

	/**
	 * An implementation of the {@link Iterator} for the {@link SimpleHashTable}
	 * .
	 * 
	 * @author Ivan Anić
	 * @version 1.0
	 */
	private class IteratorImpl implements Iterator<SimpleHashTable.TableEntry<K, V>> {

		/** References the data from {@link SimpleHashTable} itself. */
		TableEntry<K, V>[] tableIt = table;
		/**
		 * Remembers the number of modifications undergone in this collection.
		 */
		private int modificationCnt = modificationCount;
		/** Remembers the current slot which is being iterated. */
		private int tablePointer;
		/** Remembers the current entry of the table. */
		TableEntry<K, V> curr = table[tablePointer];

		/**
		 * {@inheritDoc}
		 * 
		 * @throws ConcurrentModificationException
		 *             if the collection has been changed from outside of the
		 *             iterator.
		 */
		@Override
		public boolean hasNext() {
			if (modificationCnt != modificationCount) {
				throw new ConcurrentModificationException("Collection has been changed.");
			}

			if (tablePointer > tableIt.length) {
				return false;
			} else if (curr == null) {
				if (tablePointer + 1 >= tableIt.length) {
					return false;
				}
				curr = table[++tablePointer];
				return this.hasNext();
			}

			return true;
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @throws ConcurrentModificationException
		 *             if the collection has been changed from outside of the
		 *             iterator.
		 */
		@Override
		public TableEntry<K, V> next() {
			if (modificationCnt != modificationCount) {
				throw new ConcurrentModificationException("Collection has been changed.");
			}

			if (curr == null) {
				curr = table[++tablePointer];
				return this.next();
			}
			TableEntry<K, V> entry = curr;

			curr = curr.next;
			return entry;
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @throws ConcurrentModificationException
		 *             if the collection has been changed from outside of the
		 *             iterator.
		 */
		@Override
		public void remove() {
			if (modificationCnt != modificationCount) {
				throw new ConcurrentModificationException("Collection has been changed.");
			}
			modificationCnt++;
			SimpleHashTable.this.remove(curr);
		}

	}

	/**
	 * A factory method for producing the iterators.
	 */
	@Override
	public Iterator<TableEntry<K, V>> iterator() {
		return new IteratorImpl();
	}

}
