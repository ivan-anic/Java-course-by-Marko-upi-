package hr.fer.zemris.java.tecaj.hw6.demo3;

import java.util.Iterator;

/**
 * Represents a collection of prime numbers. It is capable to accept a value,
 * and can afterwards only return the specified number of primes.
 *
 * @author Ivan Anić
 * @version 1.0
 */
public class PrimesCollection implements Iterable<Integer> {

	/** The number of prime numbers that have to be calculated. */
	private int counter;

	/**
	 * Instantiates a new primes collection which will be able to output only a
	 * certain number of primes.
	 *
	 * @param counter
	 *            the counter
	 */
	public PrimesCollection(int counter) {
		super();
		this.counter = counter;
	}

	@Override
	public Iterator<Integer> iterator() {

		return new IteratorImpl();
	}

	/**
	 * An implementation of the {@link Iterator} for the
	 * {@link PrimesCollection}.
	 */
	private class IteratorImpl implements Iterator<Integer> {

		/** The number of prime numbers that have to be calculated. */
		private int count = counter;
		/** Remembers the last calculated prime. Initially set to 1. */
		private int currPrime = 2;

		@Override
		public boolean hasNext() {
			return count > 0;
		}

		@Override
		public Integer next() {
			if (currPrime < 3) {
				count--;
				return currPrime++;
			} else if (currPrime == 3) {
				count--;
				Integer ret = currPrime;
				currPrime += 2;
				return ret;
			}
			while (true) {
				if (isPrime(currPrime)) {
					--count;
					Integer ret = currPrime;
					currPrime += 2;
					return ret;
				}
				currPrime += 2;
			}
		}

		/**
		 * Checks whether the given number is prime.
		 *
		 * @param number
		 *            the given number
		 * @return true, if the argument number is prime
		 */
		private boolean isPrime(int number) {
			int limit = (int) Math.sqrt(number);
			for (int i = 2; i <= limit; ++i) {
				if (number % i == 0) {
					return false;
				}
			}
			return true;
		}
	}
}