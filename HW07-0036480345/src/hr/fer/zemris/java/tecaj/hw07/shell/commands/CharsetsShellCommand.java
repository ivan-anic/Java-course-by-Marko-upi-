package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

import hr.fer.zemris.java.tecaj.hw07.shell.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;

/**
 * An implementation of the {@linkplain ShellCommand} interface. This command
 * lists names of supported charsets for your Java platform.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class CharsetsShellCommand implements ShellCommand {

	/** The name of the command. */
	private final String name = "charsets";
	/** The command description. */
	private final List<String> description = Arrays.asList(
			"This command lists names of supported charsets for your Java platform.");

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) throws IOException {
		if (!arguments.equals("")) {
			env.writeln("This command takes no arguments!");
			return ShellStatus.CONTINUE;
		}

		for (Charset s : Charset.availableCharsets().values()) {
			env.writeln(s.toString());
		}

		return ShellStatus.CONTINUE;
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
