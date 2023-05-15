package hr.fer.oprpp1.custom.scripting.elems;

/**
 * Class inherits Element, used to represent integer constant
 *
 */
public class ElementConstantInteger extends Element {
	
	private int value;
	
	/**
	 * Default constructor
	 * @param value
	 */
	public ElementConstantInteger(int value) {
		this.value = value;
	}

	/**
	 * Public getter
	 * @return Returns value
	 */
	public int getValue() {
		return value;
	}

	@Override
	public String asText() {
		return Integer.toString(getValue());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if(!(obj instanceof ElementConstantInteger)) return false;
		ElementConstantInteger o = (ElementConstantInteger) obj;
		return this.value == o.value;
	}

	@Override
	public String toString() {
		return Integer.toString(getValue());
	}
	

}
