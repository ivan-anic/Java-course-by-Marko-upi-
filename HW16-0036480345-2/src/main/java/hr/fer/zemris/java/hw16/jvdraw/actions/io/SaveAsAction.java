package hr.fer.zemris.java.hw16.jvdraw.actions.io;

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
 * can later be reconstructed with the open action, while choosing a concrete
 * name for the file, whether a the current drawing has previously been saved or
 * not.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class SaveAsAction extends AbstractIOAction {

	/** Serial version user ID */
	private static final long serialVersionUID = -4575596200143947846L;

	/**
	 * Instantiates a new save as action.
	 *
	 * @param jvDraw
	 *            the application context
	 */
	public SaveAsAction(JVDraw jvDraw) {
		super(jvDraw);
		putValue(Action.NAME, "Save As");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Path openedFilePath = jvDraw.getCurrentPath();

		if (openedFilePath == null) {
			JFileChooser fc = new JFileChooser();
			FileNameExtensionFilter jvdfilter = new FileNameExtensionFilter(
					"jvd files (*.jvd)", "jvd");
			fc.setDialogTitle("Save document");
			fc.setFileFilter(jvdfilter);

			if (fc.showOpenDialog(jvDraw) != JFileChooser.APPROVE_OPTION) {
				JOptionPane.showMessageDialog(jvDraw, "Nothing was saved.", "Warning", JOptionPane.WARNING_MESSAGE);
				return;
			}
			openedFilePath = fc.getSelectedFile().toPath();
			if (openedFilePath.toFile().exists()) {
				int decision = JOptionPane.showConfirmDialog(jvDraw, "File exists. Overwrite?", "System message",
						JOptionPane.YES_NO_CANCEL_OPTION);
				if (decision != JOptionPane.YES_OPTION) {
					return;
				}
			}
		}

		List<String> lines = new ArrayList<>();
		int len = jvDraw.source.getSize();
		for (int i = 0; i < len; ++i) {
			GeometricalObject obj = jvDraw.source.getObject(i);
			String line = null;
			if (obj instanceof Line) {
				line = SaveAction.fillFromLine((Line) obj);
			} else if (obj instanceof FilledCircle) {
				line = SaveAction.fillFromFilledCircle((FilledCircle) obj);
			} else if (obj instanceof Circle) {
				line = SaveAction.fillFromCircle((Circle) obj);
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

}