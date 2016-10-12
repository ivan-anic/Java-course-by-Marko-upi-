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
 * {@linkplain JNotepadPP} functionality of setting the currently selected text
 * to lower case.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class ActionLowerCase extends AbstractActionChangeCase {

	/** The generated serial user ID */
	private static final long serialVersionUID = 8794266594303724978L;

	/**
	 * Instantiates a new instance of this action.
	 *
	 * @param provider
	 *            the provider which listens to language changes
	 * @param util
	 *            the {@linkplain IUtilComm} interface
	 */
	public ActionLowerCase(IlocalizationProvider provider, IUtilComm util) {
		super("lower", CaseOperation.TO_LOWER, provider, util);
		putValue(
				Action.ACCELERATOR_KEY,
				KeyStroke.getKeyStroke("control J"));
		putValue(
				Action.MNEMONIC_KEY,
				KeyEvent.VK_J);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		super.perform();
	}

}
