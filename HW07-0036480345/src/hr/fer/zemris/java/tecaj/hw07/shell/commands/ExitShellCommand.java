package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import hr.fer.zemris.java.tecaj.hw07.shell.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;

/**
 * An implementation of the {@linkplain ShellCommand} interface. This command
 * terminates the shell.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class ExitShellCommand implements ShellCommand {

	/** The name of the command. */
	private final String name = "exit";
	/** The command description. */
	private final List<String> description = Arrays.asList("Terminates the shell.");

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) throws IOException {
		if (!arguments.equals("")) {
			env.writeln("This command takes no arguments!");
			return ShellStatus.CONTINUE;
		}
		return ShellStatus.TERMINATE;
	}

	@Override
	public String getCommandName() {
		return name;
	}

	@Override
	public List<String> getCommandDescription() {
		return description;
	}

}
