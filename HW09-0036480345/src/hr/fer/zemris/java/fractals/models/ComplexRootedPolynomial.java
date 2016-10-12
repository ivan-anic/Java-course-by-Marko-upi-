package hr.fer.zemris.java.fractals.models;

import java.util.ArrayList;
import java.util.List;

/**
 * A class which represents an unmodifiable complex polynomial represented by
 * its roots. It offers various operations which can be executed to perform
 * mathematical operations needed for complex mathematics algebra.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class ComplexRootedPolynomial {

	/** A list containing the roots of this complex polynomial. */
	private List<Complex> complexRoots;

	/**
	 * Instantiates a new polynomial complex number.
	 *
	 * @param roots
	 *            the roots of the number
	 */
	public ComplexRootedPolynomial(Complex... roots) {
		complexRoots = new ArrayList<>();
		for (Complex c : roots) {
			complexRoots.add(c);
		}
	}

	/**
	 * Computes the polynomial value at given point z
	 *
	 * @param z
	 *            the point at which the polynom will be computed
	 * @return the desired complex number
	 */
	public Complex apply(Complex z) {
		ComplexPolynomial complexFactors = toComplexPolynom();

		return complexFactors.apply(z);
	}

	/**
	 * Converts this representation to {@link ComplexPolynomial} type
	 *
	 * @return the complex polynomial
	 */
	public ComplexPolynomial toComplexPolynom() {
		ComplexPolynomial z = new ComplexPolynomial(Complex.ONE);

		for (Complex c : complexRoots) {
			z = z.multiply(new ComplexPolynomial(Complex.ONE, c.negate()));
		}

		return z;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		for (Complex c : complexRoots) {
			if (c.getReal() != 0 && c.getImaginary() != 0) {
				sb.append(c.toString() + "(" + "+" + c + ")");
			}
		}

		return sb.toString();
	}

	/**
	 * Finds the index of closest root for given complex number z that is within
	 * the given threshold
	 *
	 * @param z
	 *            the z given complex number
	 * @param threshold
	 *            the given threshold
	 * @return the index of the closest root, if there is no such root, returns
	 *         -1
	 */
	public int indexOfClosestRootFor(Complex z, double threshold) {
		int index = -1;
		double min = threshold;

		for (Complex c : complexRoots) {
			double dist = calculateDistance(c, z);
			if (dist < threshold && dist < min) {
				index = complexRoots.indexOf(c);
				min = dist;
			}
		}

		return index;
	}

	/**
	 * Calculate the distance between the two given complex numbers.
	 *
	 * @param w1 the first complex number 
	 * @param w2 the second complex number 
	 * @return the distance from the two given numbers
	 */
	private double calculateDistance(Complex w1, Complex w2) {
		double real = w1.sub(w2).getReal();
		double imaginary = w1.sub(w2).getImaginary();

		double result = Math.sqrt(
				Math.pow(Math.abs(real), 2)
						+ Math.pow(Math.abs(imaginary), 2));

		return result;
	}

}
