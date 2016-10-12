package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;
import hr.fer.zemris.java.simplecomp.models.Registers;

/**
 * An implementation of the {@linkplain Instruction} interface, performs the
 * return from the current given subroutine.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class InstrRet implements Instruction {

	/**
	 * The address of stack pointer.
	 */
	private int stackPtrAddress;

	/**
	 * Instantiates a new return operation.
	 *
	 * @param arguments
	 *            implementations of {@linkplain InstructionArgument}, arguments
	 *            which will be used in the operation.
	 */
	public InstrRet(List<InstructionArgument> arguments) {
		if (arguments.size() != 0) {
			throw new IllegalArgumentException("Expected no arguments!");
		}

		stackPtrAddress = Registers.STACK_REGISTER_INDEX;
	}

	@Override
	public boolean execute(Computer computer) {
		stackPtrAddress = (Integer)computer.getRegisters().getRegisterValue(stackPtrAddress);
		Object value = computer.getMemory().getLocation(stackPtrAddress);
		
		computer.getMemory().setLocation(computer.getRegisters().getProgramCounter(), value);

		computer.getRegisters().setProgramCounter((Integer) computer.getMemory().getLocation(++stackPtrAddress));
		computer.getRegisters().setRegisterValue(Registers.STACK_REGISTER_INDEX, --stackPtrAddress);

		return false;
	}
}

/**
 * S vrha stoga skida adresu i postavlja je kao vrijednost registra PC (program
 * counter)
 */