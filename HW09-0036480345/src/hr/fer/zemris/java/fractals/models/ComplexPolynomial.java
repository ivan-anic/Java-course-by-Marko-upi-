package hr.fer.zemris.java.fractals.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A class which represents an unmodifiable complex polynomial. It offers various
 * operations which can be executed to perform mathematical operations needed
 * for complex mathematics algebra.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class ComplexPolynomial {

	/** A list containing the factors of this complex polynomial. */
	private List<Complex> complexFactors;

	/**
	 * Instantiates a new polynomial complex number.
	 *
	 * @param factors the factors of the number
	 */
	public ComplexPolynomial(Complex... factors) {
		complexFactors = new ArrayList<>();
		for (Complex c : factors) {
			complexFactors.add(c);
		}
	}

	
	/**
	 * Calculates the order of this polynom.
	 *
	 * @return the desired order
	 */
	public short order() {
		int size = complexFactors.size();
		return (short) ((size == 0) ? 0 : (complexFactors.size() - 1));
	}

	/**
	 * Multiplies the polynomial p with the polynomial which this method is called on
	 * 
	 * @param p
	 *            - the polynomial to be multiplied with the referenced
	 *            number
	 * @return a new instance of the ComplexPolynomial class which contains the
	 *         result of the wanted mathematical operation.
	 */
	public ComplexPolynomial multiply(ComplexPolynomial p) {
		int thisSize = complexFactors.size() - 1;
		int totalSize = complexFactors.size() * (p.order() + 1);
		int pSize = p.order();
		Complex[] c = new Complex[totalSize];
		Arrays.fill(c, Complex.ZERO);

		List<Complex> asList = p.complexFactors;

		Complex temp = new Complex();

		for (int i = 0; i <= thisSize; i++) {
			for (int j = 0; j <= pSize; j++) {
				temp = (complexFactors.get(i).multiply(asList.get(j)));
				c[i + j] = c[i + j].add(temp);
			}
		}
		return new ComplexPolynomial(c);
	}

	
	/**
	 * Derive the polynomial p and returns it.
	 *
	 * @return the desired derivation of the given complex polynomial.
	 */
	public ComplexPolynomial derive() {
		int len = complexFactors.size() - 1;

		Complex[] result = new Complex[len];

		for (int i = 0; i < len; i++) {
			result[i] = complexFactors.get(i + 1).multiply(new Complex(i + 1, 0));
		}

		return new ComplexPolynomial(result);
	}

	
	/**
	 * Computes the polynomial value at given point z
	 *
	 * @param z the point at which the polynom will be computed
	 * @return the desired complex number
	 */
	public Complex apply(Complex z) {
		int len = complexFactors.size();

		Complex result = Complex.ZERO;

		for (int i = 0; i < len; i++) {
			Complex temp = complexFactors.get(i).multiply(z.power(i)); 
			result = result.add(temp);
		}

		return result;
	}

	@Override
	public String toString() {
		int len = complexFactors.size();

		StringBuilder sb = new StringBuilder();

		sb.append("(" + complexFactors.get(0).toString() + ")");

		for (int i = 1; i < len; i++) {
			Complex num = complexFactors.get(i);
			if (num.getReal() != 0 || num.getImaginary() != 0) {
				sb.append("+" + "(" + num.toString() + ")*z^" + i);
			}
		}

		return sb.toString();
	}
}