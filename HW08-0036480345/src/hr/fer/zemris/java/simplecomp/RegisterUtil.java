package hr.fer.zemris.java.simplecomp;

import java.text.DecimalFormat;

/**
 * A utility class offering several operations for getting data from a register,
 * depending on its type.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class RegisterUtil {

	/**
	 * The decimal formatter used to expand leading zeros to the given register
	 * descriptor.
	 */
	private static DecimalFormat df = new DecimalFormat("000000000000000000000000");

	/**
	 * Gets the register index.
	 *
	 * @param registerDescriptor the register descriptor
	 * @return the register index
	 */
	public static int getRegisterIndex(int registerDescriptor) {

		double bytes = Double.valueOf(Integer.toBinaryString(registerDescriptor));
		String formatted = df.format(bytes);
		return (short) Integer.parseInt(formatted.substring(17), 2);
	}

	/**
	 * Checks if the register descriptor implements indirect addressing.
	 *
	 * @param registerDescriptor the register descriptor
	 * @return true, if is indirect
	 */
	public static boolean isIndirect(int registerDescriptor) {
		double bytes = Double.valueOf(Integer.toBinaryString(registerDescriptor));
		String formatted = df.format(bytes);
		return (Integer.parseInt(formatted.substring(0, 1), 2) == 1);
	}

	/**
	 * Gets the register offset.
	 *
	 * @param registerDescriptor the register descriptor
	 * @return the register offset
	 */
	public static int getRegisterOffset(int registerDescriptor) {
		double bytes = Double.valueOf(Integer.toBinaryString(registerDescriptor));
		String formatted = df.format(bytes);
		return (short) Integer.parseInt(formatted.substring(1, 17), 2);
	}
}