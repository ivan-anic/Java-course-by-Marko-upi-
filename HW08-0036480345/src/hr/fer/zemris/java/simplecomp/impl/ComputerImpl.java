package hr.fer.zemris.java.simplecomp.impl;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Memory;
import hr.fer.zemris.java.simplecomp.models.Registers;

/**
 * An implementation if the {@linkplain Computer} interface.
 *
 * @author Ivan Anić
 * @version 1.0
 */
public class ComputerImpl implements Computer{
	
	/** The registers kept in this computer. */
	Registers registers;
	/** The memory of this computer. */
	Memory memory;
	
	
	/**
	 * Instantiates a new computer implementation.
	 *
	 * @param memSize the desired memory size
	 * @param numReg the desired number of registers.
	 */
	public ComputerImpl(int memSize, int numReg) {
		this.registers = new RegistersImpl(numReg);
		this.memory = new MemoryImpl(memSize);
	}

	@Override
	public Registers getRegisters() {
		return registers;
	}

	@Override
	public Memory getMemory() {
		return memory;
	}

}
