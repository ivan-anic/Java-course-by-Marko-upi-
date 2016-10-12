package hr.fer.zemris.java.hw16.jvdraw.listeners;

import java.awt.Color;

import hr.fer.zemris.java.hw16.jvdraw.models.DrawingModel;

/**
 * A listener which listens to a color being changed.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public interface ColorChangeListener {

	/**
	 * Triggers when a new color is selected.
	 *
	 * @param source
	 *            the {@linkplain DrawingModel} instance
	 * @param oldColor
	 *            the old color
	 * @param newColor
	 *            the new color
	 */
	public void newColorSelected(IColorProvider source, Color oldColor, Color newColor);
}
