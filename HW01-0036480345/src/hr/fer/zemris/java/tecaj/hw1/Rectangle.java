package hr.fer.zemris.java.tecaj.hw1;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * A program which calculates the perimeter and area of an arbitrary rectangle.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class Rectangle {
	/**
	 * Calculates the perimeter of the given rectangle.
	 * 
	 * @param width
	 *            - the rectangle width
	 * @param height
	 *            - the rectangle height
	 * @return - perimeter of the given rectangle
	 */
	private static double Perimeter(double width, double height) {
		return 2 * (width + height);
	}

	/**
	 * Calculates the area of the given rectangle.
	 * 
	 * @param width
	 *            - the rectangle width
	 * @param height
	 *            - the rectangle height
	 * @return - area of the given rectangle
	 */
	private static double Area(double width, double height) {
		return width * height;
	}

	/**
	 * The main method, first one to be executed when the program starts.
	 *
	 * @param args
	 *            - the width and height of the specified rectangle.
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		String line;
		Double width = 0.0, height = 0.0, par;
		if (args.length == 2) {
			width = Double.parseDouble(args[0]);
			height = Double.parseDouble(args[1]);
			System.out.println(
					"The specified rectangle has width: " + width + ", and heigth: " + height + ". Its area is "
							+ Area(width, height) + " and its perimeter is " + Perimeter(width, height) + ".");
			return;
		}
		if ((args.length != 2) && (args.length != 0)) {
			System.out.println("Invalid number of arguments was provided.");
			return;
		}
		BufferedReader reader = new BufferedReader(new InputStreamReader(new BufferedInputStream(System.in)));
		while ((width == 0) || (height == 0)) {
			System.out.println("Please provide " + ((width == 0) ? "Width" : "Height") + "");
			if ((line = reader.readLine().trim()).isEmpty())
				System.out.println("Nothing was given.");
			else if ((par = Double.parseDouble(line)) < 0)
				System.out.println(((width == 0) ? "Width" : "Height") + " is negative.");
			else if (width == 0)
				width = par;
			else
				height = par;
		}
		System.out.println("You have specified a rectangle with width " + width + " and height " + height
				+ ". Its area is " + Area(width, height) + " and its perimeter is " + Perimeter(width, height) + ".");
		return;
	}
}
