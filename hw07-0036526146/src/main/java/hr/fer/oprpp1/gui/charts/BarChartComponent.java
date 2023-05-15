package hr.fer.oprpp1.gui.charts;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import javax.swing.JComponent;

/**
 * Class representation of GUI component for bar chart
 * Extends JComponent
 *
 */
public class BarChartComponent extends JComponent {

	private static final long serialVersionUID = 1L;
	
	private BarChart barChart;
	
	/**
	 * Constructor
	 * @param barChart
	 */
	public BarChartComponent(BarChart barChart) {
		this.barChart = barChart;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		
		Dimension dimension = this.getSize();
		
		int width = dimension.width - 15; 	// insets
		int height = dimension.height - 15;
		
		int numRows = (int) Math.round(barChart.getyMax() * 1. / barChart.getSpace()); //hm
		int numColumns = barChart.getXyValues().size();		
		
		int rowHeight = height / (numRows+1) - 1;
		int columnWidth = width / (numColumns+1);
		
		
		Graphics2D graphics2d = (Graphics2D) g;
		graphics2d.setColor(Color.white);
		graphics2d.fillRect(0, 0, dimension.width, dimension.height);
		
		graphics2d.setFont(new Font("default", Font.BOLD, 12));
		
		FontMetrics fontMetrics = graphics2d.getFontMetrics();
		
		int marklen = 5;

		//row lines
		for (int i = 1; i <= numRows+1; i++) {
			int h = height - rowHeight*(i);// + 10;
			graphics2d.setColor(Color.orange);
			graphics2d.drawLine(columnWidth, h, (numColumns+1)*columnWidth + marklen, h);
			
			graphics2d.setColor(Color.gray);
			graphics2d.drawLine(columnWidth - marklen, h, columnWidth, h);
			
			graphics2d.setColor(Color.black);
			//font bold
			if ((i-1)*barChart.getSpace() < 10)
				graphics2d.drawString(String.valueOf((i-1)*barChart.getSpace()), columnWidth - marklen - columnWidth / 3 + fontMetrics.stringWidth(String.valueOf((i-1)*barChart.getSpace())), h + marklen);
			else
				graphics2d.drawString(String.valueOf((i-1)*barChart.getSpace()), columnWidth - marklen - columnWidth / 3, h + marklen);
		}
		
		//column lines
		for (int i = 1; i <= numColumns+1; i++) {
			int w = columnWidth * i;
			int h = height - rowHeight;
			graphics2d.setColor(Color.orange);
			graphics2d.drawLine(w, (h - numRows* rowHeight) - marklen, w, h);
			//graphics2d.drawLine(75+w*(i-1), (h - numRows* rowHeight) - marklen, 75+w*(i-1), h);
			
			graphics2d.setColor(Color.black);
			graphics2d.drawLine(w, h, w, h + marklen);
			//graphics2d.drawLine(75+w*(i-1), h, 75+w*(i-1), h + marklen);
			
			if (i <= numColumns) {
				//font bold
				String n = String.valueOf(barChart.getXyValues().get(i-1).getX());
				graphics2d.drawString(n, w + (columnWidth / 2) - n.length() / 2, height - 1 * (rowHeight / 3));
			}
			
		}
		
		//draw data bars
		for (int i = 1; i < numColumns+1; i++) {
			int h = (barChart.getXyValues().get(i-1).getY() - barChart.getyMin()) / barChart.getSpace() * rowHeight;
			int w = (i)* columnWidth + 1;
			graphics2d.setColor(Color.orange);
			graphics2d.fillRect(w, height - rowHeight - h, columnWidth, h);
			//graphics2d.fillRect(75+w*(i-1), height - rowHeight - h, columnWidth, h);
			
			graphics2d.setColor(Color.white);
			graphics2d.fillRect(w + columnWidth - 1, height - rowHeight - h, 1, h);
			//graphics2d.fillRect(75+w*(i-1) + columnWidth - 1, height - rowHeight - h, 1, h);
			
		}
		
		int arrow = 5;

		Graphics2D g2 = (Graphics2D) graphics2d.create();
		g2.setColor(Color.gray);

		int h = Math.abs(-(height - rowHeight));
		AffineTransform at = AffineTransform.getTranslateInstance(columnWidth, height - rowHeight);
		at.concatenate(AffineTransform.getRotateInstance(-Math.PI/2));
		g2.transform(at);
		g2.drawLine(0, 0, h, 0);
		g2.fillPolygon(new int[] { h, h - arrow, h - arrow, h }, new int[] { 0, -arrow, arrow, 0 }, 4);
		
		
		g2 = (Graphics2D) graphics2d.create();
		g2.setColor(Color.gray);

		h = Math.abs(-(width + 10 - columnWidth));
		at = AffineTransform.getTranslateInstance(columnWidth, height - rowHeight);
		at.concatenate(AffineTransform.getRotateInstance(0));
		g2.transform(at);
		g2.drawLine(0, 0, h, 0);
		g2.fillPolygon(new int[] { h, h - arrow, h - arrow, h }, new int[] { 0, -arrow, arrow, 0 }, 4);
		
		
		graphics2d.setColor(Color.BLACK);
		graphics2d.setFont(new Font("default", Font.PLAIN, 12));
		graphics2d.drawString(barChart.getxDescription(), numColumns * columnWidth / 2, height+5 );

		at = AffineTransform.getQuadrantRotateInstance(3);
		graphics2d.setTransform(at);
		graphics2d.drawString(barChart.getyDescription(), -height / 2, columnWidth / 3);
		
		at = AffineTransform.getQuadrantRotateInstance(0);
	}

}

