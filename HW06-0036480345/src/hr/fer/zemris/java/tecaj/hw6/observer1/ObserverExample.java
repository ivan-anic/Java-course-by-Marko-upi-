package hr.fer.zemris.java.tecaj.hw6.observer1;

/**
 * Represents a demonstration of the functionalities of this package. <br>
 * Classes demonstrated:
 * <ul>
 * <li>{@linkplain IntegerStorageObserver}</li>
 * <li>{@linkplain IntegerStorage}</li>
 * </ul>
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class ObserverExample {

	/**
	 * The main method, first one to be executed when the program starts.
	 * 
	 * @param args
	 *            - the input arguments (not used)
	 */
	public static void main(String[] args) {
		IntegerStorage istorage = new IntegerStorage(20);
		IntegerStorageObserver observer = new SquareValue();
		
		istorage.addObserver(observer);
		istorage.setValue(5);
		istorage.setValue(2);
		istorage.setValue(25);

		istorage.removeObserver(observer);
		
		istorage.addObserver(new ChangeCounter());
		istorage.addObserver(new DoubleValue(2));
		istorage.addObserver(new DoubleValue(1));
		istorage.addObserver(new DoubleValue(2));

		istorage.setValue(13);
		istorage.setValue(22);
		istorage.setValue(15);
	}
}
