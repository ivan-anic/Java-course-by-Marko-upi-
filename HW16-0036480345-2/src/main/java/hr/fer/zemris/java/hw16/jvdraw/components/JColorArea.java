package hr.fer.zemris.java.hw16.jvdraw.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.swing.JColorChooser;
import javax.swing.JComponent;

import hr.fer.zemris.java.hw16.jvdraw.listeners.ColorChangeListener;
import hr.fer.zemris.java.hw16.jvdraw.listeners.IColorProvider;

/**
 * A {@linkplain JComponent} implementation with a single purpose - to give the
 * user the option to choose a colour and to notify all of the registered
 * listeners about that change.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class JColorArea extends JComponent implements IColorProvider {
	/** Serial version user ID */
	private static final long serialVersionUID = -150197703932917112L;
	/** Hold a reference to the currently selected colour. */
	Color selectedColor;
	/**
	 * Holds a reference to all of the registered
	 * {@linkplain ColorChangeListener}s
	 */
	List<ColorChangeListener> listeners;
	/**
	 * Holds a reference to all of the registered {@linkplain MouseListener}s
	 */
	List<MouseListener> mouseListeners;

	/** The preferred dimension. */
	private static final int PREF_DIM = 15;

	/**
	 * Instantiates a new j color area.
	 *
	 * @param startColor
	 *            the start color
	 */
	public JColorArea(Color startColor) {
		super();
		this.selectedColor = startColor;
		this.listeners = new ArrayList<>();
		this.mouseListeners = new ArrayList<>();

		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Color newColor = JColorChooser.showDialog(JColorArea.this, "Pick color", selectedColor);
				selectedColor = newColor;
				listeners.forEach(z -> z.newColorSelected(JColorArea.this, selectedColor, newColor));
			}
		});

		addColorChangeListener((src, oldColor, newColor) -> {
			selectedColor = newColor;
			repaint();
		});

	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(PREF_DIM, PREF_DIM);
	}

	@Override
	protected void paintComponent(Graphics g) {
		g.setColor(selectedColor);
		g.fillRect(0, 0, getPreferredSize().width, getPreferredSize().height);
	}

	public Color getCurrentColor() {
		return selectedColor;
	}

	/**
	 * Adds a new color change listener.
	 *
	 * @param l
	 *            the new listener
	 */
	public void addColorChangeListener(ColorChangeListener l) {
		Objects.requireNonNull(l);
		listeners.add(l);
	}

	/**
	 * Removes the desired color change listener.
	 *
	 * @param l
	 *            the listener to be removed
	 */
	public void removeColorChangeListener(ColorChangeListener l) {
		Objects.requireNonNull(l);
		listeners.remove(l);
	}

}
