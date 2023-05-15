package hr.fer.oprpp1.gui.layouts;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;


/**
 * Class represents calculator layout
 *
 */
public class CalcLayout implements LayoutManager2{
	
	private static final int rows = 5;
	
	private static final int columns = 7;
	
	private Map<RCPosition, Component> components;
	
	private int space;
	
	/**
	 * Default constructor
	 */
	public CalcLayout() {
		this(0);
	}
	
	/**
	 * Constructor
	 * @param space
	 * @throws CalcLayoutException when space is negative
	 */
	public CalcLayout(int space) {
		if (space < 0) throw new CalcLayoutException("Int space can't be negative!");
		this.space = space;
		this.components = new HashMap<RCPosition, Component>();
	}

	@Override
	public void addLayoutComponent(String name, Component comp) {
		throw new UnsupportedOperationException("Unsupported operation!");
	}

	@Override
	public void removeLayoutComponent(Component comp) {
		if (comp == null) throw new NullPointerException("Component can't be null!");
		components.values().remove(comp);
	}

	@Override
	public Dimension preferredLayoutSize(Container parent) {
		if (parent == null) throw new NullPointerException("Component can't be null!");
		return getLayoutSize(parent, "preferred");
	}

	@Override
	public Dimension minimumLayoutSize(Container parent) {
		if (parent == null) throw new NullPointerException("Component can't be null!");
		return getLayoutSize(parent, "minimum");
	}
	
	@Override
	public void layoutContainer(Container parent) {
		if (parent == null) throw new NullPointerException("Component can't be null!");
		
		int height = 0;
		int width = 0;
		
		for (var entry : this.components.entrySet()) {
			if (entry.getValue().getPreferredSize() != null) {
				height = Math.max(height, entry.getValue().getPreferredSize().height);
				if (entry.getKey().getRow() == 1 && entry.getKey().getColumn() == 1)
					continue;
				width = Math.max(width, entry.getValue().getPreferredSize().width);
			}
		}
		
		height *= parent.getHeight() * 1.0 / preferredLayoutSize(parent).getHeight();
		width *= parent.getWidth() * 1.0 / preferredLayoutSize(parent).getWidth();
		
		int[] widths = new int[columns];
		int wdif = (parent.getWidth() - this.space*columns) - width * columns;
		if (wdif != 0) {
			for (int i = 0; i < columns; i++) {
				
				if (i % (wdif * 1. /columns) == 0) {
					widths[i] = width + 1;
				} else {
					widths[i] = width;
				}
			}		
		}
		
		int[] heights = new int[columns];
		int hdif = (parent.getHeight() - this.space*rows) - height * rows;
		if (hdif != 0) {
			for (int i = 0; i < rows; i++) {
				if (i % (hdif * 1. /rows) == 0) {
					heights[i] = height + 1;
				} else {
					heights[i] = height;
				}
			}		
		}
		
		for (var entry : components.entrySet()) {
			
			if (entry.getKey().getRow() == 1 && entry.getKey().getColumn() == 1) {
				entry.getValue().setBounds(0, 0, 5 * width + 4 * this.space, height);
				
			}
			else {
				entry.getValue().setBounds((entry.getKey().getColumn() - 1) * (width + this.space),
						(entry.getKey().getRow() - 1) * (height + this.space), width, height);
//				entry.getValue().setBounds((entry.getKey().getColumn() - 1) * (widths[entry.getKey().getColumn()-1] + this.space),
//						(entry.getKey().getRow() - 1) * (heights[entry.getKey().getRow()-1] + this.space),
//						widths[entry.getKey().getColumn()-1], 
//						heights[entry.getKey().getRow()-1]);
			}
		}
	}

	@Override
	public void addLayoutComponent(Component comp, Object constraints) {
		if (comp == null) throw new NullPointerException("Component can't be null!");
		if (constraints == null) throw new NullPointerException("Constraint can't be null!");
		
		RCPosition rcPosition = null;
		
		if (constraints instanceof RCPosition) {
			rcPosition = (RCPosition) constraints;
		}
		else if (constraints instanceof String) {
			try {
				rcPosition = RCPosition.parse((String)constraints);
			} catch (IllegalArgumentException e) {
				throw new IllegalArgumentException("Can't parse constraints!");
			}
		}
		else {
			throw new IllegalArgumentException("Constraints can't be parsed!");
		}
		
		if (rcPosition.getRow() < 1 || rcPosition.getRow() > rows)
			throw new CalcLayoutException("Invalid row number!");
		
		if (rcPosition.getColumn() < 1 || rcPosition.getColumn() > columns)
			throw new CalcLayoutException("Invalid column number!");
		
		if (rcPosition.getRow() == 1 && rcPosition.getColumn() > 1 && rcPosition.getColumn() < 6)
			throw new CalcLayoutException("Invalid column number!");
		
		if (this.components.containsKey(rcPosition)) {
			throw new CalcLayoutException("Component exists on given position!");
		}
		
		this.components.put(rcPosition, comp);
		
	}

	@Override
	public Dimension maximumLayoutSize(Container target) {
		if (target == null) throw new NullPointerException("Component can't be null!");
		return getLayoutSize(target, "maximum");
	}

	@Override
	public float getLayoutAlignmentX(Container target) {
		return 0;
	}

	@Override
	public float getLayoutAlignmentY(Container target) {
		return 0;
	}

	@Override
	public void invalidateLayout(Container target) {		
	}
	
	/**
	 * Layout size getter
	 * @param parent
	 * @param layoutType
	 * @return Returns specified layout size
	 */
	private Dimension getLayoutSize(Container parent, String layoutType) {
		int height = 0;
		int width = 0;
		
		for (var entry : components.entrySet()) {
			int entryHeight = 0;
			int entryWidth = 0;
			
			if (layoutType.equals("preferred")) {
				entryHeight = entry.getValue().getPreferredSize().height;
				entryWidth = entry.getValue().getPreferredSize().width;
			}
			else if (layoutType.equals("maximum")) {
				entryHeight = entry.getValue().getMaximumSize().height;
				entryWidth = entry.getValue().getMaximumSize().width;
			}
			else if (layoutType.equals("minimum")) {
				entryHeight = entry.getValue().getMinimumSize().height;
				entryWidth = entry.getValue().getMinimumSize().width;
			}
			
			if (entry.getKey().getRow() == 1 && entry.getKey().getColumn() == 1) {
				entryWidth = (entryWidth - 4 * this.space) / rows;
			}
			
			if (entryHeight > height)
				height = entryHeight;
			
			if (entryWidth > width)
				width = entryWidth;
		}
		
		Insets insets = parent.getInsets();
		
		width = width * columns + this.space * (columns - 1) + insets.left + insets.right;
		height = height * rows + this.space * (rows - 1) + insets.top + insets.bottom;
		
		return new Dimension(width, height);
		
	}

}
