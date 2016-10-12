package hr.fer.zemris.java.tecaj.hw1;

/**
 * A program which decomposes the given number to prime factors.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class NumberDecomposition {

	/**
	 * Executes the decomposition itself.
	 * 
	 * @param n
	 *            - the given number to decompose
	 */
	private static void decompose(int n) {
		int cnt = 1;
		while (n > 0) {
			for (int i = 2; i <= n; i++) {
				if (n % i == 0) {
					System.out.println(cnt++ + ". " + i);
					n /= i;
					break;
				}
			}
		}
		return;
	}

	/**
	 * The main method, first one to be executed when the program starts.
	 * 
	 * @param args
	 *            - the number to decompose to prime factors
	 */
	public static void main(String[] args) {
		if (Integer.valueOf(args[0]) < 1) {
			System.out.println("Argument invalid.");
			return;
		}
		System.out.println("You requested decomposition of number " + args[0] + " onto prime factors. Here they are:");
		decompose(Integer.valueOf(args[0]));
		return;
	}
}