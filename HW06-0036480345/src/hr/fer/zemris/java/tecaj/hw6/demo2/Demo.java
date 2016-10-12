package hr.fer.zemris.java.tecaj.hw6.demo2;

import java.util.Optional;

/**
 * A class used for the testing of the class contained in this project.
 * 
 * Classes demonstrated:
 * <ul>
 * <li>{@linkplain LikeMedian}</li>
 * </ul>
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class Demo {

	/**
	 * The main method, first one to be executed when the program starts.
	 * 
	 * @param args
	 *            - the input arguments (not used)
	 */
	public static void main(String[] args) {

		LikeMedian<Integer> likeMedian = new LikeMedian<>();

		System.out.println(likeMedian.get());

		likeMedian.add(new Integer(10));
		likeMedian.add(new Integer(10));
		likeMedian.add(new Integer(4));
		likeMedian.add(new Integer(5));
		likeMedian.add(new Integer(4));
		likeMedian.add(new Integer(3));

		Optional<Integer> result = likeMedian.get();
		System.out.println(result.get());
		System.out.println();
		for (Integer elem : likeMedian) {
			System.out.println(elem);
		}
	}
}