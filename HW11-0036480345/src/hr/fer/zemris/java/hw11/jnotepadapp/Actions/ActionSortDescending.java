package hr.fer.zemris.java.hw11.jnotepadapp.Actions;

import java.awt.event.KeyEvent;
import java.text.Collator;
import java.util.Locale;

import javax.swing.Action;
import javax.swing.KeyStroke;

import hr.fer.zemris.java.hw11.jnotepadapp.IUtilComm;
import hr.fer.zemris.java.hw11.jnotepadapp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadapp.local.IlocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadapp.local.LocalizableAction;

/**
 * A class derived from {@linkplain LocalizableAction}. It represents a
 * {@linkplain JNotepadPP} functionality of sorting the currently selected lines
 * of the text in descending order.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class ActionSortDescending extends AbstractActionSort {

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
	public ActionSortDescending(IlocalizationProvider provider, IUtilComm util) {
		super("desc", provider, util, z -> {
			Locale locale = new Locale(provider.getLanguage());
			Collator hrCollator = Collator.getInstance(locale);
			z.sort(hrCollator.reversed());
			return z;
		});

		putValue(
				Action.ACCELERATOR_KEY,
				KeyStroke.getKeyStroke("alt L"));
		putValue(
				Action.MNEMONIC_KEY,
				KeyEvent.VK_L);

	}
}
