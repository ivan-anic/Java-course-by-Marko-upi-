package hr.fer.zemris.java.graphics;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import hr.fer.zemris.java.graphics.raster.BWRaster;
import hr.fer.zemris.java.graphics.raster.BWRasterMem;
import hr.fer.zemris.java.graphics.shapes.Circle;
import hr.fer.zemris.java.graphics.shapes.Ellipse;
import hr.fer.zemris.java.graphics.shapes.GeometricShape;
import hr.fer.zemris.java.graphics.shapes.Rectangle;
import hr.fer.zemris.java.graphics.shapes.Square;
import hr.fer.zemris.java.graphics.views.RasterView;
import hr.fer.zemris.java.graphics.views.SimpleRasterView;

/**
 * Represents an interactive demonstration of this package. Offers various
 * inputs, which result in displaying the desired geometric shapes onto the
 * screen.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class Demo {

	/**
	 * The main method, first one to be executed when the program starts.
	 * 
	 * @param args
	 *            - the raster size
	 */
	public static void main(String[] args) {
		if (args.length < 1 || args.length > 2) {
			System.out.println("Please provide one or two arguments.");
			System.exit(1);
		}
		BWRaster raster = null;
		if (args.length == 1) {
			try {
				raster = new BWRasterMem(Integer.parseInt(args[0]), Integer.parseInt(args[0]));
			} catch (IllegalArgumentException ex) {
				System.out.println("Invalid arguments.");
				System.exit(1);
			}
		} else {
			try {
				raster = new BWRasterMem(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
			} catch (IllegalArgumentException ex) {
				System.out.println(ex.getMessage());
				System.exit(1);
			}
		}

		BufferedReader reader = new BufferedReader(new InputStreamReader(new BufferedInputStream(System.in)));
		int cnt = 0;
		try {
			cnt = Integer.parseInt(reader.readLine());
			if (cnt<=0) throw new NumberFormatException();
		} catch (NumberFormatException e) {
			System.out.println("Invalid number of shapes. Please provide a natural number.");
			System.exit(1);
		} catch (IOException e) {
			System.out.println(e.getMessage());
			System.exit(1);
		}

		String line = "";
		GeometricShape[] shapes = new GeometricShape[cnt];
		for (int i = 0; i < cnt; i++) {
			try {
				line = reader.readLine();
			} catch (IOException e) {
				System.out.println("Error during input.");
				System.exit(1);
			}

			String[] params = line.replaceAll("\\s+", " ").split(" ");
			try {
				switch (params[0].toUpperCase()) {
				case ("FLIP"):
					shapes[i] = null;
					break;
				case ("RECTANGLE"):
					shapes[i] = new Rectangle(Integer.parseInt(params[1]), Integer.parseInt(params[2]),
							Integer.parseInt(params[3]), Integer.parseInt(params[4]));
					break;
				case ("SQUARE"):
					shapes[i] = new Square(Integer.parseInt(params[1]), Integer.parseInt(params[2]),
							Integer.parseInt(params[3]));
					break;
				case ("ELLIPSE"):
					shapes[i] = new Ellipse(Integer.parseInt(params[3]), Integer.parseInt(params[4]),
							Integer.parseInt(params[1]), Integer.parseInt(params[2]));
					break;
				case ("CIRCLE"):
					shapes[i] = new Circle(Integer.parseInt(params[3]), Integer.parseInt(params[1]),
							Integer.parseInt(params[2]));
					break;
				default:
					System.out.println("Invalid shape.");
					System.exit(1);
				}
			} catch (IndexOutOfBoundsException ex) {
				System.out.println("Invalid number of arguments.");
				System.exit(1);
			}catch (Exception ex){
				System.out.println(ex.getMessage());
				System.exit(1);
			}
		}

		for (GeometricShape s : shapes) {
			if (s == null) {
				raster.toggleFlipMode();
			} else {
				s.draw(raster);
			}
		}

		RasterView view = new SimpleRasterView();
		view.produce(raster);
	}
}
/*
 * square -5 -5 8 circle -1 11 5 ellipse 12 12 7
 */