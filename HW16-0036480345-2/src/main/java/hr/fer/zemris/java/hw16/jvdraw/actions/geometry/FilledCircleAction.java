package hr.fer.zemris.java.hw16.jvdraw.actions.geometry;

import java.awt.Color;
import java.awt.Point;

import hr.fer.zemris.java.hw16.jvdraw.components.FilledCircle;
import hr.fer.zemris.java.hw16.jvdraw.components.GeometricalObject;

/**
 * A simple implementation of {@linkplain IGeometricalObjectAction} interface.
 * This implementation generates a single {@linkplain FilledCircle} object and
 * returns it to the caller.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class FilledCircleAction implements IGeometricalObjectAction {

	@Override
	public GeometricalObject execute(Point start, Point end, Color frontColor, Color backColor, int id) {
		double radius = Math.sqrt(Math.pow((start.x - end.x), 2) + Math.pow((start.y - end.y), 2));
		return new FilledCircle("FilledCircle " + id, start, radius, frontColor, backColor);
	}
}
