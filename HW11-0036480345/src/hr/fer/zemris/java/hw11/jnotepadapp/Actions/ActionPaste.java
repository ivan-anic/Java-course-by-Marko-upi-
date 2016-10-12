package hr.fer.zemris.java.hw11.jnotepadapp.Actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.KeyStroke;

import hr.fer.zemris.java.hw11.jnotepadapp.IUtilComm;
import hr.fer.zemris.java.hw11.jnotepadapp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadapp.local.IlocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadapp.local.LocalizableAction;

/**
 * A class derived from {@linkplain LocalizableAction}. It represents a
 * {@linkplain JNotepadPP} functionality of pasting a file from the clipboard.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class ActionPaste extends LocalizableAction {

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
	public ActionPaste(IlocalizationProvider provider, IUtilComm util) {
		super("paste", provider);
		this.util = util;

		putValue(
				Action.ACCELERATOR_KEY,
				KeyStroke.getKeyStroke("control V"));
		putValue(
				Action.MNEMONIC_KEY,
				KeyEvent.VK_V);

	}

	/** The generated serial user ID */
	private static final long serialVersionUID = 8794266594303724978L;

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			util.getContext().cut();
		} catch (ArrayIndexOutOfBoundsException ex) {
			return;
		}
	}
}
