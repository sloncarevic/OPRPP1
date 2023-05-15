package hr.fer.oprpp1.fractals;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.oprpp1.math.Complex;
import hr.fer.oprpp1.math.ComplexPolynomial;
import hr.fer.oprpp1.math.ComplexRootedPolynomial;
import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;

/**
 * Class representation for calcualting fractals derived from Newton-Raphson iteration - with parallel working threads
 *
 */
public class NewtonParallel {
	
	
	public static void main(String[] args) {
		
		int workers = 0;
		int tracks = 0;
		
		try {
			for (int i = 0; i < args.length; i++) {
				if(args[i].contains("--workers=")) {
					workers = Integer.parseInt(args[i].replace("--workers=", "").trim());
				}
				else if (args[i].contains("-w")) {
					workers = Integer.parseInt(args[i].replace("-w", "").trim());
				}
				else if(args[i].contains("--tracks=")) {
					tracks = Integer.parseInt(args[i].replace("--tracks=", "").trim());
				}
				else if (args[i].contains("-t")) {
					tracks = Integer.parseInt(args[i].replace("-t", "").trim());
				} else {
					System.out.println("Invalid argument!");
					return;
				}
			}
		} catch (NumberFormatException e) {
			System.err.println("Can't parse argument\n" + e.getMessage());
			return;
		}
		
		if (workers == 0) 
			workers = Runtime.getRuntime().availableProcessors();
		if(tracks == 0)
			tracks = Runtime.getRuntime().availableProcessors() * 4;
		
		System.out.println("Welcome to Newton-Raphson iteration-based fractal viewer.");
		System.out.println("Please enter at least two roots, one root per line. Enter 'done' when done.");
		
		System.out.println("Number of workers: " + workers);
		System.out.println("Number of tracks: " + tracks);
		
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
				
		FractalViewer.show(new MyProducer(new ComplexRootedPolynomial(Complex.ONE, roots.toArray(Complex[]::new)), workers, tracks));

	}
	
	
	/**
	 * Class implementation of Runnable - defines thread job for calculating color values of pixels
	 *
	 */
	public static class PosaoIzracuna implements Runnable {
		
		private static final double convergenceThreshold = 0.001;
		private static final double rootThreshold = 0.002;
		
		double reMin;
		double reMax;
		double imMin;
		double imMax;
		int width;
		int height;
		int yMin;
		int yMax;
		int m;
		short[] data;
		AtomicBoolean cancel;
		public static PosaoIzracuna NO_JOB = new PosaoIzracuna();
		
		private ComplexRootedPolynomial complexRootedPolynomial;
		
		/**
		 * Default constructor
		 */
		private PosaoIzracuna() {
		}
		
		/**
		 * Constructor
		 * @param reMin
		 * @param reMax
		 * @param imMin
		 * @param imMax
		 * @param width
		 * @param height
		 * @param yMin
		 * @param yMax
		 * @param m
		 * @param data
		 * @param cancel
		 * @param complexRootedPolynomial
		 */
		public PosaoIzracuna(double reMin, double reMax, double imMin,
				double imMax, int width, int height, int yMin, int yMax, 
				int m, short[] data, AtomicBoolean cancel, ComplexRootedPolynomial complexRootedPolynomial) {
			super();
			this.reMin = reMin;
			this.reMax = reMax;
			this.imMin = imMin;
			this.imMax = imMax;
			this.width = width;
			this.height = height;
			this.yMin = yMin;
			this.yMax = yMax;
			this.m = m;
			this.data = data;
			this.cancel = cancel;
			this.complexRootedPolynomial = complexRootedPolynomial;
		}
		
		

		@Override
		public void run() {
			
			ComplexPolynomial polynomial = this.complexRootedPolynomial.toComplexPolynom();
			ComplexPolynomial derived = polynomial.derive();
			
			int offset = this.yMin * this.width;
			short index;
			
			for (int y = this.yMin; y < this.yMax+1; y++) {
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
					this.data[offset++] = (short)(index + 1);
					
				}
			}
			
		}
		
	}
	
	
	/**
	 * Class implementation of IFractalProducer
	 *
	 */
	public static class MyProducer implements IFractalProducer {
		
		private ComplexRootedPolynomial complexRootedPolynomial;
		private int noworkers;
		private int notracks;
		
		/**
		 * Constructor
		 * @param complexRootedPolynomial
		 * @param noworkers
		 * @param notracks
		 */
		public MyProducer(ComplexRootedPolynomial complexRootedPolynomial, int noworkers, int notracks) {
			this.complexRootedPolynomial = complexRootedPolynomial;
			this.noworkers = noworkers;
			this.notracks = notracks;
		}

		@Override
		public void produce(double reMin, double reMax, double imMin, double imMax,
				int width, int height, long requestNo, IFractalResultObserver observer, AtomicBoolean cancel) {
			
			if (this.notracks > height) this.notracks = height;
			
			int m = 16*16*16;
			
			short[] data = new short[width * height];
			
			int brojYPoTraci = height / notracks;
			
			final BlockingQueue<PosaoIzracuna> queue = new LinkedBlockingQueue<>();
			
			Thread[] workers = new Thread[this.noworkers];
			for (int i = 0; i < workers.length; i++) {
				workers[i] = new Thread(new Runnable() {
					
					@Override
					public void run() {
						while (true) {
							PosaoIzracuna p = null;
							try {
								p = queue.take();
								if (p == PosaoIzracuna.NO_JOB) break;
							} catch (InterruptedException e) {
								continue;
							}
							p.run();
						}
					}
				});
				
			}
			for (int i = 0; i < workers.length; i++) {
				workers[i].start();
			}
			
			for (int i = 0; i < this.notracks; i++) {
				int yMin = i*brojYPoTraci;
				int yMax = (i+1)*brojYPoTraci-1;
				if(i==this.notracks - 1)
					yMax = height-1;
				PosaoIzracuna posao = new PosaoIzracuna(reMin, reMax, imMin, imMax, 
						width, height, yMin, yMax, m, data, cancel, this.complexRootedPolynomial);
				
				while(true) {
					try {
						queue.put(posao);
						break;
					} catch (InterruptedException e) {
						
					}
				}
			}
			for (int i = 0; i < workers.length; i++) {
				while(true) {
					try {
						queue.put(PosaoIzracuna.NO_JOB);
						break;
					} catch (Exception e) {
		
					}
				}
			}
			
			for (int i = 0; i < workers.length; i++) {
				while(true) {
					try {
						workers[i].join();
						break;
					} catch (InterruptedException e) {
						
					}
				}
			}
		
			observer.acceptResult(data, (short)(complexRootedPolynomial.toComplexPolynom().order() + 1), requestNo);

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
