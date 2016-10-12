package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;
import hr.fer.zemris.java.simplecomp.RegisterUtil;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * An implementation of the {@linkplain Instruction} interface, performs the
 * arithmetical addition.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class InstrAdd implements Instruction {
	/**
	 * The index of the register given in the first argument, where the result
	 * will be stored.
	 */
	private int register1;
	/**
	 * The index of the register given in the second argument.
	 */
	private int register2;
	/**
	 * The index of the register given in the third argument.
	 */
	private int register3;

	/**
	 * Instantiates a new addition operation.
	 *
	 * @param arguments
	 *            implementations of {@linkplain InstructionArgument}, arguments
	 *            which will be used in the operation.
	 */
	public InstrAdd(List<InstructionArgument> arguments) {
		if (arguments.size() != 3) {
			throw new IllegalArgumentException("Expected 3 arguments!");
		}
		for (int i = 0; i < 3; i++) {
			if (!arguments.get(i).isRegister() ||
					RegisterUtil.isIndirect((Integer) arguments.get(i).getValue())) {
				throw new IllegalArgumentException(
						"Type mismatch for argument " + i + "!");
			}
		}
		this.register1 = RegisterUtil.getRegisterIndex((Integer) arguments.get(0).getValue());
		this.register2 = RegisterUtil.getRegisterIndex((Integer) arguments.get(1).getValue());
		this.register3 = RegisterUtil.getRegisterIndex((Integer) arguments.get(2).getValue());
	}

	public boolean execute(Computer computer) {
		computer.getRegisters().setRegisterValue(
				register1,
				Integer.valueOf((Integer) computer.getRegisters().getRegisterValue(register2)
						+ (Integer) computer.getRegisters().getRegisterValue(register3)));
		return false;
	}
}
