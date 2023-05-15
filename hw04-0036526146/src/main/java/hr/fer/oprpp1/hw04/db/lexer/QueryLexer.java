package hr.fer.oprpp1.hw04.db.lexer;


/**
 * Implementation of query lexer
 *
 */
public class QueryLexer {
	
	private char[] data;
	
	private QueryToken token;
	
	private int currentIndex;
	
	/**
	 * Default constructor
	 * @param text
	 * @throws NullPointerException when text is null
	 */
	public QueryLexer(String text) {
		if (text == null) throw new NullPointerException("Text can not be null");
		this.data = text.toCharArray();
		this.currentIndex = 0;
		this.token = null;
	}
	
	/**
	 * @return Returns current token
	 */
	public QueryToken getToken() {
		if (this.token == null) throw new NullPointerException("Token is null");
		return token;
	}
	
	/**
	 * @return Returns next token
	 * @throws QueryLexerException
	 */
	public QueryToken nextToken() {
		if(this.token != null && this.token.getType() == QueryTokenType.EOF)
				throw new QueryLexerException("EOF!");
		
		this.skipBlanks();
		
		//checks for EOF
		if (this.currentIndex >= this.data.length) {
			this.token = new QueryToken(QueryTokenType.EOF, null);
				return this.token;
		}
		
		if(isOperator())
			return operatorToken();
		
		else if(Character.isLetter(this.data[this.currentIndex]))
			return attributeToken();
		
		else if(this.data[this.currentIndex] == '"')
			return stringLiteralToken();
		
		else
			throw new QueryLexerException("Can not generate token");
		
	}

	
	/**
	 * @return Returns query token of operator
	 */
	private QueryToken operatorToken() {
		if (this.data[this.currentIndex] == '<') {
			if (this.currentIndex+1 < this.data.length) {
				if (this.data[currentIndex+1] == '=') {
					this.token = new QueryToken(QueryTokenType.OPERATOR, "<=");
					this.currentIndex+=2;
				}
				else {
					this.token = new QueryToken(QueryTokenType.OPERATOR, "<");
					this.currentIndex++;
				}
			}
		}
		else if (this.data[this.currentIndex] == '>') {
			if (this.currentIndex+1 < this.data.length) {
				if (this.data[currentIndex+1] == '=') {
					this.token = new QueryToken(QueryTokenType.OPERATOR, ">=");
					this.currentIndex += 2;
				}
				else {
					this.token = new QueryToken(QueryTokenType.OPERATOR, ">");
					currentIndex++;
				}
			}
		}
		else if (this.data[this.currentIndex] == '=') {
			this.token = new QueryToken(QueryTokenType.OPERATOR, "=");
			this.currentIndex++;
		}
		else if (this.data[this.currentIndex] == '!') {
			if (this.currentIndex+1 < this.data.length) {
				if (this.data[currentIndex+1] == '=') {
					this.token = new QueryToken(QueryTokenType.OPERATOR, "!=");
					this.currentIndex += 2;
				}
			}
		} else {
			this.token = new QueryToken(QueryTokenType.OPERATOR, "LIKE");
			this.currentIndex += 4;
			}
		return this.token;
	}
	
	
	/**
	 * @return Returns query token of attribute
	 */
	private QueryToken attributeToken() {
		String value = "";
		
		while (this.currentIndex+1 < this.data.length) {
			if (!Character.isLetter(this.data[this.currentIndex])) break;
			value += this.data[this.currentIndex];
			currentIndex++;
		}
		
		this.token = new QueryToken(QueryTokenType.ATTRIBUTE_NAME, value);
		return this.token;
	}
	
	/**
	 * @return Returns query token of string literal
	 */
	private QueryToken stringLiteralToken() {
		String value = "";
		this.currentIndex++;
		while(this.currentIndex+1 < this.data.length) {
			if (this.data[this.currentIndex] == '"') break;
			value += this.data[this.currentIndex];
			this.currentIndex++;
		}
		this.currentIndex++;
		
		this.token = new QueryToken(QueryTokenType.STRING_LITERAL, value);
		return this.token;
	}
	
	
	
	private boolean isOperator() {
		char c1 = this.data[this.currentIndex];
		if(c1 == '<' || c1 == '>' || c1 == '=') 
			return true;
		
		if (this.currentIndex+1 < this.data.length) {
			char c2 = this.data[this.currentIndex+2];
			if (c1 == '<' && c2 == '=' || c1 == '>' && c2 == '=' || c1 == '!' && c2 == '=' )
				return true;
		}
		
		if (this.currentIndex+3 < this.data.length) {
			char[] s = {this.data[this.currentIndex],this.data[this.currentIndex+1],
					this.data[this.currentIndex+2],this.data[this.currentIndex+3]};
			String str = String.valueOf(s);
			if (str.toLowerCase().equals("like")) {
				return true;}
		}
		
		return false;
			
	}

	/**
	 * skipping over blank spaces
	 */
	private void skipBlanks() {
		while (currentIndex < this.data.length) {
			char c = data[currentIndex];
			if (c == ' ' || c == '\t' || c == '\r' || c == '\n') {
				currentIndex++;
			} else {
				break;
			}
		}
	}
}
