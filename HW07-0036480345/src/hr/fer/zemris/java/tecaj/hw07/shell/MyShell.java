package hr.fer.zemris.java.tecaj.hw07.shell;

import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import hr.fer.zemris.java.tecaj.hw07.shell.commands.CatShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.commands.CharsetsShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.commands.CopyShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.commands.ExitShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.commands.HelpShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.commands.HexDumpShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.commands.LsShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.commands.MkdirShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.commands.SymbolShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.commands.TreeShellCommand;

/**
 * An implementation of a command-line program which offers various commands:
 * <br>
 * 
 * <ul>
 * <li>{@linkplain CatShellCommand}</li>
 * <li>{@linkplain CharsetsShellCommand}</li>
 * <li>{@linkplain CopyShellCommand}</li>
 * <li>{@linkplain ExitShellCommand}</li>
 * <li>{@linkplain HelpShellCommand}</li>
 * <li>{@linkplain HexDumpShellCommand}</li>
 * <li>{@linkplain LsShellCommand}</li>
 * <li>{@linkplain MkdirShellCommand}</li>
 * <li>{@linkplain SymbolShellCommand}</li>
 * <li>{@linkplain TreeShellCommand}</li>
 * </ul>
 * 
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class MyShell {

	/**
	 * The main method, first one to be executed when the program starts.
	 * 
	 * @param args
	 *            - the input arguments, not used here
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {

		ShellEnvironment env = new ShellEnvironment();

		env.initializeShell();

		ShellStatus status = ShellStatus.CONTINUE;

		env.writeln("Welcome to MyShell v 1.0");

		do {
			env.write(env.getPromptSymbol().toString());

			String[] arg = null;
			try {
				String command = env.readLine();
				command = processQuotations(command);
				arg = command.split(" ");

				if (arg.length == 0) {
					env.writeln("No command was entered.");
					continue;
				}
			} catch (IOException ex) {
				env.writeln("An error occured during reading from the standard input.");
			}

			String commandName = arg[0];
			if (commandName.charAt(commandName.length() - 1) == env.getMorelinesSymbol()) {
				commandName.substring(0, commandName.length() - 1);
			}
			if (!env.commands.containsKey(commandName)) {
				env.writeln("Invalid command.");
				continue;
			}

			String temp = Arrays.toString(Arrays.copyOfRange(arg, 1, arg.length))
					.replace("[", "").replace("]", "").replace(",", "");

			temp = processInput(temp, env);

			ShellCommand shellCommand = env.commands.get(commandName);
			try {
				status = shellCommand.executeCommand((Environment) env, temp);
			} catch (IOException ex) {
				env.writeln("An error occured during reading from the standard input.");
			} catch (InvalidPathException ex) {
				env.writeln("Invalid characters in the path name.");
			}
		} while (status != ShellStatus.TERMINATE);

		env.writeln("Goodbye!");
	}

	/**
	 * @param arg
	 *            - the user console input
	 * @param env
	 *            - the current working environment
	 * @return the string with all of the arguments
	 * @throws IOException
	 *             - if an exception occurs during input/output
	 */
	private static String processInput(String arg, Environment env) throws IOException {
		if (arg.equals("")) {
			return arg;
		}
		String returnArgs = "";
		String newLine = null;

		char lastchar;
		if (arg.charAt(arg.length() - 1) == env.getMorelinesSymbol()) {
			returnArgs += arg.substring(0, arg.length() - 1);

			while (true) {
				try {
					env.write(env.getMultilineSymbol().toString());
					newLine = env.readLine();
				} catch (IOException e) {
					env.writeln("An error occured during reading from the standard input.");
					break;
				}

				if (newLine.equals("")) {
					break;
				}

				lastchar = newLine.charAt(newLine.length() - 1);

				if (lastchar != env.getMorelinesSymbol()) {
					returnArgs += " " + newLine.substring(0, newLine.length());
					break;
				} else {
					returnArgs += " " + newLine.substring(0, newLine.length() - 1);
				}
				returnArgs = returnArgs.trim();
			}
		} else {
			returnArgs += arg.substring(0, arg.length());
		}
		return returnArgs.trim();
	}

	/**
	 * Processes quotations, rearranging whitespaces together and escaping the
	 * quotation marks for compatibility with the filesystem.
	 *
	 * @param arg
	 *            the input string
	 * @return the edited string
	 */
	private static String processQuotations(String arg) {
		int len = arg.length();
		char c;
		int flag = 1;
		
		for (int i = 0; i < len; i++) {
			c = arg.charAt(i);
			if (c == '"' && !(arg.charAt(i - 1) == '\\')) {
				flag = 1 - flag;
			}
			
			if (flag == 0) {
				if (c == ' ' && !(arg.charAt(i - 1) == '"') && !(arg.charAt(i - 2) == '\\')) {
					arg = arg.substring(0, i) + ':' + arg.substring(i + 1);
				}
			}
		}

		len = arg.length();
		for (int i = 0; i < len; i++) {
			c = arg.charAt(i);
			if (c == '"' && !(arg.charAt(i - 1) == '\\')) {
				flag = 1 - flag;
			}
			
			if (flag == 0) {
				if (c == '\\' && (arg.charAt(i + 1) == '"')) {
					arg = arg.substring(0, i) + '"' + arg.substring(i + 2);
					len--;
				}

				if (c == '\\' && (arg.charAt(i + 1) == '\\')) {
					arg = arg.substring(0, i) + '\\' + arg.substring(i + 2);
					len--;
				}
			}
		}
		return arg;
	}

	/**
	 * An environment used by a shell (i.e. {@linkplain MyShell}) or
	 * communication with the user.
	 * 
	 * @author Ivan Anić
	 * @version 1.0
	 */
	static class ShellEnvironment implements Environment {

		/** A map of available command for using in this shell. */
		private Map<String, ShellCommand> commands = new HashMap<>();;

		/**
		 * A symbol which represents that a command will span through multiple
		 * lines.
		 */
		private Character MULTILINE_SYMBOL = '|';
		/**
		 * A symbol which represents that the shell awaits a new command.
		 */
		private Character PROMPT_SYMBOL = '>';
		/**
		 * A symbol which represents that the shell awaits a line of a multiline
		 * command.
		 */
		private Character MORELINES_SYMBOL = '\\';

		/**
		 * Initialises the shell; puts all of the available shell commands into
		 * a {@linkplain HashMap}.
		 */
		private void initializeShell() {
			commands.put("ls", new LsShellCommand());
			commands.put("tree", new TreeShellCommand());
			commands.put("exit", new ExitShellCommand());
			commands.put("hexdump", new HexDumpShellCommand());
			commands.put("copy", new CopyShellCommand());
			commands.put("charsets", new CharsetsShellCommand());
			commands.put("cat", new CatShellCommand());
			commands.put("mkdir", new MkdirShellCommand());
			commands.put("symbol", new SymbolShellCommand());
			commands.put("help", new HelpShellCommand());

		}

		/**
		 * A {@linkplain Scanner} used to get a new line from the standard
		 * input.
		 */
		Scanner sc = new Scanner(System.in);

		@Override
		public String readLine() throws IOException {
			return sc.nextLine();
		}

		@Override
		public void write(String text) throws IOException {
			System.out.print(text);
		}

		@Override
		public void writeln(String text) throws IOException {
			System.out.println(text);
		}

		@Override
		public Iterable<ShellCommand> commands() {
			return commands.values();
		}

		@Override
		public Character getMultilineSymbol() {
			return MULTILINE_SYMBOL;
		}

		@Override
		public void setMultilineSymbol(Character symbol) {
			MULTILINE_SYMBOL = symbol;
		}

		@Override
		public Character getPromptSymbol() {
			return PROMPT_SYMBOL;
		}

		@Override
		public void setPromptSymbol(Character symbol) {
			PROMPT_SYMBOL = symbol;
		}

		@Override
		public void setMorelinesSymbol(Character symbol) {
			MORELINES_SYMBOL = symbol;
		}

		@Override
		public Character getMorelinesSymbol() {
			return MORELINES_SYMBOL;
		}

	}

}
