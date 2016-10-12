package hr.fer.zemris.java.custom.scripting.parser;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;
import hr.fer.zemris.java.custom.collections.EmptyStackException;
import hr.fer.zemris.java.custom.collections.ObjectStack;
import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantDouble;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantInteger;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementOperator;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;
import hr.fer.zemris.java.custom.scripting.lexer.Lexer;
import hr.fer.zemris.java.custom.scripting.lexer.Token;
import hr.fer.zemris.java.custom.scripting.lexer.TokenType;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;

/**
 * An implementation of a document parser. Uses an implementation of a
 * {@link Lexer} class for token production, later producing a {@link Node} tree
 * contained in a {@link DocumentNode}.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class SmartScriptParser {

	/**
	 * An instance of the {@link Lexer} class, used for {@link Token}
	 * production.
	 */
	private Lexer lexer;

	/**
	 * An instance of the {@link ObjectStack} class, used for the production of
	 * the {@link DocumentNode} tree.
	 */
	private ObjectStack stack;

	/** A list of allowed tags. */
	private static final ArrayIndexedCollection tags;
	static {
		tags = new ArrayIndexedCollection(3);
		tags.add("FOR");
		tags.add("END");
		tags.add("=");
	}

	/**
	 * Creates an instance of a {@link SmartScriptParser} which accepts the
	 * document body as a <code>String</code>.
	 * 
	 * @param documentBody
	 *            the document body (<code>String</code>).
	 */
	public SmartScriptParser(String documentBody) {
		stack = new ObjectStack();

		lexer = new Lexer(documentBody);
		parse(lexer, documentBody);

	}

	/**
	 * Returns the document node, containing the {@link Node} tree generated by
	 * the parser.
	 * 
	 * @return the {@link DocumentNode} tree
	 */
	public DocumentNode getDocumentNode() {
		return (DocumentNode) stack.peek();
	}

	/**
	 * Parses the document using the {@linkplain Lexer} class for {@link Token}
	 * creation, thus producing a {@link Node} tree contained in a
	 * {@link DocumentNode}.
	 * 
	 * @param lexer
	 * @param document
	 */
	public void parse(Lexer lexer, String document) {
		stack.push(new DocumentNode());
		Token token;
		token = lexer.getCurrentToken();

		while (token.getType() != TokenType.EOF) {

			// creating tag nodes
			if (token.getType() == TokenType.STARTTAG) {
				token = lexer.getNextToken();

				// if a tag is the first one after the braces
				if (token.getType() == TokenType.TAG) {

					// if a tag is a correct one
					if (tags.contains(token.getValue())) {

						switch ((String) token.getValue().toString().toLowerCase()) {
						case "for":
							processForLoopNode();
							break;
						case "end":
							processEnd();
							break;
						case "=":
							processEchoNode();
						}
					}
				}
			} else if (token.getType() != TokenType.ENDTAG) {

				// not STARTTAG; textnode
				Node temp = (Node) stack.peek();
				TextNode node = new TextNode((String) token.getValue());
				temp.addChildNode(node);
			}
			token = lexer.getNextToken();
		}
	}

	/**
	 * Processes the FOR @{@link TokenType}. Creates corresponding
	 * {@link Element}s, and finally builds a {@link ForLoopNode}. Also pushes
	 * it to the stack, so the next nodes will be its children, until an END
	 * {@link TokenType} is encountered.
	 */
	private void processForLoopNode() {
		Token var = lexer.getNextToken();
		if (var.getType() == TokenType.VARIABLE) {

			Element[] param = new Element[4];
			param[0] = new ElementVariable((String) var.getValue());

			// getting the parameters
			if (var.getType() == TokenType.VARIABLE) {
				for (int i = 1; i < 4; i++) {
					var = lexer.getNextToken();
					if (var.getType() == TokenType.ENDTAG) {
						if (i < 3) {
							throw new SmartScriptParserException("Invalid for-loop semantics.");
						} else {
							break;
						}
					}
					if (var.getType() == TokenType.VARIABLE) {
						param[i] = new ElementVariable((String) var.getValue());
					} else if (var.getType() == TokenType.CONSTANTINT) {
						param[i] = new ElementConstantInteger((String) var.getValue());
					} else if (var.getType() == TokenType.CONSTANTDOUBLE) {
						param[i] = new ElementConstantDouble((String) var.getValue());
					} else if (var.getType() == TokenType.STRING) {
						param[i] = new ElementString((String) var.getValue());
					} else {
						throw new SmartScriptParserException("Invalid for-loop semantics.");
					}
				}
			}
			if (lexer.getCurrentToken().getType() != TokenType.ENDTAG) {
				var = lexer.getNextToken();
				if (var.getType() != TokenType.ENDTAG) {
					throw new SmartScriptParserException("Invalid for-loop semantics.");
				}
			}

			Node temp = (Node) stack.peek();
			ForLoopNode node = new ForLoopNode(param);
			temp.addChildNode(node);
			stack.push(node);

		}
	}

	/**
	 * Processes the END {@link TokenType} by popping a FOR node from the stack.
	 */
	private void processEnd() {
		try {
			stack.pop();
		} catch (EmptyStackException e) {
			System.out.println("You encountered an empty stack. Invalid FOR-END syntax.");
			System.exit(-1);
		}
	}

	/**
	 * Processes the tokens contained in a {@code =} tag, builds corresponding
	 * {@link Element}s and places them into an {@link EchoNode} afterwards.
	 */
	private void processEchoNode() {
		Token var = lexer.getNextToken();
		ArrayIndexedCollection param = new ArrayIndexedCollection();

		while (var.getType() != TokenType.ENDTAG) {

			if (var.getType() == TokenType.VARIABLE) {
				param.add(new ElementVariable((String) var.getValue()));
			} else if (var.getType() == TokenType.CONSTANTINT) {
				param.add(new ElementConstantInteger((String) var.getValue()));
			} else if (var.getType() == TokenType.CONSTANTDOUBLE) {
				param.add(new ElementConstantDouble((String) var.getValue()));
			} else if (var.getType() == TokenType.STRING) {
				param.add(new ElementString((String) var.getValue()));
			} else if (var.getType() == TokenType.FUNCTION) {
				param.add(new ElementFunction((String) var.getValue()));
			} else if (var.getType() == TokenType.OPERATOR) {
				param.add(new ElementOperator((String) var.getValue()));
			} else {
				throw new SmartScriptParserException("Invalid tag semantics.");
			}

			var = lexer.getNextToken();
		}
		if (param.isEmpty()) {
			throw new SmartScriptParserException("Empty tags not allowed.");
		}

		int len = param.size();
		Element[] paramElems = new Element[len];

		for (int i = 0; i < len; i++) {
			paramElems[i] = (Element) param.get(i);
		}
		Node node = new EchoNode(paramElems);
		Node temp = (Node) stack.peek();
		temp.addChildNode(node);

	}
}