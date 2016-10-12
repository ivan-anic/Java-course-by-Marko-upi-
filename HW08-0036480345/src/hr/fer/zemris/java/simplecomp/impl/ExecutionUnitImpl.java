package hr.fer.zemris.java.simplecomp.impl;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.ExecutionUnit;
import hr.fer.zemris.java.simplecomp.models.Instruction;

/**
 * An implementation of {@linkplain ExecutionUnit} interface. Executes the given
 * instructions.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class ExecutionUnitImpl implements ExecutionUnit {

	@Override
	public boolean go(Computer computer) {
		computer.getRegisters().setProgramCounter(0);
		while (true) {
			Instruction instr = (Instruction) computer.getMemory().getLocation(
					computer.getRegisters().getProgramCounter());
			computer.getRegisters().incrementProgramCounter();
			if (instr.execute(computer)) {
				break;
			}
		}
		return true;
	}

}