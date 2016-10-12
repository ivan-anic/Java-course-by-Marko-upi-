package hr.fer.zemris.java.custom.scripting.exec;

/**
 * A read-write wrapper type object. Contains a numerical value and provides
 * various arithmetical operations.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class ValueWrapper {
	/** The numerical value kept in this wrapper. */
	private Object value;

	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * Sets the value.
	 *
	 * @param value
	 *            the new value
	 */
	public void setValue(Object value) {
		this.value = value;
	}

	/**
	 * Instantiates a new value wrapper. Sets this value to the given argument.
	 *
	 * @param value
	 *            the value
	 */
	public ValueWrapper(Object value) {
		super();
		this.value = value;
		this.value = checkNullAndString(value);
	}

	/**
	 * Performs the arithmetical addition over the two values.
	 *
	 * @param incValue
	 *            the value which this value will be added with
	 */
	public void increment(Object incValue) {
		checkArguments(incValue);
		incValue = checkNullAndString(incValue);
		value = checkNullAndString(value);

		if (value instanceof Double || incValue instanceof Double) {
			if (value instanceof Integer) {
				value = new Double((double) (int) value + (double) incValue);
				return;
			}
			if (incValue instanceof Integer) {
				value = new Double((double) value + (double) (int) incValue);
				return;
			}
		} else {
			value = new Integer((int) value + (int) incValue);
			return;
		}
		value = (double) value + (double) incValue;
	}

	/**
	 * Performs the arithmetical subtraction over the two values. Subtracts this
	 * value with the given argument.
	 *
	 * @param decValue
	 *            the value which this value will be subtracted by
	 */
	public void decrement(Object decValue) {
		checkArguments(decValue);
		decValue = checkNullAndString(decValue);

		if (value instanceof Double || decValue instanceof Double) {
			if (value instanceof Integer) {
				value = new Double((double) (int) value - (double) decValue);
				return;
			}
			if (decValue instanceof Integer) {
				value = new Double((double) value - (double) (int) decValue);
				return;
			}
		} else {
			value = new Integer((int) value - (int) decValue);
			return;
		}
		value = (double) value - (double) decValue;
	}

	/**
	 * Performs the arithmetical multiplication over the two values; this value
	 * and the argument value.
	 * 
	 * @param mulValue
	 *            the value which this value will be multiplied with
	 */
	public void multiply(Object mulValue) {
		checkArguments(mulValue);
		mulValue = checkNullAndString(mulValue);

		if (value instanceof Double || mulValue instanceof Double) {
			if (value instanceof Integer) {
				value = new Double((double) (int) value * (double) mulValue);
				return;
			}
			if (mulValue instanceof Integer) {
				value = new Double((double) value * (double) (int) mulValue);
				return;
			}
		} else {
			value = new Integer((int) value * (int) mulValue);
			return;
		}
		value = (double) value * (double) mulValue;
	}

	/**
	 * Performs the arithmetical division over the two values. Divides this
	 * value with the given argument.
	 *
	 * @param divValue
	 *            the value which this value will be divided with
	 */
	public void divide(Object divValue) {
		checkArguments(divValue);
		divValue = checkNullAndString(divValue);

		if (value instanceof Double || divValue instanceof Double) {
			if (value instanceof Integer) {
				value = new Double((double) (int) value / (double) divValue);
				return;
			}
			if (divValue instanceof Integer) {
				value = new Double((double) value / (double) (int) divValue);
				return;
			}
		} else {
			value = new Integer((int) value / (int) divValue);
			return;
		}
		value = (double) value / (double) divValue;
	}

	/**
	 * Performs numerical comparison between currently stored value in
	 * {@linkplain ValueWrapper} and given argument.
	 * 
	 * @param withValue
	 *            the given argument to be compared with this value.
	 * @return returns an integer less than zero if currently stored value is
	 *         smaller than argument, an integer greater than zero if currently
	 *         stored value is larger than argument or an integer 0 if they are
	 *         equal.
	 */
	public int numCompare(Object withValue) {
		checkArguments(withValue);
		withValue = checkNullAndString(withValue);
		value = checkNullAndString(value);

		if (value instanceof Double || withValue instanceof Double) {
			if (value instanceof Integer) {
				return Double.compare((double) (int) value, (double) withValue);

			}
			if (withValue instanceof Integer) {
				return Double.compare((double) value, (double) (int) withValue);

			}
		} else {
			return Double.compare((int) value, (int) withValue);
		}
		return Double.compare((double) value, (double) withValue);

	}

	// Rules for numCompare method are similar. This method does not perform any
	// change. It perform numerical
	// comparison between currently stored value in ValueWrapper and given
	// argument. The method returns an
	// integer less than zero if currently stored value is smaller than
	// argument, an integer greater than zero if
	// currently stored value is larger than argument or an integer 0 if they
	// are equal.

	/**
	 * Checks if input arguments are valid. Valid types are string, integer and
	 * double.
	 *
	 * @param argument
	 *            the argument
	 * @throws RuntimeException
	 *             if the argument is of invalid type.
	 */
	private void checkArguments(Object argument) {
		if (!(argument instanceof String) && !(argument instanceof Integer) && !(argument instanceof Double)) {
			throw new RuntimeException("Invalid argument type!");
		}
	}

	/**
	 * Check null and string.
	 *
	 * @param argument
	 *            the argument the argument to be checked and converted
	 * @return the object the object in the desired state
	 * @throws IllegalStateException
	 *             if an error occurs.
	 */
	private Object checkNullAndString(Object argument) {
		try {
			return (argument == null) ? Integer.valueOf(0)
					: (argument instanceof String) ? Integer.valueOf((String) argument) : argument;
		} catch (NumberFormatException ex) {
			return Double.valueOf((String) argument);
		}
	}
}