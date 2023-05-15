package hr.fer.oprpp1.custom.scripting.nodes;

import java.util.Arrays;

import hr.fer.oprpp1.custom.scripting.elems.Element;

/**
 * A node representing a command which generates some textual output dynamically
 *
 */
public class EchoNode extends Node {
	
	private Element[] elements;
	
	/**Default constructor
	 * @param elements Elements array
	 * @throws NullPointerException when elements is null
	 */
	public EchoNode(Element[] elements) {
		if(elements == null) throw new NullPointerException("Elements is null");
		this.elements = elements;
	}
	
	
	/**
	 * Public getter
	 * @return Returns elements
	 */
	public Element[] getElements() {
		return this.elements;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if(!(obj instanceof EchoNode)) return false;
		EchoNode o = (EchoNode) obj;
		return Arrays.equals(this.elements, o.elements);
	}
	
	@Override
	public String toString() {
		String value = "{$= ";
		for (int i = 0; i < elements.length; i++) {
			value = value + elements[i].asText() + " ";
		}
		value += "$}";
		return value;
	}
}
