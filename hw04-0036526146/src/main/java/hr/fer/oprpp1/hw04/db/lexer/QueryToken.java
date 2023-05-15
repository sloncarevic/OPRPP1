package hr.fer.oprpp1.hw04.db.lexer;

/**
 * Implementation of query token
 *
 */
public class QueryToken {

	private QueryTokenType type;
	
	private String value;
	
	/**
	 * Constructor
	 * @param type
	 * @param value
	 * @throws NullPointerException
	 */
	public QueryToken(QueryTokenType type, String value) {
		if(type == null) throw new NullPointerException("Type can not be null!");
		if(value == null && type != QueryTokenType.EOF) throw new NullPointerException("String value can not be null!");
		
		this.type = type;
		this.value = value;
	}
	
	/**
	 * @return Returns QueryTokenType
	 */
	public QueryTokenType getType() {
		return this.type;
	}
	
	/**
	 * @return Returns value
	 */
	public String getValue() {
		return this.value;
	}
	
	@Override
	public String toString() {
		return value;
	}
	
}
