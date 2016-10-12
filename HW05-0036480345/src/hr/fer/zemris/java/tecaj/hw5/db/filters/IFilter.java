package hr.fer.zemris.java.tecaj.hw5.db.filters;

import hr.fer.zemris.java.tecaj.hw5.db.StudentRecord;

/**
 * An interface representing an abstraction for a filter which checks whether
 * the StudentRecord matches the given input.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public interface IFilter {

	/**
	 * @param record the {@link StudentRecord} to be checked
	 * @return true if the record matches the filter parameters
	 */
	public boolean accepts(StudentRecord record);
}
