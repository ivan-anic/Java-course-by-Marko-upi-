package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import hr.fer.zemris.java.tecaj.hw07.shell.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;

/**
 * An implementation of the {@linkplain ShellCommand} interface. Creates a new
 * directory in the desired path.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class MkdirShellCommand implements ShellCommand {

	/** The name of the command. */
	private final String name = "mkdir";
	/** The command description. */
	private final List<String> description = Arrays.asList("Creates a new directory with the specified path.");

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) throws IOException {
		String[] args = arguments.split(" ");

		if (args.length != 1) {
			env.writeln("You need to provide one argument!");
			return ShellStatus.CONTINUE;
		}

		args[0] = ShellCommand.reProcessQuotations(args[0]);
		
		if (arguments.equals("")) {
			System.err.println("You need to provide an argument!");
			return ShellStatus.CONTINUE;
		}

		Path dest = Paths.get(args[0]).toAbsolutePath().normalize();
		if (Files.exists(dest)) {
			System.err.println("Already exists!");
			return ShellStatus.CONTINUE;
		}

		File destination = dest.toFile();
		Files.createDirectories(dest);
		destination.mkdir();

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
