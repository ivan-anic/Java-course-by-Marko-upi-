package hr.fer.zemris.java.hw16.jvdraw.models;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw16.jvdraw.components.GeometricalObject;
import hr.fer.zemris.java.hw16.jvdraw.listeners.DrawingModelListener;

/**
 * An implementation of {@linkplain DrawingModel}. Acts as a simple drawing
 * model which handles the objects inside and registered listeners.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class DrawingModelImpl implements DrawingModel {

	/** Hold references to all registered listeners. */
	List<DrawingModelListener> listeners = new ArrayList<>();
	/** Holds references to all of the added objects. */
	List<GeometricalObject> objects = new ArrayList<>();

	public int getSize() {
		return objects.size();
	}

	public GeometricalObject getObject(int index) {
		if (objects.isEmpty()) {
			return null;
		}

		return objects.get(index);
	}

	public List<DrawingModelListener> getListeners() {
		return listeners;
	}

	public void add(GeometricalObject object) {
		objects.add(object);
		listeners.forEach(z -> z.objectsAdded(this, objects.size() - 1, objects.size() - 1));
	}

	public void remove(GeometricalObject object) {
		int index = objects.indexOf(object);
		objects.remove(object);
		listeners.forEach(z -> z.objectsRemoved(this, index, index));
	}

	public void removeAll() {
		objects.clear();
		listeners.forEach(z -> z.objectsRemoved(this, 0, 0));
	}

	public void addDrawingModelListener(DrawingModelListener l) {
		listeners.add(l);
	}

	public void removeDrawingModelListener(DrawingModelListener l) {
		listeners.remove(l);
	}

	public List<GeometricalObject> getObjects() {
		return objects;
	}

}
