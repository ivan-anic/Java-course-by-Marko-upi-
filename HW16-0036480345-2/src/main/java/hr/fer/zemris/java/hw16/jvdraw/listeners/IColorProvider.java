package hr.fer.zemris.java.hw16.jvdraw.listeners;

import java.awt.Color;

/**
 * An interface providing a single method; {@link #getCurrentColor()}.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public interface IColorProvider {

	/**
	 * Gets the currently selected color.
	 *
	 * @return the current color
	 */
	public Color getCurrentColor();

}
