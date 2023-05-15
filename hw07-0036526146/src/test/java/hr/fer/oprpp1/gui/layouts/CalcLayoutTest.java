package hr.fer.oprpp1.gui.layouts;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.junit.jupiter.api.Test;

class CalcLayoutTest {

	@Test
	void testInvalidRowColumnPositions() {
		JPanel jPanel = new JPanel(new CalcLayout());
		
		assertThrows(CalcLayoutException.class, () -> jPanel.add(new JLabel("a"), new RCPosition(0, 0)));
		
		assertThrows(CalcLayoutException.class, () -> jPanel.add(new JLabel("a"), new RCPosition(0, 1)));
		
		assertThrows(CalcLayoutException.class, () -> jPanel.add(new JLabel("a"), new RCPosition(1, 0)));
		
		assertThrows(CalcLayoutException.class, () -> jPanel.add(new JLabel("a"), new RCPosition(1, 8)));
		
		assertThrows(CalcLayoutException.class, () -> jPanel.add(new JLabel("a"), new RCPosition(6, 1)));
		
	}
	
	@Test
	void testInvalidColumnPositions() {
		JPanel jPanel = new JPanel(new CalcLayout());
		
		assertThrows(CalcLayoutException.class, () -> jPanel.add(new JLabel("a"), new RCPosition(1, 2)));
		
		assertThrows(CalcLayoutException.class, () -> jPanel.add(new JLabel("a"), new RCPosition(1, 5)));
		
	}
	

	
	@Test
	void testSettingExisting() {
		JPanel jPanel = new JPanel(new CalcLayout());
		
		jPanel.add(new JLabel("a"), new RCPosition(2, 3));
		
		assertThrows(CalcLayoutException.class, () -> jPanel.add(new JLabel("a"), new RCPosition(2, 3)));
	
	}
	
	@Test
	void testPrefferedSize() {
		JPanel p = new JPanel(new CalcLayout(2));
		JLabel l1 = new JLabel(""); l1.setPreferredSize(new Dimension(10,30));
		JLabel l2 = new JLabel(""); l2.setPreferredSize(new Dimension(20,15));
		p.add(l1, new RCPosition(2,2));
		p.add(l2, new RCPosition(3,3));
		Dimension dim = p.getPreferredSize();
		
        assertEquals(new Dimension(152, 158), dim);

	}
	
    @Test
    void testPreferredSize2() {
        JPanel p = new JPanel(new CalcLayout(2));
        JLabel l1 = new JLabel(""); l1.setPreferredSize(new Dimension(108, 15));
        JLabel l2 = new JLabel(""); l2.setPreferredSize(new Dimension(16, 30));
        p.add(l1, new RCPosition(1, 1));
        p.add(l2, new RCPosition(3, 3));
        Dimension dim = p.getPreferredSize();

        assertEquals(new Dimension(152, 158), dim);
    }
	
}
