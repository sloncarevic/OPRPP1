package hr.fer.oprpp1.math;

/**
 * Class representation of complex polynomial 
 *
 */
public class ComplexPolynomial {

	private final Complex[] factors;

	/**
	 * Constructor
	 * @param factors
	 * @throws NullPointerException when param is null
	 */
	public ComplexPolynomial(Complex ...factors) {
		if (factors == null) throw new NullPointerException("Factors can't be null!");
		this.factors = factors;
	}
	
	/**
	 * @return Returns order of this polynom; eg. For (7+2i)z^3+2z^2+5z+1 returns 3
	 */
	public short order() {
		return (short) (this.factors.length - 1);
	}
	
	/**
	 * Computes a new polynomial this*p
	 * @param p
	 * @return Complex polynomial
	 * @throws NullPointerException when param is null
	 */
	public ComplexPolynomial multiply(ComplexPolynomial p) {
		if (p == null) throw new NullPointerException("Complex polynomial can't be null!");
		
		int size = this.order() + p.order() + 1;
		Complex[] resultFactors = new Complex[size];
		for (int i = 0; i < size; i++)
			resultFactors[i] = Complex.ZERO;
 		
		for (int i = 0, li = this.factors.length; i < li; i++) {
			for (int j = 0, lj = p.factors.length; j < lj; j++) {
				resultFactors[i+j] = resultFactors[i+j].add(factors[i].multiply(p.factors[j]));
			}
		}
		
		return new ComplexPolynomial(resultFactors);
	}
	

	/**
	 * Computes first derivative of this polynomial; for example, for 
	 * (7+2i)z^3+2z^2+5z+1 returns (21+6i)z^2+4z+5
	 * @return Complex polynomial
	 */
	public ComplexPolynomial derive() {
		Complex[] resultFactors = new Complex[this.factors.length - 1];
		
		for (int i = 1, l = this.factors.length; i < l; i++) {
			resultFactors[i-1] = this.factors[i].multiply(new Complex(i, 0));
		}
		
		return new ComplexPolynomial(resultFactors);
	}
	

	/**
	 * Computes polynomial value at given point z
	 * @param z
	 * @return Complex number
	 * @throws NullPointerException when param is null
	 */
	public Complex apply(Complex z) {
		if (z == null) throw new NullPointerException("Complex z can't be null!");
		
		Complex result = this.factors[0];
		
		for (int i = 1, l = this.factors.length; i < l; i++) {
			result = result.add(z.power(i).multiply(this.factors[i]));
		}

		return result;
		
	}
	
	@Override
	public String toString() {
		String out = "";
		int i;
		for (i = this.factors.length-1; i > 0; i--) {
			out += "(" + this.factors[i] + ")" + "*z^" + i + "+";
		}
		out += "(" + this.factors[i] + ")" + "*z^" + i;
		return out;
	}
	
}
