package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.RegisterUtil;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;
import hr.fer.zemris.java.simplecomp.models.Registers;

/**
 * An implementation of the {@linkplain Instruction} interface, pops the desired
 * value from the stack.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class InstrPop implements Instruction {

	/**
	 * The address of stack pointer.
	 */
	private int stackPtrAddress;

	/**
	 * The index of the register given in the first argument, where the popped
	 * data will be stored.
	 */
	private int register;

	/**
	 * Instantiates a new pop operation.
	 *
	 * @param arguments
	 *            implementations of {@linkplain InstructionArgument}, arguments
	 *            which will be used in the operation.
	 */
	public InstrPop(List<InstructionArgument> arguments) {
		if (arguments.size() != 1) {
			throw new IllegalArgumentException("Expected 1 argument!");
		}

		if (!arguments.get(0).isRegister()) {
			throw new IllegalArgumentException(
					"Type mismatch for arguments!");
		}

		stackPtrAddress = Registers.STACK_REGISTER_INDEX;
		register = RegisterUtil.getRegisterIndex((Integer) arguments.get(0).getValue());
	}

	@Override
	public boolean execute(Computer computer) {
		stackPtrAddress = (Integer) computer.getRegisters().getRegisterValue(stackPtrAddress);
		Object value = (Integer) computer.getRegisters().getRegisterValue(++stackPtrAddress);

		computer.getRegisters().setRegisterValue(register, value);

		computer.getRegisters().setRegisterValue(Registers.STACK_REGISTER_INDEX, ++stackPtrAddress);

		return false;
	}

}
