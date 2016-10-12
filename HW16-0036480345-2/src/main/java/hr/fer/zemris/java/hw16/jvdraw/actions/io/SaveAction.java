package hr.fer.zemris.java.hw16.jvdraw.actions.io;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import hr.fer.zemris.java.hw16.jvdraw.JVDraw;
import hr.fer.zemris.java.hw16.jvdraw.components.Circle;
import hr.fer.zemris.java.hw16.jvdraw.components.FilledCircle;
import hr.fer.zemris.java.hw16.jvdraw.components.GeometricalObject;
import hr.fer.zemris.java.hw16.jvdraw.components.Line;

/**
 * A simple implementation of {@linkplain AbstractIOAction}, giving the user
 * option to save the drawn content into a .jvd textual file on the disk which
 * can later be reconstructed with the open action.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class SaveAction extends AbstractIOAction {

	/** Serial version user ID */
	private static final long serialVersionUID = 6567190652830466743L;

	/**
	 * Instantiates a new save action.
	 *
	 * @param jvDraw
	 *            the application context
	 */
	public SaveAction(JVDraw jvDraw) {
		super(jvDraw);
		putValue(Action.NAME, "Save");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JFileChooser fc = new JFileChooser();
		FileNameExtensionFilter jvdfilter = new FileNameExtensionFilter(
				"jvd files (*.jvd)", "jvd");
		fc.setDialogTitle("Save document");
		fc.setFileFilter(jvdfilter);

		if (fc.showOpenDialog(jvDraw) != JFileChooser.APPROVE_OPTION) {
			JOptionPane.showMessageDialog(jvDraw, "Nothing was saved.", "Warning", JOptionPane.WARNING_MESSAGE);
			return;
		}
		Path openedFilePath = fc.getSelectedFile().toPath();
		if (openedFilePath.toFile().exists()) {
			int decision = JOptionPane.showConfirmDialog(jvDraw, "File exists. Overwrite?", "System message",
					JOptionPane.YES_NO_CANCEL_OPTION);
			if (decision != JOptionPane.YES_OPTION) {
				return;
			}
		}

		List<String> lines = new ArrayList<>();
		int len = jvDraw.source.getSize();
		for (int i = 0; i < len; ++i) {
			GeometricalObject obj = jvDraw.source.getObject(i);
			String line = null;
			if (obj instanceof Line) {
				line = fillFromLine((Line) obj);
			} else if (obj instanceof FilledCircle) {
				line = fillFromFilledCircle((FilledCircle) obj);
			} else if (obj instanceof Circle) {
				line = fillFromCircle((Circle) obj);
			}
			lines.add(line);
		}

		try {
			Files.write(openedFilePath, lines);
		} catch (IOException ex) {
			JOptionPane.showMessageDialog(jvDraw, "Error saving file " + openedFilePath + ".", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		JOptionPane.showMessageDialog(jvDraw, "File saved.", "Information", JOptionPane.INFORMATION_MESSAGE);
	}

	/**
	 * Creates a string representation of a {@linkplain Line} object for saving
	 * into a textual .jvd file
	 *
	 * @param obj
	 *            the given {@linkplain Line} object
	 * @return the desired string
	 */
	public static String fillFromLine(Line obj) {
		StringBuilder sb = new StringBuilder();
		sb.append("LINE");
		sb.append(" " + (int) obj.getStart().getX() + " " + (int) obj.getStart().getY());
		sb.append(" " + (int) obj.getEnd().getX() + " " + (int) obj.getEnd().getY());
		Color c = obj.getColor();
		sb.append(" " + c.getRed() + " " + c.getGreen() + " " + c.getBlue());
		return sb.toString();
	}

	/**
	 * Creates a string representation of a {@linkplain Circle} object for
	 * saving into a textual .jvd file
	 *
	 * @param obj
	 *            the given {@linkplain Circle} object
	 * @return the desired string
	 */
	public static String fillFromCircle(Circle obj) {
		StringBuilder sb = new StringBuilder();
		sb.append("CIRCLE");
		sb.append(" " + (int) obj.getCenter().getX() + " " + (int) obj.getCenter().getY());
		sb.append(" " + (int) obj.getRadius());
		Color c = obj.getColor();
		sb.append(" " + c.getRed() + " " + c.getGreen() + " " + c.getBlue());
		return sb.toString();
	}

	/**
	 * Creates a string representation of a {@linkplain FilledCircle} object for
	 * saving into a textual .jvd file
	 *
	 * @param obj
	 *            the given {@linkplain FilledCircle} object
	 * @return the desired string
	 */
	public static String fillFromFilledCircle(FilledCircle obj) {
		StringBuilder sb = new StringBuilder();
		sb.append("FCIRCLE");
		sb.append(" " + (int) obj.getCenter().getX() + " " + (int) obj.getCenter().getY());
		sb.append(" " + (int) obj.getRadius());
		Color c = obj.getColor();
		sb.append(" " + c.getRed() + " " + c.getGreen() + " " + c.getBlue());
		c = obj.getBackColor();
		sb.append(" " + c.getRed() + " " + c.getGreen() + " " + c.getBlue());
		return sb.toString();
	}

}