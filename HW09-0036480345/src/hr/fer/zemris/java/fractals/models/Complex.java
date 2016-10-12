package hr.fer.zemris.java.fractals.models;

import static java.lang.Math.hypot;

import java.util.Arrays;
import java.util.List;
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
public class Complex {

	/** real part of the complex number */
	private Double real;
	/** imaginary part of the complex number */
	private Double imaginary;

	/** An instance of a complex number containing zero values. */
	public static final Complex ZERO = new Complex(0, 0);
	/** An instance of a complex number containing 1 for the real value. */
	public static final Complex ONE = new Complex(1, 0);
	/** An instance of a complex number containing -1 for the real value. */
	public static final Complex ONE_NEG = new Complex(-1, 0);
	/** An instance of a complex number containing 1 for the imaginary value. */
	public static final Complex IM = new Complex(0, 1);
	/** An instance of a complex number containing -1 for the imaginary value. */
	public static final Complex IM_NEG = new Complex(0, -1);

	/**
	 * Creates an instance of a Complex class, sets both parameters to 0.
	 * Returns the aforementioned object.
	 */
	public Complex() {
		this(0, 0);
	}

	/**
	 * Creates an instance of a Complex class. Takes the number arguments,
	 * and accordingly sets the class variables.
	 * 
	 * @param real
	 *            - real part of the number
	 * @param imaginary
	 *            - imaginary part of the number
	 */
	public Complex(double real, double imaginary) {
		this.real = real;
		this.imaginary = imaginary;
	}

	/**
	 * Returns the module of the number which this method is called on. Uses
	 * the {@linkplain #hypot} function.
	 * 
	 * @return - the magnitude of the number
	 */
	public double module() {
		return hypot(real, imaginary);
	}
	
	/**
	 * Adds the number c to the number which this method is called on
	 * 
	 * @param c
	 *            - the complex number to be added to the referenced number
	 * @return a new instance of the Complex class which contains the
	 *         result of the wanted mathematical operation.
	 */
	public Complex add(Complex c) {
		return new Complex(this.real + c.real, this.imaginary + c.imaginary);
	}

	/**
	 * Subtracts the number c from the number which this method is called on
	 * 
	 * @param c
	 *            - the complex number to be subtracted from the referenced
	 *            number
	 * @return a new instance of the Complex class which contains the
	 *         result of the wanted mathematical operation.
	 */
	public Complex sub(Complex c) {
		return new Complex(this.real - c.real, this.imaginary - c.imaginary);
	}

	/**
	 * Multiplies the number c with the number which this method is called on
	 * 
	 * @param c
	 *            - the complex number to be multiplied with the referenced
	 *            number
	 * @return a new instance of the Complex class which contains the
	 *         result of the wanted mathematical operation.
	 */
	public Complex multiply(Complex c) {
		return new Complex(this.real * c.real - this.imaginary * c.imaginary, this.real * c.imaginary + this.imaginary * c.real);
	}

	/**
	 * Divides the number which this method is called on with the number c
	 * 
	 * @param c
	 *            - the complex number which the referenced number will be
	 *            divided with
	 * @return a new instance of the Complex class which contains the
	 *         result of the wanted mathematical operation.
	 */
	public Complex divide(Complex c) {
		return new Complex(
				(this.real * c.real + this.imaginary * c.imaginary) / (Math.pow(c.real, 2) + Math.pow(c.imaginary, 2)),
				(-this.real * c.imaginary + this.imaginary * c.real) / (Math.pow(c.real, 2) + Math.pow(c.imaginary, 2)));
	}

	
	/**
	 * Negates this complex number and returns it.
	 *
	 * @return a new instance of the Complex class which contains the
	 *         negated complex number
	 */
	public Complex negate() {
		return new Complex(-this.real, -this.imaginary);
	}

