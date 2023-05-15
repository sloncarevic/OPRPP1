package hr.fer.oprpp1.custom.scripting.nodes;


/**
 * A node representing a piece of textual data.
 *
 */
public class TextNode extends Node {
	
	private String text;
	
	/**
	 * Default constructor
	 * @param text
	 * @throws NullPointerException when text is null
	 */
	public TextNode(String text) {
		
		if(text == null) throw new NullPointerException("Text is null");
		this.text = text;
	}
	
	/**
	 * Public getter
	 * @return Returns text of current TextNode
	 */
	public String getText() {
		return this.text;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if(!(obj instanceof TextNode)) return false;
		TextNode o = (TextNode) obj;
		return this.text.equals(o.text);
	}
	
	@Override
	public String toString() {
		return this.text;
	}


}
