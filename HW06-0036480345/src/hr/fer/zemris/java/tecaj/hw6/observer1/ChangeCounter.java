package hr.fer.zemris.java.tecaj.hw6.observer1;

/**
 * A concrete observer of {@linkplain IntegerStorageObserver}. <br>
 * Writes how many times the integer stored in the {@linkplain IntegerStorage}
 * has been changed to the standard output.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class ChangeCounter implements IntegerStorageObserver {
	/** The number of times the {@linkplain IntegerStorage} has been changed. */
	private int cnt;

	@Override
	public void valueChanged(IntegerStorage storage) {
		System.out.printf("Number of value changes since tracking: %d.\n", ++cnt);
	}

}
