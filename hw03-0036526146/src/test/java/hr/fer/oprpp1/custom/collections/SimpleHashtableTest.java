package hr.fer.oprpp1.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ConcurrentModificationException;
import java.util.Iterator;

import org.junit.jupiter.api.Test;

class SimpleHashtableTest {
	
	@Test
	void testSimpleHashtable() {
		SimpleHashtable<String, Integer> sh = new SimpleHashtable<>(1);
		assertEquals(0, sh.size());
	}
	
	@Test
	void testSimpleHashtableConstructorException() {
		assertThrows(IllegalArgumentException.class, () -> new SimpleHashtable<String, Integer>(0));
	}

	@Test
	void testPut() {
		SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);
		examMarks.put("Ivana", 2);
		assertEquals(1, examMarks.size());
	}
	
	@Test
	void testPutNull() {
		SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);
		assertThrows(NullPointerException.class, () -> examMarks.put(null, 2));
	}

	@Test
	void testGet() {
		SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);
		examMarks.put("Ivana", 2);
		assertEquals(2, examMarks.get("Ivana"));
	}

	@Test
	void testSize() {
		SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);
		assertEquals(0, examMarks.size());
		examMarks.put("Ivana", 2);
		assertEquals(1, examMarks.size());
	}

	@Test
	void testContainsKey() {
		SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);
		examMarks.put("Ivana", 2);
		assertEquals(true, examMarks.containsKey("Ivana"));
	}
	
	@Test
	void testContainsKeyFalse() {
		SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);
		examMarks.put("Ivana", 2);
		assertEquals(false, examMarks.containsKey(null));
	}

	@Test
	void testContainsValue() {
		SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);
		examMarks.put("Ivana", 2);
		assertEquals(true, examMarks.containsValue(2));
	}

	@Test
	void testContainsValueFalse() {
		SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);
		examMarks.put("Ivana", 2);
		assertEquals(false, examMarks.containsValue(null));
	}
	
	@Test
	void testRemove() {
		SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);
		examMarks.put("Ivana", 2);
		assertEquals(2, examMarks.remove("Ivana"));
	}

	@Test
	void testRemoveNull() {
		SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);
		assertEquals(null, examMarks.remove(null));
	}
	
	@Test
	void testIsEmpty() {
		SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);
		assertEquals(true, examMarks.isEmpty());
	}
	
	@Test
	public void test1() {
		// create collection:
		SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);
		// fill data:
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5); // overwrites old grade for Ivana
		
		// query collection:
		Integer kristinaGrade = examMarks.get("Kristina");
		//System.out.println("Kristina's exam grade is: " + kristinaGrade); // writes: 5
		assertEquals(5, kristinaGrade);
		// What is collection's size? Must be four!
		//System.out.println("Number of stored pairs: " + examMarks.size()); // writes: 4
		assertEquals(4, examMarks.size());
		
	}
	
	@Test
	public void test2() {
		
		// create collection:
		SimpleHashtable<String,Integer> examMarks = new SimpleHashtable<>(2);
		// fill data:
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5); // overwrites old grade for Ivana
		
		String t = "";
		for(SimpleHashtable.TableEntry<String,Integer> pair : examMarks) {
			//System.out.printf("%s => %d%n", pair.getKey(), pair.getValue());
			t += String.format("%s => %d%n", pair.getKey(), pair.getValue());
		}
		String s = "Ante => 2\r\n"
				+ "Ivana => 5\r\n"
				+ "Jasna => 2\r\n"
				+ "Kristina => 5\r\n";
		
		assertEquals(s, t);
		
		t = "";
		for(SimpleHashtable.TableEntry<String,Integer> pair1 : examMarks) {
			for(SimpleHashtable.TableEntry<String,Integer> pair2 : examMarks) {
				//System.out.printf("(%s => %d) - (%s => %d)%n",pair1.getKey(), pair1.getValue(), pair2.getKey(), pair2.getValue());
				t += String.format("(%s => %d) - (%s => %d)%n",pair1.getKey(), pair1.getValue(), pair2.getKey(), pair2.getValue());
			}
		}
		
		s = "(Ante => 2) - (Ante => 2)\r\n"
				+ "(Ante => 2) - (Ivana => 5)\r\n"
				+ "(Ante => 2) - (Jasna => 2)\r\n"
				+ "(Ante => 2) - (Kristina => 5)\r\n"
				+ "(Ivana => 5) - (Ante => 2)\r\n"
				+ "(Ivana => 5) - (Ivana => 5)\r\n"
				+ "(Ivana => 5) - (Jasna => 2)\r\n"
				+ "(Ivana => 5) - (Kristina => 5)\r\n"
				+ "(Jasna => 2) - (Ante => 2)\r\n"
				+ "(Jasna => 2) - (Ivana => 5)\r\n"
				+ "(Jasna => 2) - (Jasna => 2)\r\n"
				+ "(Jasna => 2) - (Kristina => 5)\r\n"
				+ "(Kristina => 5) - (Ante => 2)\r\n"
				+ "(Kristina => 5) - (Ivana => 5)\r\n"
				+ "(Kristina => 5) - (Jasna => 2)\r\n"
				+ "(Kristina => 5) - (Kristina => 5)\r\n";
		
		assertEquals(s, t);
	}
	
	@Test
	void test3() {
		SimpleHashtable<String,Integer> examMarks = new SimpleHashtable<>(2);
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5);
		
		Iterator<SimpleHashtable.TableEntry<String,Integer>> iter = examMarks.iterator();
		while(iter.hasNext()) {
			SimpleHashtable.TableEntry<String,Integer> pair = iter.next();
			String.format("%s => %d%n", pair.getKey(), pair.getValue());
			//System.out.printf("%s => %d%n", pair.getKey(), pair.getValue());
			iter.remove();
		}
		//System.out.printf("Veličina: %d%n", examMarks.size());
	}
	
	@Test
	void test4() {
		SimpleHashtable<String,Integer> examMarks = new SimpleHashtable<>(2);
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5);
		
		Iterator<SimpleHashtable.TableEntry<String,Integer>> iter = examMarks.iterator();
		while(iter.hasNext()) {
			SimpleHashtable.TableEntry<String,Integer> pair = iter.next();
			if(pair.getKey().equals("Ivana")) {
					iter.remove();
					assertThrows(IllegalStateException.class, () -> iter.remove());
				}
		}
	}
	
	@Test
	void test5() {
		SimpleHashtable<String,Integer> examMarks = new SimpleHashtable<>(2);
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5);
		
		assertThrows(ConcurrentModificationException.class, () -> {
			Iterator<SimpleHashtable.TableEntry<String,Integer>> iter = examMarks.iterator();
			while(iter.hasNext()) {
				SimpleHashtable.TableEntry<String,Integer> pair = iter.next();
				if(pair.getKey().equals("Ivana")) {
						examMarks.remove("Ivana");	
					}
				}
		});
	}

}
