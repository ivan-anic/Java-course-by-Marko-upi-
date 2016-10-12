package hr.fer.zemris.java.fractals;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import hr.fer.zemris.java.fractals.models.Complex;
import hr.fer.zemris.java.fractals.models.ComplexPolynomial;
import hr.fer.zemris.java.fractals.models.ComplexRootedPolynomial;
import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;

/**
 * Simulates the Newton-Raphson iteration algorythm, and produces the visual
 * representation of fractals.
 * 
 * Uses the following classes:
 * <ul>
 * <li>{@linkplain Complex}</li>
 * <li>{@linkplain ComplexPolynomial}</li>
 * <li>{@linkplain ComplexRootedPolynomial}</li>
 * </ul>
 */
public class Newton {

	/**
	 * Maximum iterations allowed for the Newton-Rapshon iteration algorithm.
	 */
	private static final int MAX_ITERATIONS = Integer.MAX_VALUE;
	/** The predefined convergence threshold for Newton-Rapshon iteration. */
	private static final double CONVERGENCE_THRESHOLD = 0.002;

	/** The given polynom, represented by its roots. */
	private static ComplexRootedPolynomial rootedPolynom;
	/** The given polynom, represented by its derivation. */
	private static ComplexPolynomial derivedPolynom;

	/**
	 * The main method, first one to be executed when the program starts.
	 * 
	 * @param args
	 *            - the input arguments, not used here
	 */
	public static void main(String[] args) {

		System.out.println("Welcome to Newton-Raphson iteration-based fractal viewer. \n"
				+ "Please enter at least two roots, one root per line. Enter 'done' when done.");

		Scanner sc = new Scanner(System.in);
		String line;
		int cnt = 0;
		List<Complex> complexRoots = new ArrayList<>();

		System.out.print("Root " + cnt++ + "> ");

		while (!(line = sc.nextLine()).equals("done")) {
			Complex c = Complex.parse(line.trim());
			complexRoots.add(c);

			System.out.print("Root " + cnt++ + "> ");
		}
		if (complexRoots.size() < 2) {
			System.out.println("You have to enter at least two roots!");
			System.exit(1);
		}

		System.out.println("Image of fractal will appear shortly. Thank you.");

		rootedPolynom = new ComplexRootedPolynomial(complexRoots.toArray(new Complex[complexRoots.size()]));
		derivedPolynom = rootedPolynom.toComplexPolynom().derive();

		FractalViewer.show(new NewtonRapshonProducer());

		sc.close();

	}

	/**
	 * Prepares the necessary data for Newton-Raphson iteration.
	 */
	private static class NewtonRapshonComputation implements Callable<Void> {

		/** The minimum real value. */
		double reMin;
		/** The maximum real value. */
		double reMax;
		/** The minimum imaginary value. */
		double imMin;
		/** The maximum imaginary value. */
		double imMax;
		/** The width of the drawing window. */
		int width;
		/** The height of the drawing window. */
		int height;
		/** The minimum value of Y-axis. */
		int yMin;
		/** The maximum value of Y-axis. */
		int yMax;
		/** The data storage for calculation results. */
		short[] data;

		/**
		 * Creates new computation class with provided arguments.
		 * 
		 * @param reMin
		 *            - the minimum real value
		 * @param reMax
		 *            - the maximum real value
		 * @param imMin
		 *            - the minimum imaginary value
		 * @param imMax
		 *            - the maximum imaginary value
		 * @param width
		 *            - the width of the drawing window
		 * @param height
		 *            - the height of the drawing window
		 * @param yMin
		 *            - the minimum value of Y-axis
		 * @param yMax
		 *            - the maximum value of Y-axis
		 * @param data
		 *            - the data storage for calculation results
		 */
		public NewtonRapshonComputation(double reMin, double reMax, double imMin, double imMax,
				int width, int height, int yMin, int yMax,
				short[] data) {

			this.reMin = reMin;
			this.reMax = reMax;
			this.imMin = imMin;
			this.imMax = imMax;
			this.width = width;
			this.height = height;
			this.yMin = yMin;
			this.yMax = yMax;
			this.data = data;
		}

		@Override
		public Void call() throws Exception {
			int offset = yMin * width;

			for (int y = yMin; y <= yMax; y++) {
				for (int x = 0; x < width; x++) {
					double zReal = x / (width - 1.0) * (reMax - reMin) + reMin;
					double zImaginary = (height - 1 - y) / (height - 1.0) * (imMax - imMin) + imMin;

					Complex zn = new Complex(zReal, zImaginary);
					Complex zn1 = new Complex();

					int cnt = 0;
					double module = 0;

					do {
						Complex numerator = rootedPolynom.apply(zn);
						Complex denominator = derivedPolynom.apply(zn);
						Complex fraction = numerator.divide(denominator);

						zn1 = zn.sub(fraction);
						module = zn1.sub(zn).module();
						zn = zn1;

					} while ((++cnt < MAX_ITERATIONS) && (module > CONVERGENCE_THRESHOLD));

					short index = (short) rootedPolynom.indexOfClosestRootFor(zn1, CONVERGENCE_THRESHOLD);
					data[offset++] = (short) ((index == -1) ? 0 : index +1 );
				}
			}

			return null;
		}
	}

	/**
	 * An implementation of {@linkplain IFractalProducer}, produces the
	 * visualisation of fractals given the desired roots of a polynom.
	 */
	private static class NewtonRapshonProducer implements IFractalProducer {

		@Override
		public void produce(double reMin, double reMax, double imMin, double imMax,
				int width, int height, long requestNo, IFractalResultObserver observer) {
			int numberOfAvailableProcessors = Runtime.getRuntime().availableProcessors();

			System.out.println("Beginning calculation...");

			ExecutorService pool = Executors.newFixedThreadPool(numberOfAvailableProcessors);
			List<Future<Void>> results = new ArrayList<Future<Void>>();

			short[] data = new short[width * height];
			int stripsCount = height / 8 * numberOfAvailableProcessors;
			int stripHeight = height / stripsCount;

			for (int i = 0; i < stripsCount; i++) {
				int yMin = i * stripHeight;
				int yMax = (i + 1) * stripHeight - 1;

				if (i == (stripsCount - 1)) {
					yMax = height - 1;
				}

				NewtonRapshonComputation job = new NewtonRapshonComputation(reMin, reMax, imMin, imMax, width, height,
						yMin, yMax, data);
				results.add(pool.submit(job));
			}

			for (Future<Void> job : results) {
				try {
					job.get();
				} catch (InterruptedException | ExecutionException ignorable) {
				}
			}
			pool.shutdown();

			System.out.println("Calculation done. Notifying the observer (GUI)...");
			observer.acceptResult(data, (short) (rootedPolynom.toComplexPolynom().order() + 1), requestNo);
		}
	}

}
