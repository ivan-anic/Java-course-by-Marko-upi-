package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.RegisterUtil;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * An implementation of the {@linkplain Instruction} interface, checks whether
 * the arguments are equal.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class InstrTestEquals implements Instruction {

	/**
	 * The index of the register given in the first argument,the data to be
	 * compared.
	 */
	private int register1;
	/**
	 * The index of the register given in the second argument, the data to be
	 * compared.
	 */
	private int register2;

	/**
	 * Instantiates a new test operation.
	 *
	 * @param arguments
	 *            implementations of {@linkplain InstructionArgument}, arguments
	 *            which will be used in the operation.
	 */
	public InstrTestEquals(List<InstructionArgument> arguments) {
		if (arguments.size() != 2) {
			throw new IllegalArgumentException("Expected 2 arguments!");
		}

		if (!arguments.get(0).isRegister() || !arguments.get(0).isRegister()) {
			throw new IllegalArgumentException(
					"Type mismatch for arguments, must be registers!");
		}
		this.register1 = RegisterUtil.getRegisterIndex((Integer) arguments.get(0).getValue());
		this.register2 = RegisterUtil.getRegisterIndex((Integer) arguments.get(1).getValue());
	}

	public boolean execute(Computer computer) {
		computer.getRegisters().setFlag(computer.getRegisters().getRegisterValue(register1)
				.equals(computer.getRegisters().getRegisterValue(register2)));
		return false;
	}
}