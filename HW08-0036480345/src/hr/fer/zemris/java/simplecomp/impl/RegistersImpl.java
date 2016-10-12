package hr.fer.zemris.java.simplecomp.impl;

import java.util.HashMap;
import java.util.Map;

import hr.fer.zemris.java.simplecomp.models.Registers;

/**
 * An implementation of the {@linkplain Registers} interface. Represents the
 * registers of the {@linkplain ComputerImpl}.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class RegistersImpl implements Registers {

	/** The program counter, points to the next command which will be executed. */
	private int programCounter;
	/** The flag, used for testing various conditions.*/
	private boolean flag;
	/** The registers kept in thie {@linkplain ComputerImpl}.*/
	private Map<Integer, Object> registers;

	/**
	 * Instantiates a new registers implementation.
	 *
	 * @param regsLen the desired number of registers
	 */
	public RegistersImpl(int regsLen) {
		super();
		if (regsLen <= Registers.STACK_REGISTER_INDEX) {
			throw new IllegalArgumentException(
					"Must be at least the value of the stack pointer, " + Registers.STACK_REGISTER_INDEX);
		}
		this.registers = new HashMap<Integer, Object>(regsLen);
	}

	@Override
	public Object getRegisterValue(int index) {
		return registers.get(index);
	}

	@Override
	public void setRegisterValue(int index, Object value) {
		registers.put(index, value);
	}

	@Override
	public int getProgramCounter() {
		return programCounter;
	}

	@Override
	public void setProgramCounter(int value) {
		programCounter = value;
	}

	@Override
	public void incrementProgramCounter() {
		programCounter++;
	}

	@Override
	public boolean getFlag() {
		return flag;
	}

	@Override
	public void setFlag(boolean value) {
		flag = value;
	}

}
