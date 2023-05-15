package hr.fer.oprpp1.gui.calc;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.DoubleBinaryOperator;

import javax.swing.BorderFactory;
import javax.swing.JButton;

import hr.fer.oprpp1.gui.calc.model.CalcModel;
import hr.fer.oprpp1.gui.calc.model.CalculatorInputException;

/**
 * Class representation of calculator button
 * Extends JButton
 *
 */
public class CalcButton extends JButton{

	private static final long serialVersionUID = 1L;
	
	/**
	 * Default constructor
	 * @param text
	 */
	public CalcButton(String text) {
		super(text);
		
		initGUI();
	}
	
	
	/**
	 * Overloaded constructor for digit buttons
	 * @param text
	 * @param actionListener
	 */
	public CalcButton(String text, ActionListener actionListener) {
		super(text);
		
		try { 
			addActionListener(actionListener);
		} catch (CalculatorInputException e) {
			System.err.println(e.getMessage());
		}
	
		initGUI();
	}
	
	/**
	 * Overloaded constructor for binary operations
	 * @param text
	 * @param operator
	 * @param calcModel
	 */
	public CalcButton(String text, DoubleBinaryOperator operator, CalcModel calcModel) {
		super(text);
		
		try {
			addActionListener(createMyActionListener(operator, calcModel));
		} catch (NullPointerException e) {
			System.err.println(e.getMessage());
		} catch (CalculatorInputException e) {
			System.err.println(e.getMessage());
		}
		
		initGUI();
	}
	
	private boolean isInv;
	
	private String text;
	
	private String inverseText;
	
	private ActionListener operation;
	
	private ActionListener inverseOperation;
	
	/**
	 * Overloaded constructor for invertible operations
	 * @param text
	 * @param inverseText
	 * @param operator
	 * @param inverseOperator
	 * @param calcModel
	 */
	public CalcButton(String text, String inverseText, ActionListener operator, 
			ActionListener inverseOperator, CalcModel calcModel) {
		
		super(text);
		
		this.text = text;
		this.inverseText = inverseText;
		this.operation = operator;
		
		addActionListener(operation);

		this.inverseOperation = inverseOperator;
		
		isInv = false;
		
		initGUI();
				
	}
	
	/**
	 * Overloaded constructor for x^n
	 * @param text
	 * @param inverseText
	 * @param operator
	 * @param inverseOperator
	 * @param calcModel
	 */
	public CalcButton(String text, String inverseText, DoubleBinaryOperator operator, 
			DoubleBinaryOperator inverseOperator, CalcModel calcModel) {

		super(text);

		this.text = text;
		this.inverseText = inverseText;
		
		this.operation = createMyActionListener(operator, calcModel);

		addActionListener(operation);

		this.inverseOperation = createMyActionListener(inverseOperator, calcModel);

		isInv = false;

		initGUI();

	}
		
	/**
	 * @param operator
	 * @param calcModel
	 * @return Action Listener from Double Binary Operator
	 */
	private ActionListener createMyActionListener(DoubleBinaryOperator operator, CalcModel calcModel) {
			return new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				///
				if (calcModel.isActiveOperandSet() && calcModel.getPendingBinaryOperation() != null && !calcModel.hasFrozenValue()) {
					if (calcModel.hasFrozenValue()) throw new CalculatorInputException("Frozen value!");
					if (calcModel.getPendingBinaryOperation() == null) throw new CalculatorInputException("Pending binary operation is null!");
					double result = calcModel.getPendingBinaryOperation()
							.applyAsDouble(calcModel.getActiveOperand(), calcModel.getValue());
					calcModel.freezeValue(String.valueOf(result));
					calcModel.setActiveOperand(calcModel.getValue());

				} else {
					calcModel.setActiveOperand(calcModel.getValue());
				}
				
				calcModel.setPendingBinaryOperation(operator);
				calcModel.clear();
			}
		};

	}
	
	/**
	 * Inverting invertible buttons
	 */
	public void inverse() {
		if (isInv) {
			setText(text);
			removeActionListener(this.inverseOperation);
			addActionListener(operation);
			this.isInv = false;
		} else {
			setText(inverseText);
			removeActionListener(this.operation);
			addActionListener(inverseOperation);
			this.isInv = true;
		}
	}
	
	/**
	 * GUI initialisation
	 */
	private void initGUI() {
		setBackground(Color.LIGHT_GRAY);
		setAlignmentX(CENTER);
		setAlignmentY(CENTER);
		setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		setFont(this.getFont().deriveFont(25f));
	}

}
