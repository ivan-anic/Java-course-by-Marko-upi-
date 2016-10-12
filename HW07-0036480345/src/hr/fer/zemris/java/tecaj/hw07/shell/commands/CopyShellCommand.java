package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;

import hr.fer.zemris.java.tecaj.hw07.shell.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;

/**
 * An implementation of the {@linkplain ShellCommand} interface. This command
 * copies the desired file to a given path.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class CopyShellCommand implements ShellCommand {

	/** The name of the command. */
	private final String name = "copy";
	/** The command description. */
	private final List<String> description = Arrays.asList(
			"Takes two arguments and copies the file stated in the first argument to the destination file",
					"which is specified in the second argument. If the second argument is a directory, the shell",
					"copies the file into the specified directory with the the same name.");

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) throws IOException {
		String[] args = arguments.split(" ");

		if (args.length != 2) {
			env.writeln("You need to provide two arguments!");
			return ShellStatus.CONTINUE;
		}

		args[0] = ShellCommand.reProcessQuotations(args[0]);
		args[1] = ShellCommand.reProcessQuotations(args[1]);

		Path src = Paths.get(args[0]).toAbsolutePath().normalize();
		Path dest = Paths.get(args[1]).toAbsolutePath().normalize();

		if (Files.isDirectory(src)) {
			env.writeln("First argument must be a file!");
			return ShellStatus.CONTINUE;
		}

		if (Files.exists(Paths.get(dest.toString() + "/" + src.getFileName()))) {
			while (true) {
				env.writeln("File already exists! Do you want to overwrite it? [Y/N]");
				String s = env.readLine();
				if (s.equals("Y")) {
					break;
				} else if (s.equals("N")) {
					return ShellStatus.CONTINUE;
				} else {
					env.writeln("Wrong input. Please input 'Y' for yes or 'N' for no.");
				}
			}
		}

		if (Files.isDirectory(dest)) {
			dest = Paths.get(dest.toString() + "/" + src.getFileName());
		}
		byte[] buffer = new byte[4096];

		try (InputStream is = new BufferedInputStream(Files.newInputStream(src, StandardOpenOption.READ));
				OutputStream os = new BufferedOutputStream(new FileOutputStream(dest.toFile()))) {

			while ((is.read(buffer)) > 0) {
				if (is != null) {
					os.write(buffer);
				}
			}

		} catch (IOException ex) {
			System.err.println(ex.getMessage());

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
