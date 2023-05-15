package hr.fer.oprpp1.hw04.db;

/**
 * Implementation of comparators
 *
 */
public class ComparisonOperators {

	public static final IComparisonOperator LESS = ((v1, v2) -> v1.compareTo(v2) < 0);
	
	public static final IComparisonOperator LESS_OR_EQUALS = ((v1, v2) -> v1.compareTo(v2) <= 0) ;
	
	public static final IComparisonOperator GREATER = ((v1, v2) -> v1.compareTo(v2) > 0) ;
	
	public static final IComparisonOperator GREATER_OR_EQUALS = ((v1, v2) -> v1.compareTo(v2) >= 0) ;
	
	public static final IComparisonOperator EQUALS = ((v1, v2) -> v1.compareTo(v2) == 0) ;
	
	public static final IComparisonOperator NOT_EQUALS = ((v1, v2) -> v1.compareTo(v2) != 0) ;
	
	public static final IComparisonOperator LIKE = ((v1, v2) -> {
		if (v1 == null || v2 == null) throw new NullPointerException("Value is null!");
		
		long c = v2.chars().filter(ch -> ch == '*').count();
		if (c > 1) throw new IllegalArgumentException("More than 1 wildcard found!");
		
		return v1.matches(v2.replace(".", "\\.").replace("*", ".*"));
	});
	
	
}
