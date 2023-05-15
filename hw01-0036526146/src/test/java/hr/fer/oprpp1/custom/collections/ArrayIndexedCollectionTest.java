package hr.fer.oprpp1.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ArrayIndexedCollectionTest {
	
	private ArrayIndexedCollection testArr;
	private ArrayIndexedCollection testArrFilled;

	@BeforeEach
	public void initialization() {
		testArr = new ArrayIndexedCollection();
		
		testArrFilled = new ArrayIndexedCollection();
		testArrFilled.add("one");
		testArrFilled.add(2);
		testArrFilled.add("three");
	}
	
	@Test
	public void testDefaultConstructor() {
		assertEquals(0, new ArrayIndexedCollection().size());
	}

	@Test
	public void testConstructorWithInitialCapacity() {
		assertEquals(0, new ArrayIndexedCollection(2).size());
	}
	
	@Test
	public void testConstructorWithInvalidInitialCapacity() {
		assertThrows(IllegalArgumentException.class, () -> new ArrayIndexedCollection(0));
	}
	
	@Test
	public void testConstructorWithOtherCollection() {
		assertEquals(0, new ArrayIndexedCollection(testArr).size());
	}
	
	@Test
	public void testConstructorWithNullOtherCollection() {
		assertThrows(NullPointerException.class, () -> new ArrayIndexedCollection(null));
		
	}
	
	@Test
	public void testConstructorWithOtherCollectionAndInitialCapacity() {
		assertEquals(0, new ArrayIndexedCollection(testArr, 5).size());
	}
	
	@Test
	public void testConstructorWithOtherCollectionFilled() {
		testArr.add(1);
		assertEquals(1, new ArrayIndexedCollection(testArr).size());
	}
	
	@Test
	public void testConstructorWithOtherCollectionFilledAndInitialCapacity() {
		testArr.add(1);
		testArr.add(2);
		assertEquals(2, new ArrayIndexedCollection(testArr, 5).size());
	}
	
	@Test
	public void testConstructorWithOtherCollectionFilledAndInvalidInitialCapacity() {
		testArr.add("obj");
		assertThrows(IllegalArgumentException.class, () -> new ArrayIndexedCollection(testArr, -2));
	}
	
	@Test
	public void testIsEmpty() {
		assertEquals(true, testArr.isEmpty());
	}
	
	@Test
	public void testIsEmptyFilled() {
		testArr.add("element");
		assertEquals(false, testArr.isEmpty());
	}
	
	@Test
	public void testToArray() {
		assertArrayEquals(new Object[0], testArr.toArray());
	}
	
	@Test
	public void testToArrayFilled() {
		testArr.add(1);
		testArr.add(2);
		assertArrayEquals(new Object[] {1, 2}, testArr.toArray());
	}
	
	@Test
	public void testAddAll() {
		testArr.add(1);
		testArr.addAll(testArrFilled);
		assertEquals(4, testArr.size());		
	}
	
	@Test
	public void testAddAllToArray() {
		testArr.add(1);
		testArr.addAll(testArrFilled);
		assertArrayEquals(new Object[] {1, "one", 2, "three"}, testArr.toArray());		
	}
	
	@Test
	public void testClearSize() {
		testArr.add(1);
		testArr.clear();
		assertEquals(1, testArr.size());
	}
	
	@Test
	public void testClearElement() {
		testArr.add(1);
		testArr.clear();
		assertArrayEquals(new Object[] {null}, testArr.toArray());
	}
	
	@Test
	public void testInsertSize() {
		testArr.insert(1, 0);
		assertEquals(1, testArr.size());
	}
	
	@Test
	public void testInsertElement() {
		testArr.insert(1, 0);
		assertArrayEquals(new Object[] {1}, testArr.toArray());
	}
	
	@Test
	public void testInsertElementFilled() {
		testArrFilled.insert("obj", 2);
		assertArrayEquals(new Object[] {"one", 2, "obj", "three"}, testArrFilled.toArray());
		
	}
	
	@Test
	public void testGet() {
		assertEquals(2, testArrFilled.get(1));
	}
	
	@Test
	public void testGetInvalidIndex() {
		assertThrows(IndexOutOfBoundsException.class, () -> testArrFilled.get(57));
	}
	
	@Test
	public void testContains() {
		assertEquals(true, testArrFilled.contains("one"));
	}
	
	@Test
	public void testIndexOf() {
		assertEquals(0, testArrFilled.indexOf("one"));
	}
	
	@Test
	public void testIndexOfNull() {
		assertEquals(-1, testArrFilled.indexOf(null));
	}
	
	@Test
	public void testForEach() {
		ArrayIndexedCollection processingArr = new ArrayIndexedCollection();
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
	public void testRemoveCheckSize() {
		testArrFilled.remove(0);
		assertEquals(2, testArrFilled.size());
	}
	
	@Test 
	public void testRemoveCheckElements() {
		testArrFilled.remove(0);
		assertArrayEquals(new Object[] {2, "three"}, testArrFilled.toArray());
	}

	
	
}





