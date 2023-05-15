package hr.fer.oprpp1.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LinkedListIndexedCollectionTest {
	private LinkedListIndexedCollection testListEmpty;
	private LinkedListIndexedCollection testListFilled;
	
	@BeforeEach
	void initialization() {
		testListEmpty = new LinkedListIndexedCollection();
		testListFilled = new LinkedListIndexedCollection();
		testListFilled.add(1);
		testListFilled.add(2);
		testListFilled.add(3);
		
	}
	
	@Test
	void testSizeEmpty() {
		assertEquals(0, testListEmpty.size());
	}

	@Test
	void testSizeFilled() {
		assertEquals(3, testListFilled.size());
	}
	
	@Test
	void testAdd() {
		testListEmpty.add(1);
		assertEquals(1, testListEmpty.size());
	}
	
	
	@Test
	void testAddNull() {
		assertThrows(NullPointerException.class, () -> testListEmpty.add(null));
	}

	@Test
	void testContainsEmpty() {
		assertEquals(false, testListEmpty.contains(1));
	}
	
	@Test
	void testContainsFilled() {
		assertEquals(true, testListFilled.contains(1));
	}

	@Test
	void testRemoveObject() {
		testListFilled.remove(1);
		assertEquals(2, testListFilled.size());
	}
	

	@Test
	void testToArray() {
		assertArrayEquals(new Object[] {1,2,3}, testListFilled.toArray());
	}
	
	@Test
	void testToArrayEmpty() {
		assertArrayEquals(new Object[] {}, testListEmpty.toArray());
	}
	
	@Test
	public void testForEach() {
		LinkedListIndexedCollection processingArr = new LinkedListIndexedCollection();
		processingArr.add(1);
		processingArr.add(2);
		
		int[] sum = {0};
		
		class SumProcessor extends Processor {
			@Override
			public void process(Object value) {
				sum[0] = sum[0] + (int)value;
			}
		}
				
		SumProcessor processor = new SumProcessor();
		
		processingArr.forEach(processor);
		
		assertEquals(3, sum[0]);
	}

	@Test
	void testClear() {
		testListFilled.clear();
		assertEquals(0, testListFilled.size());
	}

	@Test
	void testLinkedListIndexedCollectionDefaultConstructor() {
		assertEquals(0, new LinkedListIndexedCollection().size());
	}

	@Test
	void testLinkedListIndexedCollectionConstructor() {
		assertEquals(3, new LinkedListIndexedCollection(testListFilled).size());
	}

	@Test
	void testGet() {
		assertEquals(testListFilled.get(0), testListFilled.toArray()[0]);
	}

	@Test
	void testGetInvalidIndex() {
		assertThrows(IndexOutOfBoundsException.class, () -> testListEmpty.get(1));
	}
	
	@Test
	void testInsert() {
		testListFilled.insert(1, 1);
		assertEquals(4, testListFilled.size());
	}
	
	@Test
	void testInsertFirst() {
		testListFilled.insert(1, 0);
		assertEquals(4, testListFilled.size());
	}
	
	@Test
	void testInsertFirstEmpty() {
		testListEmpty.insert(1, 0);
		assertEquals(1, testListEmpty.size());
	}
	
	@Test
	void testInsertNull() {
		assertThrows(NullPointerException.class, () -> testListEmpty.insert(null, 1));
	}
	
	@Test
	void testInsertInvalidIndex() {
		assertThrows(IndexOutOfBoundsException.class, () -> testListEmpty.insert(2, 1));
	}

	@Test
	void testIndexOf() {
		assertEquals(2, testListFilled.indexOf(3));
	}

	@Test
	void testRemoveInt() {
		testListFilled.remove(0);
		assertEquals(2, testListFilled.size());
	}
	
	@Test
	void testRemoveIntInvalidIndex() {
		assertThrows(IndexOutOfBoundsException.class,() -> testListFilled.remove(7));
	}

	@Test
	void testIsEmpty() {
		assertEquals(true, testListEmpty.isEmpty());
	}

	@Test
	void testAddAll() {
		testListEmpty.addAll(testListFilled);
		assertEquals(3, testListEmpty.size());
	}

}
