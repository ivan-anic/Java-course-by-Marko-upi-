package hr.fer.zemris.java.tecaj.hw5.db.fieldstrategies;

import hr.fer.zemris.java.tecaj.hw5.db.StudentRecord;

/**
 * An implementation of {@link IFieldValueGetter} interface. Used for getting
 * the last name of the student.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class LastNameFieldGetter implements IFieldValueGetter {

	@Override
	public String get(StudentRecord record) {
		return record.getLastName();
	}

}
