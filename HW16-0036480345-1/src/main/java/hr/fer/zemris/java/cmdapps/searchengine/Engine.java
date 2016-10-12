package hr.fer.zemris.java.cmdapps.searchengine;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * A simple implementation of a search engine which searches the files in the
 * given directory. <br>
 * The application takes a single argument; the path to the directory which
 * contains the files to be searched. <br>
 * The application commands supported are:
 * <ul>
 * <li>query - executes the search (i.e.
 * "query darovit glumac zadnje akademske klase")</li>
 * <li>type - accepts a result index and prints the specified document to the
 * console</li>
 * <li>results - prints the previous query results to the screen</li>
 * <li>exit - exits the console</li>
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class Engine {

	/** A message which signals that the number of arguments is invalid. */
	private final static String ILLEGAL_ARGUMENTS_MSG = "Illegal number of arguments. "
			+ "Please provide a path to the articles directory.";
	/**
	 * A message which signals that the given path does not point to a
	 * directory.
	 */
	private final static String INVALID_PATH_MSG = "Given path is not a directory!";
	/** A message which signals that the given directory is empty. */
	private final static String DIRECTORY_EMPTY_MSG = "Given directory contains no files!";
	/** A message which signals that a certain file can't be read. */
	private final static String NON_READABLE_FILE = "Can't read file: ";
	/** A message which signals that the given arguments is invalid. */
	private final static String ILLEGAL_COMMAND_ARGUMENTS = "Illegal argument/s. "
			+ "Please provide a number representing a search result.";

	/** A set containing the stop words. */
	private static Set<String> stopWords = new TreeSet<>();
	/** A set containing all of the allowed words. */
	private static Set<String> dictionary;
	/** A set containing all of the documents from the disk. */
	private static List<Document> documents = new ArrayList<>();
	/** A reference to the document representation of a query. */
	private static Document other;
	/** Holds a reference to the map of results. */
	static Map<String, Double> resultMap;

	static {
		try {
			Files.readAllLines(Paths.get("hrvatski_stoprijeci.txt"), StandardCharsets.UTF_8)
					.forEach(z -> {
						stopWords.add(z);
					});
		} catch (IOException ignorable) {
		}
	}

	/**
	 * The main method, first one to be executed when the program starts.
	 * 
	 * @param args
	 *            - the input arguments, the path to the directory which
	 *            contains the files to be searched.
	 */
	public static void main(String[] args) {

		if (args.length != 1) {
			showError(ILLEGAL_ARGUMENTS_MSG);
		}

		try {
			processFiles(args[0]);
		} catch (IOException e) {
			showError(NON_READABLE_FILE);
		}

		initShell();

		System.out.println(documents.get(0).getWords().toString());
		System.out.println(documents.get(0).getTfidf().toString());
		System.out.println("The dictionary contains " + dictionary.size() + " words.");
	}

	/**
	 * Initialises the interactive shell.
	 */
	private static void initShell() {
		Scanner sc = new Scanner(System.in);

		String line;
		List<String> lineArgs;
		String command;

		System.out.print("Enter command > ");
		while (!(line = sc.nextLine()).equals("exit")) {
			lineArgs = new LinkedList<>(Arrays.asList(line.split(" ")));
			command = lineArgs.get(0);
			lineArgs.remove(0);

			switch (command) {
			case ("query"):
				launchQuery(lineArgs);
				break;
			case ("type"):
				launchType(lineArgs);
				break;
			case ("results"):
				launchResults(lineArgs);
				break;
			default:
				System.out.println("Command not supported. Please enter a valid command.");
			}
			System.out.print("Enter command > ");
		}
		sc.close();
	}

	/**
	 * Launches the {@code query} processing.
	 *
	 * @param args
	 *            the given query as a list
	 */
	private static void launchQuery(List<String> args) {

		System.out.println("Query is: " + Arrays.toString(args.stream().toArray()));
		System.out.println("Top 10 results:");

		Map<String, AtomicInteger> map = new HashMap<>();

		args.forEach(z -> map.put(z, new AtomicInteger(1)));

		Document doc = new Document("", map);
		documents.add(doc);
		other = doc;
		generateTFIDFdocuments();

		resultMap = new TreeMap<>();

		resultMap = documents.stream().collect(Collectors.toMap(Document::getPath, Document::getDifference));

		resultMap = sortByValue(resultMap);

		if (!resultMap.isEmpty()) {
			resultMap.remove("");
		}
		Object[] keys = resultMap.keySet().toArray();
		for (int i = 0; i < 10; i++) {
			System.out.printf("[%d] (%.4f) %s \n", i, (resultMap.get(keys[i])), keys[i]);
			if (i + 1 >= resultMap.size() || resultMap.get(keys[i + 1]) == 0) {
				break;
			}
		}

	}

	/**
	 * Launches the {@code type} command processing.
	 *
	 * @param args
	 *            the given query as a list
	 */
	private static void launchType(List<String> args) {
		if (args.size() != 1) {
			showError(ILLEGAL_COMMAND_ARGUMENTS);
		}
		if (resultMap.isEmpty()) {
			showError(ILLEGAL_ARGUMENTS_MSG);
		}

		Object[] keys = resultMap.keySet().toArray();
		String fileName = (String) keys[(Integer.parseInt(args.get(0)))];

		for (int i = 0; i < fileName.length(); ++i) {
			System.out.print("-");
		}

		System.out.println();
		System.out.println("Document: " + fileName);
		try {
			List<String> lines = Files.readAllLines(Paths.get(fileName));
			for (String l : lines) {
				System.out.println(l);
			}
		} catch (IOException e) {
			System.out.println("Error opening file: " + e);
		}

		for (int i = 0; i < fileName.length(); ++i) {
			System.out.print("-");
		}
		System.out.println();
	}

	/**
	 * Launches the {@code results} command processing.
	 *
	 * @param args
	 *            the given query as a list
	 */
	private static void launchResults(List<String> args) {
		if (args.size() != 0) {
			showError(ILLEGAL_COMMAND_ARGUMENTS);
		}
		Object[] keys = resultMap.keySet().toArray();
		for (int i = 0; i < 10; i++) {
			System.out.printf("[%d] (%.4f) %s \n", i, (resultMap.get(keys[i])), keys[i]);
			if (i + 1 >= resultMap.size() || resultMap.get(keys[i + 1]) == 0) {
				break;
			}
		}
	}

	/**
	 * Generates the tfid vectors for all documents.
	 */
	private static void generateTFIDFdocuments() {
		documents.forEach(z -> z.generateTFIDF(documents));
	}

	/**
	 * Processes all of the files from the given directory. Fills the dictionary
	 * with all of the appeared words without the "stop-words", the ones which
	 * will not be searched for.
	 * 
	 * @param path
	 *            - the path to the directory containing the files
	 * @throws IOException
	 *             - if an exception during reading the files occurs
	 */
	private static void processFiles(String path) throws IOException {

		File dir = new File(path);
		if (!dir.isDirectory()) {
			showError(INVALID_PATH_MSG);
		}

		File[] files = dir.listFiles();
		if (files.length == 0) {
			showError(DIRECTORY_EMPTY_MSG);
		}

		dictionary = new HashSet<>();
		BufferedReader reader;
		String line;
		String word;

		Map<String, AtomicInteger> docMap;
		// for each file from the directory
		for (File file : files) {
			docMap = new HashMap<>();
			reader = new BufferedReader(new FileReader(file));

			// for each line in the file
			while ((line = reader.readLine()) != null) {
				Pattern p = Pattern.compile("\\p{L}+");
				Matcher m = p.matcher(line);

				while (m.find()) {
					word = line.substring(m.start(), m.end()).toLowerCase();

					if (!stopWords.contains(word)) {
						dictionary.add(word);
						if (!docMap.containsKey(word)) {
							docMap.put(word, new AtomicInteger(1));
						} else {
							docMap.get(word).incrementAndGet();
						}
					}
				}
			}
			// create a new document wrapper with path and word count
			documents.add(new Document(file.getName(), docMap));
			reader.close();
		}
	}

	/**
	 * Gets the doc.
	 *
	 * @return the doc
	 */
	public static Document getDoc() {
		return other;
	}

	/**
	 * Displays the given error to the screen and terminates the console.
	 * 
	 * @param message
	 *            - the error message to be displayed
	 */
	private static void showError(String message) {
		System.out.println(message);
		System.exit(1);
	}

	/**
	 * Sort by value.
	 *
	 * @param unsortedMap
	 *            the unsorted map
	 * @return the map
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Map<String, Double> sortByValue(Map<String, Double> unsortedMap) {
		Map<String, Double> sortedMap = new TreeMap(new ValueComparator(unsortedMap));
		sortedMap.putAll(unsortedMap);
		return sortedMap;
	}

}
