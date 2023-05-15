package hr.fer.oprpp1.fractals;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.oprpp1.math.Complex;
import hr.fer.oprpp1.math.ComplexPolynomial;
import hr.fer.oprpp1.math.ComplexRootedPolynomial;
import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;

/**
 * Class representation for calcualting fractals derived from Newton-Raphson iteration
 *
 */
public class Newton {
	
	public static void main(String[] args) {
		
		System.out.println("Welcome to Newton-Raphson iteration-based fractal viewer.");
		System.out.println("Please enter at least two roots, one root per line. Enter 'done' when done.");
		
		int rootNumber = 0;
		List<Complex> roots = new ArrayList<>();
	
		
		try {
			Scanner sc = new Scanner(System.in);
			
			while (true) {
				System.out.print("Root " + (rootNumber+1) + "> ");
				String line = sc.nextLine().strip().replaceAll(" ", "");
				
				if (line.contains("done")) {
					if (rootNumber < 2) {
						System.out.println("Less than 2 roots entered!");
						continue;
					}
					System.out.println("Image of fractal will appear shortly. Thank you.");
					break;
				}
				
				try {
					Complex complex = inputParser(line);
					
					//System.out.println(complex.toString());
					
					roots.add(complex);
					
					rootNumber++;		
					
				} catch (IllegalArgumentException e) {
					System.out.println(e.getMessage());
					continue;
				}	
			}
			
			sc.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
				
		FractalViewer.show(new MyProducer(new ComplexRootedPolynomial(Complex.ONE, roots.toArray(s -> new Complex[s]))));
	}

	
	
	
	/**
	 * Class implementation of IFractalProducer
	 *
	 */
	private static class MyProducer implements IFractalProducer {
		
		/**
		 * Convergence threshold
		 */
		private static final double convergenceThreshold = 0.001;
		/**
		 * Root threshold
		 */
		private static final double rootThreshold = 0.002;
		
		private ComplexRootedPolynomial complexRootedPolynomial;
		
		/**
		 * Constructor
		 * @param complexRootedPolynomial
		 */
		public MyProducer(ComplexRootedPolynomial complexRootedPolynomial) {
			this.complexRootedPolynomial = complexRootedPolynomial;
		}
		
		@Override
		public void produce(double reMin, double reMax, double imMin, double imMax,
				int width, int height, long requestNo, IFractalResultObserver observer, AtomicBoolean cancel) {
			
			int m = 16*16*16;
			int offset = 0;
			short[] data = new short[width * height];
			short index;
			
			ComplexPolynomial polynomial = this.complexRootedPolynomial.toComplexPolynom();
			ComplexPolynomial derived = polynomial.derive();
			
			for (int y = 0; y < height; y++) {
				if(cancel.get()) break;
				for (int x = 0; x < width; x++) {
					double cre = x / (width-1.0) * (reMax - reMin) + reMin;
					double cim = (height-1.0-y) / (height-1) * (imMax-imMin) + imMin;
					
					Complex zn = new Complex(cre, cim);
					Complex znold;
					
					double module;
					int iters = 0;
					
					do {
						znold = zn;
						
						Complex numerator = polynomial.apply(zn);
						Complex denominator = derived.apply(zn);

						Complex fraction = numerator.divide(denominator);
						zn = zn.sub(fraction);

						module = znold.sub(zn).module();
						
						iters++;
					} while (iters < m && module > convergenceThreshold);
					
					index = (short) (this.complexRootedPolynomial.indexOfClosestRootFor(zn, rootThreshold));
					data[offset++] = (short)(index + 1);
					
				}
			}
			
			observer.acceptResult(data, (short)(polynomial.order() +1), requestNo);
			
		}
		
	}
	
	/**
	 * @param input
	 * @return Returns complex number parsed form input
	 * @throws IllegalArgumentException when argument can't be parsed
	 */
	private static Complex inputParser(String input) {

		String[] inputarr = new String[2];
		
		if(input.matches("^-?\\d+$")) { //only real part
			inputarr[0] = input;
			inputarr[1] = "0";
		}
		else if(input.matches("^-?\\d+i$") || input.matches("^-?i\\d+$") || input.matches("^+?-?i$")) { //only imaginary part
			inputarr[0] = "0";
			inputarr[1] = input.replace("i", "");
			if (inputarr[1].equals("")) inputarr[1] = "1";
			if (inputarr[1].equals("-")) inputarr[1] = "-1";
		}
		else if (input.contains("+")) {
			int i = input.lastIndexOf("+");
			inputarr[0] = input.substring(0, i);
			inputarr[1] = input.substring(i+1).replace("i", "");
		}
		else if (input.contains("-")) {
			int i = input.lastIndexOf("-");
			inputarr[0] = input.substring(0, i);
			inputarr[1] = input.substring(i).replace("i", "");
			if (inputarr[1].equals("")) inputarr[1] = "1";
			if (inputarr[1].equals("-")) inputarr[1] = "-1";
		}
		else {
			throw new IllegalArgumentException("Can't parse input");
		}
		
		Complex complex = null;
		try {
			complex = new Complex(Double.parseDouble(inputarr[0]), Double.parseDouble(inputarr[1]));
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Can't parse entered root!");
		}
		
		return complex;
	}
	
}
