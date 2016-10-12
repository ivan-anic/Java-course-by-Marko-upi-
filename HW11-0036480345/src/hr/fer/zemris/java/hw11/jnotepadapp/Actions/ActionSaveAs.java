package hr.fer.zemris.java.hw11.jnotepadapp.Actions;

import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.KeyStroke;

import hr.fer.zemris.java.hw11.jnotepadapp.IUtilComm;
import hr.fer.zemris.java.hw11.jnotepadapp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadapp.local.IlocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadapp.local.LocalizableAction;

/**
 * A class derived from {@linkplain LocalizableAction}. It represents a
 * {@linkplain JNotepadPP} functionality of saving the currently opened file
 * with a specified name.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class ActionSaveAs extends AbstractActionSave {

	/** The generated serial user ID */
	private static final long serialVersionUID = 8770114486750062581L;

	/**
	 * Instantiates a new instance of this action.
	 *
	 * @param provider
	 *            the provider which listens to language changes
	 * @param util
	 *            the {@linkplain IUtilComm} interface
	 */
	public ActionSaveAs(IlocalizationProvider provider, IUtilComm util) {
		super("saveas", provider, util);

		putValue(
				Action.ACCELERATOR_KEY,
				KeyStroke.getKeyStroke("control alt S"));
		putValue(
				Action.MNEMONIC_KEY,
				KeyEvent.VK_A);

	}
}
