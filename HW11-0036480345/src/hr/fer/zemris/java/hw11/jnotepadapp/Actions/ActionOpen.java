package hr.fer.zemris.java.hw11.jnotepadapp.Actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import hr.fer.zemris.java.hw11.jnotepadapp.DocumentWrapper;
import hr.fer.zemris.java.hw11.jnotepadapp.IUtilComm;
import hr.fer.zemris.java.hw11.jnotepadapp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadapp.local.IlocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadapp.local.LocalizableAction;

/**
 * A class derived from {@linkplain LocalizableAction}. It represents a
 * {@linkplain JNotepadPP} functionality of opening a new file.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class ActionOpen extends LocalizableAction {

	/** The generated serial user ID */
	private static final long serialVersionUID = 8794266594303724978L;
	/** Keeps a reference to the {@linkplain IUtilComm} interface. */
	private IUtilComm util;

	/**
	 * Instantiates a new instance of this action.
	 *
	 * @param provider
	 *            the provider which listens to language changes
	 * @param util
	 *            the {@link #util}
	 */
	public ActionOpen(IlocalizationProvider provider, IUtilComm util) {
		super("open", provider);
		this.util = util;

		putValue(
				Action.ACCELERATOR_KEY,
				KeyStroke.getKeyStroke("control O"));
		putValue(
				Action.MNEMONIC_KEY,
				KeyEvent.VK_O);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JFileChooser fc = new JFileChooser();
		fc.setDialogTitle("Open file");
		if (fc.showOpenDialog(util.getNotepad()) != JFileChooser.APPROVE_OPTION) {
			return;
		}

		File fileName = fc.getSelectedFile();
		DocumentWrapper file = new DocumentWrapper(null, null, fileName);

		Path filePath = fileName.toPath();

		if (!Files.isReadable(filePath)) {
			JOptionPane.showMessageDialog(util.getNotepad(),
					"Datoteka " + fileName.getAbsolutePath() + " ne postoji!",
					"Pogreška", JOptionPane.ERROR_MESSAGE);
		}

		byte[] okteti;
		try {
			okteti = Files.readAllBytes(filePath);
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(util.getNotepad(),
					"Pogreška prilikom čitanja datoteke " + fileName.getAbsolutePath(),
					"Pogreška", JOptionPane.ERROR_MESSAGE);
			return;
		}

		util.getNotepad().addTab(file, new String(okteti));

	}

}
