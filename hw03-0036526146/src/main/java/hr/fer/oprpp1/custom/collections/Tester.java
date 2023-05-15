package hr.fer.oprpp1.custom.collections;

/**
 * Interface models objects which test if another object is acceptable
 *
 */

public interface Tester<T> {
	
	/**
	 * Tests the given object
	 * @param obj
	 * @return returns true if object is acceptable otherwise false
	 */
	boolean test(T obj);
	
}
