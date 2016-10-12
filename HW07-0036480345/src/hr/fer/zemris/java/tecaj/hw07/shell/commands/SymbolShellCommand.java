package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import hr.fer.zemris.java.tecaj.hw07.shell.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;

/**
 * An implementation of the {@linkplain ShellCommand} interface. This command
 * changes the default shell symbols.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class SymbolShellCommand implements ShellCommand {

	/** The name of the command. */
	private final String name = "symbol";
	/** The command description. */
	private final List<String> description = Arrays.asList("Changes the default shell symbols.");

	/**
	 * One of the possible user arguments, indicates editing the default prompt
	 * symbol.
	 */
	private final String SYMBOL_PARAM = "PROMPT";
	/**
	 * One of the possible user arguments, indicates editing the default
	 * multiline symbol.
	 */
	private final String MULTILINE_PARAM = "MULTILINE";
	/**
	 * One of the possible user arguments, indicates editing the default
	 * morelines symbol.
	 */
	private final String MORELINES_PARAM = "MORELINES";

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) throws IOException {
		String[] args = arguments.split(" ");

		if (args.length < 1 || args.length > 2) {
			env.writeln("You need to provide one or two arguments!");
			return ShellStatus.CONTINUE;
		}

		if (args.length == 1) {
			switch (args[0]) {
			case (SYMBOL_PARAM):
				env.writeln("Symbol for " + args[0] + " is '" + env.getPromptSymbol() + "'");
				break;
			case (MULTILINE_PARAM):
				env.writeln("Symbol for " + args[0] + " is '" + env.getMultilineSymbol() + "'");
				break;
			case (MORELINES_PARAM):
				env.writeln("Symbol for " + args[0] + " is '" + env.getMorelinesSymbol() + "'");
				break;
			default:
				env.writeln("Invalid argument!");
				return ShellStatus.CONTINUE;
			}
		} else {
			if (args[1].length() != 1) {
				env.writeln("Second argument is not a symbol!");
				return ShellStatus.CONTINUE;
			}

			Character temp;
			switch (args[0]) {
			case (SYMBOL_PARAM):
				temp = env.getPromptSymbol();
				env.setPromptSymbol((args[1]).charAt(0));
				env.writeln(
						"Symbol for " + args[0] + " is changed from '" + temp + "' to '" + env.getPromptSymbol() + "'");
				break;
			case (MULTILINE_PARAM):
				temp = env.getMultilineSymbol();
				env.setMultilineSymbol((args[1]).charAt(0));
				env.writeln(
						"Symbol for " + args[0] + " is changed from '" + temp + "' to '" + env.getMultilineSymbol()
								+ "'");
				break;
			case (MORELINES_PARAM):
				temp = env.getMorelinesSymbol();
				env.setMorelinesSymbol((args[1]).charAt(0));
				env.writeln(
						"Symbol for " + args[0] + " is changed from '" + temp + "' to '" + env.getMorelinesSymbol()
								+ "'");
				break;
			default:
				env.writeln("Invalid argument!");
				return ShellStatus.CONTINUE;

			}
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
