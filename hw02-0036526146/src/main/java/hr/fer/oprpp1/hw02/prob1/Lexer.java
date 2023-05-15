package hr.fer.oprpp1.hw02.prob1;

/**
 * Implementation of lexical analysis
 *
 */
public class Lexer {

	private char[] data; // ulazni tekst
	
	private Token token; // trenutni token
	
	private int currentIndex; // indeks prvog neobrađenog znaka
	
	private LexerState state;
	
	// konstruktor prima ulazni tekst koji se tokenizira
	/**
	 * Default constructor
	 * @param text
	 */
	public Lexer(String text) { 
		if (text == null) throw new NullPointerException("Text is null!");
		
		this.data = text.toCharArray();
		this.currentIndex = 0;
		this.token = null;
		this.state = LexerState.BASIC;
	}
	
	// generira i vraća sljedeći token
	// baca LexerException ako dođe do pogreške
	/**
	 * Gets next token
	 * @return Generates and returns next token
	 * @throws LexerException when token is null and EOF or can't generate next token
	 */
	public Token nextToken() { 
		if (this.token != null && this.token.getType() == TokenType.EOF) 
			throw new LexerException("No tokens available");
		
		skipBlanks();
		
		//checks for EOF
		if (this.currentIndex >= this.data.length) {
			this.token = new Token(TokenType.EOF, null);
			return this.token;
		}
		
		if (this.state == LexerState.EXTENDED) {
			return wordTokenExtended();
		}
		
		if(Character.isLetter(this.data[this.currentIndex]) || this.data[currentIndex] == '\\') {
			return wordToken();
		}
		else if(Character.isDigit(this.data[this.currentIndex])) {
			return numberToken();
		}
		else if (!Character.isDigit(this.data[this.currentIndex])) {
			return symbolToken();
		} else {
			throw new LexerException("Can't generate next token");
		}

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
	 * Word tokenizer
	 * @return Returns new WORD token
	 */
	private Token wordToken() {
		
		int escapeCounter = 0;
		String value = "";
 		while (this.currentIndex < this.data.length && 
				Character.isLetter(this.data[currentIndex]) ||
				this.data[currentIndex] == '\\' ||
				Character.isDigit(this.data[currentIndex]) && escapeCounter >= 1) {

			if (this.data[currentIndex] == '\\') {
				escapeCounter++;
				
				if (currentIndex+1 >= this.data.length) 			//checks EOF
					throw new LexerException("Invalid escape");
				
				if (Character.isLetter(this.data[currentIndex+1])) 	//checks \\a
					throw new LexerException("Invalid escape");
				
				if (this.data[currentIndex-1] == '\\')				//checks \\\
					value = value + this.data[currentIndex];

			} else {
				value = value + this.data[currentIndex];
				
				if (escapeCounter >= 1) escapeCounter--;
			}
			
			this.currentIndex++;
			if (this.currentIndex >= this.data.length) break;
		
		}
		
		this.token = new Token(TokenType.WORD, value);

		return this.token;
	}
	
	/**
	 * Number tokenizer
	 * @return Returns new NUMBER token
	 */
	private Token numberToken() {
		int startIndex = this.currentIndex;
		while (this.currentIndex < this.data.length && Character.isDigit(this.data[currentIndex])) {
			this.currentIndex++;
		}
		int endIndex = this.currentIndex;
		
		String strValue = new String(this.data, startIndex, endIndex-startIndex);
		long value;
		try {
			value = Long.parseLong(strValue);
		} catch (NumberFormatException e) {
			throw new LexerException("Can't parse to long");
		}
		
		this.token = new Token(TokenType.NUMBER, value);
		return this.token;
	}
	
	/**
	 * Symbol tokenizer
	 * @return Returns new SYMBOL token
	 */
	private Token symbolToken() {
		this.token = new Token(TokenType.SYMBOL, this.data[currentIndex]);

		currentIndex++;

		return this.token;
	}
	
	
	// vraća zadnji generirani token; može se pozivati
	// više puta; ne pokreće generiranje sljedećeg tokena
	/**
	 * @return Returns last generated token
	 */
	public Token getToken() {
		if (this.token == null) throw new LexerException("No token");
		return this.token;
	}
	
	/**
	 * State setter
	 * @param state
	 */
	public void setState(LexerState state) {
		if (state == null) throw new NullPointerException("Lexer state is null");
		this.state = state;
	}
	
	/**
	 * Word tokentizer for EXTENDED state
	 * @return Returns new WORD token
	 */
	private Token wordTokenExtended() {
		
		int startIndex = this.currentIndex;
		while (this.currentIndex < this.data.length && 
				!(this.data[currentIndex] == ' ' ||
				this.data[currentIndex] == '\r' ||
				this.data[currentIndex] == '\n' ||
				this.data[currentIndex] == '\t' )) {
			
			this.currentIndex++;
			
			if (this.data[currentIndex] == '#') {
				setState(LexerState.BASIC);
				break;
			}
		}
		int endIndex = this.currentIndex;
		
		String value = new String(this.data, startIndex, endIndex-startIndex);
		//System.out.println(value);
		
		this.token = new Token(TokenType.WORD, value);
		return this.token;
	}
	
}
