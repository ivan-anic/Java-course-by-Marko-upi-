package hr.fer.zemris.java.tecaj.hw6.observer1;

/**
 * A concrete observer of {@linkplain IntegerStorageObserver}. <br>
 * Writes a double value of the integer stored in the
 * {@linkplain IntegerStorage} to the standard output.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class DoubleValue implements IntegerStorageObserver {

	/** The number of times this observer will observe the changes. */
	private int cnt;

	/**
	 * Instantiates this observer. Initialises counter to the desired value.
	 * 
	 * @param cnt
	 *            the desired counter value.
	 */
	public DoubleValue(int cnt) {
		super();
		this.cnt = cnt;
	}

	@Override
	public void valueChanged(IntegerStorage storage) {
		if (cnt != 0) {
			System.out.printf("Double value: %d.\n", storage.getValue() * 2);
			cnt--;
		} else {
			storage.addForRemoval(this);
		}
	}
}
