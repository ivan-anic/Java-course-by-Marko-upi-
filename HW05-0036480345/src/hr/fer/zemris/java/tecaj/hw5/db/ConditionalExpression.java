package hr.fer.zemris.java.tecaj.hw5.db;

import hr.fer.zemris.java.tecaj.hw5.db.comparisonstrategies.IComparisonOperator;
import hr.fer.zemris.java.tecaj.hw5.db.fieldstrategies.IFieldValueGetter;

/**
 * Represents an instance of a conditional expression. It consists of three parts:
 * <ul>
 * <li> an implementation of the {@link IFieldValueGetter} interface, which serves as a getter for the getter specified by the keyword entered by the user. </li>
 * <li> a string literal, which represents a value which is to be searched for in the database.</li>
 * <li> an implementation of the {@link IComparisonOperator} interface, which checks whether the operators conditions are met.</li>
 * </ul>
 * @author Ivan Anić
 * @version 1.0
 */
public class ConditionalExpression {
	/** @see ConditionalExpression*/
	private IFieldValueGetter field;
	/** @see ConditionalExpression*/
	private String literal;
	/** @see ConditionalExpression*/
	private IComparisonOperator operator;

	/**
	 * Instantiates a new conditional expression.
	 * 
	 * @param field the field used for getting the value
	 * @param literal the literal the value to be compared
	 * @param operator the operator which decides the comparison
	 * @see ConditionalExpression
	 */
	public ConditionalExpression(IFieldValueGetter field, String literal, IComparisonOperator operator) {
			this.field = field;
			this.literal = literal;
			this.operator = operator;
		}

	/**
	 * Gets the field.
	 *
	 * @return the element of the {@link StudentRecord} to be returned
	 */
	public IFieldValueGetter getField() {
			return field;
		}

	/**
	 * Gets the literal.
	 *
	 * @return the literal the value which is being compared to the  {@link StudentRecord} value.
	 */
	public String getLiteral() {
			return literal;
		}

	/**
	 * Gets the operator which decides the comparison being performed.
	 *
	 * @return the operator performing the operation
	 */
	public IComparisonOperator getOperator() {
			return operator;
		}
}
