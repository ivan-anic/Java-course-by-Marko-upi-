package hr.fer.zemris.java.hw16.jvdraw.listeners;

import hr.fer.zemris.java.hw16.jvdraw.components.GeometricalObject;
import hr.fer.zemris.java.hw16.jvdraw.models.DrawingModel;

/**
 * A listener which listens to instances of {@linkplain GeometricalObject} being
 * added, removed or changed.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public interface DrawingModelListener {

	/**
	 * Triggers when some objects are added.
	 *
	 * @param source
	 *            the {@linkplain DrawingModel} instance
	 * @param index0
	 *            the index0
	 * @param index1
	 *            the index1
	 */
	public void objectsAdded(DrawingModel source, int index0, int index1);

	/**
	 * Triggers when some objects are removed.
	 *
	 * @param source
	 *            the {@linkplain DrawingModel} instance
	 * @param index0
	 *            the index0
	 * @param index1
	 *            the index1
	 */
	public void objectsRemoved(DrawingModel source, int index0, int index1);

	/**
	 * Triggers when some objects are changed.
	 *
	 * @param source
	 *            the {@linkplain DrawingModel} instance
	 * @param index0
	 *            the index0
	 * @param index1
	 *            the index1
	 */
	public void objectsChanged(DrawingModel source, int index0, int index1);

}
