package hr.fer.zemris.java.tecaj.hw5.db;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import hr.fer.zemris.java.tecaj.hw5.db.filters.IFilter;
import hr.fer.zemris.java.tecaj.hw5.db.filters.QueryFilter;

/**
 * Represents an interactive demonstration of this package. Supports various
 * options, which result in returning the wanted records from the database.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class StudentDB {

	/**
	 * The main method, first one to be executed when the program starts.
	 * 
	 * @param args
	 *            - the input arguments (not used)
	 * @throws NullPointerException if an error occurred 
	 * @throws IOException if a error occurred
	 * 
	 */
	@SuppressWarnings("null")
	public static void main(String[] args) throws NullPointerException, IOException {

		List<String> lines = Files.readAllLines(Paths.get("./database.txt"), StandardCharsets.UTF_8);

		int size = lines.size();

		String[] parts = new String[size];

		for (int i = 0; i < size; i++) {
			parts[i] = lines.get(i);
		}

		String re = "(\\p{L}+).*";
		Pattern p = Pattern.compile(re, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
		Matcher m;
		String s;

		StudentDatabase<String, StudentRecord> DB = new StudentDatabase<String, StudentRecord>(parts);

		Scanner scanner = new Scanner(System.in);

		String word = null;
		String op = null;
		String par2 = null;

		List<StudentRecord> result = null;
		
		while ((s = scanner.nextLine().trim()) != "exit") {

			m = p.matcher(s);

			if (m.find()) {
				word = m.group(1);
			}

			switch (word.toLowerCase()) {
			case ("query"):
				IFilter filter = null;
				try {
					filter = new QueryFilter(s.substring(5, s.length()));
					result = DB.filter(filter);
					printOut(result);
				} catch (NullPointerException ex) {
					System.out.println("Invalid query command.");
				} catch (Exception e) {
					System.out.println("An error occurred.");
				}
				break;

			case ("indexquery"):
				if (op.equals("=")) {
					printOutSingleEntry(DB.forJMBAG(par2));
				}
			}
		}

		System.out.println("Goodbye!\n");
		scanner.close();
	}

	/**
	 * Prints out only a single entry from the database.
	 * 
	 * @param par
	 *            the student record to be displayed.
	 */
	private static void printOutSingleEntry(StudentRecord par) {
		System.out.println(par.getJMBAG());
	}

	/**
	 * Prints out a formatted list of all of the entries which satisfy the given
	 * command.
	 *
	 * @param records
	 *            the records to be displayed.
	 */
	private static void printOut(List<StudentRecord> records) {
		
		int maxLengthFirstName = 0;
		int maxLengthLastName = 0;
		
		for (StudentRecord studentRecord : records) {
			int currentLen;
			if ((currentLen = studentRecord.getFirstName().length()) > maxLengthFirstName) {
				maxLengthFirstName = currentLen;
			}
			if ((currentLen = studentRecord.getLastName().length()) > maxLengthLastName) {
				maxLengthLastName = currentLen;
			}
		}
		
		System.out.println("+" + drawLine(12) + "+" + drawLine(maxLengthLastName + 2) + "+" + drawLine(maxLengthFirstName + 2) + "+"
			+ drawLine(3) + "+");
		
		for (StudentRecord studentRecord : records) {
			String jmbag = new String(" " + studentRecord.getJMBAG() + " ");
			String lastName = new String(" " + studentRecord.getLastName()
					+ spaces(maxLengthLastName - studentRecord.getLastName().length()) + " ");
			String firstName = new String(" " + studentRecord.getFirstName()
					+ spaces(maxLengthFirstName - studentRecord.getFirstName().length()) + " ");
			String grade = new String(" " + studentRecord.getFinalGrade() + " ");
			System.out.println("|" + jmbag + "|" + lastName + "|" + firstName + "|" + grade + "|");
		}
		
		System.out.println("+" + drawLine(12) + "+" + drawLine(maxLengthLastName + 2) + "+" + drawLine(maxLengthFirstName + 2) + "+"
				+ drawLine(3) + "+");
	}
	
	/**
	 * Prints the neccessary number of "=" signs to the screen.
	 *
	 * @param cnt the number of "=" signs needed,
	 * @return the line itself
	 */
	private static String drawLine(int cnt){
		StringBuilder sb = new StringBuilder();
		for (int i=0; i< cnt; i++){
			sb.append("=");
		}
		return sb.toString();
	}
	
	/**
	 * Prints out the neccessary number of spaces to the screen.
	 * 
	 * @param cnt number of spaces needed,
	 * @return the whitespaces
	 */
	private static String spaces(int cnt){
		StringBuilder sb = new StringBuilder();
		for (int i=0; i< cnt; i++){
			sb.append(" ");
		}
		return sb.toString();
	}
	
	}

