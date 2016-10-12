package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;
import hr.fer.zemris.java.simplecomp.models.Registers;

/**
 * An implementation of the {@linkplain Instruction} interface, performs the
 * launch of a given subroutine.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class InstrCall implements Instruction {

	/**
	 * The address of stack pointer.
	 */
	private int stackPtrAdress;

	/**
	 * The address of the subroutine which will be executed.
	 */
	private int address;

	/**
	 * Instantiates a new call operation.
	 *
	 * @param arguments
	 *            implementations of {@linkplain InstructionArgument}, arguments
	 *            which will be used in the operation.
	 */

	public InstrCall(List<InstructionArgument> arguments) {
		if (arguments.size() != 1) {
			throw new IllegalArgumentException("Expected 1 argument!");
		}

		if (!arguments.get(0).isNumber()) {
			throw new IllegalArgumentException(
					"Type mismatch for arguments!");
		}

		stackPtrAdress = Registers.STACK_REGISTER_INDEX;
		address = (Integer) arguments.get(0).getValue();
	}

	@Override
	public boolean execute(Computer computer) {
		stackPtrAdress = (Integer) computer.getRegisters().getRegisterValue(stackPtrAdress);
		Integer pcCnt = computer.getRegisters().getProgramCounter();

		computer.getMemory().setLocation(stackPtrAdress--, pcCnt);
		
		computer.getRegisters().setRegisterValue(Registers.STACK_REGISTER_INDEX, stackPtrAdress);

		computer.getRegisters().setProgramCounter(address);
		return false;
	}
}