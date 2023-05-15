package hr.fer.oprpp1.gui.layouts;

import java.util.Objects;

/**
 * Class representation of element position
 *
 */
public class RCPosition {

	private int row;
	
	private int column;
	
	/**
	 * Default constructor
	 * @param row
	 * @param column
	 */
	public RCPosition(int row, int column) {
		this.row = row;
		this.column = column;
	}
	
	/**
	 * Getter
	 * @return Returns row
	 */
	public int getRow() {
		return this.row;
	}
	
	/**Getter
	 * @return Returns column
	 */
	public int getColumn() {
		return this.column;
	}
	
	/**
	 * @param text
	 * @return Returns parsed RCPosition object
	 * @throws NullPointerException when argument is null
	 */
	public static RCPosition parse(String text) {
		if (text == null) throw new NullPointerException("String text can't be null!");
		
		String[] textspec = text.strip().split(",");
		
		if (textspec.length != 2 || !textspec[0].matches("\\d") || !textspec[1].matches("\\d"))
			throw new IllegalArgumentException("Invalid arguments!");
		
		RCPosition res = null;
		try {
			res = new RCPosition(Integer.parseInt(textspec[0]), Integer.parseInt(textspec[1]));
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Invalid arguments! Can't parse!");
		}
		
		return res;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(column, row);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RCPosition other = (RCPosition) obj;
		return column == other.column && row == other.row;
	}
	

	@Override
	public String toString() {
		return "(" + this.row + ", " + this.column + ")";
	}
	
}
