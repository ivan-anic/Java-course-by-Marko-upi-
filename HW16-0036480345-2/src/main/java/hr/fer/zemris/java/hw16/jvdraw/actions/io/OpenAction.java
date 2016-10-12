package hr.fer.zemris.java.hw16.jvdraw.actions.io;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
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
 * option to open a .jvd textual file from the disk and display its content into
 * the application.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class OpenAction extends AbstractIOAction {

	/** Serial version user ID */
	private static final long serialVersionUID = -1946948041486932432L;

	/**
	 * Instantiates a new open action.
	 *
	 * @param jvDraw
	 *            the application context
	 */
	public OpenAction(JVDraw jvDraw) {
		super(jvDraw);
		putValue(Action.NAME, "Open");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JFileChooser fc = new JFileChooser();
		FileNameExtensionFilter jvdfilter = new FileNameExtensionFilter(
				"jvd files (*.jvd)", "jvd");
		fc.setDialogTitle("Open file");
		fc.setFileFilter(jvdfilter);

		if (fc.showOpenDialog(jvDraw) != JFileChooser.APPROVE_OPTION) {
			return;
		}
		File fileName = fc.getSelectedFile();
		Path filePath = fileName.toPath();
		if (!Files.isReadable(filePath)) {
			JOptionPane.showMessageDialog(jvDraw, "File " + fileName.getAbsolutePath() + " doesn't exist!", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		List<String> lines = null;
		try {
			lines = Files.readAllLines(filePath);
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(jvDraw, "Error reading file " + fileName.getAbsolutePath() + ".", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		jvDraw.setCurrentPath(filePath);

		jvDraw.source.removeAll();

		int i = 0;
		List<Integer> nums;
		for (String line : lines) {
			String[] args = line.split(" ");
			GeometricalObject obj = null;

			switch (args[0]) {
			case "LINE":
				if (args.length != 8)
					return;
				try {
					args = Arrays.copyOfRange(args, 1, args.length);
					nums = parseNumbers(args);
				} catch (NumberFormatException ex) {
					return;
				}
				obj = new Line("Line " + i, new Point(nums.get(0), nums.get(1)),
						new Point(nums.get(2), nums.get(3)), new Color(nums.get(4), nums.get(5), nums.get(6)));
				break;

			case "CIRCLE":
				if (args.length != 7)
					return;
				try {
					args = Arrays.copyOfRange(args, 1, args.length);
					nums = parseNumbers(args);
				} catch (NumberFormatException ex) {
					return;
				}
				obj = new Circle("Circle " + i, new Point(nums.get(0), nums.get(1)), nums.get(2),
						new Color(nums.get(3), nums.get(4), nums.get(5)));
				break;

			case "FCIRCLE":
				if (args.length != 10)
					return;
				try {
					args = Arrays.copyOfRange(args, 1, args.length);
					nums = parseNumbers(args);
				} catch (NumberFormatException ex) {
					return;
				}
				obj = new FilledCircle("Filled circle " + i, new Point(nums.get(0), nums.get(1)), nums.get(2),
						new Color(nums.get(3), nums.get(4), nums.get(5)),
						new Color(nums.get(6), nums.get(7), nums.get(8)));
				break;

			default:
				return;
			}
			jvDraw.source.add(obj);
			++i;
		}
	}

	/**
	 * Parses the given numbers fron a string array.
	 *
	 * @param args
	 *            the args
	 * @return the list
	 * @throws NumberFormatException
	 *             if a parsing error occurs
	 */
	private List<Integer> parseNumbers(String[] args) throws NumberFormatException {
		List<Integer> nums = new ArrayList<>();
		for (String arg : args) {
			int num = Integer.parseInt(arg);
			nums.add(num);
		}
		return nums;
	}

}