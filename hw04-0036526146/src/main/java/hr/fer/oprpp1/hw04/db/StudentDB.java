package hr.fer.oprpp1.hw04.db;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Implementation of simple database emulator
 *
 */
public class StudentDB {
	
	
	/**
	 * Main method for running queries on database
	 * @param args
	 */
	public static void main(String[] args) {
		
		List<String> rows = null;
		
		try {
			rows = Files.readAllLines(Paths.get("./database.txt"), StandardCharsets.UTF_8);
		} catch (IOException ioe) {
			System.err.println(ioe.getMessage());
		}
		
		StudentDatabase db = new StudentDatabase(rows);
		
		Scanner sc = new Scanner(System.in);
		
		while(true) {
			System.out.print("> ");
			String line = sc.nextLine();
			
			if (line.contains("exit")) {
				System.out.println("Goodbye!");
				break;
			}
			
			if (line.trim().startsWith("query"))
				line = line.replace("query", "");
			else
				throw new IllegalArgumentException("Invalid query");
			
			List<StudentRecord> result = new ArrayList<StudentRecord>();
			
			QueryParser queryParser = new QueryParser(line);
			if (queryParser.isDirectQuery()) {
				StudentRecord studentRecord = db.forJMBAG(queryParser.getQueriedJMBAG());
				if (studentRecord != null) result.add(studentRecord);
			} else {
				result = db.filter(new QueryFilter(queryParser.getQuery()));
			}
			
			List<String> outputList = new RecordFormatter().format(result);
			outputList.forEach(i -> System.out.println(i));
			
		}
	
		sc.close();
	}

}
