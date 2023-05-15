package hr.fer.oprpp1.hw04.db;

/**
 * Interface strategy getter
 *
 */
public interface IFieldValueGetter {
	
	/**
	 * @param record
	 * @return Returns field value for given record
	 */
	String get(StudentRecord record);
		
}
