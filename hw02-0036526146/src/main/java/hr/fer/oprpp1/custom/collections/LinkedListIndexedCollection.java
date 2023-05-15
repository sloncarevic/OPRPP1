package hr.fer.oprpp1.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Implementation of linked list-backed collection of objects
 *
 */
public class LinkedListIndexedCollection implements List {

	/**
	 * Class with pointers to previous and next list node and additional reference for value storage.
	 *
	 */
	private static class ListNode {
		
		private ListNode previous;

		private ListNode next;
		
		private Object value;
		
		/**
		 * Default constructor
		 */
		private ListNode(Object value) {
			this.previous = null;
			this.next = null;
			this.value = value;
		}
		
		/**
		 * @param previous
		 * @param next
		 * @param value
		 */
		private ListNode(ListNode previous, ListNode next , Object value) {
			this.previous = previous;
			this.next = next;
			this.value = value;
		}

		/**
		 *
		 */
		@Override
		public boolean equals(Object obj) {
			if (this == obj) return true;
			if (!(obj instanceof ListNode)) return false;
			ListNode o = (ListNode) obj;
			return Objects.equals(this.value, o.value);
		}
			
	}
	
	
	private int size;			//current size of list
	private ListNode first;		//first node of list
	private ListNode last;		//last node of list
	
	private long modificationCount = 0;	//change at add, insert, remove, clear
	
	
	/**
	 * Default constructor - empty instance
	 */
	public LinkedListIndexedCollection() {
		this.first = this.last = null;
		this.size = 0;
	}
	
	/**
	 * Constructor - stores objects from other Collection instance
	 * @param other
	 */
	public LinkedListIndexedCollection(Collection other) {
		this.addAll(other);
	}
	
	
	/**
	 * @param index
	 * @return object at given index
	 * @throws IndexOutOfBoundsException when index is invalid
	 */
	public Object getNode(int index) {
		if (index < 0 || index > this.size) throw new IndexOutOfBoundsException();
		
		ListNode currentNode;
		
		if (index <= (this.size-1)/2) {
			currentNode = this.first;
			for(int i = 0; i < index; i++) currentNode = currentNode.next;
		} else {
			currentNode = this.last;
			for(int i = this.size-1; i > index; i--) currentNode = currentNode.previous;
		}
		
		return currentNode;
	}
	
	/**
	 * Returns the object that is stored in linked list at position index
	 * @param index
	 * @return object value at given index
	 * @throws IndexOutOfBoundsException when index is invalid
	 */
	public Object get(int index) {
		if (index < 0 || index > this.size) throw new IndexOutOfBoundsException();
		
		ListNode currentNode;
		
		if (index <= (this.size-1)/2) {
			currentNode = this.first;
			for(int i = 0; i < index; i++) currentNode = currentNode.next;
		} else {
			currentNode = this.last;
			for(int i = this.size-1; i > index; i--) currentNode = currentNode.previous;
		}
		
		return currentNode.value;
	}
	
	/**
	 * Inserts (does not overwrite) the given value at the given position in linked-list
	 * @param value 
	 * @param position
	 * @throws NullPointerException when value is null
	 * @throws IndexOutOfBoundsException when position is invalid
	 */
	public void insert(Object value, int position) {
		if (value == null) throw new NullPointerException();
		if (position < 0 || position > this.size) throw new IndexOutOfBoundsException();
		
		ListNode newnode = new ListNode(value);
		if (this.first == null) {
			this.first = this.last = newnode;
		} else {
			if (position == 0) {
				newnode.next = this.first;
				this.first.previous = newnode;
				this.first = newnode;
			} else if (position < this.size) {
				ListNode currentNode = (ListNode) getNode(position);
				currentNode.previous.next = newnode;
				newnode.previous = currentNode.previous;
				newnode.next = currentNode;
				currentNode.previous = newnode;
			} else {
				newnode.previous = this.last;
				this.last.next = newnode;
				this.last = newnode;
			}
		}
 		this.size++;
 		this.modificationCount++;
	}
	
	/**
	 * Searches the collection and returns the index of the first occurrence of the given value or -1 if the value is not found
	 * @param value
	 * @return returns the index of the first occurrence of the given value or -1 if the value is not found
	 */
	public int indexOf(Object value) {
		if(value == null) return -1;
		
		ListNode currentNode = this.first;
		for (int i = 0; i < this.size; i++) {
			if(value.equals(currentNode.value)) return i;
			currentNode = currentNode.next;
			if(currentNode == null) break;
		}
		return -1;
	}
	
