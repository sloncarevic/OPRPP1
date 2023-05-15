package hr.fer.oprpp1.hw04.db;

/**
 * Exception while parsing
 *
 */
public class QueryParserException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8168172345843806632L;

	/**
	 * Default constructor
	 */
	public QueryParserException() {
		super();
	}

	/**
	 * Constructor with message
	 * @param message
	 */
	public QueryParserException(String message) {
		super(message);
	}

	
	
}
