package hr.fer.zemris.java.custom.scripting.demo;

/**
 * Represents a demonstration of the functionalities of this package. <br>
 * Classes demonstrated:
 * <ul>
 * <li>{@linkplain ObjectMultistack}</li>
 * <li>{@linkplain ValueWrapper}</li>
 * </ul>
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class ObjectMultistackDemo {

	/**
	 * The main method.
	 *
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {
		ObjectMultistack multistack = new ObjectMultistack();

		ValueWrapper year = new ValueWrapper(Integer.valueOf(2000));
		multistack.push("year", year);
		ValueWrapper price = new ValueWrapper(200.51);
		multistack.push("price", price);
		System.out.println("Current value for year: "
				+ multistack.peek("year").getValue());
		System.out.println("Current value for price: "
				+ multistack.peek("price").getValue());
		multistack.push("year", new ValueWrapper(Integer.valueOf(1900)));
		System.out.println("Current value for year: "
				+ multistack.peek("year").getValue());
		multistack.peek("year").setValue(
				((Integer) multistack.peek("year").getValue()).intValue() + 50);
		System.out.println("Current value for year: "
				+ multistack.peek("year").getValue());
		multistack.pop("year");
		System.out.println("Current value for year: "
				+ multistack.peek("year").getValue());
		multistack.peek("year").increment("5");
		System.out.println("Current value for year: "
				+ multistack.peek("year").getValue());
		multistack.peek("year").increment(5);
		System.out.println("Current value for year: "
				+ multistack.peek("year").getValue());
		multistack.peek("year").increment(5.0);
		System.out.println("Current value for year: "
				+ multistack.peek("year").getValue());
	}
}