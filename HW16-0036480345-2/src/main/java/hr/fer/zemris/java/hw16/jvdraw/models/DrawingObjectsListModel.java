package hr.fer.zemris.java.hw16.jvdraw.models;

import javax.swing.AbstractListModel;
import javax.swing.JList;

import hr.fer.zemris.java.hw16.jvdraw.listeners.DrawingModelListener;

/**
 * A class derived from {@linkplain AbstractListModel}. Used together with an
 * implementation of {@linkplain JList} component to dynamically add created
 * elements to the list instance.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class DrawingObjectsListModel extends AbstractListModel<String> implements DrawingModelListener {
	/** Serial version user ID. */
	private static final long serialVersionUID = -4931793260329335254L;
	/** Holds a reference to the {@linkplain DrawingModelImpl} instance. */
	DrawingModelImpl source;

	/**
	 * Instantiates a new drawing objects list model.
	 *
	 * @param source
	 *            the source
	 */
	public DrawingObjectsListModel(DrawingModelImpl source) {
		this.source = source;
	}

	public int getSize() {
		return source.getSize();
	}

	public String getElementAt(int index) {
		return source.getObject(index).getName();
	}

	public void objectsAdded(DrawingModel source, int index0, int index1) {
		fireIntervalAdded(this, index0, index1);
	}

	public void objectsRemoved(DrawingModel source, int index0, int index1) {
		fireIntervalRemoved(this, index0, index1);
	}

	public void objectsChanged(DrawingModel source, int index0, int index1) {
		fireContentsChanged(this, index0, index1);
	}
}
