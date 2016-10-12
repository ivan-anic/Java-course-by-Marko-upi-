package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.RegisterUtil;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * An implementation of the {@linkplain Instruction} interface, outputs the data
 * contained in the register to the standard output.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class InstrEcho implements Instruction {

	/**
	 * The index of the register given in the first argument, the data to be
	 * printed to the standard output.
	 */
	private int register1;

	/**
	 * The offset, used in case of indirect addressing.
	 */
	private int offset;

	/**
	 * Instantiates a new echo operation.
	 *
	 * @param arguments
	 *            implementations of {@linkplain InstructionArgument}, arguments
	 *            which will be used in the operation.
	 */

	public InstrEcho(List<InstructionArgument> arguments) {
		if (arguments.size() != 1) {
			throw new IllegalArgumentException("Expected 1 argument!");
		}

		if (arguments.size() == 2 && arguments.get(1).isNumber()) {
			this.offset = RegisterUtil.getRegisterOffset((Integer) arguments.get(1).getValue());
		}

		if (arguments.get(0).isRegister()) {
			this.register1 = RegisterUtil.getRegisterIndex((Integer) arguments.get(0).getValue());
		} else {
			throw new IllegalArgumentException("Type mismatch for input arguments!");
		}
	}

	public boolean execute(Computer computer) {
		Object value = (offset == 0) ? computer.getRegisters().getRegisterValue(register1)
				: computer.getRegisters().getRegisterValue(register1 + offset);
		System.out.print(value);
		return false;
	}
}
