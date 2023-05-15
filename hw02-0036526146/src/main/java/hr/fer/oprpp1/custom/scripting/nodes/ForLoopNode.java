package hr.fer.oprpp1.custom.scripting.nodes;

import hr.fer.oprpp1.custom.scripting.elems.Element;
import hr.fer.oprpp1.custom.scripting.elems.ElementConstantDouble;
import hr.fer.oprpp1.custom.scripting.elems.ElementConstantInteger;
import hr.fer.oprpp1.custom.scripting.elems.ElementString;
import hr.fer.oprpp1.custom.scripting.elems.ElementVariable;

/**
 * A node representing a single for-loop construct
 *
 */
public class ForLoopNode extends Node{
	
	private ElementVariable variable;
	
	private Element startExpression;
	
	private Element endExpression;
	
	private Element stepExpression;
	
	
	/**
	 * Default constructor 
	 * stepExpression is null
	 * @param variable
	 * @param startExpression
	 * @param endExpression
	 * @throws NullPointerException when argument is null
	 */
	public ForLoopNode(ElementVariable variable, Element startExpression, Element endExpression) {
		
		if(variable == null || startExpression == null || endExpression == null) 
			throw new NullPointerException("Argument is null");
		
		if (!(startExpression instanceof ElementVariable || startExpression instanceof ElementString || startExpression instanceof ElementConstantInteger || startExpression instanceof ElementConstantDouble))
			throw new IllegalArgumentException("Invalid argument type");
		if (!(endExpression instanceof ElementVariable || endExpression instanceof ElementString || endExpression instanceof ElementConstantInteger || endExpression instanceof ElementConstantDouble))
			throw new IllegalArgumentException("Invalid argument type");
		
		this.variable = variable;
		this.startExpression = startExpression;
		this.endExpression = endExpression;
		this.stepExpression = null;
		
	}
	
	/**
	 * Default constructor 
	 * stepExpression is not null
	 * @param variable
	 * @param startExpression
	 * @param endExpression
	 * @throws NullPointerException when argument is null
	 */
	public ForLoopNode(ElementVariable variable, Element startExpression, Element endExpression, Element stepExpression) {
		if(variable == null || startExpression == null || endExpression == null) 
			throw new NullPointerException("Argument is null");
		
		if (!(startExpression instanceof ElementVariable || startExpression instanceof ElementString || startExpression instanceof ElementConstantInteger || startExpression instanceof ElementConstantDouble))
			throw new IllegalArgumentException("Invalid argument type");
		if (!(endExpression instanceof ElementVariable || endExpression instanceof ElementString || endExpression instanceof ElementConstantInteger || endExpression instanceof ElementConstantDouble))
			throw new IllegalArgumentException("Invalid argument type");
		if (!(stepExpression instanceof ElementVariable || stepExpression instanceof ElementString || stepExpression instanceof ElementConstantInteger || stepExpression instanceof ElementConstantDouble))
			throw new IllegalArgumentException("Invalid argument type");
		
		this.variable = variable;
		this.startExpression = startExpression;
		this.endExpression = endExpression;
		this.stepExpression = stepExpression;
	}
	
		
	/**
	 * Public getter
	 * @return Returns variable
	 */
	public ElementVariable getVariable() {
		return variable;
	}

	/**
	 * Public getter
	 * @return Returns startExpression
	 */
	public Element getStartExpression() {
		return startExpression;
	}

	/**
	 * Public getter
	 * @return Returns endExpression
	 */
	public Element getEndExpression() {
		return endExpression;
	}

	/**
	 * Public getter
	 * @return Returns stepExpression
	 */
	public Element getStepExpression() {
		return stepExpression;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if(!(obj instanceof ForLoopNode)) return false;
		ForLoopNode o = (ForLoopNode) obj;
		return this.variable.equals(o.variable) &&
				this.startExpression.equals(o.startExpression) &&
				this.endExpression.equals(o.endExpression) &&
				this.stepExpression.equals(o.stepExpression);
	}
	
	@Override
	public String toString() {
		String value = "{$ FOR " + 
					this.variable + " " + 
					this.startExpression + " " +
					this.endExpression + " ";
		if (this.stepExpression != null) value = value + this.stepExpression + " ";
		value = value + "$}";
		for (int i = 0; i < this.numberOfChildren(); i++) {
			value = value + this.getChild(i).toString(); // + "\n";
		}
		value = value + "{$END$}";
		return value;
	}
	
	
	

}
