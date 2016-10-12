package hr.fer.zemris.java.tecaj.hw6.observer2;

/**
 * A concrete observer of {@linkplain IntegerStorageObserver}. <br>
 * Writes a square of the integer stored in the {@linkplain IntegerStorage} to
 * the standard output.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class SquareValue implements IntegerStorageObserver {

	@Override
	public void valueChanged(IntegerStorageChange storage) {
		System.out.printf("Provided new value: %d, square is %.0f.\n", storage.getValue(),
				Math.pow(storage.getValue(), 2.0));
	}
}
