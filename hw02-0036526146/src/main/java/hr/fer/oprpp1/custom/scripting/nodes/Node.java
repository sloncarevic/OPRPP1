package hr.fer.oprpp1.custom.scripting.nodes;

import java.util.Objects;

import hr.fer.oprpp1.custom.collections.ArrayIndexedCollection;

/**
 * Base class used to for the representation of structured documents
 *
 */
public abstract class Node {
	
	private ArrayIndexedCollection children;
	
	/**
	 * Default constructor
	 */
	public Node() {
		this.children = null;
	}
	
	/**
	 * Adds given child to an internally managed collection of children
	 * @param child
	 */
	public void addChildNode(Node child) {
		if (this.children == null)
			this.children = new ArrayIndexedCollection();
		this.children.add(child);
	}
	
	/**
	 * @return Returns a number of (direct) children
	 */
	public int numberOfChildren() {
		if(this.children == null)
			return 0;
		else
			return this.children.size();
	}
	
	/**
	 * Returns selected child or throws an appropriate exception if the index is invalid
	 * @param index
	 * @return Returns child at given index
	 * @throws IndexOutOfBoundsException when children is null or index is invalid
	 */
	public Node getChild(int index) {
		if (children == null) throw new IndexOutOfBoundsException("Children is null");
		if (index < 0 || index >= this.numberOfChildren()) throw new IndexOutOfBoundsException("Invalid index");
		return (Node)this.children.get(index); 
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if(!(obj instanceof Node)) return false;
		Node o = (Node) obj;		
		return Objects.equals(this.children, o.children);
	}

}