	/**
	 * Calculates the n-th power of the number which this method is called on
	 * 
	 * @param n
	 *            - the power to which the number this method is called on will
	 *            be raised
	 * @return a new instance of the Complex class which contains the
	 *         result of the wanted mathematical operation.
	 */
	public Complex power(int n) {
		if (n < 0) {
			throw new IllegalArgumentException();
		}
		Double realValue, cmplxValue;
		Double r = Math.hypot(real, imaginary);
		r = Math.pow(r, (n));
		Double angle = Math.atan2(imaginary, real);

		realValue = r * Math.cos(angle * n);
		cmplxValue = r * Math.sin(angle * n);
		return new Complex(realValue, cmplxValue);
	}
	// returns n-th root of this, n is positive integer

	/**
	 * Calculates the n-th roots of the number which this method is called on
	 * 
	 * @param n
	 *            - the desired root of the referenced number
	 * @return an array of instances of the Complex class which contains the
	 *         results of the wanted mathematical operation.
	 */
	public List<Complex> root(int n) {
		if (n < 1) {
			throw new IllegalArgumentException();
		}
		Complex[] array = new Complex[n];
		Double realValue, cmplxValue;
		Double r = Math.hypot(real, imaginary);
		Double angle = Math.atan2(imaginary, real);
		r = Math.pow(r, (1 / (n)));

		for (int i = 0; i < n; i++) {
			realValue = r * Math.cos((angle + 2 * i * Math.PI) / n);
			cmplxValue = r * Math.sin((angle + 2 * i * Math.PI) / n);
			array[i] = new Complex(realValue, cmplxValue);
		}
		return Arrays.asList(array);
	}
	
	/**
	 * Gets the real part of the complex number.
	 *
	 * @return the desired number
	 */
	public Double getReal() {
		return real;
	}

	/**
	 * Gets the imaginary part of the complex number.
	 *
	 * @return the desired number
	 */
	public Double getImaginary() {
		return imaginary;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		String real = "";
		String imaginary = "";

		StringBuilder sb = new StringBuilder();
		
		if (this.real != 0) {
			real = Double.toString(this.real);
		}

		if (this.imaginary != 0) {
			if (this.imaginary > 0 && this.real != 0) {
				imaginary = "+";
			} else if (this.imaginary < 0) {
				imaginary = "-";
			}

			if (Math.abs(this.imaginary) != 1) {
				imaginary += Math.abs(this.imaginary) + "i";
			} else {
				imaginary += "i";
			}
		}
		sb.append(real + imaginary);

		return sb.toString();
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
	public static Complex parse(String s) {
		s = s.replaceAll("\\s", "");
		Pattern pattern = Pattern.compile("([+-]?\\d*(\\.\\d+)?i?)([+-]\\d*(\\.\\d+)?i?)?",
				Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(s);

		if (matcher.matches()) {
			String first = matcher.group(1);
			String second = matcher.group(3);

			double imaginary;
			double real;
			if (second == null) {
				if (isImaginary(first)) {
					imaginary = parseImaginary(first);
					real = 0;
				} else {
					real = Double.parseDouble(first);
					imaginary = 0;
				}
			} else {
				if (!(isImaginary(first) ^ isImaginary(second))) {
					throw new NumberFormatException();
				}

				if (isImaginary(first)) {
					imaginary = parseImaginary(first);
					real = Double.parseDouble(second);
				} else {
					real = Double.parseDouble(first);
					imaginary = parseImaginary(second);
				}
			}

			return new Complex(real, imaginary);
		} else {
			throw new NumberFormatException();
		}
	}

	/**
	 * Checks if the given number is imaginary.
	 *
	 * @param value the given number, in string format
	 * @return true, if the number is imaginary
	 */
	private static boolean isImaginary(String value) {
		value = value.toLowerCase();
		if (value.endsWith("i")) {
			return true;
		}
		return false;
	}

	/**
	 * Parses the imaginary number from the given string.
	 *
	 * @param s the given number, in string format
	 * @return the desired number
	 */
	private static double parseImaginary(String s) {
		s = s.substring(0, s.length() - 1);
		if (s.isEmpty() || !Character.isDigit(s.charAt(s.length() - 1))) {
			s += "1";
		}
		return Double.parseDouble(s);
	}

	
}
