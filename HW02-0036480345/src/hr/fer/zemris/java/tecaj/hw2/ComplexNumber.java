package hr.fer.zemris.java.tecaj.hw2;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A class which represents an unmodifiable complex number. It offers various
 * operations which can be executed to perform mathematical operations needed
 * for complex mathematics algebra.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
/**
 * @author Ivan Anić
 * @version 1.0
 */
public class ComplexNumber {
	/** real part of the complex number */
	private Double real;
	/** imaginary part of the complex number */
	private Double cmplx;

	/**
	 * Creates an instance of a ComplexNumber class. Takes the number arguments,
	 * and accordingly sets the class variables.
	 * 
	 * @param real
	 *            - real part of the number
	 * @param cmplx
	 *            - imaginary part of the number
	 */
	public ComplexNumber(double real, double cmplx) {
		this.real = real;
		this.cmplx = cmplx;
	}

	/**
	 * Creates an instance of a ComplexNumber class with a real value, and sets
	 * the imaginary value to 0. Returns the aforementioned object.
	 * 
	 * @param real
	 *            - real part of the number
	 * @return - an instance of ComplexNumber class, with its variables
	 *         accordingly set
	 */

	public static ComplexNumber fromReal(double real) {
		return new ComplexNumber(real, 0);
	}

	/**
	 * Creates an instance of a ComplexNumber class with an imaginary value, and
	 * sets the real value to 0. Returns the aforementioned object.
	 * 
	 * @param imaginary
	 *            - imaginary part of the number
	 * @return - an instance of ComplexNumber class, with its variables
	 *         accordingly set
	 */
	public static ComplexNumber fromImaginary(double imaginary) {
		return new ComplexNumber(0, imaginary);
	}

	/**
	 * Creates an instance of a ComplexNumber class from the given magnitude and
	 * angle values. Uses the following formulas:
	 * 
	 * a = r * cos(phi) b = r * sin(phi)
	 * 
	 * Returns the aforementioned object.
	 * 
	 * @param magnitude
	 *            - magnitude of the complex number
	 * @param angle
	 *            - angle of the complex number
	 * @return - an instance of ComplexNumber class, with its variables
	 *         accordingly set
	 */
	public static ComplexNumber fromMagnitudeAndAngle(double magnitude, double angle) {
		return new ComplexNumber(magnitude * Math.cos(angle), magnitude * Math.sin(angle));
	}

	/**
	 * Creates an instance of a ComplexNumber class from the given string. Uses
	 * the regular expressions to parse the input string by checking if the
	 * string matches the regular expressions three times; if it matches a real
	 * number, a complex number with no real value, or a number with both real
	 * and complex values.
	 * 
	 * Returns the aforementioned object using the methods implemented above.
	 * 
	 * @param s
	 *            - a complex number in the form of a string
	 * @return - an instance of ComplexNumber class, with its variables
	 *         accordingly sets
	 */
	public static ComplexNumber parse(String s) {
		Double realValue = 0.0, cmplxValue = 0.0;

		String patternReal = "([-+]?[0-9]*\\.?[0-9]*)";
		String patternCmplx = "([-+]?[0-9]*\\.?[0-9]*i)";
		String pattern = "([-+]?[0-9]*\\.?[0-9]*)([-+]?[0-9]*\\.?[0-9]*)i";

		Pattern complexPattern;
		Matcher match;

		complexPattern = Pattern.compile(patternReal);
		match = complexPattern.matcher(s);

		if (match.matches()) {
			realValue = Double.parseDouble(match.group(1));
			return fromReal(realValue);
		}

		complexPattern = Pattern.compile(patternCmplx);
		match = complexPattern.matcher(s);

		if (match.matches()) {
			String temp = match.group(1);
			if (temp == "-i") {
				temp = "-1";
			} else if (temp == "i") {
				temp = "1";
			}
			cmplxValue = Double.parseDouble(temp);
			return fromImaginary(cmplxValue);
		}

		complexPattern = Pattern.compile(pattern);
		match = complexPattern.matcher(s);

		if (match.matches()) {
			realValue = Double.parseDouble(match.group(1));
			cmplxValue = Double.parseDouble(match.group(2));

			return new ComplexNumber(realValue, cmplxValue);
		}

		throw new IllegalArgumentException();
	}

	/**
	 * Returns the real part of the number which this method is called on.
	 * 
	 * @return - the real value of the number
	 */
	public double getReal() {
		return real;
	}

