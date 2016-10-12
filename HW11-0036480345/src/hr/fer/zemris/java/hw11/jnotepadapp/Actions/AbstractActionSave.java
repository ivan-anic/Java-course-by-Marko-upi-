package hr.fer.zemris.java.hw11.jnotepadapp.Actions;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import hr.fer.zemris.java.hw11.jnotepadapp.IUtilComm;
import hr.fer.zemris.java.hw11.jnotepadapp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadapp.local.IlocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadapp.local.LocalizableAction;

/**
 * A class derived from {@linkplain LocalizableAction}. It represents a
 * {@linkplain JNotepadPP} functionality of saving the currently opened file.
 * 
 * @see ActionSave
 * @see ActionSaveAs
 * @author Ivan Anić
 * @version 1.0
 */
public class AbstractActionSave extends LocalizableAction {

	/** The generated serial user ID */
	private static final long serialVersionUID = 8794266594303724978L;
	/** Keeps a reference to the currently opened text area. */
	private static JTextArea context;
	/** Keeps a reference to the {@linkplain IUtilComm} interface. */
	static IUtilComm util;
	/** Keeps a reference to the key used for language changes. */
	private static String key;

	/**
	 * Instantiates a new instance of this action.
	 * 
	 * @param key
	 *
	 * @param provider
	 *            the provider which listens to language changes
	 * @param util
	 *            the {@link #util}
	 */
	public AbstractActionSave(String key, IlocalizationProvider provider, IUtilComm util) {
		super(key, provider);
		AbstractActionSave.util = util;
		AbstractActionSave.key = key;
	}

	/**
	 * Invoked when an action occurs.
	 */
	public static void perform() {
		if (util.getOpenedFilePath().endsWith("untitled") || key.equals("saveas")) {

			JFileChooser jfc = new JFileChooser();
			jfc.setDialogTitle("Save document");
			if (jfc.showOpenDialog(context) != JFileChooser.APPROVE_OPTION) {
				JOptionPane.showMessageDialog(context, "Ništa nije snimljeno!",
						"Upozorenje", JOptionPane.ERROR_MESSAGE);
				return;
			}
			util.setOpenedFilePath(jfc.getSelectedFile().toPath());
		}
		try {
			List<String> data = Arrays.asList(util.getContext().getText().split("\n"));
			Files.write(util.getSelectedFilePath(), data);
		} catch (IOException | ArrayIndexOutOfBoundsException ex) {
			JOptionPane.showMessageDialog(context,
					"Pogreška prilikom pisanja datoteke " + util.getOpenedFilePath(),
					"Pogreška", JOptionPane.ERROR_MESSAGE);
			return;
		}

		JOptionPane.showMessageDialog(context,
				"Datoteka snimljena ",
				"Informacija", JOptionPane.INFORMATION_MESSAGE);
		util.updateTabName();
		util.setImage(false);
		util.getContext().getDocument().putProperty("changed", false);
		return;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		perform();
	}

}
