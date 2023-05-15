package hr.fer.oprpp1.hw04.db;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ComparisonOperatorsTest {

	
	@Test
	void testEquals() {
		IComparisonOperator co = ComparisonOperators.EQUALS;
		assertEquals(true, co.satisfied("a", "a"));
	}
	
	@Test
	void testLess() {
		IComparisonOperator co = ComparisonOperators.LESS;
		assertEquals(true, co.satisfied("a", "b"));
	}
	
	@Test
	void testGreater() {
		IComparisonOperator co = ComparisonOperators.GREATER;
		assertEquals(true, co.satisfied("b", "a"));
	}
	
	@Test
	void testLessEquals() {
		IComparisonOperator co = ComparisonOperators.LESS_OR_EQUALS;
		assertEquals(true, co.satisfied("a", "a"));
	}
	
	@Test
	void testGreaterEquals() {
		IComparisonOperator co = ComparisonOperators.GREATER_OR_EQUALS;
		assertEquals(true, co.satisfied("b", "a"));
	}
	
	@Test
	void testNotEquals() {
		IComparisonOperator co = ComparisonOperators.NOT_EQUALS;
		assertEquals(true, co.satisfied("a", "b"));
	}

	@Test
	void testLike() {
		IComparisonOperator co = ComparisonOperators.LIKE;
		assertEquals(true, co.satisfied("ab", "a*"));
	}
	
	@Test
	void testLikeComp() {
		IComparisonOperator co = ComparisonOperators.LIKE;
		assertEquals(true, co.satisfied("AAAA", "AA*AA"));
	}
	
	@Test
	void testLikeCompFalse() {
		IComparisonOperator co = ComparisonOperators.LIKE;
		assertEquals(false, co.satisfied("AAA", "AA*AA"));
	}
	
	@Test
	void testMultipleWildcards() {
		IComparisonOperator co = ComparisonOperators.LIKE;
		assertThrows(IllegalArgumentException.class, () -> co.satisfied("AAA", "*A*"));
	}
}
