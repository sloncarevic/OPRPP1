package hr.fer.oprpp1.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DictionaryTest {

	private Dictionary<Integer, String> dict;
	@BeforeEach
	void setup() {
		dict = new Dictionary<Integer, String>();
	}
	
	@Test
	void testDictionary() {

		assertEquals(0, dict.size());
	}
	
	@Test
	void testDictionaryEmpty() {

		assertEquals(dict, new Dictionary<>());
	}
	
	@Test
	void testDictionaryNull() {
		
		assertThrows(NullPointerException.class, () -> dict.put(null, null));
	}

	@Test
	void testIsEmptyTrue() {
		
		assertTrue(dict.isEmpty());
	}
	
	@Test
	void testIsEmptyFalse() {
		dict.put(1, "2");
		assertFalse(dict.isEmpty());
	}

	@Test
	void testSizeZero() {
		
		assertEquals(0, dict.size());
	}
	
	@Test
	void testSizeOne() {
		
		dict.put(1, "a");
		assertEquals(1, dict.size());
	}

	@Test
	void testPut() {

		dict.put(1, "a");
		dict.put(2, "b");
		assertEquals(2, dict.size());
	}
	
	@Test
	void testPutSameKey() {

		dict.put(1, "a");
		dict.put(1, "b");
		assertEquals("b", dict.get(1));
	}
	
	@Test
	void testPutNull() {

		assertThrows(NullPointerException.class, () -> dict.put(null, null));
	}
	
	@Test
	void testPutExisting() {

		dict.put(1, "a");
		dict.put(1, "b");
		
		assertEquals(1, dict.size());
		assertEquals("b", dict.get(1));
	}

	@Test
	void testGet() {
		dict.put(1, "a");
		dict.put(2, "b");
		assertEquals("a", dict.get(1));
		assertEquals("b", dict.get(2));
	}
	
	@Test
	void testGetNullException() {
		assertThrows(NullPointerException.class, () -> dict.get(null));
	}
	
	@Test
	void testGetNull() {
		assertNull(dict.get(1));
	}

	@Test
	void testRemove() {
		dict.put(1, "a");
		dict.remove(1);
		assertTrue(dict.isEmpty());
	}
	
	@Test
	void testRemoveNull() {

		assertEquals(null, dict.remove(1));
	}
	
	@Test
	void testRemoveNullException() {

		assertThrows(NullPointerException.class, () -> dict.remove(null));
	}
	


}
