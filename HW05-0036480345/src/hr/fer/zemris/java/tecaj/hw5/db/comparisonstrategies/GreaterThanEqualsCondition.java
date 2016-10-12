package hr.fer.zemris.java.tecaj.hw5.db.comparisonstrategies;

/**
 * Represents an implementation of the {@link IComparisonOperator} interface. Checks whether
 * the "greater than or equals" operator is true or false.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class GreaterThanEqualsCondition implements IComparisonOperator {

	@Override
	public boolean satisfied(String value1, String value2) {
		return value1.compareTo(value2)>=0;
	}

}
