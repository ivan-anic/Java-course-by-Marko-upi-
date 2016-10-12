package hr.fer.zemris.java.hw11.jnotepadapp;

import java.nio.file.Path;

import javax.swing.JTextArea;

/**
 * An interface implemented by the {@linkplain JNotepadPP}. Its purpose is the
 * communication with the notepad actions, giving them access to certain
 * elements of the notepad context.
 */
public interface IUtilComm {

	/**
	 * Gets the reference to the notepad.
	 *
	 * @return the reference to the notepad
	 */
	public JNotepadPP getNotepad();

	/**
	 * Gets the currently selected text area.
	 *
	 * @return the currently selected text area.
	 */
	public JTextArea getContext();

	/**
	 * Gets the opened file path.
	 *
	 * @return the opened file path
	 */
	public Path getOpenedFilePath();

	/**
	 * Gets the selected file path.
	 *
	 * @return the selected file path
	 */
	public Path getSelectedFilePath();

	/**
	 * Sets the image to a tab. There are two types of images;
	 * <ul>
	 * <li>Green diskette - file is unchanged</li>
	 * <li>Red diskette - file is changed</li>
	 * </ul>
	 *
	 * @param changed
	 *            the new image
	 */
	public void setImage(boolean changed);

	/**
	 * Sets the opened file path to the desired path.
	 *
	 * @param path
	 *            the new opened file path
	 */
	public void setOpenedFilePath(Path path);

	/**
	 * Updates the tab name.
	 */
	public void updateTabName();
}
