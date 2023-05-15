package hr.fer.oprpp1.custom.collections.demo;

import hr.fer.oprpp1.custom.collections.MyEmptyStackException;
import hr.fer.oprpp1.custom.collections.ObjectStack;

/**
 *  Command-line application which accepts a single command-line argument: expression which should be evaluated
 *
 */
public class StackDemo {
	
	/**
	 * @param str
	 * @return returns true if string can be parsed to integer, false otherwise
	 */
	public static boolean isNumeric(String str) {
		try {
			Integer.parseInt(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
	/**
	 * @param args
	 * 
	 */
	public static void main(String[] args) {
		
		if (args.length != 1) throw new IllegalArgumentException();
		
		String[] expression = args[0].split(" ");
		
		ObjectStack stack = new ObjectStack();
		
		for (int i = 0; i < expression.length; i++) {
			if (isNumeric(expression[i])) stack.push(expression[i]);
			else if (expression[i].matches("\\+|\\-|\\*|\\/|\\%")) {
				try {
					int operand2 = Integer.parseInt(stack.pop().toString());
					int operand1 = Integer.parseInt(stack.pop().toString());
					
					if (expression[i].equals("+")) {
						stack.push(operand1 + operand2);
					} else if (expression[i].equals("-")) {
						stack.push(operand1 - operand2);
					} else if (expression[i].equals("*")) {
						stack.push(operand1 * operand2);
					} else if (expression[i].equals("/")) {
						if (operand2 == 0) throw new ArithmeticException();
						stack.push(operand1 / operand2);
					} else if (expression[i].equals("%")) {
						if (operand2 == 0) throw new ArithmeticException();
						stack.push(operand1 % operand2);
					}		
				} catch (MyEmptyStackException e) {
					System.err.println(e.getMessage());
				}
			} else {
				throw new IllegalArgumentException();
			}
		}
		
		if (stack.size() == 1) {
			System.out.println(stack.pop());
		} else {
			System.err.println("Stack size not equal to 1");
		}
	}
}
