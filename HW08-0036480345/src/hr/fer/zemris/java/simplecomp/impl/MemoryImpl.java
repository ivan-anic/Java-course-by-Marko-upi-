package hr.fer.zemris.java.simplecomp.impl;

import java.util.HashMap;
import java.util.Map;

import hr.fer.zemris.java.simplecomp.models.Memory;

/**
 * An implementation of the {@linkplain Memory} interface. Represents the memory
 * of the {@linkplain ComputerImpl}.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class MemoryImpl implements Memory {

	/** The data kept in this memory. */
	private Map<Integer, Object> memory;

	/**
	 * Instantiates a new memory implementation.
	 *
	 * @param size the desired memory size
	 */
	public MemoryImpl(int size) {
		super();
		this.memory = new HashMap<Integer, Object>(size);
	}

	@Override
	public void setLocation(int location, Object value) {
		memory.put(location, value);
	}

	@Override
	public Object getLocation(int location) {
		return memory.get(location);
	}

}
