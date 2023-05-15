package hr.fer.oprpp1.hw04.db;

import java.util.List;

/**
 * Implementation of query filter from conditional expressions
 *
 */
public class QueryFilter implements IFilter {
	
	private List<ConditionalExpression> conditionalExpressions;
	
	/**
	 * Constructor
	 * @param conditionalExpressions
	 * @throws NullPointerException
	 */
	public QueryFilter(List<ConditionalExpression> conditionalExpressions) {
		if (conditionalExpressions == null)
			throw new NullPointerException("Conditional expressions list can not be null!");
		this.conditionalExpressions = conditionalExpressions;
	}

	@Override
	public boolean accepts(StudentRecord record) {
		if (record == null)
			throw new NullPointerException("Student record can not be null!");
		
		for (var ce : this.conditionalExpressions) {
			if (!ce.getComparisonOperator()
					.satisfied(ce.getFieldValueGetter().get(record),
							ce.getLiteral()))
				return false;
		}
		
		return true;
	}

}
