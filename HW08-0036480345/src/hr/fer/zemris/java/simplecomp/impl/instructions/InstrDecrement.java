package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;
import hr.fer.zemris.java.simplecomp.RegisterUtil;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * An implementation of the {@linkplain Instruction} interface, decrements the
 * given register.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class InstrDecrement implements Instruction {

	/**
	 * The index of the register to be incremented.
	 */
	private int register1;

	/**
	 * Instantiates a new decremet operation.
	 *
	 * @param arguments
	 *            implementations of {@linkplain InstructionArgument}, arguments
	 *            which will be used in the operation.
	 */

	public InstrDecrement(List<InstructionArgument> arguments) {
		if (arguments.size() != 1) {
			throw new IllegalArgumentException("Expected 1 argument!");
		}

		if (!arguments.get(0).isRegister() ||
				RegisterUtil.isIndirect((Integer) arguments.get(0).getValue())) {
			throw new IllegalArgumentException(
					"Type mismatch for arguments!");
		}

		this.register1 = RegisterUtil.getRegisterIndex((Integer) arguments.get(0).getValue());
	}

	public boolean execute(Computer computer) {
		computer.getRegisters().setRegisterValue(
				register1, ((Integer) computer.getRegisters().getRegisterValue(register1) - 1));
		return false;
	}
}
