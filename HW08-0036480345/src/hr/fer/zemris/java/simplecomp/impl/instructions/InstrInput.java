package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * An implementation of the {@linkplain Instruction} interface, reads a number
 * from the standard input, and saves it to the desired memory location.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class InstrInput implements Instruction {

	/**
	 * The location where the input string will be saved.
	 */
	private int location;

	/**
	 * Instantiates a new input operation.
	 *
	 * @param arguments
	 *            implementations of {@linkplain InstructionArgument}, arguments
	 *            which will be used in the operation.
	 */
	public InstrInput(List<InstructionArgument> arguments) {
		if (arguments.size() != 1) {
			throw new IllegalArgumentException("Expected 1 argument!");
		}
		location = (Integer) arguments.get(0).getValue();

	}

	public boolean execute(Computer computer) {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String input = null;

		try {
			input = br.readLine();
		} catch (IOException e) {
			computer.getRegisters().setFlag(false);
		}

		computer.getRegisters().setFlag(true);

		computer.getMemory().setLocation(location, Integer.parseInt(input));
		return false;
	}
}
