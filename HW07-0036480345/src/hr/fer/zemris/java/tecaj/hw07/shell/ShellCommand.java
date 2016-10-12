package hr.fer.zemris.java.tecaj.hw07.shell;

import java.io.IOException;
import java.util.List;

/**
 * An abstraction for commands available in the {@linkplain MyShell}.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public interface ShellCommand {

	/**
	 * Executes the command.
	 *
	 * @param env
	 *            - the environment in which the command will be executed
	 * @param arguments
	 *            - the input arguments
	 * @return the status which the shell will assume after the execution of
	 *         this command
	 * @throws IOException
	 */
	ShellStatus executeCommand(Environment env, String arguments) throws IOException;

	/**
	 * Gets the command name.
	 *
	 * @return the command name
	 */
	String getCommandName();

	/**
	 * Gets the command description.
	 *
	 * @return the command description
	 */
	List<String> getCommandDescription();

	/**
	 * Re processes quotations, putting back whitespaces together and removing
	 * the quotation marks for compatibility with the filesystem.
	 *
	 * @param arg
	 *            the string to be edited
	 * @return the reprocessed string
	 */
	static String reProcessQuotations(String arg) {
		return arg.replaceAll(":", " ").replaceAll("\"", "");
	}
}
