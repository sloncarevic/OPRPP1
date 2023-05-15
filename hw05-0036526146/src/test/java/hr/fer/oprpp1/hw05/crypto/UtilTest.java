package hr.fer.oprpp1.hw05.crypto;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class UtilTest {

	@Test
	void testHexToByte() {
        assertArrayEquals(new byte[] {1, -82, 34}, Util.hextobyte("01aE22"));
	}

	@Test
	void testHexToByteNull() {
		assertThrows(NullPointerException.class, () -> Util.hextobyte(null));
	}
	
	@Test
	void testHexToByteIllegalArgumentException() {
		assertThrows(IllegalArgumentException.class, () -> Util.hextobyte("01a"));
	}
	
	@Test
	void testHexToByteIllegalArgumentException2() {
		assertThrows(IllegalArgumentException.class, () -> Util.hextobyte("01ag"));
	}

    @Test
    void testHexToByteEmptyString() {
        assertArrayEquals(new byte[0], Util.hextobyte(""));
    }
    
    
	@Test
	void testByteToHex() {
        assertEquals("01ae22", Util.bytetohex(new byte[] {1, -82, 34}));
	}
    
	@Test
	void testByteToHexNull() {
		assertThrows(NullPointerException.class, () -> Util.bytetohex(null));
	}
	
    @Test
    void testByteToHexEmptyArray() {
        assertEquals("", Util.bytetohex(new byte[0]));
    }    
    
}
