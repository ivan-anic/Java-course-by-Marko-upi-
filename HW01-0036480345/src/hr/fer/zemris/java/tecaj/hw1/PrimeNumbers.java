package hr.fer.zemris.java.tecaj.hw1;

/**
 * Finds and prints the first n prime numbers.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class PrimeNumbers {

	/**
	 * Checks if the given numbers is a prime number.
	 * 
	 * @param number
	 *            - the given number
	 * @return - true if the number is prime; false if it isn't
	 */
	public static boolean isPrime(int number) {
		for (int i = 2; i <= Math.sqrt(number); i++) {
			i++;
			if (number % i == 0) {
				return false;
			}
		}
		return true;
	}

	/**
	 * @param n
	 *            - the wanted number of prime numbers.
	 */
	private static void findPrimeNumbers(int n) {
		int cnt = 2;

		if (n > 2)
			System.out.println("1. 2");
		for (int i = 3; cnt < (n + 1); i += 2) {
			if (isPrime(i))
				System.out.println(cnt++ + ". " + i);
		}

	}

	/**
	 * The main method, first one to be executed when the program starts.
	 * 
	 * @param args
	 *            - the wanted amount of prime numbers
	 */
	public static void main(String[] args) {
		System.out.println("You requested calculation of " + args[0] + " prime numbers. Here they are:");
		findPrimeNumbers(Integer.parseInt(args[0]));
		return;
	}
}
