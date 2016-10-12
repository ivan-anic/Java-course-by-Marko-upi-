package hr.fer.zemris.java.custom.scripting.exec;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementOperator;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;
import hr.fer.zemris.java.custom.scripting.exec.functions.AbstractFunction;
import hr.fer.zemris.java.custom.scripting.exec.functions.FunctionFactory;
import hr.fer.zemris.java.custom.scripting.exec.functions.IFunction;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * A class whose purpose is to execute the document obtained using the
 * {@linkplain SmartScriptParser}.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class SmartScriptEngine {

	/** Holds a reference to the given document node. */
	private DocumentNode documentNode;
	/** Holds a reference to the given request context. */
	private RequestContext requestContext;
	/** Holds a reference to the multistack instance used in this class. */
	private ObjectMultistack multistack = new ObjectMultistack();
	/** Holds a reference to the visitor. */
	private INodeVisitor visitor = new INodeVisitor() {

		@Override
		public void visitTextNode(TextNode node) {
			requestContext.write(node.getText());
		}

		@Override
		public void visitForLoopNode(ForLoopNode node) {

			String var = node.getVariable().getFunctionName();
			String endValue = node.getEndExpression().getName();
			String step = node.getStepExpression().getName();
			if (step == null) {
				step = "1";
			}

			multistack.push(var, new ValueWrapper(node.getStartExpression().getName()));

			while (multistack.peek(var).numCompare(endValue) <= 0) {
				for (int i = 0; i < node.numberOfChildren(); i++) {
					node.getChild(i).accept(visitor);
				}
				multistack.peek(var).increment(step);
			}
		}

		@Override
		public void visitEchoNode(EchoNode node) {
			Element[] elems = node.getElements();

			Stack<String> tempStack = new Stack<>();

			Arrays.asList(elems).forEach(e -> {
				if (e instanceof ElementVariable) {
					tempStack.push((String.valueOf(multistack.peek(e.getName()).getValue())));
				} else if (e instanceof ElementOperator) {
					ValueWrapper arg1 = new ValueWrapper(tempStack.pop());
					Object arg2 = tempStack.pop();
					switch (e.getName()) {
					case ("+"):
						arg1.increment(arg2);
						break;
					case ("-"):
						arg1.decrement(arg2);
						break;
					case ("*"):
						arg1.multiply(arg2);
						break;
					case ("/"):
						arg1.divide(arg2);
						break;
					}
					tempStack.push((String.valueOf(arg1.getValue())));
				} else if (e instanceof ElementFunction) {
					IFunction function = FunctionFactory.create((ElementFunction) e);
					if (function instanceof AbstractFunction) {
						function.execute(tempStack);
					} else {
						function.execute(tempStack, requestContext);
					}
				} else { // is a constant (double,int,string)
					tempStack.push(e.getName());
				}
			});
			List<String> remaining = new ArrayList<>(tempStack);
			remaining.forEach(e -> requestContext.write(e));
		}

		@Override
		public void visitDocumentNode(DocumentNode node) {
			for (int i = 0; i < node.numberOfChildren(); i++) {
				node.getChild(i).accept(visitor);
			}
		}
	};

	/**
	 * Instantiates a new smart script engine.
	 *
	 * @param documentNode
	 *            the document node
	 * @param requestContext
	 *            the request context
	 */
	public SmartScriptEngine(DocumentNode documentNode, RequestContext requestContext) {
		this.documentNode = documentNode;
		this.requestContext = requestContext;
	}

	/**
	 * Executes this engine.
	 */
	public void execute() {
		documentNode.accept(visitor);
	}

}
