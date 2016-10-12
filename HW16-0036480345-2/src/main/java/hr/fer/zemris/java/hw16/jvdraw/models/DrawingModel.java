package hr.fer.zemris.java.hw16.jvdraw.models;

import java.util.List;

import hr.fer.zemris.java.hw16.jvdraw.components.GeometricalObject;
import hr.fer.zemris.java.hw16.jvdraw.listeners.DrawingModelListener;

/**
 * An interface implemented by the {@linkplain DrawingModelImpl} class. This is
 * an abstraction for a drawing model which handles adding and removing objects
 * and listeners from the current drawing model.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public interface DrawingModel {

	/**
	 * Gets the size of the {@linkplain GeometricalObject} list.
	 *
	 * @return the size
	 */
	public int getSize();

	/**
	 * Gets the object.
	 *
	 * @param index
	 *            the index
	 * @return the object
	 */
	public GeometricalObject getObject(int index);

	/**
	 * Adds a new object to the model.
	 *
	 * @param object
	 *            the object
	 */
	public void add(GeometricalObject object);

	/**
	 * Removes the desired object from the model.
	 *
	 * @param object
	 *            the object
	 */
	public void remove(GeometricalObject object);

	/**
	 * Removes all objects from this model.
	 */
	public void removeAll();

	/**
	 * Adds the drawing model listener.
	 *
	 * @param l
	 *            the listener
	 */
	public void addDrawingModelListener(DrawingModelListener l);

	/**
	 * Removes the drawing model listener.
	 *
	 * @param l
	 *            the listener
	 */
	public void removeDrawingModelListener(DrawingModelListener l);

	/**
	 * Gets the list of all objects in this model.
	 *
	 * @return the objects
	 */
	public List<GeometricalObject> getObjects();

	/**
	 * Gets the list of all registered listeners to this model.
	 *
	 * @return the listeners
	 */
	public List<DrawingModelListener> getListeners();

}
