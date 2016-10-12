package hr.fer.zemris.java.tecaj.hw07.shell;

import java.io.IOException;

/**
 * An abstraction which will be passed to each defined {@linkplain ShellCommand}
 * . The each implemented command communicates with user (reads user input and
 * writes response) only through this interface.
 * 
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public interface Environment {

	/**
	 * Reads a line from the standard input.
	 *
	 * @return the line from the standard input.
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	String readLine() throws IOException;

	/**
	 * Writes some content to the standard output.
	 * 
	 * @param text
	 *            - the content to be written to the standard output
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	void write(String text) throws IOException;

	/**
	 * Writes a line to the standard output.
	 * 
	 * @param text
	 *            - the line to be written to the standard output
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	void writeln(String text) throws IOException;

	/**
	 * Gets a collection of available commands for this environment.
	 *
	 * @return an instance of Iterable, containing the commands of this
	 *         environment.
	 */
	Iterable<ShellCommand> commands();

	/**
	 * Gets the multiline symbol.
	 *
	 * @return the multiline symbol
	 */
	Character getMultilineSymbol();

	/**
	 * Sets the multiline symbol.
	 *
	 * @param symbol the new multiline symbol
	 */
	void setMultilineSymbol(Character symbol);

	/**
	 * Gets the prompt symbol.
	 *
	 * @return the prompt symbol
	 */
	Character getPromptSymbol();

	/**
	 * Sets the prompt symbol.
	 *
	 * @param symbol the new prompt symbol
	 */
	void setPromptSymbol(Character symbol);

	/**
	 * Gets the morelines symbol.
	 *
	 * @return the morelines symbol
	 */
	Character getMorelinesSymbol();

	/**
	 * Sets the morelines symbol.
	 *
	 * @param symbol the new morelines symbol
	 */
	void setMorelinesSymbol(Character symbol);

}
