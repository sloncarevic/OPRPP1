package hr.fer.oprpp1.hw04.db;

/**
 * Implementation of field value getters
 *
 */
public class FieldValueGetters {

	public static final IFieldValueGetter FIRST_NAME = sr -> sr.getFirstName();
	
	public static final IFieldValueGetter LAST_NAME = sr -> sr.getLastName();
	
	public static final IFieldValueGetter JMBAG = sr -> sr.getJmbag();
	
}
