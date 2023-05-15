package hr.fer.oprpp1.gui.calc;


import java.awt.Color;
import java.awt.Container;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import hr.fer.oprpp1.gui.calc.model.CalcModel;
import hr.fer.oprpp1.gui.calc.model.CalculatorInputException;
import hr.fer.oprpp1.gui.layouts.CalcLayout;
import hr.fer.oprpp1.gui.layouts.RCPosition;

/**
 * Class representation of GUI calculator
 *
 */
public class Calculator extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private static final int space = 5;
	
	private CalcModel calcModel;
	
	private Map<String, CalcButton> inverseOperations;
	
	private Stack<Double> stack;
		
	/**
	 * Constructor
	 */
	public Calculator() {
		super();
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setLocation(20,20);
		setSize(700, 400);
		setTitle("Java Calculator v1.0");
		
		this.calcModel = new CalcModelImpl();
		
		initGUI();
	
		setVisible(true);
		
		//pack();
	}
	
	/**
	 * GUI initialisation
	 */
	private void initGUI() {
		Container cp = getContentPane();
		
		cp.setLayout(new CalcLayout(space));
		
		JLabel display = new JLabel(calcModel.toString());
		display.setHorizontalAlignment(SwingConstants.RIGHT);
		display.setVerticalAlignment(SwingConstants.CENTER);
		display.setFont(display.getFont().deriveFont(30f));
		display.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		display.setBackground(Color.yellow);
		display.setOpaque(true);
		
		cp.add(display, "1,1");
		this.calcModel.addCalcValueListener(calcModel -> display.setText(calcModel.toString()));
		
		int c = 9;
		for (int i = 0; i <= 3; i++) {
			for (int j = 2; j >= 0; j--) {
				final int f = c;
				if (c == 0) {
					cp.add(new CalcButton(String.valueOf(c), b -> calcModel.insertDigit(f)), new RCPosition(i+2, j+1));
					break;
				}
				cp.add(new CalcButton(String.valueOf(c), b -> calcModel.insertDigit(f)), new RCPosition(i+2, j+3));
				c--;
			}
		}
		
		cp.add(new CalcButton("=", b -> {
			if (calcModel.getPendingBinaryOperation() != null) {
				calcModel.setActiveOperand(calcModel.getPendingBinaryOperation()
						.applyAsDouble(calcModel.getActiveOperand(), calcModel.getValue()));
			}
			calcModel.setPendingBinaryOperation(null);
			calcModel.setValue(calcModel.getActiveOperand());
		}), "1,6");
		
		cp.add(new CalcButton("/", (x,y) -> x / y, calcModel), "2,6");
		cp.add(new CalcButton("*", (x,y) -> x * y, calcModel), "3,6");
		cp.add(new CalcButton("-", (x,y) -> x - y, calcModel), "4,6");
		cp.add(new CalcButton("+", (x,y) -> x + y, calcModel), "5,6");
		
		cp.add(new CalcButton("+/-", b -> calcModel.swapSign()), "5,4");
		cp.add(new CalcButton(".", b -> calcModel.insertDecimalPoint()), "5,5");
		
		cp.add(new CalcButton("1/x", b -> calcModel.setValue(1 / calcModel.getValue())), "2,1");
		
		this.inverseOperations = new HashMap<>();
		this.inverseOperations.put("2,2", new CalcButton("sin", "arcsin",
				b -> {calcModel.setValue(Math.sin(calcModel.getValue()));},
				b -> {calcModel.setValue(Math.asin(calcModel.getValue()));},
				calcModel));
		this.inverseOperations.put("3,1", new CalcButton("log", "10^x",
				b -> {calcModel.setValue(Math.log10(calcModel.getValue()));},
				b -> {calcModel.setValue(Math.pow(10, calcModel.getValue()));},
				calcModel));
		this.inverseOperations.put("3,2", new CalcButton("cos", "arccos",
				b -> {calcModel.setValue(Math.cos(calcModel.getValue()));},
				b -> {calcModel.setValue(Math.acos(calcModel.getValue()));},
				calcModel));
		this.inverseOperations.put("4,1", new CalcButton("ln", "e^x",
				b -> {calcModel.setValue(Math.log(calcModel.getValue()));},
				b -> {calcModel.setValue(Math.pow(Math.E, calcModel.getValue()));},
				calcModel));
		this.inverseOperations.put("4,2", new CalcButton("tan", "arctan",
				b -> {calcModel.setValue(Math.tan(calcModel.getValue()));},
				b -> {calcModel.setValue(Math.atan(calcModel.getValue()));},
				calcModel));
		this.inverseOperations.put("5,1", new CalcButton("x^n", "x^(1/n)",
				(x,y) -> Math.pow(x, y),
				(x,y) -> Math.pow(x, 1/y),
				calcModel));
		this.inverseOperations.put("5,2", new CalcButton("ctg", "arcctg",
				b -> {calcModel.setValue(1 / Math.tan(calcModel.getValue()));},
				b -> {calcModel.setValue(1 / Math.atan(calcModel.getValue()));},
				calcModel));
		
		for (var o : inverseOperations.entrySet()) {
			cp.add(o.getValue(), o.getKey());
		}
		
		JCheckBox jCheckBox = new JCheckBox("INV");
		jCheckBox.setHorizontalAlignment(SwingConstants.CENTER);
		jCheckBox.setVerticalAlignment(SwingConstants.CENTER);
		jCheckBox.addActionListener(l -> { for (var o : inverseOperations.entrySet()) {o.getValue().inverse();}});
		
		cp.add(jCheckBox, "5,7");
		
		cp.add("1,7", new CalcButton("clr", b -> calcModel.clear()));
		cp.add("2,7", new CalcButton("reset", b -> calcModel.clearAll()));
		
		this.stack = new Stack<Double>();
		cp.add("3,7", new CalcButton("push", b -> this.stack.push(calcModel.getValue())));
		cp.add("4,7", new CalcButton("pop", b -> {
			if (this.stack.isEmpty())
				throw new CalculatorInputException("Stack is empty!");
			calcModel.setValue(this.stack.pop());
			}));
		
	}
	
		
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new Calculator());
	}

}
