package hr.fer.zemris.java.hw16.jvdraw.components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;

import hr.fer.zemris.java.hw16.jvdraw.actions.geometry.IGeometricalObjectAction;
import hr.fer.zemris.java.hw16.jvdraw.listeners.DrawingModelListener;
import hr.fer.zemris.java.hw16.jvdraw.models.DrawingModel;

/**
 * An implementation of {@linkplain JComponent} which is responsible for drawing
 * the elements onto the screen; capturing mouse clicks and correctly delegating
 * the drawing itself.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class JDrawingCanvas extends JComponent implements DrawingModelListener {

	/** Serial version user ID. */
	private static final long serialVersionUID = 4363597153277389740L;
	/** Holds a reference to the starting point of the component being drawn. */
	private Point startPoint;
	/** Holds a reference to the ending point of the component being drawn. */
	@SuppressWarnings("unused")
	private Point endPoint;
	/** Holds a reference to the */
	private DrawingModel source;
	/** Holds a reference to the */
	private IGeometricalObjectAction objectAction;
	/** Holds a reference to the */
	private JColorArea backColor;
	/** Holds a reference to the */
	private JColorArea frontColor;
	/** Holds a reference to the */
	private GeometricalObject currentObject;
	/** Holds a reference to the */
	private int idCounter;

	/**
	 * Sets the command which will be executed while creating a new
	 * {@linkplain GeometricalObject}
	 *
	 * @param objectAction
	 *            the new object command
	 */
	public void setObjectCommand(IGeometricalObjectAction objectAction) {
		this.objectAction = objectAction;
	}

	/**
	 * Instantiates a new j drawing canvas.
	 *
	 * @param source
	 *            the {@linkplain DrawingModel} instance
	 * @param backColor
	 *            the background color
	 * @param frontColor
	 *            the foreground color
	 */
	public JDrawingCanvas(DrawingModel source, JColorArea backColor, JColorArea frontColor) {
		this.source = source;
		this.backColor = backColor;
		this.frontColor = frontColor;

		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (objectAction != null) {
					Point p = e.getPoint();
					if (startPoint == null) {
						startPoint = p;
						invokeDraw(p, false);
					} else {
						endPoint = p;
						invokeDraw(p, true);
					}
				}
			}
		});

		addMouseMotionListener(new MouseAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				if (startPoint != null) {
					invokeDraw(e.getPoint(), false);
				}
			}
		});
	}

	@Override
	protected void paintComponent(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, getWidth(), getHeight());

		source.getObjects().forEach(z -> {
			z.draw(g);
		});

		if (currentObject != null) {
			currentObject.draw(g);
		}
	}

	/**
	 * Invokes the drawing onto the pane.
	 *
	 * @param p
	 *            the referenced point
	 * @param finished
	 *            true if a second mouse click was registered; the component
	 *            should be permanently drawn
	 */
	private void invokeDraw(Point p, boolean finished) {
		GeometricalObject o = objectAction.execute(startPoint, p,
				frontColor.getCurrentColor(), backColor.getCurrentColor(), 0);

		currentObject = o;
		if (finished) {
			o = objectAction.execute(startPoint, p,
					frontColor.getCurrentColor(), backColor.getCurrentColor(), idCounter++);
			startPoint = null;
			endPoint = null;
			source.add(o);
			currentObject = null;
		}
		repaint();
	}

	public void objectsAdded(DrawingModel source, int index0, int index1) {
		repaint();
	}

	public void objectsRemoved(DrawingModel source, int index0, int index1) {
		repaint();
	}

	public void objectsChanged(DrawingModel source, int index0, int index1) {
		repaint();
	}

}
