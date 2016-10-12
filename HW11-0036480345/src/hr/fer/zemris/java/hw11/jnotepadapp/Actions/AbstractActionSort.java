package hr.fer.zemris.java.hw11.jnotepadapp.Actions;

import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Utilities;

import hr.fer.zemris.java.hw11.jnotepadapp.IUtilComm;
import hr.fer.zemris.java.hw11.jnotepadapp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadapp.local.IlocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadapp.local.LocalizableAction;

/**
 * A class derived from {@linkplain LocalizableAction}. It represents a
 * {@linkplain JNotepadPP} functionality of sorting the currently selected text.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class AbstractActionSort extends LocalizableAction {

	/** The generated serial user ID */
	private static final long serialVersionUID = 8794266594303724978L;
	/** Keeps a reference to the {@linkplain IUtilComm} interface. */
	private IUtilComm util;
	/** Keeps a reference to the start of the selected string. */
	private int start;
	/** Keeps a reference to the sorting function given to this action. */
	Function<List<String>, List<String>> func;

	/**
	 * Instantiates a new instance of this action.
	 * 
	 * @param key
	 *            the key
	 * @param provider
	 *            the provider which listens to language changes
	 * @param util
	 *            the {@link #util}
	 * @param func
	 *            the sorting function given to this action
	 */
	public AbstractActionSort(String key, IlocalizationProvider provider,
			IUtilComm util, Function<List<String>, List<String>> func) {

		super(key, provider);
		this.util = util;
		this.func = func;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		List<String> res = getSelected();

		if (res == null) {
			return;
		}

		res = func.apply(res);

		String str = String.join("\n", res);

		try {
			util.getContext().getDocument().insertString(start, str, null);
		} catch (BadLocationException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * Gets the selected text from the current text area.
	 *
	 * @return the selected text
	 */
	public List<String> getSelected() {
		JTextArea editor;
		try {
			editor = util.getContext();
		} catch (ArrayIndexOutOfBoundsException ex) {
			return null;
		}
		Document doc = editor.getDocument();

		int dot = editor.getCaret().getDot();
		int mark = editor.getCaret().getMark();

		int len = Math.abs(dot - mark);

		if (len == 0) {
			return null;
		}

		int offset = Math.min(mark, dot);

		try {
			start = Utilities.getRowStart(editor, offset);
			int end = Utilities.getRowEnd(editor, offset + len);

			String text = editor.getText().substring(start, end);
			doc.remove(start, end - start);

			return Arrays.asList(text.split("\r\n|\r|\n"));

		} catch (BadLocationException e) {
			e.printStackTrace();
		}
		return null;
	}

}
