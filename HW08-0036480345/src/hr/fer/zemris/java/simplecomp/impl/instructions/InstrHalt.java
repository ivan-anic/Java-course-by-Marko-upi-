package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * An implementation of the {@linkplain Instruction} interface, terminates the
 * computer.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class InstrHalt implements Instruction {

	/**
	 * Instantiates a new <i>halt</i> operation.
	 *
	 * @param arguments
	 *            implementations of {@linkplain InstructionArgument}, arguments
	 *            which will be used in the operation.
	 */
	public InstrHalt(List<InstructionArgument> arguments) {
		if (arguments.size() != 0) {
			throw new IllegalArgumentException("No arguments expected!");
		}
	}

	@Override
	public boolean execute(Computer computer) {
		return true;
	}

}
