package hr.fer.zemris.java.hw3;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.lexer.Lexer;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;

/**
 * A method used for testing the functionality of the {@link SmartScriptParser}
 * and {@link Lexer} classes.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class SmartScriptTester {

	/** A constant used for a space symbol. */
	public static final String SPACE = " ";

	/** A constant used for a dollar sign. */
	public static final String CLOSE_DOLLAR_CURLY = "$}";

	/** A constant used for an echo tag opening sequence. */
	public static final String OPEN_ECHO = "{$=";

	/** A constant used for an end tag sequence. */
	public static final String END_TAG = "{$END$}";

	/** A constant used for an opening for sequence. */
	public static final String OPEN_FOR = "{$FOR";

	/**
	 * the main method, used for testing
	 * 
	 * @param args
	 *            - the input arguments (not used)
	 * 
	 */
	public static void main(String[] args) {
		StringBuilder sb = new StringBuilder();

		String docBody = null;
		try {
			docBody = new String(Files.readAllBytes(Paths.get("doc.txt")), StandardCharsets.UTF_8);
		} catch (IOException e1) {
			System.out.println("File not found!");
			System.exit(-1);
		}

		SmartScriptParser parser = null;

		try {
			parser = new SmartScriptParser(docBody);
		} catch (SmartScriptParserException e) {
			System.out.println("Unable to parse document!");
			System.exit(-1);
		} catch (NumberFormatException e) {
			System.out.println("Invalid number in the document.");
			System.exit(-1);
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("If this line ever executes, you have failed this class!");
			System.exit(-1);
		}
		DocumentNode document = parser.getDocumentNode();
		for (int i = document.numberOfChildren(); i > 0; i--) {
			sb.append(createOriginalDocumentBody(document, i));
		}
		String newArg = (sb.toString());

		// second iteration
		sb = new StringBuilder();

		try {
			parser = new SmartScriptParser(newArg);
		} catch (SmartScriptParserException e) {
			System.out.println("Unable to parse document!");
			System.exit(-1);
		} catch (NumberFormatException e) {
			System.out.println("Invalid number in the document.");
			System.exit(-1);
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("If this line ever executes, you have failed this class!");
			System.exit(-1);
		}
		DocumentNode newDocument = parser.getDocumentNode();
		for (int i = newDocument.numberOfChildren(); i > 0; i--) {
			sb.append(createOriginalDocumentBody(newDocument, i));
		}
		if (newArg.compareTo((sb.toString())) == 0) {
			System.out.println("Parsing successful after second iteration!");
		}
		;

	}

	/**
	 * A method which recursively traverses the {@link DocumentNode} tree.
	 * Generates the textual format of the file, and returns it.
	 * 
	 * @param document
	 *            the document being transformed.
	 * @param n
	 *            the ordinal number of the child which is fetched from the
	 *            document.
	 * @return a string which represents a textual format of the file.
	 */
	private static String createOriginalDocumentBody(Node document, int n) {
		StringBuilder sb = new StringBuilder();
		Node node = document.getChild(document.numberOfChildren() - n);
		if (node instanceof ForLoopNode) {
			sb.append(OPEN_FOR + SPACE);
			sb.append(((ForLoopNode) node).getVariable().asText() + SPACE);
			sb.append(((ForLoopNode) node).getStartExpression().asText() + SPACE);
			sb.append(((ForLoopNode) node).getEndExpression().asText() + SPACE);

			if (((ForLoopNode) node).getStepExpression() != null) {
				sb.append(((ForLoopNode) node).getStepExpression().asText());
			}
			sb.append(CLOSE_DOLLAR_CURLY);

		} else if (node instanceof EchoNode) {
			Element[] elems = ((EchoNode) node).getElements();
			sb.append(OPEN_ECHO);
			for (Element elem : elems) {
				sb.append(elem.asText() + SPACE);
			}
			sb.append(CLOSE_DOLLAR_CURLY);
		} else if (node instanceof TextNode) {
			sb.append(((TextNode) node).getText());
		}

		if (node instanceof ForLoopNode && node.numberOfChildren() > 0) {
			for (int i = node.numberOfChildren(); i > 0; i--) {
				sb.append(createOriginalDocumentBody(node, i));
			}
			sb.append(END_TAG);
		} else
			return sb.toString();
		return sb.toString();
	}

}
