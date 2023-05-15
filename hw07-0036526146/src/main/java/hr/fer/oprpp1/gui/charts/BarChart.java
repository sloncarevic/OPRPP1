package hr.fer.oprpp1.gui.charts;

import java.util.List;

/**
 * Class representation of bar chart
 *
 */
public class BarChart {
	
	private List<XYValue> xyValues;
	
	private String xDescription;
	
	private String yDescription;
	
	private int yMin;
	
	private int yMax;
	
	private int space;

	/**
	 * Constructor
	 * @param xyValues
	 * @param xDescription
	 * @param yDescription
	 * @param yMin
	 * @param yMax
	 * @param space
	 * @throws NullPointerException when xyValues is null
	 * @throws IllegalArgumentException when y values are invalid
	 */
	public BarChart(List<XYValue> xyValues, String xDescription, String yDescription, int yMin, int yMax, int space) {
		if (xyValues == null) throw new NullPointerException("xyValues can't be null!");
		
		if (yMin < 0) throw new IllegalArgumentException("yMin can't be negative");
		if (yMin >= yMax) throw new IllegalArgumentException("yMax has to be greater than yMin!");
		
		if ((yMax - yMin) % space != 0)
			yMax = yMin + space * ((yMax - yMin) / space + 1);
			
		this.xyValues = xyValues;
		this.xDescription = xDescription;
		this.yDescription = yDescription;
		this.yMin = yMin;
		this.yMax = yMax;
		this.space = space;
	}

	/**Getter
	 * @return xyValues
	 */
	public List<XYValue> getXyValues() {
		return xyValues;
	}

	/**Getter
	 * @return xDescription
	 */
	public String getxDescription() {
		return xDescription;
	}
	
	/**Getter
	 * @return yDescription
	 */
	public String getyDescription() {
		return yDescription;
	}

	/**Getter
	 * @return yMin
	 */
	public int getyMin() {
		return yMin;
	}

	/**Getter
	 * @return yMax
	 */
	public int getyMax() {
		return yMax;
	}

	/**Getter
	 * @return space
	 */
	public int getSpace() {
		return space;
	}
	


}
