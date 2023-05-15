package hr.fer.oprpp1.hw04.db;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.OptionalInt;

/**
 * Implementation of output formatter
 *
 */
public class RecordFormatter {
	
	/**
	 * 
	 * @param result
	 * @return Returns List of Stirngs formatted for output
	 * @throws NullPointerException when result is null
	 */
	public List<String> format(List<StudentRecord> result){
		
		if(result == null) throw new NullPointerException("List of student records is null!");;
		if (result.size() == 0) {
			List<String> o = new ArrayList<String>();
			o.add("Records selected: 0\n");
			return o;
		}
		
		List<String> output = new ArrayList<String>();
		
		int jmbagLen = result.stream().mapToInt(i -> i.getJmbag().length()).max().getAsInt();

		int lastNameLen = result.stream().mapToInt(i -> i.getLastName().length()).max().getAsInt();

		int firstNameLen = result.stream().mapToInt(i -> i.getFirstName().length()).max().getAsInt();

		String firstLastLine = "+" + "=".repeat(jmbagLen+2) + 
								"+" + "=".repeat(lastNameLen+2) + 
								"+" + "=".repeat(firstNameLen+2) + 
								"+===+";
		output.add(firstLastLine);
		
		for (var sr : result) {
			String row = "| " + sr.getJmbag() + " ".repeat(jmbagLen-sr.getJmbag().length()) +
					 " | " + sr.getLastName() + " ".repeat(lastNameLen-sr.getLastName().length()) +
					 " | " + sr.getFirstName() + " ".repeat(firstNameLen-sr.getFirstName().length()) +
					 " | " + sr.getFinalGrade() + " |";
			output.add(row);
		}
	
		output.add(firstLastLine);
		
		String recsel = "Records selected: "+ result.size() + "\n";
		output.add(recsel);
		
		return output;
	}

}
