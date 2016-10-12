package hr.fer.zemris.java.hw16.jvdraw.actions.geometry;

import java.awt.Color;
import java.awt.Point;

import hr.fer.zemris.java.hw16.jvdraw.components.GeometricalObject;

/**
 * An abstraction for the various geometrical actions implemented.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public interface IGeometricalObjectAction {

	/**
	 * Executes the action, creating a desired instance of
	 * {@linkplain GeometricalObject} and returning it.
	 *
	 * @param start
	 *            the start
	 * @param end
	 *            the end
	 * @param frontColor
	 *            the front color
	 * @param backColor
	 *            the back color
	 * @param id
	 *            the id
	 * @return the geometrical object
	 */
	public GeometricalObject execute(Point start, Point end, Color frontColor,
			Color backColor, int id);
}
