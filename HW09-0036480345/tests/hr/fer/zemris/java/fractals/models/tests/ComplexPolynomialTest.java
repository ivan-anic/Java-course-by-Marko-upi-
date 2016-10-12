package hr.fer.zemris.java.fractals.models.tests;
import static org.junit.Assert.*;

import org.junit.Test;

import hr.fer.zemris.java.fractals.models.Complex;
import hr.fer.zemris.java.fractals.models.ComplexPolynomial;

public class ComplexPolynomialTest {
	Complex c1 = new Complex(1, -1);
	Complex c2 = new Complex(5, 0);
	Complex c3 = new Complex(2, 3);
	Complex c4 = new Complex(-7, -3);

	Complex c5 = new Complex(1, 0);
	Complex c6 = new Complex(5, 0);
	Complex c7 = new Complex(2, 0);
	Complex c8 = new Complex(7, 2);

	@Test
	public void test() {
		ComplexPolynomial cmplx = new ComplexPolynomial(c1, c2, c3, c4);
		assertEquals("(1.0-i)+(5.0)*z^1+(2.0+3.0i)*z^2+(-7.0-3.0i)*z^3",
				(cmplx.toString()));
	}

	@Test
	public void testOrder() {
		ComplexPolynomial cmplx = new ComplexPolynomial(c1, c2, c3, c4);
		assertEquals(3, cmplx.order());
	}

	@Test
	public void testMultiply() {
		ComplexPolynomial cmplx = new ComplexPolynomial(c1, c4, c7);
		assertEquals("(-2.0i)+(-5.0+5.0i)*z^1+(-1.0-31.0i)*z^2+(-10.0)*z^3+(4.0)*z^4",
				cmplx.multiply(new ComplexPolynomial(c1, c3, c7)).toString());
	}
	
	@Test
	public void testDerive() {
		ComplexPolynomial cmplx = new ComplexPolynomial(c5, c6, c7, c8);
		
		ComplexPolynomial c = cmplx.derive();
		
		assertEquals("(5.0)+(4.0)*z^1+(21.0+6.0i)*z^2",
				c.toString());
	}
	
	@Test
	public void testApply() {
		ComplexPolynomial cmplx = new ComplexPolynomial(c1, c4, c7);
		
		Complex c = cmplx.apply(c8);
		assertEquals("48.0+20.0i",	c.toString());
	}

}
