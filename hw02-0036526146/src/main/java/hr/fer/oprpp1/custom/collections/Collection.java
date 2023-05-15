package hr.fer.oprpp1.custom.collections;

/**
 * Interface Collection represents some general collection of objects.
 *
 */
public interface Collection {
	
	/**
	 * Checks id collection conatins no elements  
	 * @return Returns true if collection contains no objects and false otherwise.
	 */
	default boolean isEmpty() {
		return this.size() == 0;
	}

	/**
	 * Determines the size of collection
	 * @return Returns the number of currently stored objects in this collections
	 */
	int size();
	
	/**
	 * Adds the given object into this collection
	 * @param value Adds object into collection
	 */
	void add(Object value);
	
	/**
	 * Checks if collection contains given value
	 * @param value
	 * @return Returns true only if the collection contains given value
	 */
	boolean contains(Object value);
	
	/**
	 * Removes given object from collection
	 * @param value
	 * @return Returns true only if the collection contains given value and removes one occurrence of it 
	 */
	boolean remove(Object value);
	
	/**
	 * Allocates new array with size equals to the size of this collections, fills it with collection content and returns the array
	 * @return Returns new array with size equals to the size of this collection and content
	 * @throws UnsupportedOperationException when method is not implemented
	 */
	Object[] toArray();
	
	/**
	 * @param processor Method calls processor.process(.) for each element of this collection
	 */
	default void forEach(Processor processor) {
		if (processor == null) throw new NullPointerException("Processor equals null");
		
		this.createElementsGetter().processRemaining(processor);
	}
	
	/**
	 * Method adds into the current collection all elements from the given collection
	 * @param other collection to be added in the current collection
	 * @throws NullPointerException when param equals null
	 */
	default void addAll(Collection other) {
		
		if (other == null) throw new NullPointerException("Collection is null!");
		
		other.forEach(
				new Processor() {
					
					@Override
					public void process(Object value) {
						add(value);
					}
				});
	}
	
	/**
	 * Removes all elements from this collection
	 */
	void clear();
	
	/**
	 * Abstract method for creating ElementsGetter
	 * @return instance of ElementsGetter for given collection
	 */
	ElementsGetter createElementsGetter();
	
	/**
	 * @param col
	 * @param tester
	 * @throws NullPointerException when collection or tester is null
	 */
	default void addAllSatisfying(Collection col, Tester tester) {
		if (col == null) throw new NullPointerException("Collection is null");
		if (tester == null) throw new NullPointerException("Tester is null");
		
		col.createElementsGetter().processRemaining( new Processor() {
			
			@Override
			public void process(Object value) {
				if(tester.test(value))
					add(value);
			}
		});
		
	}
	
	
	
}
