package hr.fer.oprpp1.custom.collections;


/**
 * Interface for getting elements of given collection by calling a method
 *
 */
public interface ElementsGetter<T> {

		
	/**
	 * Determines if collection has elements left.
	 * @return Returns true if there are elements to get, otherwise false
	 */
	boolean hasNextElement();
	
	
	/**
	 * Element getter
	 * @return returns the next element in collection if exists
	 * @throws NoSuchElementException when there is no elements left
	 */
	T getNextElement();
	
	
	/**
	 * Executes processor on all remaining elements
	 * @param p processor
	 * @throws NullPointerException when processor p is null
	 */
	default void processRemaining(Processor<? super T> p) {
		if (p == null) throw new NullPointerException("Processor is null");
		while (this.hasNextElement()) {
			p.process(this.getNextElement());
		}	
	}
	
	
}
