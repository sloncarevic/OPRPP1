package hr.fer.oprpp1.hw02;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import hr.fer.oprpp1.custom.scripting.nodes.DocumentNode;
import hr.fer.oprpp1.custom.scripting.parser.SmartScriptParser;
import hr.fer.oprpp1.custom.scripting.parser.SmartScriptParserException;

public class SmartScriptTester {
	
	public static void main(String[] args) throws IOException {
		
		if (args.length != 1) throw new IllegalArgumentException("Wrong number of arguments");
		
		String docBody = new String(Files.readAllBytes(Paths.get(args[0])));

		//String docBody = new String(Files.readAllBytes(Paths.get("C:\\Users\\user\\eclipse-workspace\\hw02-0036526146\\src\\test\\resources\\extra\\primjer1.txt")), StandardCharsets.UTF_8);
		
		SmartScriptParser parser = null;
		try {
			parser = new SmartScriptParser(docBody);
		} catch (SmartScriptParserException e) {
			System.out.println("Unable to parse document!");
			 System.exit(-1);
		} catch (Exception e) {
			System.out.println("If this line ever executes, you have failed this class!");
			System.exit(-1);
		}
		DocumentNode document = parser.getDocumentNode();

		String originalDocumentBody = document.toString();

		SmartScriptParser parser2 = new SmartScriptParser(originalDocumentBody);
		DocumentNode document2 = parser2.getDocumentNode();
		// now document and document2 should be structurally identical trees
		boolean same = document.equals(document2); // ==> "same" must be true
		//System.out.println(same);


		
		
		
//		SmartScriptParser myParser = new SmartScriptParser("{$ = a $}");
//		DocumentNode myDocument = myParser.getDocumentNode();
//		System.out.println(myDocument);
		
//		SmartScriptParser myParser2 = new SmartScriptParser("{$ FOR i 1 1 1 $}{$END$}");
//		DocumentNode myDocument2 = myParser2.getDocumentNode();
//		System.out.println(myDocument2);
		
//        String docBody2 = Files.readString(Paths.get("C:\\Users\\user\\eclipse-workspace\\hw02-0036526146\\src\\test\\resources\\extra\\primjer6.txt"));
//        DocumentNode doc = new SmartScriptParser(docBody2).getDocumentNode();
//        System.out.println(doc);
		
	}

}
