package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import hr.fer.zemris.java.tecaj.hw07.shell.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;

/**
 * An implementation of the {@linkplain ShellCommand} interface. This command
 * writes a directory listing of the given directory.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class LsShellCommand implements ShellCommand {

	/** The name of the command. */
	private final String name = "ls";
	/** The command description. */
	private final List<String> description = Arrays.asList(
			"This command takes a single argument – directory – and writes a directory listing (not recursive).",
			"Output consists of four columns:\n First column indicates if current object is directory (d),",
			"readable (r),writable (w) and executable (x). \nSecond column contains object size in bytes that is right",
			"aligned and occupies 10 characters. \nFollows file creation date/time and finally file name");

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) throws IOException {
		arguments = ShellCommand.reProcessQuotations(arguments);

		Path dir = Paths.get(arguments).toAbsolutePath().normalize();

		if (!Files.isDirectory(dir)) {
			env.writeln("You need to provide a path to a directory!");
			return ShellStatus.CONTINUE;
		}

		walkCurrentDirectory(dir, env);
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

	/**
	 * Performs the directory detour, printing the data about its contents to
	 * the console.
	 * 
	 * @param dir
	 *            - the given path
	 * @param env
	 *            - the current working environment
	 * @throws IOException
	 */
	private void walkCurrentDirectory(Path dir, Environment env) throws IOException {
		File path = dir.toFile();

		File[] files = path.listFiles();

		if ((files.length) == 0) {
			return;
		}

		for (File file : files) {
			String permissions = "";
			if (file.isFile()) {
				permissions += "-";
			} else {
				permissions += "d";
			}

			if (file.canRead()) {
				permissions += "r";
			} else {
				permissions += "-";
			}

			if (file.canWrite()) {
				permissions += "w";
			} else {
				permissions += "-";
			}

			if (file.canExecute()) {
				permissions += "x";
			} else {
				permissions += "-";
			}

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Path p = file.toPath();
			BasicFileAttributeView faView = Files.getFileAttributeView(
					p, BasicFileAttributeView.class, LinkOption.NOFOLLOW_LINKS);
			
			BasicFileAttributes attributes = null;
			try {
				attributes = faView.readAttributes();
			} catch (IOException e) {
				e.getMessage();
			}
			
			FileTime fileTime = attributes.creationTime();
			String formattedDateTime = sdf.format(new Date(fileTime.toMillis()));

			env.writeln(
					String.format("%-5s\t%10d %s %s", permissions, file.length(), formattedDateTime, file.getName()));
		}
	}
}
