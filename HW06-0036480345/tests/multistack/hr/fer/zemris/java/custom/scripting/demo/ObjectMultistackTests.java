package hr.fer.zemris.java.custom.scripting.demo;

import org.junit.Assert;
import org.junit.Test;

/**
 * There is a series of test of arithmetical operations. Since they are all based
 * on the same algorithm, there is no need to test each and every one of them
 * for every possible input combination, hence the low percentage of test
 * coverage.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class ObjectMultistackTests {

	/**
	 * Method: push(String name, ValueWrapper valueWrapper).
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testPush() throws Exception {
		ObjectMultistack multistack = new ObjectMultistack();

		multistack.push("Ivana", new ValueWrapper(5));
		multistack.push("Ivan", new ValueWrapper(3));
	}

	/**
	 * Method: pop(String name).
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testPop() throws Exception {
		ObjectMultistack multistack = new ObjectMultistack();

		multistack.push("Ivana", new ValueWrapper(5));
		multistack.push("Ivan", new ValueWrapper(3));

		Assert.assertEquals(5, multistack.pop("Ivana").getValue());

	}

	/**
	 * Method: isEmpty(String name).
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testIsEmpty() throws Exception {
		ObjectMultistack multistack = new ObjectMultistack();

		multistack.push("Ivana", new ValueWrapper(5));
		multistack.push("Ivan", new ValueWrapper(3));

		Assert.assertEquals(5, multistack.pop("Ivana").getValue());
		Assert.assertEquals(true, multistack.isEmpty("Ivana"));
	}

	/**
	 * Method: peek(String name).
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testPeek() throws Exception {
		ObjectMultistack multistack = new ObjectMultistack();

		multistack.push("Ivana", new ValueWrapper(5));
		multistack.push("Ivan", new ValueWrapper(3));

		Assert.assertEquals(5, multistack.peek("Ivana").getValue());
		Assert.assertEquals(false, multistack.isEmpty("Ivana"));
	}

	/**
	 * Method: getValue().
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testGetValue() throws Exception {
		ObjectMultistack multistack = new ObjectMultistack();

		multistack.push("Ivana", new ValueWrapper(5));
		multistack.push("Ivan", new ValueWrapper(3));

		Assert.assertEquals((Object) 5, multistack.peek("Ivana").getValue());

	}

	/**
	 * Method: setValue(Object value).
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testSetValue() throws Exception {
		ObjectMultistack multistack = new ObjectMultistack();

		multistack.push("Ivana", new ValueWrapper(5));
		multistack.push("Ivan", new ValueWrapper(3));
		multistack.peek("Ivana").setValue("6");

		Assert.assertEquals("6", multistack.peek("Ivana").getValue());
	}

	/**
	 * Method: increment(Object incValue).
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testIncrement() throws Exception {
		ValueWrapper value1 = new ValueWrapper("5");
		ValueWrapper value2 = new ValueWrapper(3);

		value1.increment(3);

		Assert.assertEquals(8, value1.getValue());
		Assert.assertEquals(3, value2.getValue());
	}

	/**
	 * Method: decrement(Object decValue).
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testDecrement() throws Exception {
		ValueWrapper value1 = new ValueWrapper(5);

		value1.decrement(3.0);

		Assert.assertEquals(2.0, value1.getValue());
	}

	/**
	 * Method: multiply(Object mulValue).
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testMultiply() throws Exception {
		ValueWrapper value1 = new ValueWrapper(5.0);

		value1.multiply(3.0);

		Assert.assertEquals(15.0, value1.getValue());
	}

	/**
	 * Method: divide(Object divValue).
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testDivide() throws Exception {
		ValueWrapper value1 = new ValueWrapper(15);

		value1.divide(3);

		Assert.assertEquals(5, value1.getValue());
	}

	/**
	 * Method: numCompare(Object withValue).
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testNumCompare() throws Exception {
		ValueWrapper value1 = new ValueWrapper(15);

		Assert.assertEquals(1, value1.numCompare("3"));
		Assert.assertEquals(0, value1.numCompare(15.0));
		Assert.assertEquals(-1, value1.numCompare(20));
	}

}
