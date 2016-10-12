package hr.fer.zemris.java.hw16.jvdraw.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

/**
 * An abstraction for all geometrical objects. Holds a reference to the name of
 * the object and its colour.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public abstract class GeometricalObject {
	/** Holds a reference to the name of this geometrical object. */
	private String name;
	/** Holds a reference to the colour of this geometrical object. */
	private Color color;

	/**
	 * Instantiates a new geometrical object.
	 *
	 * @param name
	 *            the name
	 * @param color
	 *            the color
	 */
	public GeometricalObject(String name, Color color) {
		super();
		this.name = name;
		this.color = color;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the color.
	 *
	 * @return the color
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * Sets the color.
	 *
	 * @param color
	 *            the new color
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	/**
	 * Draws the component using the {@linkplain Graphics} class functions.
	 *
	 * @param g
	 *            the graphics instance
	 */
	public abstract void draw(Graphics g);

	/**
	 * Invoke property dialog.
	 *
	 * @param dimen
	 *            the dimen
	 * @return true, if successful
	 */
	public abstract boolean invokePropertyDialog(Dimension dimen);
}
