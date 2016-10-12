package hr.fer.zemris.java.custom.scripting.demo;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;

/**
 * A method used for testing the functionality of the {@link SmartScriptParser}
 * and {@link INodeVisitor} classes.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class TreeWriter {

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
			System.out.println("Please enter a path to the code.");
			path = sc.nextLine();
		} else if (args.length == 1) {
			path = args[0];
		} else {
			System.out.println("Invalid input.");
			System.exit(1);
		}

		sc.close();

		String docBody = null;
		try {
			docBody = new String(Files.readAllBytes(Paths.get(path)), StandardCharsets.UTF_8);
		} catch (IOException e1) {
			System.out.println("File not found!");
			System.exit(-1);
		}

		SmartScriptParser p = new SmartScriptParser(docBody);
		WriterVisitor visitor = new WriterVisitor();
		p.getDocumentNode().accept(visitor);
	}

	/**
	 * An implementation of {@linkplain INodeVisitor}. It provides methods for
	 * visitind the document tree and printing its contents to the standard
	 * output.
	 * 
	 * @author Ivan Anić
	 * @version 1.0
	 */
	static class WriterVisitor implements INodeVisitor {

		/** A constant used for a space symbol. */
		public static final String SPACE = " ";

		/** A constant used for a dollar sign. */
		public static final String CLOSE_DOLLAR_CURLY = "$}";

		/** A constant used for an echo tag opening sequence. */
		public static final String OPEN_ECHO = "{$=";

		/** A constant used for an opening for sequence. */
		public static final String OPEN_FOR = "{$FOR";

		/** A constant used for an end tag sequence. */
		public static final String END_TAG = "{$END$}";

		/**
		 * A stringbuilder used to concatenate string elements before the
		 * output.
		 */
		private StringBuilder sb = new StringBuilder();

		@Override
		public void visitTextNode(TextNode node) {
			System.out.print(node.getText());
		}

		@Override
		public void visitForLoopNode(ForLoopNode node) {
			sb.append(OPEN_FOR + SPACE);
			sb.append(((ForLoopNode) node).getVariable().getFunctionName() + SPACE);
			sb.append(((ForLoopNode) node).getStartExpression().getName() + SPACE);
			sb.append(((ForLoopNode) node).getEndExpression().getName() + SPACE);

			if (((ForLoopNode) node).getStepExpression() != null) {
				sb.append(((ForLoopNode) node).getStepExpression().getName());
			}
			sb.append(CLOSE_DOLLAR_CURLY);

			for (int i = 0; i < node.numberOfChildren(); i++) {
				node.getChild(i).accept(this);
			}

			sb.append(END_TAG);

			System.out.println(sb.toString());
			sb.setLength(0);
		}

		@Override
		public void visitEchoNode(EchoNode node) {
			Element[] elems = ((EchoNode) node).getElements();
			sb.append(OPEN_ECHO);
			for (Element elem : elems) {
				if (elem instanceof ElementString) {
					sb.append('"' + elem.getName() + '"' + SPACE);
				}
				sb.append(elem.getName() + SPACE);
			}
			sb.append(CLOSE_DOLLAR_CURLY);

			System.out.println(sb.toString());
			sb.setLength(0);
		}

		@Override
		public void visitDocumentNode(DocumentNode node) {
			for (int i = 0; i < node.numberOfChildren(); i++) {
				node.getChild(i).accept(this);
			}
		}
	}
}
