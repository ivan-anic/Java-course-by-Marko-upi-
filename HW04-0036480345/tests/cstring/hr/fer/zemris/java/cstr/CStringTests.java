package hr.fer.zemris.java.cstr;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * The Class CStringTests.
 */
public class CStringTests {

	/**
	 * Test null input on first constructor.
	 */
	@Test(expected = NullPointerException.class)
	public void testNullInputOnFirstConstructor() {
		// must throw!
		new CString(null, 2, 5);
	}

	/**
	 * Test left.
	 */
	@Test
	public void testLeft() {
		CString string = new CString(String.valueOf("Pikachu Pikachu Pikachu").toCharArray(), 0, 23);
		assertEquals("Expected 'Pik'.", "Pik", string.left(3).toString());
	}

	/**
	 * Test right.
	 */
	@Test
	public void testRight() {
		CString string = new CString(String.valueOf("Pikachu Pikachu Pikachu").toCharArray(), 0, 23);
		assertEquals("Expected 'chu'.", "chu", string.right(3).toString());
	}

	/**
	 * Test substring with invalid arguments1.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSubstringWithInvalidArguments1() {
		// must throw!
		CString.fromString("Pikachu vs Onyx.").substring(-1, 3);
	}

	/**
	 * Test substring with invalid arguments2.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSubstringWithInvalidArguments2() {
		// must throw!
		CString.fromString("Pikachu vs Onyx.").substring(2, 1);
	}

	/**
	 * Test all with null value.
	 */
	@Test(expected = NullPointerException.class)
	public void testAllWithNullValue() {
		// must throw!
		CString.fromString("Pikachu.").add(null);
	}

	/**
	 * Test replace all with null as value1.
	 */
	@Test(expected = NullPointerException.class)
	public void testReplaceAllWithNullAsValue1() {
		// must throw!
		CString.fromString("Pikachu vs Onyx.").replaceAll(null, CString.fromString("zeko"));

	}

	/**
	 * Test replace all with null as value2.
	 */
	@Test(expected = NullPointerException.class)
	public void testReplaceAllWithNullAsValue2() {
		// must throw!
		CString.fromString("Pikachu vs Onyx.").replaceAll(CString.fromString("Marko"), null);
	}

	/**
	 * Test replace all with null as value3.
	 */
	@Test(expected = NullPointerException.class)
	public void testReplaceAllWithNullAsValue3() {
		// must throw!
		CString.fromString("Pikachu vs Onyx.").replaceAll(null, null);
	}
}