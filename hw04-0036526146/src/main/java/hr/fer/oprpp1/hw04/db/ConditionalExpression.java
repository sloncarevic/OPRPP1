package hr.fer.oprpp1.hw04.db;

/**
 * Implementation of conditional expression
 *
 */
public class ConditionalExpression {

	private IFieldValueGetter fieldValueGetter;
	
	private String literal;
	
	private IComparisonOperator comparisonOperator;

	/**
	 * Constructor
	 * @param fieldValueGetter
	 * @param literal
	 * @param comparisonOperator
	 * @throws NullPointerException when attribute is null
	 */
	public ConditionalExpression(IFieldValueGetter fieldValueGetter, String literal, IComparisonOperator comparisonOperator) {
		if (fieldValueGetter == null || literal == null || comparisonOperator == null)
			throw new NullPointerException("Value can not be null!");
			
		this.fieldValueGetter = fieldValueGetter;
		this.literal = literal;
		this.comparisonOperator = comparisonOperator;
	}

	/**
	 * @return Returns field value attribute
	 */
	public IFieldValueGetter getFieldValueGetter() {
		return fieldValueGetter;
	}

	/**
	 * @return Returns string literal
	 */
	public String getLiteral() {
		return literal;
	}

	/**
	 * @return Returns comparison operator
	 */
	public IComparisonOperator getComparisonOperator() {
		return comparisonOperator;
	}
	
	
	
	
	
}
