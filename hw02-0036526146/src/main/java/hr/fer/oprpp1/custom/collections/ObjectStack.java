package hr.fer.oprpp1.custom.collections;


/**
 * Implementation of the stack-like collection
 *
 */
public class ObjectStack {

	private ArrayIndexedCollection adaptee;
	
	/**
	 * Default constructor
	 */
	public ObjectStack() {
		adaptee = new ArrayIndexedCollection();
	}
	
	
	/**
	 * @return Returns true if stack contains no objects and false otherwise.
	 */
	public boolean isEmpty() {
		return this.adaptee.isEmpty();
	}
	
	/**
	 * @return Returns the number of currently stored objects in this stack
	 */
	public int size() {
		return this.adaptee.size();
	}
	
	/**
	 * @param value pushes given value on the stack
	 */
	public void push(Object value) {
		this.adaptee.add(value);
	}
	
	/**
	 * @return removes last value pushed on stack from stack and returns it
	 * @throws MyEmptyStackException when stack is empty
	 */
	public Object pop() {
		if (this.isEmpty()) throw new MyEmptyStackException();
		Object popObj = this.adaptee.get(this.size() - 1);
		this.adaptee.remove(this.size() - 1);
		
		return popObj;
	}
	
	/**
	 * @return returns last element placed on stack but does not delete it from stack
	 * @throws MyEmptyStackException when stack is empty
	 */
	public Object peek() {
		if (this.isEmpty()) throw new MyEmptyStackException();
		
		return this.adaptee.get(this.size() - 1);
	}
	
	/**
	 * removes all elements from stack
	 */
	public void clear() {
		this.adaptee.clear();
	}
	
	
	
	
	
	
	
}
