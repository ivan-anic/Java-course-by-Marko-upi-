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
 * A class extended from {@linkplain GeometricalObject}. Represents a simple
 * coloured line defined by its starting and ending {@linkplain Point}.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class Line extends GeometricalObject {

	/** Holds a reference to the starting point of this line. */
	private Point start;
	/** Holds a reference to the starting point of this line. */
	private Point end;

	/**
	 * Instantiates a new line.
	 *
	 * @param name
	 *            the name
	 * @param start
	 *            the start of the line
	 * @param end
	 *            the end of the line
	 * @param color
	 *            the color of the line
	 */
	public Line(String name, Point start, Point end, Color color) {
		super(name, color);
		this.start = start;
		this.end = end;
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(getColor());
		g.drawLine(start.x, start.y, end.x, end.y);
	}

	@Override
	public boolean invokePropertyDialog(Dimension dimen) {
		JPanel panel = new JPanel(new BorderLayout(5, 5));

		JPanel labels = new JPanel(new GridLayout(0, 1, 2, 2));
		labels.add(new JLabel("New starting point: ", SwingConstants.RIGHT));
		labels.add(new JLabel("New ending point: ", SwingConstants.RIGHT));

		panel.add(labels, BorderLayout.WEST);

		JPanel controls = new JPanel(new GridLayout(3, 2, 2, 2));
		JTextField startX = new JTextField();
		JTextField startY = new JTextField();
		JTextField endX = new JTextField();
		JTextField endY = new JTextField();
		startX.setText(String.valueOf(start.getX()));
		startY.setText(String.valueOf(start.getY()));
		endX.setText(String.valueOf(end.getX()));
		endY.setText(String.valueOf(end.getY()));

		controls.add(startX);
		controls.add(startY);
		controls.add(endX);
		controls.add(endY);

		panel.add(controls, BorderLayout.EAST);

		JPanel colors = new JPanel(new GridLayout(2, 2, 2, 2));
		JLabel colorLabel = new JLabel("Line color ");

		JColorArea frontColor = new JColorArea(Color.BLACK);

		colors.add(colorLabel);
		frontColor = new JColorArea(getColor());
		colors.add(frontColor);

		panel.add(colors, BorderLayout.SOUTH);

		int result = JOptionPane.showConfirmDialog(
				null, panel, "Property editing", JOptionPane.OK_CANCEL_OPTION);
		if (result == JOptionPane.OK_OPTION) {
			try {
				setStart(PropertyEditUtil.getPoint(startX, startY, dimen));
				setEnd(PropertyEditUtil.getPoint(endX, endY, dimen));
				setColor(frontColor.getCurrentColor());
			} catch (NullPointerException ex) {
				return false;
			}
			return true;
		}
		return false;
	}

	/**
	 * Gets the starting point of this line.
	 *
	 * @return the starting point of this line
	 */
	public Point getStart() {
		return start;
	}

	/**
	 * Gets the ending point of this line
	 *
	 * @return the ending point of this line
	 */
	public Point getEnd() {
		return end;
	}

	/**
	 * Sets the starting point of this line
	 *
	 * @param start
	 *            the new starting point of this line
	 */
	public void setStart(Point start) {
		Objects.requireNonNull(start);
		this.start = start;
	}

	/**
	 * Sets the ending point of this line
	 *
	 * @param end
	 *            the new ending point of this line
	 */
	public void setEnd(Point end) {
		Objects.requireNonNull(start);
		this.end = end;
	}

}