package hr.fer.oprpp1.hw02.prob1;

/**
 * 
 *
 */
public class Token {
	
	private TokenType tokenType;
	
	private Object value;
	
	/**
	 * Default constructor
	 * @param type token type
	 * @param value token value
	 * @throws NullPointerException when token type or token value equals null
	 */
	public Token(TokenType type, Object value) {
		if (type == null) throw new NullPointerException("Type is null!");
		if (type != TokenType.EOF && value == null) throw new NullPointerException("Value is null!");
		
		this.tokenType = type;
		this.value = value;
	}

	/**
	 * @return Returns value of given token
	 */
	public Object getValue() {
		return this.value;
	}
	
	/**
	 * @return Returns token type of given token
	 */
	public TokenType getType() {
		return this.tokenType;
	}

}
