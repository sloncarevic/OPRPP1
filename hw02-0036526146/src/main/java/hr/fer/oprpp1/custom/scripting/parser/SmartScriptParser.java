package hr.fer.oprpp1.custom.scripting.parser;

import java.util.Arrays;

import hr.fer.oprpp1.custom.collections.ArrayIndexedCollection;
import hr.fer.oprpp1.custom.collections.ObjectStack;
import hr.fer.oprpp1.custom.scripting.elems.Element;
import hr.fer.oprpp1.custom.scripting.elems.ElementConstantDouble;
import hr.fer.oprpp1.custom.scripting.elems.ElementConstantInteger;
import hr.fer.oprpp1.custom.scripting.elems.ElementFunction;
import hr.fer.oprpp1.custom.scripting.elems.ElementOperator;
import hr.fer.oprpp1.custom.scripting.elems.ElementString;
import hr.fer.oprpp1.custom.scripting.elems.ElementVariable;
import hr.fer.oprpp1.custom.scripting.lexer.SmartScriptLexer;
import hr.fer.oprpp1.custom.scripting.lexer.SmartScriptLexerState;
import hr.fer.oprpp1.custom.scripting.lexer.SmartScriptToken;
import hr.fer.oprpp1.custom.scripting.lexer.SmartScriptTokenType;
import hr.fer.oprpp1.custom.scripting.nodes.DocumentNode;
import hr.fer.oprpp1.custom.scripting.nodes.EchoNode;
import hr.fer.oprpp1.custom.scripting.nodes.ForLoopNode;
import hr.fer.oprpp1.custom.scripting.nodes.Node;
import hr.fer.oprpp1.custom.scripting.nodes.TextNode;

/**
 * Implementation of parser
 *
 */
public class SmartScriptParser {
	
	private SmartScriptLexer lexer;
	
	private ObjectStack stack;
	
	private DocumentNode documentNode;
	
	
	/**
	 * Default construstor
	 * 
	 * @param documentBody a string that contains document body
	 * @throws NullPointerException when documentBody is null
	 * @throws SmartScriptParserException when parsing fails
	 */
	public SmartScriptParser(String documentBody) {
		if (documentBody == null) throw new NullPointerException("DocumentBody is null");
		
		// create an instance of lexer and initialize it with obtained text
		this.lexer = new SmartScriptLexer(documentBody);
		
		this.stack = new ObjectStack();
		
		try {
			// delegate parsing
			
			this.documentNode = this.parseDocumentBody();
			
		} catch (Exception e) {
			throw new SmartScriptParserException(e.getMessage());
			
		}
	
	}
	
	/**
	 * Whole document body parser
	 * @return Returns DocumentNode
	 * @throws SmartScriptPrserException when wrong token type or invalid syntax
	 */
	private DocumentNode parseDocumentBody() {
		this.stack.push(new DocumentNode());

		SmartScriptToken currentToken = this.lexer.nextToken();
		
		while(currentToken.getType() != SmartScriptTokenType.EOF) {
			
			if (currentToken.getType() == SmartScriptTokenType.TEXTSTRING) {
				
				((Node)this.stack.peek()).addChildNode(new TextNode((String)currentToken.getValue()));
				
			} else if(currentToken.getType() == SmartScriptTokenType.IDENTIFIER) {
				this.parseTag(currentToken);
				
			} else if(currentToken.getType() == SmartScriptTokenType.STARTTAG) {
				this.lexer.setState(SmartScriptLexerState.TAG);
				
				
			} else {
				
				throw new SmartScriptParserException("Wrong token type");
			}
			
			currentToken = this.lexer.nextToken();
		}
		
		if (this.stack.isEmpty())
			throw new SmartScriptParserException("Contains more {$END$}-s than opened non-empty tags");
		
		return (DocumentNode)stack.pop();
	}


