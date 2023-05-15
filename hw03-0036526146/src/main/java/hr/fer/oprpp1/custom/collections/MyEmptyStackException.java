package hr.fer.oprpp1.custom.collections;

/**
 * Exception to be thrown when accesing elements of empty stack
 *
 */
public class MyEmptyStackException extends RuntimeException {

	private static final long serialVersionUID = -8590182769376978326L;

	/**
	 * Default constructor
	 */
	public MyEmptyStackException() {
		super();
	}
	
	/**
	 * @param msg detailed message
	 */
	public MyEmptyStackException(String msg) {
		super(msg);
	}

}
