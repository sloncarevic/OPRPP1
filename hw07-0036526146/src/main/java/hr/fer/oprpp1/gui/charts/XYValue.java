package hr.fer.oprpp1.gui.charts;

/**
 * Class representation of pair value of x and y 
 *
 */
public class XYValue {
	
	private final int x;
	
	private final int y;
	
	/**
	 * Constructor
	 * @param x
	 * @param y
	 */
	public XYValue(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Getter
	 * @return Returns x value
	 */
	public int getX() {
		return this.x;
	}
	
	/**
	 * Getter
	 * @return Returns y value
	 */
	public int getY() {
		return this.y;
	}


}
