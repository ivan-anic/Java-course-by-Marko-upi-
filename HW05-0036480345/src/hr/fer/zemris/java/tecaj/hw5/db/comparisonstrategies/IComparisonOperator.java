package hr.fer.zemris.java.tecaj.hw5.db.comparisonstrategies;

/**
 * An interface representing an abstraction for various logical comparison
 * operators.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public interface IComparisonOperator {

	/**
	 * Checks whether the operator condition is satisfied.
	 * @param value1 the first parameter to be compared
	 * @param value2 the second parameter to be compared
	 * @return true of the condition is satisfied.
	 */
	public boolean satisfied(String value1, String value2);
}
