package hr.fer.oprpp1.math;

/**
 * Class representation of complex polynomial - format z0*(z-z1)*(z-z2)*...
 *
 */
public class ComplexRootedPolynomial {

	private final Complex constant;
	
	private final Complex[] roots;
	
	/**
	 * Constructor
	 * @param constant
	 * @param roots
	 * @throws NullPointerException when either of params is null
	 */
	public ComplexRootedPolynomial(Complex constant, Complex ... roots) {
		if (constant == null) throw new NullPointerException("Constant can't be null!");
		if (roots == null) throw new NullPointerException("Roots can't be null!");
		
		this.constant = constant;
		this.roots = roots;
	}
	
	/**
	 * Computes polynomial value at given point z
	 * @param z
	 * @return returns Complex number
	 * @throws NullPointerException when param is null
	 */
	public Complex apply(Complex z) {
		if (z == null) throw new NullPointerException("Complex z can't be null!");
		
		Complex result = Complex.ONE;
		for (int i = 0, l = this.roots.length; i < l; i++) {
			result = result.multiply(z.sub(roots[i]));
		}
		return result;
	}
	

	/**
	 * Converts this representation to ComplexPolynomial type
	 * @return ComplexPolynomial 
	 */
	public ComplexPolynomial toComplexPolynom() {
		ComplexPolynomial result = new ComplexPolynomial(this.constant);
		for (int i = 0, l = this.roots.length; i < l; i++) {
			result = result.multiply(new ComplexPolynomial(roots[i].negate(), Complex.ONE));
		}
		return result;
	}
	
	@Override
	public String toString() {
		String out = "" + constant;
		for (int i = 0, l = this.roots.length; i < l; i++) {
			out += "*(z-(" + roots[i] + "))";
		}
		return out;
	}
	

	/**
	 * finds index of closest root for given complex number z that is within
	 * threshold; if there is no such root, returns -1
	 * first root has index 0, second index 1, etc
	 * 
	 * @param z
	 * @param treshold
	 * @return int index of closest root
	 * @throws NullPointerException when param is null
	 */
	public int indexOfClosestRootFor(Complex z, double treshold) {
		if (z == null) throw new NullPointerException("Complex z can't be null!");
		
		int index = -1;
		
		for (int i = 0, l = this.roots.length; i < l; i++) {
			if (z.sub(roots[i]).module() < treshold) {
				treshold = z.sub(roots[i]).module();
				index = i;
			}
		}
		
		return index;
	}

	
}
