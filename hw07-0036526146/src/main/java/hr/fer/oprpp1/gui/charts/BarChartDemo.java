package hr.fer.oprpp1.gui.charts;

import java.awt.BorderLayout;
import java.awt.Color;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * Class representation of GUI bar chart
 *
 */
public class BarChartDemo extends JFrame {

	private static final long serialVersionUID = 1L;

	
	/**
	 * Constructor
	 * @param barChart
	 * @param path
	 */
	public BarChartDemo(BarChart barChart, String path) {
		super();
		if (barChart == null) throw new NullPointerException();
		if (path == null) throw new NullPointerException();
		
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Bar Chart");
		setLocation(20, 20);
		setSize(500, 400);
		
		initGUI(barChart, path);
		
		setVisible(true);
		
	}
	
	/**
	 * GUI initialisation
	 * @param barChart
	 * @param path
	 */
	private void initGUI(BarChart barChart, String path) {
		setLayout(new BorderLayout());
		getContentPane().setBackground(Color.white);
		add(new JLabel(path), BorderLayout.PAGE_START);
		add(new BarChartComponent(barChart), BorderLayout.CENTER);
	}
	
	
	public static void main(String[] args) {
		
		if (args.length != 1) throw new IllegalArgumentException("Invalid number of arguments!");
		
		
		Path path = Paths.get(args[0].trim());
		
		List<String> lines = null;
		try {
			lines = Files.readAllLines(path);
		} catch (IOException e1) {
			e1.printStackTrace();
			System.exit(1);
		}
		
		List<XYValue> xyValues = new ArrayList<XYValue>();
		String xDescription = null;
		String yDescription = null;
		int maxy = 0;
		int miny = 0;
		int space = 0;
		try {
			xDescription = lines.get(0);
			yDescription = lines.get(1);
			miny = Integer.parseInt(lines.get(3));
			maxy = Integer.parseInt(lines.get(4));
			space = Integer.parseInt(lines.get(5));
			
			String[] xys = lines.get(2).split("\\s+");
			for (int i = 0; i < xys.length; i++) {
				String[] xy = xys[i].split(",");
				xyValues.add(new XYValue(Integer.parseInt(xy[0]), Integer.parseInt(xy[1])));
			}
			
		} catch (NumberFormatException e) {
			System.err.println("Can't parse input!");
			System.err.println(e.getMessage());
		}
		
		BarChart bc = new BarChart(xyValues, xDescription, yDescription, miny, maxy, space);
		
		SwingUtilities.invokeLater(() -> new BarChartDemo(bc, path.toAbsolutePath().toString()));
		
	}
	
}
