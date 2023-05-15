package hr.fer.oprpp1.custom.scripting.lexer;

/**
 * Exception to be thrown when lexical analysis fails
 *
 */
public class SmartScriptLexerException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3884366149016973337L;
	
	/**
	 * Default constructor
	 */
	public SmartScriptLexerException() {
		super();
	}
	
	/**
	 * Default constructor with message
	 * @param msg
	 */
	public SmartScriptLexerException(String msg) {
		super(msg);
	}

}
