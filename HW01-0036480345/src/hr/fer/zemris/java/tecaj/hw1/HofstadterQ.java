package hr.fer.zemris.java.tecaj.hw1;

/**
 * Calculates the n-th number of the Hofstadter's Q sequence.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class HofstadterQ {

	/**
	 * Calculates the wanted number and returns it.
	 * 
	 * @param i
	 *            - the wanted number position.
	 * @return
	 */
	private static long hofstadter(long i) {
		long result;
		if (i < 3)
			result = 1;
		else
			result = hofstadter(i - (hofstadter(i - 1))) + hofstadter(i - (hofstadter(i - 2)));
		return result;
	}

	/**
	 * The main method, first one to be executed when the program starts.
	 * 
	 * @param argc
	 *            - the wanted number in the Hofstadter's Q-sequence
	 */
	public static void main(String[] args) {
		int arg = Integer.parseInt(args[0]);
		if (arg < 1) {
			System.out.println("Argument invalid. Please provide a positive number.");
			return;
		}
		System.out.println("You requested calculation of " + arg + ". number of "
				+ "Hofstadter's Q-sequence. The requested number is " + hofstadter(arg) + ".");
		return;
	}
}