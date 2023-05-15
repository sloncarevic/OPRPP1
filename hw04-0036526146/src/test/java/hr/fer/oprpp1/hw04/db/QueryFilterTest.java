package hr.fer.oprpp1.hw04.db;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class QueryFilterTest {

	@Test
	void test() {
		List<ConditionalExpression> celist = new ArrayList<ConditionalExpression>();
		celist.add(new ConditionalExpression(FieldValueGetters.JMBAG, "0000000005", ComparisonOperators.LESS));
		celist.add(new ConditionalExpression(FieldValueGetters.LAST_NAME, "A", ComparisonOperators.GREATER_OR_EQUALS));
		celist.add(new ConditionalExpression(FieldValueGetters.FIRST_NAME, "*a", ComparisonOperators.LIKE));
		
		QueryFilter qf = new QueryFilter(celist);
		
		assertEquals(false, qf.accepts(new StudentRecord("0000000001", "Perić", "Pero", 2)));
		assertEquals(true, qf.accepts(new StudentRecord("0000000001", "Anić", "Ana", 2)));
		assertEquals(false, qf.accepts(new StudentRecord("0000000005", "Anić", "Ana", 2)));
		assertEquals(true, qf.accepts(new StudentRecord("0000000001", "Perić", "Peroa", 2)));
		
	}

}
