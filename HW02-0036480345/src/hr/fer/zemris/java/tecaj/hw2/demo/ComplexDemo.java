package hr.fer.zemris.java.tecaj.hw2.demo;

import hr.fer.zemris.java.tecaj.hw2.ComplexNumber;

/**
 * A class used for the testing of the {@link ComplexNumber} class
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class ComplexDemo {
	/**
	 * The main method, first one to be executed when the program starts.
	 * 
	 * @param args
	 *            - the input arguments (not used)
	 * 
	 */
	public static void main(String[] args) {

		ComplexNumber c1 = new ComplexNumber(2, 3);
		ComplexNumber c2 = ComplexNumber.parse("2.5-3i");
		ComplexNumber c3 = c1.add(ComplexNumber.fromMagnitudeAndAngle(2, 1.57))
		.div(c2).power(3).root(2)[1];
		System.out.println(c3);
	}
}
