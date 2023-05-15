package hr.fer.oprpp1.custom.scripting.lexer;

/**
 * Implementation of lexical analysis
 *
 */
public class SmartScriptLexer {
	
	private char[] data; // ulazni tekst
	
	private SmartScriptToken token; // trenutni token
	
	private int currentIndex; // indeks prvog neobrađenog znaka
	
	private SmartScriptLexerState state;
	
	/**
	 * Default constructor
	 * @param text
	 * @throws NullPointerException when text is null
	 */
	public SmartScriptLexer(String text) {
		if (text == null) throw new NullPointerException("Text is null");
		
		this.data = text.toCharArray();
		this.currentIndex = 0;
		this.token = null;
		this.state = SmartScriptLexerState.TEXT;
	}
	
	
	/**
	 * Public getter
	 * @return Returns current token
	 * @throws SmartScriptLexerException when token is null
	 */
	public SmartScriptToken getToken() {
		if (this.token == null) throw new SmartScriptLexerException("No token");
		return this.token;
	}
	
	/**
	 * Gets next token
	 * @return Generates and returns next token
	 * @throws SmartScriptLexerException when token is null and EOF or can't generate next token
	 */
	public SmartScriptToken nextToken() {
		
		if (this.token != null && this.token.getType() == SmartScriptTokenType.EOF) 
			throw new SmartScriptLexerException("No tokens available");
		
		this.skipBlanks();
		
		//checks for EOF
		if (this.currentIndex >= this.data.length) {
			this.token = new SmartScriptToken(SmartScriptTokenType.EOF, null);
				return this.token;
		}
		
		if(this.state == SmartScriptLexerState.TEXT) {
			
			//check if starting tag
			if(this.data[this.currentIndex] == '{' && this.data[this.currentIndex + 1] == '$') {
				this.currentIndex += 2;

				this.token = new SmartScriptToken(SmartScriptTokenType.STARTTAG, "{$");
				this.setState(SmartScriptLexerState.TAG); //
				
				return this.token;
			} else {
				
				return this.textToken();
			}
	
		} else if (this.state == SmartScriptLexerState.TAG) {
			
			this.skipBlanks();
			if (this.data[this.currentIndex] == '$' && this.data[this.currentIndex + 1] == '}') {
				this.currentIndex += 2;
				this.token = new SmartScriptToken(SmartScriptTokenType.ENDTAG, "$}");
				this.setState(SmartScriptLexerState.TEXT); //
				
				return this.token;
			}
			else if (this.data[this.currentIndex] == '{' && this.data[this.currentIndex + 1] == '$') {
				
				throw new SmartScriptLexerException("Twice opened starttag");
				
			} else {
				return this.inTagToken();
			}
			
		} else {
			throw new SmartScriptLexerException("SmartScriptLexer state is invalid");
		}

	}


