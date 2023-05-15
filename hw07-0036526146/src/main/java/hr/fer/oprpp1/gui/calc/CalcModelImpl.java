package hr.fer.oprpp1.gui.calc;

import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleBinaryOperator;

import hr.fer.oprpp1.gui.calc.model.CalcModel;
import hr.fer.oprpp1.gui.calc.model.CalcValueListener;
import hr.fer.oprpp1.gui.calc.model.CalculatorInputException;

/**
 * Class implementation of calculator model
 * Implements CalcModel
 */
public class CalcModelImpl implements CalcModel {
	
	private boolean editable;
	
	private boolean sign;
	
	private String input;
	
	private double value;
	
	private String frozenValue;
	
	private Double activeOperand;
	
	private DoubleBinaryOperator pendingOperation;
	
	private List<CalcValueListener> listeners = new ArrayList<CalcValueListener>();
	
	/**
	 * Constructor
	 */
	public CalcModelImpl() {
		this.editable = true;
		this.sign = true;
		this.input = "";
		this.value = 0;
		this.frozenValue = null;
		this.activeOperand = null;
		this.pendingOperation = null;
	}
	
	/**
	 * 
	 */
	private void updateListeners() {
		for (var l : listeners) 
			l.valueChanged(this);
	}

	@Override
	public void addCalcValueListener(CalcValueListener l) {
		if (l == null) throw new NullPointerException("CalcValueListener l is null!");
		this.listeners.add(l);	
	}

	@Override
	public void removeCalcValueListener(CalcValueListener l) {
		if (l == null) throw new NullPointerException("CalcValueListener l is null!");
		this.listeners.remove(l);
	}

	@Override
	public double getValue() {
		//positive negative??
		return this.value;
	}

	@Override
	public void setValue(double value) {
		this.editable = false;
		this.sign = value >= 0;
		this.input = Double.toString(value);
		this.value = value;
		this.frozenValue = null;
		
		this.updateListeners();
	}

	@Override
	public boolean isEditable() {
		return this.editable;
	}

	@Override
	public void clear() {
		this.editable = true;
		this.sign = true;
		this.input = "";
		this.value = 0;
		
		this.updateListeners();
	}

	@Override
	public void clearAll() {
		this.clear();
		this.frozenValue = null;
		this.activeOperand = null;
		this.pendingOperation = null;
		
		this.updateListeners();
	}

	@Override
	public void swapSign() throws CalculatorInputException {
		if (!this.editable) throw new CalculatorInputException("Not editable!");
		if (sign)
			this.input = "-" + this.input;
		else
			this.input = this.input.substring(1);
		this.sign = !this.sign;
		this.value = this.value * -1;
		
		this.frozenValue = null;
		
		this.updateListeners();
	}

	@Override
	public void insertDecimalPoint() throws CalculatorInputException {
		if (!this.editable) throw new CalculatorInputException("Not editable!");
		if (this.input.isBlank()) throw new CalculatorInputException("Input is blank!");
		
//		if (this.input.endsWith(".0")) {
//			this.input = this.input.replace(".0", "");
//		}

		
		if (this.input.contains(".")) throw new CalculatorInputException("Input contains decimal point!");
		
		try {
			String concInput = this.input + ".";
			double concValue = Double.parseDouble(concInput);
			
			//this.input = String.valueOf(concValue).replace(".0", ".");
			this.input = concInput;//.replace(".0", ".");
			this.value = concValue;
			this.frozenValue = null;
			
			this.updateListeners();
			
		} catch (NumberFormatException e) {
			throw new CalculatorInputException("Can't parse new value!");
		}
		
	}

	@Override
	public void insertDigit(int digit) throws CalculatorInputException, IllegalArgumentException {
		if (!this.editable) throw new CalculatorInputException("Not editable!");
		if (!Character.isDigit((char) digit + '0')) throw new IllegalArgumentException("Not a digit!");
		
		try {
			String concInput = this.input + String.valueOf(digit);
			//if (concInput.equals("00")) concInput = "0";
			if (concInput.startsWith("0") && concInput.length() > 1 && !concInput.startsWith("0."))
				concInput = concInput.substring(1);
			double concValue = Double.parseDouble(concInput);
			if (Double.isFinite(concValue)) {
				//this.input = String.valueOf(concValue).replace(".0", "");
				this.input = concInput;//.replace(".0", "");
				this.value = concValue;
				this.frozenValue = null;
				
				//System.out.println(value);
				
				this.updateListeners();
				
			} else {
				throw new CalculatorInputException("New value is not finite!");
			}
			
		} catch (NumberFormatException e) {
			throw new CalculatorInputException("Can't parse inserted digit!");
		}
		
	}

	@Override
	public boolean isActiveOperandSet() {
		return this.activeOperand != null;
	}

	@Override
	public double getActiveOperand() throws IllegalStateException {
		if (this.activeOperand == null) throw new IllegalStateException("Active operand isn't set!");
		return this.activeOperand;
	}

	@Override
	public void setActiveOperand(double activeOperand) {
		this.activeOperand = activeOperand;
		this.editable = false;
		
		this.updateListeners();
	}

	@Override
	public void clearActiveOperand() {
		this.activeOperand = null;
		
		this.updateListeners();
	}

	@Override
	public DoubleBinaryOperator getPendingBinaryOperation() {
		return this.pendingOperation;
	}

	@Override
	public void setPendingBinaryOperation(DoubleBinaryOperator op) {

		this.pendingOperation = op;
		
		this.updateListeners();
	}
	
	@Override
	public String toString() {
		if (this.frozenValue != null)
			return this.frozenValue;
		else {
			if (this.input.isBlank() || this.input.equals("-"))
				if (sign)
					return "0";
				else
					return "-0";
			else
				return this.input;
		}
	}
	
	@Override
	public void freezeValue(String value) {
		if (value == null) throw new NullPointerException("Value is null");
		this.frozenValue = value;
		
	}
	
	@Override
	public boolean hasFrozenValue() {
		return this.frozenValue != null;
	}

}