	/**
	 * @param index Removes element at specified index from collection
	 * @throws IndexOutOfBoundsException when index is invalid
	 */
	public void remove(int index) {
		if (index < 0 || index > this.size) throw new IndexOutOfBoundsException();
		
		if (this.first == this.last) {
			this.first = this.last = null;
		} else {
			if (index == 0) {
				this.first.next.previous = null;
				this.first = this.first.next;
			} else if(index < this.size -1) {
				ListNode nodeToRemove = (ListNode) getNode(index);
				nodeToRemove.previous.next = nodeToRemove.next;
				nodeToRemove.next.previous = nodeToRemove.previous;
			} else {
				this.last.previous.next = null;
				this.last = last.previous;
			}
		}
		this.size--;
		this.modificationCount++;
	}
	
	/**
	 *
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		
		if (!(obj instanceof LinkedListIndexedCollection)) return false;
		
		LinkedListIndexedCollection o = (LinkedListIndexedCollection) obj;
		if (this.size == o.size) {
			ListNode node1 = o.first;
			ListNode node2 = this.first;
			for (int i = 0; i < this.size-1; i++) {
				if (!(node1.equals(node2))) return false;
				node1 = node1.next;
				node2 = node2.next;
			}
		}
		return true;
	}

	/**
	 *
	 */
	@Override
	public int size() {
		return this.size;
	}

	/**
	 *@throws NullPointerException when value is null
	 */
	@Override
	public void add(Object value) {
		if (value == null) throw new NullPointerException();
		
		ListNode newnode = new ListNode(value);
		
		if (this.first == null) this.first = this.last = newnode;
		else {
			this.last.next = newnode;
			newnode.previous = this.last;
			this.last = newnode;
		}
		
		this.size++;
		this.modificationCount++;
	}

	/**
	 *
	 */
	@Override
	public boolean contains(Object value) {
		int index = this.indexOf(value);
		if (index == -1) return false;
		return true;
	}

	/**
	 *
	 */
	@Override
	public boolean remove(Object value) {
		int index = this.indexOf(value);
		
		if (index == -1) return false;
		
		this.remove(index);
		
		this.modificationCount++;
		
		return true;
	}

	/**
	 *
	 */
	@Override
	public Object[] toArray() {
		Object[] resultArray = new Object[this.size];
		ListNode currentNode = this.first;
		for (int i = 0; i < this.size; i++) {
			resultArray[i] = currentNode.value;
			currentNode = currentNode.next;
		}
		return resultArray;
	}

	/**
	 *
	 */
	@Override
	public void clear() {
		this.first = this.last = null;
		this.size = 0;
		this.modificationCount++;
	}

	@Override
	public ElementsGetter createElementsGetter() {
		return new LinkedListIndexedCollectioElementsGetter(this);
	}
	
	private static class LinkedListIndexedCollectioElementsGetter implements ElementsGetter {

		private final LinkedListIndexedCollection linkedListIndexedCollection;
		
		private ListNode nextNodeToGet;
		
		private long savedModificationCount;
		
		private LinkedListIndexedCollectioElementsGetter(LinkedListIndexedCollection linkedListIndexedCollection) {
			if (linkedListIndexedCollection == null) throw new NullPointerException("Collection is null");
			
			this.linkedListIndexedCollection = linkedListIndexedCollection;
			this.nextNodeToGet = linkedListIndexedCollection.first;
			this.savedModificationCount = linkedListIndexedCollection.modificationCount;
		}
		
		
		@Override
		public boolean hasNextElement() {
			if (this.savedModificationCount != this.linkedListIndexedCollection.modificationCount)
				throw new ConcurrentModificationException("Collection modified");
			
			if (this.nextNodeToGet != null) 
				return true;
			else
				return false;
		}

		@Override
		public Object getNextElement() {
			if (this.savedModificationCount != this.linkedListIndexedCollection.modificationCount)
				throw new ConcurrentModificationException("Collection modified");
			
			if (!hasNextElement()) throw new NoSuchElementException("No elements left");
			
			Object element = this.nextNodeToGet.value;
			this.nextNodeToGet = nextNodeToGet.next;
			
			return element;
		}

		
	}

	
	
}