	/**
	 * Public setter
	 * @param state
	 * @throws NullPointerException when state is null
	 */
	public void setState(SmartScriptLexerState state) {
		if (state == null) throw new NullPointerException("State is null");
		this.state = state;
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
	
	/**
	 * @return Returns SmartScript token representing text outside of tag
	 * @throws SmartScriptLexerException when false { detected
	 */
	private SmartScriptToken textToken() {
		
		String value = "";
		while (this.currentIndex < this.data.length) {
			if (this.data[this.currentIndex] == '{' && 
					this.data[this.currentIndex + 1] == '$') break;
			
			if(this.data[currentIndex] == '{') throw new SmartScriptLexerException();
			
			if (this.data[currentIndex] == '\\') {
				if (this.data[currentIndex+1] == '\\' || this.data[currentIndex+1] == '{') {
					value = value + this.data[++currentIndex];
					this.currentIndex++;
				} else 
					throw new SmartScriptLexerException("Wrong escape");

			} else {
				value = value + this.data[currentIndex];
				currentIndex++;
			}
				
			
		}
		
		this.token = new SmartScriptToken(SmartScriptTokenType.TEXTSTRING, value);
		return this.token;
	}
	
	/**
	 * Tokenizer inside tag
	 * @return 
	 * @throws SmartScriptLexerException when token can't be generated
	 */
	private SmartScriptToken inTagToken() {
		this.skipBlanks();
		
		if(Character.isLetter(this.data[currentIndex]) || 
				this.data[currentIndex] == '=') {
			return this.identifierToken();
		}

		else if(Character.isDigit(this.data[currentIndex]) ||
				this.data[currentIndex] == '-' && Character.isDigit(this.data[currentIndex+1])) {
			return this.numberToken();
		}
		else if(this.data[currentIndex] == '+' ||
				this.data[currentIndex] == '-' ||
				this.data[currentIndex] == '*' ||
				this.data[currentIndex] == '/' ||
				this.data[currentIndex] == '^') {
			return this.operatorToken();
		}
		else if (this.data[currentIndex] == '@') {
			return this.functionToken();
		} 
		else if(this.data[currentIndex] == '"') {
			return this.stringToken();
		}
		else {

			throw new SmartScriptLexerException("Can't generate token");
		}

	}


	/**
	 * @return Returns SmartScriptToken as identifier
	 */
	private SmartScriptToken identifierToken() {

		int startPosition = this.currentIndex;
		
		while(Character.isLetter(this.data[this.currentIndex]) ||
				Character.isDigit(this.data[this.currentIndex]) || 
				this.data[this.currentIndex] == '_' ||
				this.data[this.currentIndex] == '=') {

			this.currentIndex++;
		}
		int endPosition = this.currentIndex;
		
		String value = new String(this.data, startPosition, endPosition-startPosition);
		
		this.token = new SmartScriptToken(SmartScriptTokenType.IDENTIFIER, value);
		
		return this.token;
		
	}

	/**
	 * @return Returns SmartScriptToken as number
	 */
	private SmartScriptToken numberToken() {
		int startPosition = this.currentIndex;
		boolean hasDot = false;
		while( this.data[this.currentIndex] == '-' || 
				Character.isDigit(this.data[this.currentIndex]) ||
				this.data[this.currentIndex] == '.') {
			
			if (this.data[this.currentIndex] == '.') hasDot=true;

			this.currentIndex++;
		}
		int endPosition = this.currentIndex;
		
		String stringValue = new String(this.data, startPosition, endPosition-startPosition);
		
		try {
			if(hasDot)
				this.token = new SmartScriptToken(SmartScriptTokenType.DOUBLE, Double.parseDouble(stringValue));
			else 
				this.token = new SmartScriptToken(SmartScriptTokenType.INTEGER, Integer.parseInt(stringValue));
		} catch (NumberFormatException e) {
			throw new SmartScriptLexerException("Can't parse to number");
		}
		
		return this.token;
	}
	
	/**
	 * @return Returns SmartScriptToken as operator
	 */
	private SmartScriptToken operatorToken() {
		this.token = new SmartScriptToken(SmartScriptTokenType.OPERATOR, this.data[this.currentIndex]);
		this.currentIndex++;
		return this.token;
	}

	/**
	 * @return Returns SmartScriptToken as function name
	 */
	private SmartScriptToken functionToken() {
		int startPosition = this.currentIndex;
		
		this.currentIndex++;
		
		while(Character.isLetter(this.data[this.currentIndex]) ||
				Character.isDigit(this.data[this.currentIndex]) || 
				this.data[this.currentIndex] == '_') {
			this.currentIndex++;
		}
		
		int endPosition = this.currentIndex;
		
		String value = new String(this.data, startPosition, endPosition-startPosition);
		
		this.token = new SmartScriptToken(SmartScriptTokenType.FUNCTION, value);
		return this.token;
		
	}
	
	/**
	 * @return Returns SmartScriptToken as string inside tag
	 */
	private SmartScriptToken stringToken() {
		String value = "";

		this.currentIndex++;

		while(this.currentIndex < this.data.length){
			
			if (this.data[this.currentIndex] == '"') {
				this.currentIndex++;
				break;
			}
			
			if(this.data[this.currentIndex] == '\\') {
				if(this.data[this.currentIndex] == '\\' || this.data[this.currentIndex] == '\"') {
					value = value + this.data[++this.currentIndex];
					this.currentIndex++;
				} else {
					throw new SmartScriptLexerException("Invalid escaping");
				}
			} else {
				value = value + this.data[this.currentIndex];
				this.currentIndex++;
			}
						
		}
				
		this.token = new SmartScriptToken(SmartScriptTokenType.TAGSTRING, value);
		return this.token;
	}
	

}
