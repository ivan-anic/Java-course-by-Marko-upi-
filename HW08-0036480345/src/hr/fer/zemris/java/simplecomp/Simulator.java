package hr.fer.zemris.java.simplecomp;

import java.io.IOException;
import java.util.Scanner;

import hr.fer.zemris.java.simplecomp.impl.ComputerImpl;
import hr.fer.zemris.java.simplecomp.impl.ExecutionUnitImpl;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.ExecutionUnit;
import hr.fer.zemris.java.simplecomp.models.InstructionCreator;
import hr.fer.zemris.java.simplecomp.models.Memory;
import hr.fer.zemris.java.simplecomp.models.Registers;
import hr.fer.zemris.java.simplecomp.parser.InstructionCreatorImpl;
import hr.fer.zemris.java.simplecomp.parser.ProgramParser;

/**
 * Simulates the built {@linkplain Computer} and its components;
 * {@linkplain Memory}, {@linkplain Registers}, and {@linkplain ExecutionUnit}.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class Simulator {

	/**
	 * The main method, first one to be executed when the program starts.
	 * 
	 * @param args
	 *            - the input arguments, a path to a test file should be
	 *            entered.
	 */
	public static void main(String[] args) {

		String path = null;

		Scanner sc = new Scanner(System.in);
		if (args.length == 0) {
			System.out.println(
					"Please enter a path to the assembly you want to run.");
			path = sc.nextLine();
		} else if (args.length == 1) {
			path = args[0];
		} else {
			System.out.println("Invalid input.");
			System.exit(1);
		}

		Computer comp = new ComputerImpl(256, 16);

		InstructionCreator creator = new InstructionCreatorImpl("hr.fer.zemris.java.simplecomp.impl.instructions");

		try {
			ProgramParser.parse(path, comp, creator);
		} catch (IOException e) {
			System.out.println("An I/O error occurred!");
		} catch (Exception e) {
			System.out.println("An error occurred during parsing!");
		}

		ExecutionUnit exec = new ExecutionUnitImpl();

		exec.go(comp);

		sc.close();
	}
}
