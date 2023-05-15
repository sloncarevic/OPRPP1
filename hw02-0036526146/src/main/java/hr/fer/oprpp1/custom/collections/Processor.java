package hr.fer.oprpp1.custom.collections;

/**
 * The Processor is a model of an object capable of performing some operation on the passed object.
 *
 */
public interface Processor {

	/**
	 * @param value some operation will be performed on this object
	 */
	void process(Object value);
}
