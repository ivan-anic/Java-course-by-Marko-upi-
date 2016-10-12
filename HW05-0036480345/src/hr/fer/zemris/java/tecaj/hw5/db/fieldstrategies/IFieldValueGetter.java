package hr.fer.zemris.java.tecaj.hw5.db.fieldstrategies;

import hr.fer.zemris.java.tecaj.hw5.db.StudentRecord;

/**
 * An abstraction for IFieldValueGetter strategy.
 */
public interface IFieldValueGetter {
	
	/**
	 * Gets the {@link StudentRecord}.
	 *
	 * @param record the record
	 * @return the string
	 */
	public String get(StudentRecord record);
}
