package hr.fer.zemris.java.tecaj.hw5.db.filters;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import hr.fer.zemris.java.tecaj.hw5.db.ConditionalExpression;
import hr.fer.zemris.java.tecaj.hw5.db.StudentRecord;
import hr.fer.zemris.java.tecaj.hw5.db.comparisonstrategies.EqualsCondition;
import hr.fer.zemris.java.tecaj.hw5.db.comparisonstrategies.GreaterThanCondition;
import hr.fer.zemris.java.tecaj.hw5.db.comparisonstrategies.GreaterThanEqualsCondition;
import hr.fer.zemris.java.tecaj.hw5.db.comparisonstrategies.IComparisonOperator;
import hr.fer.zemris.java.tecaj.hw5.db.comparisonstrategies.LesserThanCondition;
import hr.fer.zemris.java.tecaj.hw5.db.comparisonstrategies.LesserThanEqualsCondition;
import hr.fer.zemris.java.tecaj.hw5.db.comparisonstrategies.NotEqualsCondition;
import hr.fer.zemris.java.tecaj.hw5.db.comparisonstrategies.WildCardEqualsCondition;
import hr.fer.zemris.java.tecaj.hw5.db.fieldstrategies.FirstNameFieldGetter;
import hr.fer.zemris.java.tecaj.hw5.db.fieldstrategies.IFieldValueGetter;
import hr.fer.zemris.java.tecaj.hw5.db.fieldstrategies.JMBAGFieldGetter;
import hr.fer.zemris.java.tecaj.hw5.db.fieldstrategies.LastNameFieldGetter;

/**
 * Represents an implementation of the {@link IFilter} interface.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class QueryFilter implements IFilter {

	/**
	 * A regular expression representing the valid operators.
	 */
	private final String operator = "(<=|>=|<|>|!=|=|LIKE)";

	/**
	 * A list which contains the valid keywords.
	 */
	private final List<String> validWords = Arrays.asList("lastname", "firstname", "jmbag");
	/**
	 * A list which contains the valid operators.
	 */
	private final List<String> validOp = Arrays.asList("<=", ">=", "<", ">", "!=", "=", "LIKE");
	/**
	 * The list which will be populated with instances of
	 * {@link ConditionalExpression}.
	 */
	private List<ConditionalExpression> expressions = new ArrayList<>();

	/**
	 * Instantiates a new query filter.
	 *
	 * @param query
	 *            the query entered by the user, in string format.
	 * @throws Exception
	 *             if an error occurred.
	 */
	public QueryFilter(String query) throws Exception {

		// String re = "(?!" + operator + "\\s*\")AND";
		String re = "AND";

		String[] parts = query.split(re);

		for (String s : parts) {
			expressions.add(parse(s));
		}
	}

	@Override
	public boolean accepts(StudentRecord record) {

		for (ConditionalExpression exp : expressions) {
			if ((exp.getOperator().satisfied(exp.getField().get(record), exp.getLiteral()))) {
				return true;
			}
		}
		return false;

	}

	/**
	 * Parses the given query and returns a new {@link ConditionalExpression} if
	 * the query is correctly set.
	 * 
	 * @param query
	 *            the user query
	 * @return instance of {@link ConditionalExpression}
	 * @throws Exception
	 *             if an error occurred.
	 */
	private ConditionalExpression parse(String query) throws Exception {

		String par = null;
		String op = null;
		String par2 = null;

		String re = "\\s+" + "(\\p{L}+)" + "\\s*" + operator + "\\s*" + "\"(\\p{L}+|\\d+|.*)\"" + "\\s*";
		Pattern p = Pattern.compile(re, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

		Matcher match = p.matcher(query);

		if (match.find()) {
			par = match.group(1);
			op = match.group(2);
			par2 = match.group(3);
		}

		IFieldValueGetter get = null;
		IComparisonOperator oper = null;

		if (validWords.contains(par.toLowerCase())) {
			switch (par.toLowerCase()) {
			case "firstname":
				get = new FirstNameFieldGetter();
				break;
			case "lastname":
				get = new LastNameFieldGetter();
				break;
			case "jmbag":
				get = new JMBAGFieldGetter();
				break;
			}
		} else {
			throw new Exception("Invalid keyword.");
		}
		if (validOp.contains(op)) {
			switch (op) {
			case "<=":
				oper = new LesserThanEqualsCondition();
				break;
			case ">=":
				oper = new GreaterThanEqualsCondition();
				break;
			case "<":
				oper = new LesserThanCondition();
				break;
			case ">":
				oper = new GreaterThanCondition();
				break;
			case "!=":
				oper = new NotEqualsCondition();
				break;
			case "=":
				oper = new EqualsCondition();
				break;
			case "LIKE":
				oper = new WildCardEqualsCondition();
				break;
			}
		} else {
			throw new Exception("Invalid operator.");
		}

		return new ConditionalExpression(get, par2, oper);
	}
}
