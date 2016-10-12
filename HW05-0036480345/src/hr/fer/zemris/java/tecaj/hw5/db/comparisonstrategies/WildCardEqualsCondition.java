package hr.fer.zemris.java.tecaj.hw5.db.comparisonstrategies;

/**
 * Represents an implementation of the {@link IComparisonOperator} interface. Checks whether
 * the "LIKE" operator is true or false.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class WildCardEqualsCondition implements IComparisonOperator {

	@Override
	public boolean satisfied(String value1, String value2) {
		value2.replace("*", ".*");
		return value1.matches(value2);
	}

}
