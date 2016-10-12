package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * An implementation of the {@linkplain Instruction} interface, jumps to the
 * given point of the code, and continues execution from that point on.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class InstrJump implements Instruction {

	/**
	 * The new value of the program counter, where the program will continue its
	 * execution.
	 */
	private int newPc;

	/**
	 * Instantiates a new jump operation.
	 *
	 * @param arguments
	 *            implementations of {@linkplain InstructionArgument}, arguments
	 *            which will be used in the operation.
	 */
	public InstrJump(List<InstructionArgument> arguments) {
		if (arguments.size() != 1) {
			throw new IllegalArgumentException("Expected 1 argument!");
		}

		if (arguments.get(0).isNumber()) {
			this.newPc = ((Integer) arguments.get(0).getValue());
		} else {
			throw new IllegalArgumentException("Type mismatch for input arguments!");
		}
	}

	public boolean execute(Computer computer) {
		computer.getRegisters().setProgramCounter(newPc);
		return false;
	}
}
