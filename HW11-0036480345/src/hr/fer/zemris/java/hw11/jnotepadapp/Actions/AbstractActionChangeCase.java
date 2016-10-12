package hr.fer.zemris.java.hw11.jnotepadapp.Actions;

import java.awt.event.ActionEvent;

import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import hr.fer.zemris.java.hw11.jnotepadapp.IUtilComm;
import hr.fer.zemris.java.hw11.jnotepadapp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadapp.local.IlocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadapp.local.LocalizableAction;

/**
 * A class derived from {@linkplain LocalizableAction}. It represents a
 * {@linkplain JNotepadPP} functionality of inverting the case of the currently
 * selected text.
 * 
 * @see ActionLowerCase
 * @see ActionUpperCase
 * @see ActionInvertCase
 * @author Ivan Anić
 * @version 1.0
 */
public class AbstractActionChangeCase extends LocalizableAction {

	/** The generated serial user ID */
	private static final long serialVersionUID = 8794266594303724978L;
	/** Keeps a reference to the {@linkplain IUtilComm} interface. */
	private IUtilComm util;
	/** The given operation. */
	private CaseOperation op;

	/**
	 * Instantiates a new instance of this action.
	 * 
	 * @param key
	 *            the key
	 * @param provider
	 *            the provider which listens to language changes
	 * @param util
	 *            the {@link #util}
	 */
	public AbstractActionChangeCase(String key, IlocalizationProvider provider, IUtilComm util) {
		super(key, provider);
		this.util = util;
	}

	/**
	 * Instantiates a new instance of this action.
	 * 
	 * @param key
	 *            the key
	 * @param op
	 *            the given operation
	 * @param provider
	 *            the provider which listens to language changes
	 * @param util
	 *            the {@link #util}
	 */
	public AbstractActionChangeCase(String key, CaseOperation op, IlocalizationProvider provider, IUtilComm util) {
		super(key, provider);
		this.util = util;
		this.op = op;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		perform();
	}

	/**
	 * Performs the operation of changing tht case of the selected text.
	 */
	public void perform() {
		JTextArea editor = util.getContext();

		Document doc = editor.getDocument();
		int len = Math.abs(
				editor.getCaret().getDot() - editor.getCaret().getMark());
		int offset = 0;
		if (len != 0) {
			offset = Math.min(editor.getCaret().getDot(),
					editor.getCaret().getMark());
		} else {
			len = doc.getLength();
		}

		try {
			String text = doc.getText(offset, len);
			text = changeCase(text);
			doc.remove(offset, len);
			doc.insertString(offset, text, null);
		} catch (BadLocationException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Change the case of the given string depending on the given operation.
	 *
	 * @param text
	 *            the given text
	 * @return the edited string
	 */
	private String changeCase(String text) {
		char[] znakovi = text.toCharArray();

		for (int i = 0; i < znakovi.length; i++) {
			char c = znakovi[i];
			if (op.equals(CaseOperation.TO_LOWER)) {
				znakovi[i] = Character.toLowerCase(c);
			} else if (op.equals(CaseOperation.TO_UPPER)) {
				znakovi[i] = Character.toUpperCase(c);
			} else {
				if (Character.isLowerCase(c)) {
					znakovi[i] = Character.toUpperCase(c);
				} else if (Character.isUpperCase(c)) {
					znakovi[i] = Character.toLowerCase(c);
				}
			}
			text = new String(znakovi);
		}
		return text;
	}

}
