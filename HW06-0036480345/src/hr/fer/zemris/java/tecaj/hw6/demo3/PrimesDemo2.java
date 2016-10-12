package hr.fer.zemris.java.tecaj.hw6.demo3;

/**
 * A class used for the testing of the class contained in this project.
 * 
 * Classes demonstrated:
 * <ul>
 * <li>{@linkplain PrimesCollection}</li>
 * </ul>
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class PrimesDemo2 {
	/**
	 * The main method, first one to be executed when the program starts.
	 * 
	 * @param args
	 *            - the input arguments (not used)
	 */
	public static void main(String[] args) {

		PrimesCollection primesCollection = new PrimesCollection(3);
		for (Integer prime : primesCollection) {
			for (Integer prime2 : primesCollection) {
				System.out.println("Got prime pair: " + prime + ", " + prime2);
			}

		}
	}

}
