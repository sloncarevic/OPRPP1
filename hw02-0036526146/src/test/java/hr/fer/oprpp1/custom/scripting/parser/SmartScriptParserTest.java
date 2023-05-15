package hr.fer.oprpp1.custom.scripting.parser;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

import hr.fer.oprpp1.custom.scripting.nodes.DocumentNode;

import hr.fer.oprpp1.custom.scripting.nodes.TextNode;

class SmartScriptParserTest {

	
	private String readExample(int n) {
		  try(InputStream is = this.getClass().getClassLoader().getResourceAsStream("extra/primjer"+n+".txt")) {
		    if(is==null) throw new RuntimeException("Datoteka extra/primjer"+n+".txt je nedostupna.");
		    byte[] data = is.readAllBytes();
		    String text = new String(data, StandardCharsets.UTF_8);
		    return text;
		  } catch(IOException ex) {
		    throw new RuntimeException("Greška pri čitanju datoteke.", ex);
		  }
		}
	
	@Test
	public void testInitialization() {
		SmartScriptParser parser = new SmartScriptParser("");
		assertEquals("", parser.getDocumentNode().toString());
	}
	
	@Test
	public void testNullPointerParameter() {
		assertThrows(NullPointerException.class, () -> new SmartScriptParser(null));
	}
	
	@Test
	public void testInstanceOfTextNode() {
		String text = readExample(1);
		DocumentNode node = new SmartScriptParser(text).getDocumentNode();
		assertTrue(node.getChild(0) instanceof TextNode);
	}
	
	@Test
	public void testSingleTextNode() {
		String text = readExample(1);
		DocumentNode node = new SmartScriptParser(text).getDocumentNode();
		assertThrows(IndexOutOfBoundsException.class, () -> node.getChild(1));
	}
	
	@Test
    public void testCheckSingleTextNodeWithHalfParenthases() {
        DocumentNode node = new SmartScriptParser(readExample(2)).getDocumentNode();
        assertTrue(node.getChild(0) instanceof TextNode);
        assertThrows(IndexOutOfBoundsException.class, () -> node.getChild(1));
    }
	
	@Test
	public void testRemoveEscape() {
		DocumentNode node = new SmartScriptParser(readExample(3)).getDocumentNode();
		assertEquals(1, node.numberOfChildren());
	}
	
	@Test
	public void testInvalidEscape() {
		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(readExample(4)).getDocumentNode());
	}
	
	@Test
	public void testInvalidEscapeQuotation() {
		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(readExample(5)).getDocumentNode());

	}
	
	
	@Test
	public void testStringInTagMultipleRows() {
		DocumentNode node = new SmartScriptParser(readExample(6)).getDocumentNode();
		assertEquals(2, node.numberOfChildren());
	}
	
	@Test
	public void testTwoStringsInTagMultipleRows() {
		DocumentNode node = new SmartScriptParser(readExample(7)).getDocumentNode();
		assertEquals(2, node.numberOfChildren());
	}
	
	@Test
	public void test8() { // test ne pada a trebao bi?
		new SmartScriptParser(readExample(8)).getDocumentNode();
		
	}
	
	@Test
	public void testInvalidEscapeInTag() {
		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(readExample(9)).getDocumentNode());
	}
	
	
	
	
	
	   
	 

}
