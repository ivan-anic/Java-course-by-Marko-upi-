package hr.fer.zemris.java.custom.collections.demo;

import hr.fer.zemris.java.custom.collections.ObjectStack;

/**
 * A method which purpose is to test the stack-like collection designed in the
 * {@link ObjectStack} class.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class StackDemo {

	/**
	 * @param arg
	 *            - the string to be evaluated
	 * @return returns {@code true} if the argument if a number
	 */
	private static boolean isNumeric(String arg) {
		return arg.matches("-?\\d+");
	}

	/**
	 * Performs the wanted operation (op) on the given arguments.
	 * 
	 * @param op
	 *            - the desired operation
	 * @param argument1
	 *            - the first operand
	 * @param argument2
	 *            - the second operand
	 * @return - the result of the arithmetic operation performed on the given
	 *         arguments
	 */
	private static int operate(String op, Object argument1, Object argument2) {
		int arg1 = (int) argument2;
		int arg2 = (int) argument1;
		switch (op) {
		case ("+"):
			return arg1 + arg2;
		case ("-"):
			return arg1 - arg2;
		case ("*"):
			return arg1 * arg2;
		case ("/"):
			if (arg2 == 0){
				System.out.println("Can't divide by zero.");
				System.exit(1);
			}
			return arg1 / arg2;
		case ("%"):
			return arg1 % arg2;
		default:
			System.out.println("Invalid operation.");
			System.exit(1);
			return 0;
		}

	}

	/**
	 * The main method, used for testing.
	 * 
	 * @param args
	 *            - the input arguments (not used)
	 * 
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Invalid number of arguments.");
			System.exit(1);
		}
		String[] argArray = args[0].split(" ");
		ObjectStack stack = new ObjectStack();
		for (String node : argArray) {
			if (isNumeric(node)) {
				stack.push(Integer.valueOf(node));
			} else {
				stack.push(operate(node, stack.pop(), stack.pop()));
			}
		}
		if (stack.size() != 1) {
			System.out.println("Invalid number of arguments. Please re-check.");
		} else {
			System.out.println("Expression evaluates to " + stack.pop() + ".");
		}
	}
}
