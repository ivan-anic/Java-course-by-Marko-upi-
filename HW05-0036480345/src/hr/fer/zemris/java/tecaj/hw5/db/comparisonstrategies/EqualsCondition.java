package hr.fer.zemris.java.tecaj.hw5.db.comparisonstrategies;

/**
 * Represents an implementation of the {@link IComparisonOperator} interface. Checks whether
 * the equals operator is true or false.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class EqualsCondition implements IComparisonOperator {

	@Override
	public boolean satisfied(String value1, String value2) {
		return value1.equals(value2);
	}

}
