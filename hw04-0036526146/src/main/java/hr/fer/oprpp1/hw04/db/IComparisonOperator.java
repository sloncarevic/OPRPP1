package hr.fer.oprpp1.hw04.db;

/**
 * Interface strategy for comparison
 *
 */
public interface IComparisonOperator {
	
	/**
	 * @param value1
	 * @param value2
	 * @return Returns true if values satisfy comparison, otherwise false
	 */
	boolean satisfied(String value1, String value2);

}
