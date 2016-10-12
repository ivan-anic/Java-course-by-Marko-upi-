package hr.fer.zemris.java.hw16.jvdraw.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Point;
import java.util.Objects;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import hr.fer.zemris.java.hw16.jvdraw.PropertyEditUtil;

/**
 * A class extended from {@linkplain GeometricalObject}. Represents a filled
 * circle, with its border availible to paint in another color.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class FilledCircle extends Circle {

	/** Holds a reference to the fill colour of this circle. */
	Color backColor;

	/**
	 * Instantiates a new filled circle.
	 *
	 * @param name
	 *            the name
	 * @param center
	 *            the center
	 * @param radius
	 *            the radius
	 * @param frontColor
	 *            the foreground color
	 * @param backColor
	 *            the background color
	 */
	public FilledCircle(String name, Point center, double radius, Color frontColor, Color backColor) {
		super(name, center, radius, frontColor);
		this.backColor = backColor;
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(backColor);
		g.fillOval(center.x, center.y, (int) radius * 2, (int) radius * 2);
		super.draw(g);
	}

	@Override
	public boolean invokePropertyDialog(Dimension dimen) {
		JPanel panel = new JPanel(new BorderLayout(5, 5));

		JPanel labels = new JPanel(new GridLayout(0, 1, 2, 2));
		labels.add(new JLabel("New center: ", SwingConstants.RIGHT));
		labels.add(new JLabel("New radius: ", SwingConstants.RIGHT));

		panel.add(labels, BorderLayout.WEST);

		JPanel controls = new JPanel(new GridLayout(3, 2, 2, 2));
		JTextField centerX = new JTextField();
		JTextField centerY = new JTextField();
		JTextField radius = new JTextField();

		centerX.setText(String.valueOf(center.getX()));
		centerY.setText(String.valueOf(center.getY()));
		radius.setText(String.valueOf(this.radius));

		controls.add(centerX);
		controls.add(centerY);
		controls.add(radius);

		panel.add(controls, BorderLayout.EAST);

		JPanel colors = new JPanel(new GridLayout(2, 2, 2, 2));
		JLabel frontColorLabel = new JLabel("Line color ");
		JColorArea frontColor = new JColorArea(Color.BLACK);
		JLabel backColorLabel = new JLabel("Filling color ");
		JColorArea backColor = new JColorArea(Color.BLACK);

		colors.add(frontColorLabel);
		frontColor = new JColorArea(getColor());
		colors.add(frontColor);
		colors.add(backColorLabel);
		backColor = new JColorArea(getBackColor());
		colors.add(backColor);
		panel.add(colors, BorderLayout.SOUTH);

		int result = JOptionPane.showConfirmDialog(
				null, panel, "Property editing", JOptionPane.OK_CANCEL_OPTION);
		if (result == JOptionPane.OK_OPTION) {
			try {
				setCenter(PropertyEditUtil.getPoint(centerX, centerY, dimen));
				setRadius(PropertyEditUtil.getDouble(radius, dimen));
				setColor(frontColor.getCurrentColor());
				setBackColor(backColor.getCurrentColor());
			} catch (NullPointerException ex) {
				return false;
			}
			return true;
		}
		return false;
	}

	/**
	 * Sets the center.
	 *
	 * @param point
	 *            the new center
	 */
	private void setCenter(Point point) {
		Objects.requireNonNull(point);
		this.center = point;
	}

	/**
	 * Sets the radius.
	 *
	 * @param radius
	 *            the new radius
	 */
	private void setRadius(double radius) {
		Objects.requireNonNull(radius);
		this.radius = radius;
	}

	/**
	 * Sets the background color.
	 *
	 * @param backColor
	 *            the new background color
	 */
	private void setBackColor(Color backColor) {
		Objects.requireNonNull(backColor);
		this.backColor = backColor;
	}

	/**
	 * Gets the background color.
	 *
	 * @return the background color
	 */
	public Color getBackColor() {
		return backColor;
	}

}