package hr.fer.oprpp1.hw04.db;

import java.util.ArrayList;
import java.util.List;

import hr.fer.oprpp1.hw04.db.lexer.QueryLexer;
import hr.fer.oprpp1.hw04.db.lexer.QueryToken;
import hr.fer.oprpp1.hw04.db.lexer.QueryTokenType;

/**
 * Implementation of a parser for query
 *
 */
public class QueryParser {

	private QueryLexer lexer;
	
	private List<ConditionalExpression> expressions;
	
	private boolean directQuery;
	
	
	/**
	 * Default constuctor
	 * @param query
	 * @throws NullPointerException when query is null
	 */
	public QueryParser(String query) {
		if (query == null) throw new NullPointerException("Query can not be null!");
		
		this.lexer = new QueryLexer(query);
		this.expressions = new ArrayList<ConditionalExpression>();
		this.directQuery = false;
		
		this.parseQuery();
		
	}
	
	/**
	 * Whole query parser method
	 */
	private void parseQuery() {
		boolean hasAnd = false;
		while (true) {
			QueryToken next = this.lexer.nextToken();
			if(next.getType() == QueryTokenType.EOF)
				break;
			
			if (next.getValue().toString().toLowerCase().equals("and") && hasAnd==true) {
				throw new QueryParserException("Invalid expression! (AND)");
			}
			
			if (next.getValue().toString().toLowerCase().equals("and")) {
				hasAnd = true;
				continue;
			}
			
			if (next.getType() != QueryTokenType.ATTRIBUTE_NAME)
				throw new QueryParserException("Invalid expression!");

			this.expressions.add(parseExpression());
			hasAnd = false;
		}
		
		if (expressions.size() == 1 &&
				expressions.get(0).getFieldValueGetter() == FieldValueGetters.JMBAG &&
				expressions.get(0).getComparisonOperator() == ComparisonOperators.EQUALS)
			this.directQuery = true;
			
	}
	
	
	/**
	 * One expression parser method
	 * @return Returns conditional expression
	 * @throws QueryParserException
	 */
	private ConditionalExpression parseExpression() {
		QueryToken attribute = this.lexer.getToken(); //nextToken();
		if (attribute.getType() != QueryTokenType.ATTRIBUTE_NAME)
			throw new QueryParserException("Expected attribute name");
		
		IFieldValueGetter attributeGetter;
		if (attribute.getValue().toLowerCase().equals("jmbag"))
			attributeGetter = FieldValueGetters.JMBAG;
		else if (attribute.getValue().toLowerCase().equals("lastname"))
			attributeGetter = FieldValueGetters.LAST_NAME;
		else if (attribute.getValue().toLowerCase().equals("firstname"))
			attributeGetter = FieldValueGetters.FIRST_NAME;
		else
			throw new QueryParserException("Invalid attribute name");
		
		
		QueryToken operator = this.lexer.nextToken();
		if (operator.getType() != QueryTokenType.OPERATOR)
			throw new QueryParserException("Expected operator");
		
		IComparisonOperator comparisonOperator;
		if (operator.getValue().toLowerCase().equals("<"))
			comparisonOperator = ComparisonOperators.LESS;
		else if (operator.getValue().toLowerCase().equals(">"))
			comparisonOperator = ComparisonOperators.GREATER;
		else if (operator.getValue().toLowerCase().equals("<="))
			comparisonOperator = ComparisonOperators.LESS_OR_EQUALS;
		else if (operator.getValue().toLowerCase().equals(">="))
			comparisonOperator = ComparisonOperators.GREATER_OR_EQUALS;
		else if (operator.getValue().toLowerCase().equals("="))
			comparisonOperator = ComparisonOperators.EQUALS;
		else if (operator.getValue().toLowerCase().equals("!="))
			comparisonOperator = ComparisonOperators.NOT_EQUALS;
		else if (operator.getValue().toLowerCase().equals("like"))
			comparisonOperator = ComparisonOperators.LIKE;
		else 
			throw new QueryParserException("Invalid operator");
		
		QueryToken stringLiteral = this.lexer.nextToken();
		if (stringLiteral.getType() != QueryTokenType.STRING_LITERAL)
			throw new QueryParserException("Expected string literal");
		
		return new ConditionalExpression(attributeGetter, stringLiteral.getValue(), comparisonOperator);
		
	}

	
	/**
	 * @return Returns true if query is direct
	 */
	public boolean isDirectQuery() {
		return this.directQuery;
	}
	
	/**
	 * @return Returns queried jmbag
	 * @throws IllegalStateException if not a direct query
	 */
	public String getQueriedJMBAG() {
		if (!directQuery) throw new IllegalStateException("Not a direct query");
		return expressions.get(0).getLiteral();
	}
	
	/**
	 * @return Returns all conditional expressions from query
	 */
	public List<ConditionalExpression> getQuery(){
		return this.expressions;
	}
	
	
}
