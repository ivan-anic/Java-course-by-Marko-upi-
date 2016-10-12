package hr.fer.zemris.java.hw11.jnotepadapp.Actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;

import hr.fer.zemris.java.hw11.jnotepadapp.IUtilComm;
import hr.fer.zemris.java.hw11.jnotepadapp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadapp.local.IlocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadapp.local.LocalizableAction;

/**
 * A class derived from {@linkplain LocalizableAction}. It represents a
 * {@linkplain JNotepadPP} functionality of displaying some statistics about the
 * currently opened file.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class ActionStatistics extends LocalizableAction {

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
	public ActionStatistics(IlocalizationProvider provider, IUtilComm util) {
		super("statistics", provider);
		this.util = util;

		putValue(
				Action.ACCELERATOR_KEY,
				KeyStroke.getKeyStroke("alt enter"));
		putValue(
				Action.MNEMONIC_KEY,
				KeyEvent.VK_ENTER);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JTextArea editor;

		try {
			editor = util.getContext();
		} catch (ArrayIndexOutOfBoundsException ex) {
			return;
		}
		String text = editor.getText();

		JOptionPane.showMessageDialog(util.getNotepad(),
				"Number of non blank characters: " + text.trim().replace("\\s", "").length() + "\n" +
						"Number of characters: " + text.length() + "\n" +
						"Number of lines: " + editor.getLineCount(),
				"Statistike",
				JOptionPane.ERROR_MESSAGE);

	}
}
