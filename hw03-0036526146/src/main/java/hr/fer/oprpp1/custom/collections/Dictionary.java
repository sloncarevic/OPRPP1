package hr.fer.oprpp1.custom.collections;

import java.util.Objects;

/**
 * Dictionary collection (map implementation)
 *
 * @param <K> type of key
 * @param <V> type of value
 */
public class Dictionary<K, V> {
	
	/**
	 * Implementation of one pair of K key and V value
	 *
	 * @param <K> type of key
	 * @param <V> type of value
	 */
	private static class DictionaryElement<K, V> {
		
		private K key;
		
		private V value;
		
		/**
		 * Default constructor
		 * @param key
		 * @param value
		 */
		public DictionaryElement(K key, V value) {
			this.key = key;
			this.value = value;
		}
		
		@Override
		public boolean equals(Object obj) {
			if (this == obj) return true;
			if (!(obj instanceof DictionaryElement<?, ?>)) return false;
			DictionaryElement<?, ?> o = (DictionaryElement<?, ?>) obj;
			return Objects.equals(this.key, o.key) && Objects.equals(this.value, o.value);
		}
		
	}
	
	private ArrayIndexedCollection<DictionaryElement<K, V>> dictionaryElements;
	
	/**
	 * Default constructor
	 */
	public Dictionary() {
		this.dictionaryElements = new ArrayIndexedCollection<>();
	}
	
	/**
	 * 
	 * @return Returns true if dictionary is empty, false otherwise
	 */
	public boolean isEmpty() {
		return this.dictionaryElements.isEmpty();
	}
	
	/**
	 * @return Returns current size of dictionary
	 */
	public int size() {
		return this.dictionaryElements.size();
	}

	
	/**
	 * Clears all elements in dictionary
	 */
	public void clear() {
		this.dictionaryElements.clear();
	}
	
	/**
	 * Adds given value to dictionary (overwrites old one)
	 * @param key
	 * @param value
	 * @return Returns old value if existed or null otherwise
	 * @throws NullPointerException when key is null
	 */
	public V put(K key, V value) {
		if (key == null) throw new NullPointerException("Key is null!");
		
		DictionaryElement<K, V> element = this.getElem(key);
		
		if (element != null) {
			V oldElementValue = element.value;
			element.value = value;
			return oldElementValue;
		} else {
			this.dictionaryElements.insert(new DictionaryElement<K, V>(key, value), this.size());
			return null;
		}
	}
	
	/**
	 * @param key
	 * @return Returns pair key-value for given key if found otherwise null
	 */
	private DictionaryElement<K, V> getElem(Object key) {
		DictionaryElement<K, V> element = null;
		ElementsGetter<DictionaryElement<K, V>> elementsGetter = this.dictionaryElements.createElementsGetter();
		while(elementsGetter.hasNextElement()) {
			element = elementsGetter.getNextElement();
			if (element.key.equals(key)) return element;
		}
		return null;
	}
	
	/**
	 * @param key
	 * @return Returns element value for given key if found, otherwise null
	 * @throws NullPointerException when key is null
	 */
	public V get(Object key) {
		if (key == null) throw new NullPointerException("Key is null!");
		
		DictionaryElement<K, V> element = this.getElem(key);
		if (element != null)
			return element.value;
		return null;	
		
	}
	
	/**
	 * Removes element from dictionary
	 * @param key
	 * @return Returns value of removed element or null
	 */
	public V remove(K key) {
		if (key == null) throw new NullPointerException("Key is null!");
		
		V value = this.get(key);
		if (value == null) return null;
		if (this.dictionaryElements.remove(new DictionaryElement<K, V>(key, value)))
			return value;
		return null;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (!(obj instanceof Dictionary)) return false;
		Dictionary<?, ?> o = (Dictionary<?, ?>) obj;
		return Objects.equals(this.dictionaryElements, o.dictionaryElements);
	}

}
