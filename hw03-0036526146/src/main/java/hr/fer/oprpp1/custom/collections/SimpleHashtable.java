package hr.fer.oprpp1.custom.collections;

import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Implementation of HashMap
 *
 * @param <K> type of key
 * @param <V> type of value
 */
public class SimpleHashtable<K, V> implements Iterable<SimpleHashtable.TableEntry<K, V>> {


	/**
	 * Implementation of one linked pair of K key and V value
	 *
	 * @param <K>
	 * @param <V>
	 */
	public static class TableEntry<K, V> {
		private K key;
		private V value;
		private TableEntry<K, V> next;
		
		/**
		 * Default constructor
		 * @param key
		 * @param value
		 * @throws NullPointerException when key is null
		 */
		private TableEntry(K key, V value) {
			if (key == null) throw new NullPointerException("Key is null!");
			this.key = key;
			this.value = value;
			this.next = null;
		}
		
		/**
		 * Public getter
		 * @return Returns key
		 */
		public K getKey() {
			return this.key;
		}
		
		/**
		 * Public getter
		 * @return Returns value
		 */
		public V getValue() {
			return this.value;
		}
		
		/** 
		 * Public setter
		 * @param value
		 */
		public void setValue(V value) {
			this.value = value;
		}
		
		@Override
		public String toString() {
			return super.toString();
		}
		
		@Override
		public int hashCode() {
			return Objects.hash(this.key, this.value);
		}
		
		@Override
		public boolean equals(Object obj) {
			if (this == obj) return true;
			if (!(obj instanceof SimpleHashtable.TableEntry)) return false;
			SimpleHashtable.TableEntry<?, ?> o = (SimpleHashtable.TableEntry<?, ?>) obj;
			return Objects.equals(this.key, o.key) && Objects.equals(this.value, o.value);
		}
		
	}
	
	private static final int DEFAULT_SIZE = 16;
	
	private TableEntry<K, V>[] table;
	
	private int size;
	
	private int modificationCount = 0;
	
	/**
	 * Default constructor
	 */
	public SimpleHashtable() {
		this(DEFAULT_SIZE);
	}
	
	/**
	 * Constructor
	 * @param capacity
	 * @throws IllegalArgumentException when capacity is less than 1
	 */
	@SuppressWarnings("unchecked")
	public SimpleHashtable(int capacity) {
		if (capacity < 1) throw new IllegalArgumentException("Capacity is less than 1");
		
		capacity = calculateCapacity(capacity);
		this.table = (TableEntry<K, V>[]) new TableEntry[capacity];
		this.size = 0;
		
	}
	
	/**
	 * @param capacity
	 * @return Returns capacity - first power of 2 that is equal or greater than given capacity
	 */
	private int calculateCapacity(int capacity) {
		if (capacity % 2 == 0) return capacity;
		int i;
		for (i = 1; i < capacity; i = i * 2);
		return i;
	}
	

	/**
	 * @param key
	 * @param value
	 * @return Returns old value if existed otherwise null
	 * @throws NullPointerException when key is null
	 */
	public V put(K key, V value) {
		if (key == null) throw new NullPointerException("Key is null");
		
		this.optimization();
		
		int slotNum = Math.abs(key.hashCode()%this.table.length);
		
		TableEntry<K, V> tableEntry = this.table[slotNum];
		
		if (!this.containsKey(key)) {
			if (tableEntry != null) {
				while(tableEntry.next != null)
					tableEntry = tableEntry.next;
				tableEntry.next = new TableEntry<>(key, value);
			} else {
				this.table[slotNum] = new TableEntry<>(key, value);
			}
			this.size++;
			this.modificationCount++;
			return null;
			
		} else {
			while(tableEntry != null) {
				if(tableEntry.key.equals(key)) break;
				
				tableEntry = tableEntry.next;	
			}
			
			V oValue = tableEntry.value;
			tableEntry.setValue(value);
			this.modificationCount++;
			return oValue;	
		} 
	}
	
	/**
	 * @param key
	 * @return Returns value of given key or null
	 */
	public V get(Object key) {
		if (key == null) return null;
		
		TableEntry<K, V> tableEntry = this.table[Math.abs(key.hashCode() % this.table.length)];
		while (tableEntry != null) {
			if (tableEntry.key.equals(key)) {
				return tableEntry.value;
			}
			tableEntry = tableEntry.next;
		}
		return null;
	}
	
	/**
	 * @return Returns size of table
	 */
	public int size() {
		return this.size;
	}
	
	/**
	 * @param key
	 * @return Returns true if key is contained in table, false otherwise
	 */
	public boolean containsKey(Object key) {
		if (key == null) return false;
		
		TableEntry<K, V> tableEntry = this.table[Math.abs(key.hashCode() % this.table.length)];
		while(tableEntry != null) {
			if(tableEntry.key.equals(key)) return true;
			tableEntry = tableEntry.next;
		}
		return false;
	}
	
