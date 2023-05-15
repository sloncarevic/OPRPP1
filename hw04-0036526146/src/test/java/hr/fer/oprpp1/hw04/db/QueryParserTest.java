package hr.fer.oprpp1.hw04.db;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class QueryParserTest {

	@Test
	void testDirectQuery() {
		QueryParser qp = new QueryParser("jmbag=\"01\"");
		assertEquals(true, qp.isDirectQuery());
		
	}
	
	@Test
	void testDirectQueryFalse() {
		QueryParser qp = new QueryParser("jmbag=\"01\" and firstName like \"A*\"");
		assertEquals(false, qp.isDirectQuery());
	}
	
	@Test
	void testGetQueriedJMBAG() {
		QueryParser qp = new QueryParser("jmbag=\"0000000001\"");
		assertEquals("0000000001", qp.getQueriedJMBAG());
	}
	
	

}
