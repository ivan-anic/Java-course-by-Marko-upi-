package hr.fer.zemris.java.tecaj.hw1;

import java.text.DecimalFormat;

/**
 * A program which calculates the roots of the provided complex number.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class Roots {

	/**
	 * Calculates and prints the solutions for the given root of the given
	 * complex number.
	 * 
	 * @param real
	 *            - the real part of the given number
	 * @param complex
	 *            - the complex part of the given number
	 * @param root
	 *            - the specified root to calculate
	 */
	private static void calculateRoot(Float real, Float complex, float root) {
		Double realValue, cmplxValue;
		DecimalFormat dec = new DecimalFormat("#.##");
		Double r = Math.sqrt(Math.pow(real, 2) + Math.pow(complex, 2));
		Double angle = Math.atan2(complex, real);
		r = Math.pow(r, (1 / (root)));

		for (int i = 0; i < root; i++) {
			realValue = r * Math.cos((angle + 2 * i * Math.PI) / root);
			cmplxValue = r * Math.sin((angle + 2 * i * Math.PI) / root);
			System.out.println(i + 1 + ") " + dec.format(realValue) + ((cmplxValue < 0) ? " - " : " + ")
					+ (dec.format(Math.abs(cmplxValue))) + "i\n");
		}
		return;
	}

	/**
	 * The main method, first one to be executed when the program starts.
	 *
	 * @param args
	 *            - real part of the number, complex part of the number, the
	 *            desired root
	 */
	public static void main(String[] args) {
		System.out.println("You requested calculation of " + args[2] + ". roots. Solutions are:");
		calculateRoot(Float.parseFloat(args[0]), Float.parseFloat(args[1]), Float.parseFloat((args[2])));
		return;
	}
}