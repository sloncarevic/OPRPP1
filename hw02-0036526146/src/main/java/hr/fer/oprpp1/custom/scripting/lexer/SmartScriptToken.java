package hr.fer.oprpp1.custom.scripting.lexer;

public class SmartScriptToken {
	
	private SmartScriptTokenType type;
	
	private Object value;
	
	
	/**
	 * Default constructor
	 * @param type
	 * @param value
	 * @throws NullPointerException when type is null or value is null
	 */
	public SmartScriptToken(SmartScriptTokenType type, Object value) {
		if (type == null) throw new NullPointerException("Token type is null");
		if (value == null && type != SmartScriptTokenType.EOF)
			throw new NullPointerException("Value is null");
		
		this.type = type;
		this.value = value;
	}
	
	/**
	 * Public getter
	 * @return Returns value
	 */
	public Object getValue() {
		return this.value;
	}
	
	
	/**
	 * Public getter
	 * @return Returns type
	 */
	public SmartScriptTokenType getType() {
		return this.type;
	}
	
	@Override
	public String toString() {
		
		return value.toString();
	}

}
