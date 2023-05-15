package hr.fer.oprpp1.hw04.db;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class FieldValueGettersTest {

	@Test
	void test() {
		StudentRecord sr = new StudentRecord("0000000001", "Akšamović", "Marin", 2);
		
		IFieldValueGetter fieldValueGetter = FieldValueGetters.JMBAG;
		assertEquals("0000000001", fieldValueGetter.get(sr));
		
		fieldValueGetter = FieldValueGetters.LAST_NAME;
		assertEquals("Akšamović", fieldValueGetter.get(sr));
		
		fieldValueGetter = FieldValueGetters.FIRST_NAME;
		assertEquals("Marin", fieldValueGetter.get(sr));
	}

}
