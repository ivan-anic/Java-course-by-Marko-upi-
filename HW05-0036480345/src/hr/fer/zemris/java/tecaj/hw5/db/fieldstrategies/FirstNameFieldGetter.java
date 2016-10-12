package hr.fer.zemris.java.tecaj.hw5.db.fieldstrategies;

import hr.fer.zemris.java.tecaj.hw5.db.StudentRecord;

/**
 * An implementation of {@link IFieldValueGetter} interface. Used for getting
 * the first name of the student.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class FirstNameFieldGetter implements IFieldValueGetter {

	@Override
	public String get(StudentRecord record) {
		return record.getFirstName();
	}

}
