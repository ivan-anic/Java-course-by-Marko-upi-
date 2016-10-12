package hr.fer.zemris.java.graphics.test;

import hr.fer.zemris.java.graphics.raster.BWRaster;
import hr.fer.zemris.java.graphics.raster.BWRasterMem;
import hr.fer.zemris.java.graphics.shapes.Circle;
import hr.fer.zemris.java.graphics.shapes.Ellipse;
import hr.fer.zemris.java.graphics.shapes.Rectangle;
import hr.fer.zemris.java.graphics.shapes.Square;
import hr.fer.zemris.java.graphics.views.RasterView;
import hr.fer.zemris.java.graphics.views.SimpleRasterView;

/**
 * A class used for the testing of the class contained in this project.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class test {

	/**
	 * The main method, first one to be executed when the program starts.
	 * 
	 * @param args
	 *            - the input arguments (not used)
	 * 
	 */
	public static void main(String[] args) {
		Rectangle rect1 = new Rectangle(-1, -1, 4, 4);
		Rectangle rect2 = new Rectangle(-7, -7, 20, 20);
		Ellipse ellipse = new Ellipse(10, 1, -5, -4);
		Circle circle = new Circle(10, 45, 45);
		Square square = new Square(30, 35, 10);
		BWRaster raster = new BWRasterMem(50, 51);
		raster.enableFlipMode();
		ellipse.draw(raster);
		circle.draw(raster);
		rect1.draw(raster);
		square.draw(raster);
		rect2.draw(raster);
		RasterView view = new SimpleRasterView();
		view.produce(raster);
		view.produce(raster);
		System.out.println();
		RasterView view2 = new SimpleRasterView('X', '_');
		view2.produce(raster);
	}
}
