package hr.fer.oprpp1.custom.scripting.parser;

/**
 * Exception to be thrown when parser fails
 *
 */
public class SmartScriptParserException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7298940776722804518L;
	
	/**
	 * Default constructor
	 */
	public SmartScriptParserException() {
		super();
	}
	
	/**
	 * Constructor with message
	 * @param msg
	 */
	public SmartScriptParserException(String msg) {
		super(msg);
	}

}
