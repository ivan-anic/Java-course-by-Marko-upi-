package hr.fer.zemris.java.hw16.jvdraw.actions.geometry;

import java.awt.Color;
import java.awt.Point;

import hr.fer.zemris.java.hw16.jvdraw.components.GeometricalObject;
import hr.fer.zemris.java.hw16.jvdraw.components.Line;

/**
 * A simple implementation of {@linkplain IGeometricalObjectAction} interface.
 * This implementation generates a single {@linkplain Line} object and returns
 * it to the caller.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class LineAction implements IGeometricalObjectAction {

	@Override
	public GeometricalObject execute(Point start, Point end, Color frontColor, Color backColor, int id) {
		return new Line("Line " + id, start, end, frontColor);
	}
}
