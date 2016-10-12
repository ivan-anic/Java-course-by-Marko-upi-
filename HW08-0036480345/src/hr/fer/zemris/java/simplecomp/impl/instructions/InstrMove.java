package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.RegisterUtil;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * An implementation of the {@linkplain Instruction} interface, moves the given
 * data to the desired location.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class InstrMove implements Instruction {

	/**
	 * The index of the register given in the first argument, where the result
	 * will be stored.
	 */
	private int destinationRegDescriptor;
	/**
	 * The index of the register given in the second argument, the data to be
	 * moved.
	 */
	private int sourceRegDecriptor;
	/** Marks that the source is a constant. */
	private int locationConst;

	/**
	 * Instantiates a new move operation.
	 *
	 * @param arguments
	 *            implementations of {@linkplain InstructionArgument}, arguments
	 *            which will be used in the operation.
	 */
	public InstrMove(List<InstructionArgument> arguments) {
		if (arguments.size() != 2) {
			throw new IllegalArgumentException("Expected 2 arguments!");
		}

		if (arguments.get(1).isNumber()) {
			locationConst = (Integer) arguments.get(1).getValue();
		} else {
			sourceRegDecriptor  = (Integer) arguments.get(1).getValue();
		}
		destinationRegDescriptor  = (Integer) arguments.get(0).getValue();
	}

	@Override
	public boolean execute(Computer computer) {

		int destRegIndex = RegisterUtil.getRegisterIndex(destinationRegDescriptor);
		int srcRegIndex = RegisterUtil.getRegisterIndex(sourceRegDecriptor);


		if (locationConst != 0) {
			computer.getRegisters().setRegisterValue(destRegIndex, locationConst);
			return false;
		}
		Object newValue = computer.getRegisters().getRegisterValue(srcRegIndex);
		if (RegisterUtil.isIndirect(sourceRegDecriptor)) {
			int loc = (Integer) newValue + RegisterUtil.getRegisterOffset(sourceRegDecriptor);
			newValue = computer.getMemory().getLocation(loc);
		}

		if (RegisterUtil.isIndirect(destinationRegDescriptor)) {
			int loc = (Integer) computer.getRegisters().getRegisterValue(destRegIndex)
					+ RegisterUtil.getRegisterOffset(destinationRegDescriptor);
			computer.getMemory().setLocation(loc, newValue);
		} else {
			computer.getRegisters().setRegisterValue(destRegIndex, newValue);
		}

		return false;
	}
}