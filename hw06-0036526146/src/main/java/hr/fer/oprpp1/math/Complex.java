package hr.fer.oprpp1.math;

import java.util.ArrayList;
import java.util.List;

/**
 * Class representation of complex number
 *
 */
public class Complex {
	
	public static final Complex ZERO = new Complex(0,0);
	
	public static final Complex ONE = new Complex(1,0);
	
	public static final Complex ONE_NEG = new Complex(-1,0);
	
	public static final Complex IM = new Complex(0,1);
	
	public static final Complex IM_NEG = new Complex(0,-1);
	
	/**
	 * Real part
	 */
	private final double re;
	
	/**
	 * Imaginary part
	 */
	private final double im;
	
	/**
	 * Defalut constructor
	 */
	public Complex() {
		this(0, 0);
	}
	
	/**
	 * Constructor
	 * @param re
	 * @param im
	 */
	public Complex(double re, double im) {
		this.re = re;
		this.im = im;
	}
	
	/**
	 * @return returns module of complex number
	 */
	public double module() {
		return Math.sqrt(re*re + im*im);
	}
	
	/**
	 * @param c
	 * @return returns this*c
	 * @throws NullPointerException when complex number is null
	 */
	public Complex multiply(Complex c) {
		if (c == null) throw new NullPointerException("Complex number can't be null!");
		
		return new Complex(this.re * c.re - this.im * c.im, this.re * c.im + this.im * c.re);
	}
	

	/**
	 * @param c
	 * @return returns this/c
	 * @throws NullPointerException when complex number is null
	 * @throws ArithmeticException when dividing by zero
	 */
	public Complex divide(Complex c) {
		if (c == null) throw new NullPointerException("Complex number can't be null!");
		
		if ((c.re * c.re + c.im * c.im) == 0) throw new ArithmeticException("Division by zero!");
		
		return new Complex((this.re * c.re + this.im * c.im) / (c.re * c.re + c.im * c.im),
							(this.im * c.re - this.re * c.im) / (c.re * c.re + c.im * c.im));
	}
	
	/**
	 * @param c
	 * @return returns this+c
	 * @throws NullPointerException when complex number is null
	 */
	public Complex add(Complex c) {
		if (c == null) throw new NullPointerException("Complex number can't be null!");
		
		return new Complex(this.re + c.re, this.im + c.im);
	}
	

	/**
	 * @param c
	 * @return returns this-c
	 * @throws NullPointerException when complex number is null
	 */
	public Complex sub(Complex c) {
		if (c == null) throw new NullPointerException("Complex number can't be null!");
		
		return new Complex(this.re - c.re, this.im - c.im);
	}
	

	/**
	 * @return returns -this
	 */
	public Complex negate() {
		return new Complex(-this.re, -this.im);
	}
	
	/**
	 * @param n
	 * @return returns this^n, n is non-negative integer
	 * @throws IllegalArgumentException when param n is negative
	 */
	public Complex power(int n) {
		if (n < 0) throw new IllegalArgumentException("Negative power!");
		
		double modulen = Math.pow(this.module(), n);
		double angle = Math.atan2(this.im, this.re);
		angle = angle >= 0 ? angle : angle + 2 * Math.PI;
		
		return new Complex(modulen * Math.cos(n * angle),
							modulen * Math.sin(n * angle));
	}
	
	/**
	 * @param n
	 * @return returns n-th root of this, n is positive integer
	 * @throws IllegalArgumentException when param n is not a natural number
	 */
	public List<Complex> root(int n) {
		if (n < 1) throw new IllegalArgumentException("Root natural number");
		
		List<Complex> roots = new ArrayList<>();
		
		double moduler = Math.pow(this.module(), Double.valueOf(1/n));
		double angle = Math.atan2(this.im, this.re);
		angle = angle >= 0 ? angle : angle + 2 * Math.PI;
		
		for (int i = 0; i < n; i++)
			roots.add(new Complex(moduler * Math.cos((angle + 2* Math.PI * i) / n),
									moduler * Math.sin((angle + 2* Math.PI * i) / n )));
		
		return roots;
	}
	
	@Override
	public String toString() {
		return "(" + this.re + (this.im < 0 ? "" : "+") + this.im + "i" + ")";
	}
	

}
