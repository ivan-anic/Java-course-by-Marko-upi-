package hr.fer.zemris.java.hw16.jvdraw;

import java.awt.Dimension;
import java.awt.Point;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 * A utility class which offers support for the {@link JVDraw} application.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class PropertyEditUtil {

	/**
	 * Gets the point from the given coordinates, also processing the neccessary
	 * typechecks.
	 *
	 * @param pointX
	 *            the point x
	 * @param pointY
	 *            the point y
	 * @param dimen
	 *            the dimension
	 * @return the desired point
	 */
	public static Point getPoint(JTextField pointX, JTextField pointY, Dimension dimen) {
		int x = 0;
		int y = 0;

		try {
			x = (int) Double.parseDouble(pointX.getText());
			y = (int) Double.parseDouble(pointY.getText());
		} catch (NumberFormatException ex) {
			showMessageDialog(ex.getMessage());
			return null;
		}
		if (x < 0 || x > dimen.getWidth()) {
			showMessageDialog("Illegal x parameter: " + x);
			return null;
		} else if (y < 0 || y > dimen.getHeight()) {
			showMessageDialog("Illegal y parameter: " + y);
			return null;
		}

		return new Point(x, y);
	}

	/**
	 * Gets the double value from the given text field, also processing the
	 * neccessary typechecks
	 *
	 * @param radius
	 *            the radius
	 * @param dimen
	 *            the dimen
	 * @return the double value
	 */
	public static Double getDouble(JTextField radius, Dimension dimen) {
		double r = 0;

		try {
			r = (int) Double.parseDouble(radius.getText());
		} catch (NumberFormatException ex) {
			showMessageDialog(ex.getMessage());
			return null;
		}

		if (r < 0 || r > dimen.getWidth() / 2) {
			showMessageDialog("Illegal x parameter: " + r);
			return null;
		}

		return r;
	}

	/**
	 * Shows a message dialog.
	 *
	 * @param message
	 *            the message to be displayed
	 */
	private static void showMessageDialog(String message) {
		JOptionPane.showMessageDialog(null, message);
	}
}
