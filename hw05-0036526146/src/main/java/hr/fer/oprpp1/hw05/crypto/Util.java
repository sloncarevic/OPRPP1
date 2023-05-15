package hr.fer.oprpp1.hw05.crypto;

/**
 * Utility class with method for hex and byte conversion
 *
 */
public class Util {

	/**
	 * Hex to byte
	 * @param keyText
	 * @return Returns byte array converted from string of hex
	 * @throws NullPointerException when string is null
	 * @throws IllegalArgumentException when string is odd-sized
	 * @throws IllegalArgumentException when string is invalid
	 */
	public static byte[] hextobyte(String keyText) {
		if (keyText == null) throw new NullPointerException("String can't be null!");
		if (keyText.length() == 0) return new byte[0];
		if (keyText.length() % 2 != 0) throw new IllegalArgumentException("String can't be odd-sized!");
		if (!keyText.matches("[0123456789abcdefABCDEF]+")) throw new IllegalArgumentException("String is invalid!");
		
		char[] keyTextCharArr = keyText.toCharArray();
		for (int i = 0, l = keyTextCharArr.length; i < l; i++){
			if (keyTextCharArr[i] >= 65 && keyTextCharArr[i] <= 70) {
				keyTextCharArr[i] = (char) (keyTextCharArr[i] + 32);
			}
		}
		
		keyText = String.valueOf(keyTextCharArr);
		
		String digitsPos = "0123456789abcdef";
		
		byte[] bytearr = new byte[keyText.length() / 2];
		
		for (int i = 0, l = keyText.length(); i < l; i += 2) {
			bytearr[i / 2] = (byte) ((digitsPos.indexOf(keyText.charAt(i)) << 4) + digitsPos.indexOf(keyText.charAt(i+1)));
			
		}
		
		return bytearr;
		
	}
	
	
	/**
	 * Byte to hex
	 * @param bytearray
	 * @return Returns String of hex converted from byte array
	 * @throws NullPointerException when byte array is null
	 */
	public static String bytetohex(byte[] bytearray) {
		if (bytearray == null) throw new NullPointerException("Byte array can't be null!");
		
		String digitsPos = "0123456789abcdef";
		
		String text = "";

		for (int i = 0, l = bytearray.length; i < l; i++) {
			text += (digitsPos.charAt((bytearray[i] & 0xF0) >> 4)) + "" + (digitsPos.charAt(bytearray[i] & 0x0F));
		}
		
		return text;
	}
	
	
	
}
