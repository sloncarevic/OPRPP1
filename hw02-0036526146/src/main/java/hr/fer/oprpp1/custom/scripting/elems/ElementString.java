package hr.fer.oprpp1.custom.scripting.elems;

/**
 * Class inherits Element, used to represent string
 *
 */
public class ElementString extends Element{
	
	private String value;
	
	/**
	 * Default constructor
	 * @param value
	 * @throws NullPointerException when value is null
	 */
	public ElementString(String value) {
		if (value == null) throw new NullPointerException("Value is null!");
		this.value = value;
	}
	
	/**
	 * Public getter
	 * @return Returns value
	 */
	public String getValue() {
		return this.value;
	}

	@Override
	public String asText() {
		return getValue();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if(!(obj instanceof ElementString)) return false;
		ElementString o = (ElementString) obj;
		return this.value.equals(o.value);
	}

}
