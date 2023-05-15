package hr.fer.oprpp1.hw02.prob1;

/**
 * Exception to be thrown when lexical analysis fails
 *
 */
public class LexerException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1802935421659156465L;
	
	/**
	 * Default constructor
	 */
	public LexerException() {
		super();
	}
	
	/**
	 * Constructor with message
	 * @param msg
	 */
	public LexerException(String msg) {
		super(msg);
	}

}