	/**
	 * @param value
	 * @return Returns true if value is contained in table, false otherwise
	 */
	public boolean containsValue(Object value) {
		for (TableEntry<K, V> tableEntry : this.table) {
			while (tableEntry != null) {
				if (tableEntry.value == null && value == null) return true;
				if (tableEntry.value != null && tableEntry.value.equals(value)) return true;
				tableEntry = tableEntry.next;
			}
		}
		return false;
	}
	
	/**
	 * Removes element from table
	 * @param key
	 * @return Returns value of removed element or null
	 */
	public V remove(Object key) {
		if (this.containsKey(key)) {
			int slotNum = Math.abs(key.hashCode() % this.table.length);
			TableEntry<K, V> tableEntry = this.table[slotNum];
	
			V value = null;
			
			if (tableEntry.key.equals(key)) {
				value = tableEntry.value;
				this.table[slotNum] = tableEntry.next;

			} else {
				TableEntry<K, V> previousTableEntry;
				while(tableEntry.next != null) {
					previousTableEntry = tableEntry;
					tableEntry = tableEntry.next;
					
					if(tableEntry.key.equals(key)) {
						value = tableEntry.value;
						previousTableEntry.next = tableEntry.next;
					}
				}
			}
			this.size--;
			this.modificationCount++;
			return value;
		}
		return null;
	}
	
	/**
	 * @return Returns true if table is empty, false otherwise
	 */
	public boolean isEmpty() {
		return this.size == 0;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}

	/**
	 * @return Returns array of TableEntry elements
	 */
	public TableEntry<K, V>[] toArray(){
		@SuppressWarnings("unchecked")
		TableEntry<K, V>[] arr = (TableEntry<K, V>[]) new TableEntry[this.size];
		int index = 0;
		
		for (TableEntry<K, V> tableEntry : this.table) {
			while(tableEntry != null) {
				arr[index] = tableEntry;
				index++;
				tableEntry = tableEntry.next;
			}
		}
		return arr;
 	}
	
	/**
	 * Clears table
	 */
	public void clear() {
		Arrays.fill(this.table, null);
		this.size = 0;
		this.modificationCount++;
	}
	
	/**
	 * Checks if table is filled more than 75% and doubles the size
	 */
	@SuppressWarnings("unchecked")
	private void optimization() {
		if((this.size / Double.valueOf(this.table.length)) >= 0.75) {
			
			TableEntry<K, V>[] oldTable = this.toArray();
			this.table = (TableEntry<K, V>[]) new TableEntry[this.table.length * 2];
			
			int slotNum;
			TableEntry<K, V> currentEntry;
			TableEntry<K, V> newEntry;
			for (var entry : oldTable) {
				newEntry = new TableEntry<>(entry.key, entry.value);
				
				slotNum =  Math.abs(entry.key.hashCode() % this.table.length);
				currentEntry = this.table[slotNum];
				
				if (currentEntry == null) {
					this.table[slotNum] = newEntry;
				} else {
					while( currentEntry.next != null) {
						currentEntry = currentEntry.next;
					}
					currentEntry.next = newEntry;
				}
			}
			this.modificationCount++;
		}
	}

	@Override
	public Iterator<TableEntry<K, V>> iterator() {
		return new IteratorImpl();
	}
	
	/**
	 * Implementation of iterator
	 *
	 */
	private class IteratorImpl implements Iterator<SimpleHashtable.TableEntry<K, V>> {
		
		private TableEntry<K, V> currentTableEntry;
		
		private TableEntry<K, V> nextTableEntry;
		
		private int currentSlot;
		
		private int savedModificationCount;

		/**
		 * Default constructor
		 */
		private IteratorImpl() {
			this.currentTableEntry = null;
			this.nextTableEntry = null;
			this.currentSlot = 0;
			this.savedModificationCount = modificationCount;

			while(this.nextTableEntry == null) {
				if(this.currentSlot >= table.length) break;
				this.currentSlot++;
				this.nextTableEntry = table[this.currentSlot];
			}
			
		}
		
		@Override
		public boolean hasNext() {
			if (this.savedModificationCount != modificationCount)
				throw new ConcurrentModificationException("Table has been modified");
			
			return nextTableEntry != null;
		}

		@Override
		public SimpleHashtable.TableEntry<K, V> next() {
			if (nextTableEntry == null) throw new NoSuchElementException("No elements left!");
			
			this.currentTableEntry = this.nextTableEntry;
			
			if (this.nextTableEntry != null)
				this.nextTableEntry = this.nextTableEntry.next;

			while(this.nextTableEntry == null ) {
				this.currentSlot++;
				if (this.currentSlot >= table.length) break;
				this.nextTableEntry = table[this.currentSlot];
			}
			
			return this.currentTableEntry;
		}
		
		/**
		 * Safely removes element from table
		 * @throws ConcurrentModificationException when table has been modified
		 * @throws IllegalStateException when removing same element twice
		 */
		public void remove() {
			if (this.savedModificationCount != modificationCount)
				throw new ConcurrentModificationException("Table has been modified");
			if (currentTableEntry == null)
				throw new IllegalStateException();
			
			SimpleHashtable.this.remove(this.currentTableEntry.key);
			this.savedModificationCount = modificationCount;
			this.currentTableEntry = null;
		}
		
	}
	
}
