package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;
import java.util.List;

import hr.fer.zemris.java.tecaj.hw07.shell.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;

/**
 * An implementation of the {@linkplain ShellCommand} interface. This command
 * writes a directory listing of the given directory tree.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class TreeShellCommand implements ShellCommand {

	/** The name of the command. */
	private final String name = "tree";
	/** The command description. */
	private final List<String> description = Arrays.asList("Writes a directory listing of the given directory tree");

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) throws IOException {
		arguments = ShellCommand.reProcessQuotations(arguments);

		Path dir = Paths.get(arguments);

		if (!Files.isDirectory(dir)) {
			env.writeln("You need to path to directory!");
			return ShellStatus.CONTINUE;
		}

		try {
			Files.walkFileTree(dir, new Output(env));
		} catch (IOException e) {
			System.err.println("An exception occurred during file visiting.");
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

	/**
	 * An implementation of the {@linkplain FileVisitor} interface, performs the
	 * directory tree detour.
	 */
	private static class Output implements FileVisitor<Path> {

		/** The current depth of the tree. */
		private int level;
		/** The current environment. */
		private Environment env;

		/**
		 * Instantiates this class, sets the environment reference to the
		 * current environment.
		 *
		 * @param env
		 *            - the current working environment
		 */
		public Output(Environment env) {
			super();
			this.env = env;
		}

		@Override
		public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs)
				throws IOException {
			output(dir);
			level++;
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
			level--;
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
			output(file);
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
			return FileVisitResult.CONTINUE;
		}

		/**
		 * Prints the current directory to the standard output, shifting the
		 * output to the right depending of the current tree level.
		 *
		 * @param dir
		 *            - the path to the file which will be printed to the
		 *            standard output
		 * @throws IOException 
		 */
		private void output(Path dir) throws IOException {
			if (level == 0) {
				env.writeln(dir.normalize().toAbsolutePath().toString());
			} else {
				env.writeln(String.format("%" + (2 * level) + "s%s", "", dir.getFileName()));
			}
		}
	}

}
