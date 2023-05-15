package hr.fer.oprpp1.hw04.db;

/**
 * Functional interface for filtering
 *
 */
public interface IFilter {
	
	/**
	 * @param record
	 * @return true if record is acceptable otherwise false
	 */
	boolean accepts(StudentRecord record);

}
