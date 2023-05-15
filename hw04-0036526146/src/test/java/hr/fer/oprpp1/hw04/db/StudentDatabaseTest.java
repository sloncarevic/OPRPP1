package hr.fer.oprpp1.hw04.db;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StudentDatabaseTest {

	private StudentDatabase db;
	private IFilter alwaysTrue;
	private IFilter alwaysFalse;
	
	@BeforeEach
	void setup() throws IOException {
		db = new StudentDatabase(Files.readAllLines(Paths.get("./database.txt"), StandardCharsets.UTF_8));
		alwaysTrue = new IFilter() {
			@Override
			public boolean accepts(StudentRecord record) {
				return true;
			}
		};
		alwaysFalse = new IFilter() {
			@Override
			public boolean accepts(StudentRecord record) {
				return false;
			}
		};
	}

	
	@Test
	void testConstructorAndForJMBAG() {
		StudentRecord sr = this.db.forJMBAG("0000000001");
		assertEquals("Marin", sr.getFirstName());
	}
	
	@Test
	void testConstructorAndForJMBAGIncorrect() {
		StudentRecord sr = this.db.forJMBAG("100000000");
		assertEquals(null, sr);
	}
	
	@Test
	void testForJMBAGTrue() {
		assertEquals(63, this.db.filter(this.alwaysTrue).size());
	}
	
	@Test
	void testForJMBAGFalse() {
		assertEquals(0, this.db.filter(this.alwaysFalse).size());
	}
	
	

}
