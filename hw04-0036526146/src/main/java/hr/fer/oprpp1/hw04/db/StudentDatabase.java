package hr.fer.oprpp1.hw04.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Implementation of database
 *
 */
public class StudentDatabase {

	private List<StudentRecord> studentRecords;
	
	private Map<String, StudentRecord> indexedMap;
	
	/**
	 * Default constructor
	 * @param fileContent
	 * @throws NullPointerException when fileContent is null
	 * @throws IllegalArgumentException
	 */
	public StudentDatabase(List<String> fileContent) {
		if (fileContent == null) throw new NullPointerException("File content is null!");
		
		this.studentRecords = new ArrayList<StudentRecord>(fileContent.size());
		this.indexedMap = new HashMap<String, StudentRecord>(fileContent.size());
		
		for(String rowContent : fileContent) {
			String[] items = rowContent.split("\t");
			int grade;
			if (items.length != 4) throw new IllegalArgumentException("Row does not contain 4 items!");
			try {
				grade = Integer.parseInt(items[3]);
				if (grade < 1 || grade > 5) throw new IllegalArgumentException("Final grade is not >=1 and <=5");
			} catch (NumberFormatException e) {
				throw new NumberFormatException("Grade can't be parsed to int");
			}
			StudentRecord sr = new StudentRecord(items[0], items[1], items[2], grade);
			this.studentRecords.add(sr);
			
			if (this.indexedMap.get(sr.getJmbag()) != null)
				throw new IllegalArgumentException("Duplicate JMBAG!");
			else 
				this.indexedMap.put(sr.getJmbag(), sr);			
		}
	}
	
	/**
	 * @param jmbag
	 * @return StudentRecord for given jmbag
	 */
	public StudentRecord forJMBAG(String jmbag) {
		return this.indexedMap.get(jmbag);
	}
	
	/**
	 * @param filter
	 * @return List of Student records for given filter
	 */
	public List<StudentRecord> filter(IFilter filter){
		return this.studentRecords.stream()
				.filter(sr -> filter.accepts(sr))
				.collect(Collectors.toList());
	}
	
	
}
