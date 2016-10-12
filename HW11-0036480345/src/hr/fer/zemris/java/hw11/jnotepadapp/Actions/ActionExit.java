package hr.fer.zemris.java.hw11.jnotepadapp.Actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;

import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.KeyStroke;

import hr.fer.zemris.java.hw11.jnotepadapp.IUtilComm;
import hr.fer.zemris.java.hw11.jnotepadapp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadapp.local.IlocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadapp.local.LocalizableAction;

/**
 * A class derived from {@linkplain LocalizableAction}. It represents a
 * {@linkplain JNotepadPP} functionality of exiting the application.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class ActionExit extends LocalizableAction {

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
	public ActionExit(IlocalizationProvider provider, IUtilComm util) {
		super("exit", provider);
		this.util = util;

		putValue(
				Action.ACCELERATOR_KEY,
				KeyStroke.getKeyStroke("alt F4"));
		putValue(
				Action.MNEMONIC_KEY,
				KeyEvent.VK_F4);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		((JNotepadPP) util.getNotepad())
				.dispatchEvent(new WindowEvent(((JFrame) util.getNotepad()), WindowEvent.WINDOW_CLOSING));
	}
}
