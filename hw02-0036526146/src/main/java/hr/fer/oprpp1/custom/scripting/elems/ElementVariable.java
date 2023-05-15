package hr.fer.oprpp1.custom.scripting.elems;

/**
 * Class inherits from Element, used to for the representation of strings
 *
 */
public class ElementVariable extends Element {
	
	private String name;

	/**
	 * Default constructor
	 * @param name
	 * @throws NullPointerException when name is null
	 */
	public ElementVariable(String name) {
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
	public String toString() {
		
		return this.name;
	}
	
	

}
