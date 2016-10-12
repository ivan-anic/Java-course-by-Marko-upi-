package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import hr.fer.zemris.java.tecaj.hw07.shell.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;

/**
 * An implementation of the {@linkplain ShellCommand} interface. This command
 * provides information about the shell commands.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class HelpShellCommand implements ShellCommand {

	/** The name of the command. */
	private final String name = "help";
	/** The command description. */
	private final List<String> description = Arrays.asList("Provides information about the shell commands.");

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) throws IOException {
		String[] args = arguments.split(" ");

		if (args.length > 1) {
			env.writeln("You need to provide one or no arguments!");
			return ShellStatus.CONTINUE;
		}
		
		boolean search = false;
		boolean found = false;
		if (args.length == 1 && !(args[0].equals(""))) {
			search = true;
		}
		
		for (ShellCommand comm : env.commands()) {
			if (search) {
				if (comm.getCommandName().equals(args[0])) {
					env.writeln(comm.getCommandName());
					List<String> list = comm.getCommandDescription();
					for (String s : list) {
						env.writeln(s);
					}
					env.writeln("");
					found = true;
					break;
				}
			} else {
				env.writeln(comm.getCommandName());
			}
		}

		if (!found){
			env.writeln("No such command!");
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
