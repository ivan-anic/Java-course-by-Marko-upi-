package hr.fer.zemris.java.custom.collections;

/**
 * A class which provides the user with a stack collection, and a set of methods
 * which represent the operations which can be performed on a stack.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class ObjectStack {
	/**
	 * An instance of {@link ArrayIndexedCollection}, implemented to act as a
	 * stack in the methods implemented below.
	 */
	ArrayIndexedCollection stack = new ArrayIndexedCollection();

	/**
	 * Checks if the stack is empty.
	 * 
	 * @return - returns {@code true} if the stack contains no elements by
	 *         utilising the {@link #size()} method.
	 */
	public boolean isEmpty() {
		return stack.isEmpty();
	}

	/**
	 * Returns the current size of the stack.
	 * 
	 * @return - size of the stack, as an integer value.
	 */
	public int size() {
		return stack.size();
	}

	/**
	 * Pushes a given value onto the stack. Null values are not allowed to be
	 * pushed on the stack.
	 * 
	 * @param value
	 *            - value to be pushed.
	 */
	public void push(Object value) {
		stack.add(value);
	}

	/**
	 * Removes the last value pushed on stack from stack and returns it. If the
	 * stack is empty when method pop is called, this method throws an
	 * {@link EmptyStackException}.
	 * 
	 * @return - the object popped from the stack
	 * @throws EmptyStackException - if the stack is empty
	 */
	public Object pop() {
		if (stack.isEmpty()) {
			throw new EmptyStackException();
		}
		Object temp = stack.get(stack.size() - 1);
		stack.remove(stack.size() - 1);
		return temp;
	}

	/**
	 * Returns last element placed on stack but does not delete it from stack.
	 * If the stack is empty when method pop is called, this method throws an
	 * {@link EmptyStackException}.
	 * 
	 * @return the object on top of the stack
	 * @throws EmptyStackException - if the stack is empty 
	 */
	public Object peek() {
		if (stack.isEmpty()) {
			throw new EmptyStackException();
		}
		return stack.get(stack.size() - 1);
	}

	/**
	 * Removes all elements from the stack.
	 */
	public void clear() {
		stack.clear();
	}

}
