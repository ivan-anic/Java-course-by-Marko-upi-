package hr.fer.zemris.java.hw11.jnotepadapp;

import java.io.File;

import javax.swing.JTextArea;
import javax.swing.text.Document;

/**
 * A wrapper for the {@linkplain Document file}. Internally it has stored
 * references to the document file, to the filename, to whether the file has
 * changed since the last save.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class DocumentWrapper {

	/** Keeps reference to the document itself. */
	private Document document;
	/** Keeps reference to the document, as a file. */
	private File file;
	/** Keeps reference to the document name */
	private String name;
	/** Keeps reference to the {@linkplain JTextArea} */
	private JTextArea editor;
	/**
	 * Keeps reference to the information about whether the document has changed
	 * after the last save.
	 */
	private boolean changed;

	/**
	 * Instantiates a new document wrapper with the desired parameters..
	 *
	 * @param document
	 *            the document
	 * @param editor
	 *            the editor
	 * @param file
	 *            the file
	 */
	public DocumentWrapper(Document document, JTextArea editor, File file) {
		super();
		this.document = document;
		this.name = file.getName();
		this.file = file;
	}

	/**
	 * Gets the editor.
	 *
	 * @return the editor
	 */
	public JTextArea getEditor() {
		return editor;
	}

	/**
	 * Sets the editor.
	 *
	 * @param editor
	 *            the new editor
	 */
	public void setEditor(JTextArea editor) {
		this.editor = editor;
	}

	/**
	 * Gets the file.
	 *
	 * @return the file
	 */
	public File getFile() {
		return file;
	}

	/**
	 * Checks if the document has changed since the last save.
	 *
	 * @return true, if is has changed
	 */
	public boolean isChanged() {
		return changed;
	}

	/**
	 * Sets the changed parameter to the desired value.
	 *
	 * @param changed
	 *            the new changed parameter
	 */
	public void setChanged(boolean changed) {
		this.changed = changed;
	}

	/**
	 * Gets the document.
	 *
	 * @return the document
	 */
	public Document getDocument() {
		return document;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}
}
