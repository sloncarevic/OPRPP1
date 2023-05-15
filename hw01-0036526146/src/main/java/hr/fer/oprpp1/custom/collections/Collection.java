package hr.fer.oprpp1.custom.collections;

/**
 * Class Collection represents some general collection of objects.
 *
 */
public class Collection {
	
	/**
	 * Default constructor
	 */
	protected Collection() {
		
	}
	
	/**
	 * Checks id collection conatins no elements  
	 * @return Returns true if collection contains no objects and false otherwise.
	 */
	public boolean isEmpty() {
		return this.size() == 0;
	}

	/**
	 * Determines the size of collection
	 * @return Returns the number of currently stored objects in this collections
	 */
	public int size() {
		return 0;
	}
	
	/**
	 * Adds the given object into this collection
	 * @param value Adds object into collection
	 */
	public void add(Object value) {
		
	}
	
	/**
	 * Checks if collection contains given value
	 * @param value
	 * @return Returns true only if the collection contains given value
	 */
	public boolean contains(Object value) {
		return false;
	}
	
	/**
	 * Removes given object from collection
	 * @param value
	 * @return Returns true only if the collection contains given value and removes one occurrence of it 
	 */
	public boolean remove(Object value) {
		return false;
	}
	
	/**
	 * Allocates new array with size equals to the size of this collections, fills it with collection content and returns the array
	 * @return Returns new array with size equals to the size of this collection and content
	 * @throws UnsupportedOperationException when method is not implemented
	 */
	public Object[] toArray() {
		throw new UnsupportedOperationException("Unsupported operation");
	}
	
	/**
	 * @param processor Method calls processor.process(.) for each element of this collection
	 */
	public void forEach(Processor processor) {
		
	}
	
	/**
	 * Method adds into the current collection all elements from the given collection
	 * @param other collection to be added in the current collection
	 * @throws NullPointerException when param equals null
	 */
	public void addAll(Collection other) {
		
		if (other == null) throw new NullPointerException();
		
		class AddAllProcessor extends Processor {
			
			@Override
			public void process(Object value) {
				add(value);
			}
		}
		
		other.forEach(new AddAllProcessor());
	}
	
	/**
	 * Removes all elements from this collection
	 */
	public void clear() {
		
	}
	
	
}
