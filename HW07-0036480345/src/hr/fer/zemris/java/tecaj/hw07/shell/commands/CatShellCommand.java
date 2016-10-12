package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import hr.fer.zemris.java.tecaj.hw07.shell.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;

/**
 * An implementation of the {@linkplain ShellCommand} interface. This command
 * opens the given file and writes its content to console
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class CatShellCommand implements ShellCommand {

	/** The name of the command. */
	private final String name = "cat";
	/** The command description. */
	private final List<String> description = Arrays.asList(
			"Command cat takes one or two arguments. The first argument is path to some file and is mandatory. The",
			"second argument is charset name that should be used to interpret chars from bytes. If not provided, a default",
			"platform charset should be used. This command opens given file and writes its content to console.");

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) throws IOException {
		String[] args = arguments.split(" ");

		if (args.length < 1 || args.length > 2) {
			env.writeln("You need to provide one or two arguments!");
			return ShellStatus.CONTINUE;
		}

		args[0] = ShellCommand.reProcessQuotations(args[0]);

		Path src = Paths.get(args[0]).toAbsolutePath().normalize();

		if (Files.isDirectory(src)) {
			env.writeln("First argument must be a file!");
			return ShellStatus.CONTINUE;
		}
		@SuppressWarnings("unused")
		Charset charset = null;
		if (args.length == 2 && Charset.isSupported(args[1])) {
			charset = Charset.forName(args[1]);
		} else {
			charset = Charset.defaultCharset();
		}

		try (BufferedReader is = new BufferedReader(new FileReader(src.toFile()));) {

			String line;
			while ((line = is.readLine()) != null) {
				env.writeln(line);
			}

		} catch (IOException ex) {
			env.writeln(ex.getMessage());
		} catch (Exception ignorable) {
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
