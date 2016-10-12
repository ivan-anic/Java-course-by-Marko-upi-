package hr.fer.zemris.java.custom.scripting.exec;

import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.Map;

/**
 * A map-like structure which holds a key-value data structure. Key is a string
 * type object, and the value if an instance of {@linkplain MultistackEntry}.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class ObjectMultistack {

	/** The map. */
	private Map<String, MultistackEntry> map = new HashMap<>();

	/**
	 * The Class MultistackEntry.
	 */
	private static class MultistackEntry {

		/** The key used for getting the desired {@linkplain MultistackEntry} */
		@SuppressWarnings("unused")
		String key;

		/**
		 * The value placed into the table, wrapped into an instance of
		 * {@linkplain ValueWrapper}.
		 */
		ValueWrapper value;

		/**
		 * A reference to the next {@linkplain MultistackEntry} in the stack.
		 */
		MultistackEntry next;

		/**
		 * Instantiates a new stack entry with the desired parameters.
		 *
		 * @param key
		 *            the desired key
		 * @param value
		 *            the desired value to be placed into the table, wrapped
		 *            into an instance of {@linkplain ValueWrapper}.
		 * @param next
		 *            points to the next {@linkplain MultistackEntry} in the
		 *            stack.
		 */
		public MultistackEntry(String key, ValueWrapper value, MultistackEntry next) {
			super();
			this.key = key;
			this.value = value;
			this.next = next;
		}

	}

	/**
	 * Pushes a new entry to this stack.
	 *
	 * @param name
	 *            the name of the key of the desired value
	 * @param valueWrapper
	 *            the desired value to be placed into the table, wrapped into an
	 *            instance of {@linkplain ValueWrapper}.
	 */
	public void push(String name, ValueWrapper valueWrapper) {
		if (map.get(name) == null) {
			map.put(name, new MultistackEntry(name, valueWrapper, null));
		} else {
			MultistackEntry old = map.get(name);
			map.put(name, new MultistackEntry(name, valueWrapper, old));
		}
	}

	/**
	 * Pops an entry from the stack and returns it.
	 *
	 * @param name
	 *            the name of the key of the desired value
	 * @return the popped value.
	 */
	public ValueWrapper pop(String name) {

		MultistackEntry old = map.get(name);

		if (old == null) {
			throw new EmptyStackException();
		}

		map.put(name, old.next);

		old.next = null;
		return old.value;
	}

	/**
	 * Peek.
	 *
	 * @param name
	 *            the name
	 * @return the value wrapper
	 */
	public ValueWrapper peek(String name) {
		try {
			return map.get(name).value;
		} catch (NullPointerException x) {
			throw new EmptyStackException();
		}
	}

	/**
	 * Checks if the {@linkplain MultistackEntry} under the given key is empty.
	 *
	 * @param name
	 *            the given key to check the stack
	 * @return true, if the stack is empty
	 */
	public boolean isEmpty(String name) {
		return map.get(name) == null;
	}
}
