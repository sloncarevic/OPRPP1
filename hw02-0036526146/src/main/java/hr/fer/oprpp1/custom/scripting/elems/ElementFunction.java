package hr.fer.oprpp1.custom.scripting.elems;

/**
 * Class inherits Element, used to represent function by name
 *
 */
public class ElementFunction extends Element {
	
	private String name;

	/**
	 * Default constructor
	 * @param name
	 * @throws NullPointerException when name is null
	 */
	public ElementFunction(String name) {
		if (name == null) throw new NullPointerException("Name is null!");
		this.name = name;
	}
	
	/**
	 * Public getter
	 * @return Returns value of name
	 */
	public String getName() {
		return this.name;
	}

	
	@Override
	public String asText() {
		return getName();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if(!(obj instanceof ElementFunction)) return false;
		ElementFunction o = (ElementFunction) obj;
		return this.name.equals(o.name);
	}
	

}
