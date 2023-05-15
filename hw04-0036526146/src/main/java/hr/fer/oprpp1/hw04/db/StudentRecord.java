package hr.fer.oprpp1.hw04.db;

import java.util.Objects;

/**
 * Implementation of records for one student
 *
 */
public class StudentRecord {
	
	private String jmbag;
	private String lastName;
	private String firstName;
	private int finalGrade;
	
	
	/**
	 * Default constructor
	 * @param jmbag
	 * @param lastName
	 * @param firstName
	 * @param finalGrade
	 */
	public StudentRecord(String jmbag, String lastName, String firstName, int finalGrade) {
		this.jmbag = jmbag;
		this.lastName = lastName;
		this.firstName = firstName;
		this.finalGrade = finalGrade;
	}


	/**
	 * Getter for jmbag
	 * @return jmbag
	 */
	public String getJmbag() {
		return jmbag;
	}


	/**
	 * Getter for last name
	 * @return lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Getter for first name
	 * @return firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Getter for final grade
	 * @return finalGrade
	 */
	public int getFinalGrade() {
		return finalGrade;
	}
	
	
	/**
	 *
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (!(obj instanceof StudentRecord)) return false;
		StudentRecord o = (StudentRecord) obj;
		return this.jmbag.equals(o.jmbag);
	}
	
	/**
	 *
	 */
	@Override
	public int hashCode() {
		return Objects.hash(this.jmbag);
	}
	
	
	

}
