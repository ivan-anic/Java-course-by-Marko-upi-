package hr.fer.zemris.java.hw11.jnotepadapp.Actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.Action;
import javax.swing.KeyStroke;

import hr.fer.zemris.java.hw11.jnotepadapp.DocumentWrapper;
import hr.fer.zemris.java.hw11.jnotepadapp.IUtilComm;
import hr.fer.zemris.java.hw11.jnotepadapp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadapp.local.IlocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadapp.local.LocalizableAction;

/**
 * A class derived from {@linkplain LocalizableAction}. It represents a
 * {@linkplain JNotepadPP} functionality of opening a new blank document.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class ActionNewDocument extends LocalizableAction {

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
	public ActionNewDocument(IlocalizationProvider provider, IUtilComm util) {
		super("new", provider);
		this.util = util;

		putValue(
				Action.ACCELERATOR_KEY,
				KeyStroke.getKeyStroke("control N"));
		putValue(
				Action.MNEMONIC_KEY,
				KeyEvent.VK_N);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		util.getNotepad().addTab(new DocumentWrapper(null, null, new File("untitled")), "");
	}
}