	/**
	 * Returns the imaginary part of the number which this method is called on.
	 * 
	 * @return - the imaginary value of the number
	 */
	public double getImaginary() {
		return cmplx;
	}

	/**
	 * Returns the magnitude of the number which this method is called on. Uses
	 * the hypot(a/b) function from the java.lang.Math class.
	 * 
	 * @return - the magnitude of the number
	 */
	public double getMagnitude() {
		return Math.hypot(real, cmplx);
	}

	/**
	 * Returns the angle of the number which this method is called on. Uses the
	 * atan2(a/b) function from the java.lang.Math class.
	 * 
	 * @return - the angle of the number
	 */
	public double getAngle() {
		return Math.atan2(cmplx, real);
	}

	/**
	 * Adds the number c to the number which this method is called on
	 * 
	 * @param c
	 *            - the complex number to be added to the referenced number
	 * @return a new instance of the ComplexNumber class which contains the
	 *         result of the wanted mathematical operation.
	 */
	public ComplexNumber add(ComplexNumber c) {
		return new ComplexNumber(this.real + c.real, this.cmplx + c.cmplx);
	}

	/**
	 * Subtracts the number c from the number which this method is called on
	 * 
	 * @param c
	 *            - the complex number to be subtracted from the referenced
	 *            number
	 * @return a new instance of the ComplexNumber class which contains the
	 *         result of the wanted mathematical operation.
	 */
	public ComplexNumber sub(ComplexNumber c) {
		return new ComplexNumber(this.real - c.real, this.cmplx - c.cmplx);
	}

	/**
	 * Multiplies the number c with the number which this method is called on
	 * 
	 * @param c
	 *            - the complex number to be multiplied with the referenced
	 *            number
	 * @return a new instance of the ComplexNumber class which contains the
	 *         result of the wanted mathematical operation.
	 */
	public ComplexNumber mul(ComplexNumber c) {
		return new ComplexNumber(this.real * c.real - this.cmplx * c.cmplx, this.real * c.cmplx + this.cmplx * c.real);
	}

	/**
	 * Divides the number which this method is called on with the number c
	 * 
	 * @param c
	 *            - the complex number which the referenced number will be
	 *            divided with
	 * @return a new instance of the ComplexNumber class which contains the
	 *         result of the wanted mathematical operation.
	 */
	public ComplexNumber div(ComplexNumber c) {
		return new ComplexNumber(
				(this.real * c.real + this.cmplx * c.cmplx) / (Math.pow(c.real, 2) + Math.pow(c.cmplx, 2)),
				(-this.real * c.cmplx + this.cmplx * c.real) / (Math.pow(c.real, 2) + Math.pow(c.cmplx, 2)));
	}

	/**
	 * Calculates the n-th power of the number which this method is called on
	 * 
	 * @param n
	 *            - the power to which the number this method is called on will
	 *            be raised
	 * @return a new instance of the ComplexNumber class which contains the
	 *         result of the wanted mathematical operation.
	 */
	public ComplexNumber power(int n) {
		if (n < 0) {
			throw new IllegalArgumentException();
		}
		Double realValue, cmplxValue;
		Double r = Math.hypot(real, cmplx);
		r = Math.pow(r, (n));
		Double angle = Math.atan2(cmplx, real);

		realValue = r * Math.cos(angle * n);
		cmplxValue = r * Math.sin(angle * n);
		return new ComplexNumber(realValue, cmplxValue);
	}

	/**
	 * Calculates the n-th roots of the number which this method is called on
	 * 
	 * @param n
	 *            - the desired root of the referenced number
	 * @return an array of instances of the ComplexNumber class which contains the
	 *         results of the wanted mathematical operation.
	 */
	public ComplexNumber[] root(int n) {
		if (n < 1) {
			throw new IllegalArgumentException();
		}
		ComplexNumber[] array = new ComplexNumber[n];
		Double realValue, cmplxValue;
		Double r = Math.hypot(real, cmplx);
		Double angle = Math.atan2(cmplx, real);
		r = Math.pow(r, (1 / (n)));

		for (int i = 0; i < n; i++) {
			realValue = r * Math.cos((angle + 2 * i * Math.PI) / n);
			cmplxValue = r * Math.sin((angle + 2 * i * Math.PI) / n);
			array[i] = new ComplexNumber(realValue, cmplxValue);
		}
		return array;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		String result = "";
		if (real != 0) {
			result += real;
		}
		if (cmplx != 0) {
			result += cmplx + "i";
		}
		return result;
	}
}
