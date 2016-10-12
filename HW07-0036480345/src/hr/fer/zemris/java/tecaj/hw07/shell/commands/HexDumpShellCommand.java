package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;

import hr.fer.zemris.java.tecaj.hw07.shell.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;

/**
 * An implementation of the {@linkplain ShellCommand} interface. This command
 * produces the hex-output of the given file.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class HexDumpShellCommand implements ShellCommand {

	/** The name of the command. */
	private final String name = "hexdump";
	/** The command description. */
	private final List<String> description = Arrays.asList(
			"This command expects a single argument: file name, and produces its hex-output.",
			"First column represents the hex count, second column represents the hex value",
			"of the file, and the third column represents the raw data.");

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) throws IOException {
		arguments = ShellCommand.reProcessQuotations(arguments);

		if (arguments.equals("")) {
			env.writeln("You need to provide an argument!");
			return ShellStatus.CONTINUE;
		}

		if (!Files.exists(Paths.get(arguments))) {
			env.writeln("You need to provide a path to a file!");
			return ShellStatus.CONTINUE;
		}

		InputStream is;
		try {
			is = new BufferedInputStream(
					Files.newInputStream(Paths.get(arguments), StandardOpenOption.READ));

			int i = 0;

			while (is.available() > 0) {
				StringBuilder sb1 = new StringBuilder();
				StringBuilder sb2 = new StringBuilder("| ");
				System.out.printf("%08X: ", i * 16);

				for (int j = 0; j < 16; j++) {
					if (j == 8) {
						sb1.append('|');
					}

					if (is.available() > 0) {
						int value = (int) is.read();
						if (j == 7) {
							sb1.append(String.format("%02X", value));
						} else {
							sb1.append(String.format("%02X ", value));
						}
						if (!Character.isISOControl(value)) {
							sb2.append((char) value);
						} else {
							sb2.append(".");
						}
					} else {
						for (; j < 15; j++) {
							if (j == 7) {
								sb1.append("  |");
							}
							sb1.append("   ");
						}
					}
				}
				
				System.out.print(sb1);
				System.out.println(sb2);
				i++;
			}
			is.close();
		} catch (IOException e) {
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
