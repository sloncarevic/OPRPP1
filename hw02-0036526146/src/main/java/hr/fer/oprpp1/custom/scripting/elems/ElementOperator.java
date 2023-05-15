package hr.fer.oprpp1.custom.scripting.elems;

/**
 * Class inherits Element, used to represent operators
 *
 */
public class ElementOperator extends Element {
	
	private String symbol;

	/**
	 * Default constructor
	 * @param name
	 * @throws NullPointerException when name is null
	 */
	public ElementOperator(String symbol) {
		if (symbol == null) throw new NullPointerException("Symbol is null!");
		this.symbol = symbol;
	}
	
	/**
	 * Public getter
	 * @return Returns value of name
	 */
	public String getName() {
		return this.symbol;
	}

	@Override
	public String asText() {
		return getName();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if(!(obj instanceof ElementOperator)) return false;
		ElementOperator o = (ElementOperator) obj;
		return this.symbol.equals(o.symbol);
	}
}
