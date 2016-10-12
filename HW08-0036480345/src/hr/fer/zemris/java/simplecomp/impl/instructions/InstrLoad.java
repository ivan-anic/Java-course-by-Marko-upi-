package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.RegisterUtil;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * An implementation of the {@linkplain Instruction} interface, loads the given
 * data into the desired register.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class InstrLoad implements Instruction {

	/**
	 * The index of the register given in the first argument, where the result
	 * will be stored.
	 */
	private int register1;
	/**
	 * The index of the register given in the second argument, the data to be
	 * loaded.
	 */
	private int memIndex;

	/**
	 * Instantiates a new load operation.
	 *
	 * @param arguments
	 *            implementations of {@linkplain InstructionArgument}, arguments
	 *            which will be used in the operation.
	 */
	public InstrLoad(List<InstructionArgument> arguments) {
		if (arguments.size() != 2) {
			throw new IllegalArgumentException("Expected 2 arguments!");
		}

		if (!arguments.get(0).isRegister()
		// RegisterUtil.isIndirect((Integer) arguments.get(0).getValue())
		)// RegisterUtil.isIndirect((Integer) arguments.get(1).getValue()))
		{
			throw new IllegalArgumentException(
					"Type mismatch for arguments!");
		}
		this.register1 = RegisterUtil.getRegisterIndex((Integer) arguments.get(0).getValue());
		this.memIndex = (Integer) arguments.get(1).getValue();
	}

	public boolean execute(Computer computer) {
		Object value = computer.getMemory().getLocation(memIndex);
		computer.getRegisters().setRegisterValue(register1, value);
		return false;
	}
}