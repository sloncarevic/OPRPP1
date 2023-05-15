package hr.fer.oprpp1.custom.scripting.elems;

/**
 * Class inherits Element, used to represent double constant
 *
 */
public class ElementConstantDouble extends Element{
	
	private double value;
	
	/**
	 * Default constructor
	 * @param value
	 */
	public ElementConstantDouble(double value) {
		this.value = value;
	}
	
	/**
	 * Public getter
	 * @return Retruns value
	 */
	public double getValue() {
		return this.value;
	}

	@Override
	public String asText() {
		return Double.toString(getValue());
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if(!(obj instanceof ElementConstantDouble)) return false;
		ElementConstantDouble o = (ElementConstantDouble) obj;
		return this.value == o.value;
	}

	@Override
	public String toString() {
		return Double.toString(getValue());
	}

}