	/**
	 * Public getter
	 * @return Returns DocumentNode
	 */
	public DocumentNode getDocumentNode() {
		return this.documentNode;
	}
	
	
	/**
	 * Tag parser
	 * @param currentToken
	 * @throws SmartScriptPrserException when tag name is invalid
	 */
	private void parseTag(SmartScriptToken currentToken) {
		
		
		if (String.valueOf(currentToken.getValue()).equals("=")) {
			
			((Node)this.stack.peek()).addChildNode(this.parseEchoTag());
			
			
		} else if (String.valueOf(currentToken.getValue()).equals("FOR")) {
			ForLoopNode forLoopNode = this.parseForTag();
			
			
			((Node)this.stack.peek()).addChildNode(forLoopNode);
			this.stack.push(forLoopNode);
			
			
		} else if (String.valueOf(currentToken.getValue()).equals("END")) {
			if (this.lexer.nextToken().getType() != SmartScriptTokenType.ENDTAG)
				throw new SmartScriptParserException("Tag END name invalid");
			this.stack.pop();
			
		} else {
			
			throw new SmartScriptParserException("Tag name invalid");
		}
		
		this.lexer.setState(SmartScriptLexerState.TEXT);
		
	}
	
	
	/**
	 * EchoTag parser
	 * @return Returns EchoTag
	 * @throws SmartScriptPrserException when token type mismatch
	 */
	private EchoNode parseEchoTag() {
	
		SmartScriptToken currentToken = this.lexer.nextToken();
		
		ArrayIndexedCollection elements = new ArrayIndexedCollection();
		
		while(currentToken.getType() != SmartScriptTokenType.ENDTAG) {
			
			if (currentToken.getType() == SmartScriptTokenType.EOF) {
				throw new SmartScriptParserException("EOF tag not closed");
			}
			else if (currentToken.getType() == SmartScriptTokenType.INTEGER) {
				elements.add(new ElementConstantInteger((int)currentToken.getValue()));
			}
			else if (currentToken.getType() == SmartScriptTokenType.DOUBLE) {
				elements.add(new ElementConstantDouble((double)currentToken.getValue()));
			}
			else if (currentToken.getType() == SmartScriptTokenType.OPERATOR) {
				elements.add(new ElementOperator((String)currentToken.getValue()));
			}
			else if (currentToken.getType() == SmartScriptTokenType.FUNCTION) {
				elements.add(new ElementFunction((String)currentToken.getValue()));
			}
			else if (currentToken.getType() == SmartScriptTokenType.IDENTIFIER) {
				elements.add(new ElementVariable((String)currentToken.getValue()));
			}
			else if (currentToken.getType() == SmartScriptTokenType.TAGSTRING) {
				
				elements.add(new ElementString((String)currentToken.getValue()));
				
			}

			else
				throw new SmartScriptParserException("Token type invalid");
			
			currentToken = this.lexer.nextToken();
			
		}
		
		Element[] array =  Arrays.copyOf(elements.toArray(), elements.size(), Element[].class);
		
		return new EchoNode(array);
		
	}
	
	/**
	 * ForTag parser
	 * @return Returns ForTag
	 * @throws SmartScriptPrserException when token type mismatch
	 */
	private ForLoopNode parseForTag() {
		SmartScriptToken currentToken = this.lexer.nextToken();
		
		ArrayIndexedCollection elements = new ArrayIndexedCollection();
		
		while(currentToken.getType() != SmartScriptTokenType.ENDTAG) {
			
			if (currentToken.getType() == SmartScriptTokenType.EOF) {
				throw new SmartScriptParserException("EOF tag not closed");
			}
			else if (currentToken.getType() == SmartScriptTokenType.INTEGER) {
				elements.add(new ElementConstantInteger((int)currentToken.getValue()));
			}
			else if (currentToken.getType() == SmartScriptTokenType.DOUBLE) {
				elements.add(new ElementConstantDouble((double)currentToken.getValue()));
			}
			else if (currentToken.getType() == SmartScriptTokenType.OPERATOR) {
				elements.add(new ElementOperator((String)currentToken.getValue()));
			}
			else if (currentToken.getType() == SmartScriptTokenType.FUNCTION) {
				elements.add(new ElementFunction((String)currentToken.getValue()));
			}
			else if (currentToken.getType() == SmartScriptTokenType.IDENTIFIER) {
				
				elements.add(new ElementVariable((String)currentToken.getValue()));
			}
			else if (currentToken.getType() == SmartScriptTokenType.TAGSTRING) {
				elements.add(new ElementString((String)currentToken.getValue()));
			}
			else
				throw new SmartScriptParserException("Token type invalid");
			
			currentToken = this.lexer.nextToken();
		}
		
		Element[] array =  Arrays.copyOf(elements.toArray(), elements.size(), Element[].class);
		
		if (array.length == 2) {
			
			return new ForLoopNode((ElementVariable) array[0], array[1], array[2]);
		} else {
			
			return new ForLoopNode((ElementVariable) array[0], array[1], array[2], array[3]);
		}
		
	}
	
	

}
