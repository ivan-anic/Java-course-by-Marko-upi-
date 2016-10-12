package hr.fer.zemris.java.hw16.jvdraw.components;

import java.awt.Color;

import javax.swing.JLabel;

import hr.fer.zemris.java.hw16.jvdraw.listeners.ColorChangeListener;
import hr.fer.zemris.java.hw16.jvdraw.listeners.IColorProvider;

/**
 * A class extended from JLabel, represents a custom label which shows the
 * currently selected foreground and background colors on the bottom of the
 * screen.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class CustomLabel extends JLabel implements ColorChangeListener {

	/** Serial version user ID */
	private static final long serialVersionUID = 1518837505785676483L;
	/** Holds a reference to the foreground color provider. */
	IColorProvider foregroundProvider;
	/** Holds a reference to the background color provider. */
	IColorProvider backgroundProvider;

	/**
	 * Instantiates a new custom label.
	 *
	 * @param foregroundProvider
	 *            the foreground color provider
	 * @param backgroundProvider
	 *            the background color provider
	 */
	public CustomLabel(IColorProvider foregroundProvider, IColorProvider backgroundProvider) {
		super();
		this.foregroundProvider = foregroundProvider;
		this.backgroundProvider = backgroundProvider;

		setText(foregroundProvider.getCurrentColor(), backgroundProvider.getCurrentColor());
	}

	/**
	 * Sets the text of this JLabel.
	 *
	 * @param frontColor
	 *            the foreground color
	 * @param backColor
	 *            the background color
	 */
	private void setText(Color frontColor, Color backColor) {
		setText(String.format("Foreground color: (%d, %d, %d), background color: +(%d, %d, %d)",
				frontColor.getRed(), frontColor.getGreen(), frontColor.getBlue(),
				backColor.getRed(), backColor.getGreen(), backColor.getBlue()));
	}

	@Override
	public void newColorSelected(IColorProvider source, Color oldColor, Color newColor) {
		Color frontColor = (source == foregroundProvider) ? newColor : foregroundProvider.getCurrentColor();
		Color backColor = (source == backgroundProvider) ? newColor : backgroundProvider.getCurrentColor();

		setText(frontColor, backColor);
	}

}
