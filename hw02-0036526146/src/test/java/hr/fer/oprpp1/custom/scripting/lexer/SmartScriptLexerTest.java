package hr.fer.oprpp1.custom.scripting.lexer;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class SmartScriptLexerTest {

	
	@Test
	public void testNull() {
		assertThrows(NullPointerException.class, () -> new SmartScriptLexer(null));
	}
	
	@Test
	public void testNotNull() {
		assertNotNull(new SmartScriptLexer("").nextToken());
	}
	
	@Test
	public void testThisToken() {
		SmartScriptLexer lexer = new SmartScriptLexer("primjer");
		SmartScriptToken token = lexer.nextToken();
		assertEquals(token, lexer.getToken());
	}
	
	
	@Test
	public void testThisTokenTypeText() {
		SmartScriptLexer lexer = new SmartScriptLexer("primjer");
		assertEquals(SmartScriptTokenType.TEXTSTRING, lexer.nextToken().getType());
	}
	
	@Test
	public void testThisTokenType() {
		SmartScriptLexer lexer = new SmartScriptLexer("123 $}");
		assertEquals(SmartScriptTokenType.TEXTSTRING, lexer.nextToken().getType());
	}
	
	@Test
	public void testThisTokenTypeIntegerInTag() {
		SmartScriptLexer lexer = new SmartScriptLexer("{$123$}");
		assertEquals(SmartScriptTokenType.STARTTAG, lexer.nextToken().getType());
		assertEquals(SmartScriptTokenType.INTEGER, lexer.nextToken().getType());
		assertEquals(SmartScriptTokenType.ENDTAG, lexer.nextToken().getType());
	}
	
	@Test
	public void testThisTokenTypeIntegerAndOperatorInTag() {
		SmartScriptLexer lexer = new SmartScriptLexer("{$ 1 + 2 $}");
	
		assertEquals(SmartScriptTokenType.STARTTAG, lexer.nextToken().getType());
		assertEquals(SmartScriptTokenType.INTEGER, lexer.nextToken().getType());
		assertEquals(SmartScriptTokenType.OPERATOR, lexer.nextToken().getType());
		assertEquals(SmartScriptTokenType.INTEGER, lexer.nextToken().getType());
		assertEquals(SmartScriptTokenType.ENDTAG, lexer.nextToken().getType());
	}
	
	@Test
	public void testThisTokenTypeFunction() {
		SmartScriptLexer lexer = new SmartScriptLexer("{$@abc$}");
		assertEquals(SmartScriptTokenType.STARTTAG, lexer.nextToken().getType());
		assertEquals(SmartScriptTokenType.FUNCTION, lexer.nextToken().getType());
	}
	
	@Test
	public void testThisTokenTypeIdentifier() {
		SmartScriptLexer lexer = new SmartScriptLexer("{$abc$}");
		lexer.nextToken();
		assertEquals(SmartScriptTokenType.IDENTIFIER, lexer.nextToken().getType());
	}
	
    @Test
    public void testTwoEscapesAsOne() {
        SmartScriptLexer lexer = new SmartScriptLexer("a \\\\b");
        assertEquals("a \\b", lexer.nextToken().getValue());
    }
    
    @Test
    public void testEscapeWithParenthases() {
        SmartScriptLexer lexer = new SmartScriptLexer("a \\{b");
        assertEquals("a {b", lexer.nextToken().getValue());
    }
    
    @Test
    public void testEscapeWithQutations() {
        SmartScriptLexer lexer = new SmartScriptLexer("a \"b");
        assertEquals("a \"b", lexer.nextToken().getValue());
    }
    
    @Test
    public void testEscapeInTagString() {
    	SmartScriptLexer lexer = new SmartScriptLexer("{$\"b\"$}");

    	assertEquals(SmartScriptTokenType.STARTTAG, lexer.nextToken().getType());
    	assertEquals(SmartScriptTokenType.TAGSTRING, lexer.nextToken().getType());
    	assertEquals(SmartScriptTokenType.ENDTAG, lexer.nextToken().getType());
    }
    
    @Test
    public void testIdentifierFor() {
    	SmartScriptLexer lexer = new SmartScriptLexer("{$ FOR 1 \"a\" 3 $}");
    	
    	assertEquals(SmartScriptTokenType.STARTTAG, lexer.nextToken().getType());
    	assertEquals(SmartScriptTokenType.IDENTIFIER, lexer.nextToken().getType());
    	assertEquals(SmartScriptTokenType.INTEGER, lexer.nextToken().getType());
    	assertEquals(SmartScriptTokenType.TAGSTRING, lexer.nextToken().getType());
    	assertEquals(SmartScriptTokenType.INTEGER, lexer.nextToken().getType());
    	assertEquals(SmartScriptTokenType.ENDTAG, lexer.nextToken().getType());
    }
	
	@Test
	public void testNullState() {
		assertThrows(NullPointerException.class, () -> new SmartScriptLexer("").setState(null));
	}
	
	@Test
	public void testEOF() {
		assertEquals(SmartScriptTokenType.EOF, (new SmartScriptLexer("")).nextToken().getType());
	}
	


}
