package hr.fer.oprpp1.hw04.db;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ConditionalExpressionTest {

	@Test
	void test() {
		ConditionalExpression ce = 
				new ConditionalExpression(FieldValueGetters.FIRST_NAME,
											"M*", ComparisonOperators.LIKE);
		
		assertEquals(FieldValueGetters.FIRST_NAME, ce.getFieldValueGetter());
		assertEquals(ComparisonOperators.LIKE, ce.getComparisonOperator());
		assertEquals("M*", ce.getLiteral());
		
		StudentRecord record = new StudentRecord("0000000001", "Akšamović", "Marin", 2);
		assertEquals(true, ce.getComparisonOperator()
				.satisfied(ce.getFieldValueGetter().get(record), ce.getLiteral()));
	}

}
