package hr.fer.zemris.java.gui.calc;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import hr.fer.zemris.java.gui.calc.actions.binary.AdditionOperator;
import hr.fer.zemris.java.gui.calc.actions.binary.BinaryOperator;
import hr.fer.zemris.java.gui.calc.actions.binary.DivisionOperator;
import hr.fer.zemris.java.gui.calc.actions.binary.MultiplicationOperator;
import hr.fer.zemris.java.gui.calc.actions.binary.PowerOperator;
import hr.fer.zemris.java.gui.calc.actions.binary.RootOperator;
import hr.fer.zemris.java.gui.calc.actions.binary.SubtractionOperator;
import hr.fer.zemris.java.gui.calc.actions.unary.ArcusCoTangentOperator;
import hr.fer.zemris.java.gui.calc.actions.unary.ArcusCosineOperator;
import hr.fer.zemris.java.gui.calc.actions.unary.ArcusSineOperator;
import hr.fer.zemris.java.gui.calc.actions.unary.ArcusTangentOperator;
import hr.fer.zemris.java.gui.calc.actions.unary.CoTangentOperator;
import hr.fer.zemris.java.gui.calc.actions.unary.CosineOperator;
import hr.fer.zemris.java.gui.calc.actions.unary.ExpOperator;
import hr.fer.zemris.java.gui.calc.actions.unary.InverseOperator;
import hr.fer.zemris.java.gui.calc.actions.unary.LogarythmOperator;
import hr.fer.zemris.java.gui.calc.actions.unary.NaturalLogarythmOperator;
import hr.fer.zemris.java.gui.calc.actions.unary.PowerOf10Operator;
import hr.fer.zemris.java.gui.calc.actions.unary.SineOperator;
import hr.fer.zemris.java.gui.calc.actions.unary.TangentOperator;
import hr.fer.zemris.java.gui.calc.actions.unary.UnaryOperator;
import hr.fer.zemris.java.gui.layouts.CalcLayout;
import hr.fer.zemris.java.gui.layouts.RCPosition;

/**
 * An implementation of a calculator. It offers vairous
 * {@linkplain UnaryOperator}s and {@linkplain BinaryOperator}s.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class Calculator extends JFrame {
	/** The serial user ID */
	private static final long serialVersionUID = 1L;
	/** The calculator display. */
	private JLabel display;
	/** A reference to the calculator memory. */
	private CalculatorRam calcRam;
	/** The panel in which the calculator elements are contained. */
	private JPanel panel;

	/** Keeps the names of the regular operations. */
	private static String[] labelsReg = { "x^n", "log", "ln", "sin", "cos", "tan", "ctg" };
	/** Keeps the names of the inverse operations. */
	private static String[] labelsInv = { "x√n", "10^x", "e^x", "arcsin", "arccos", "arctan", "arcctg" };

	/**
	 * Instantiates a new calculator.
	 */
	public Calculator() {
		super();
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("JCalculator");

		calcRam = new CalculatorRam();

		setLocation(20, 20);
		setSize(640, 500);

		initGUI();
	}

	/**
	 * Initialises the graphical user interface. Adds all the necessary elements
	 * and listeners.
	 */
	private void initGUI() {
		Container cp = getContentPane();

		panel = new JPanel(new CalcLayout(5));

		display = new JLabel();
		display.setFont(new Font("Comic Sans MS", Font.BOLD, 36));
		display.setBackground(Color.ORANGE);
		display.setOpaque(true);

		panel.add(display, new RCPosition(1, 1));

		addBinaryOperators(panel);
		addUnaryOperators(panel);
		addNumbers(panel);
		addOther(panel);

		cp.add(panel);

	}

	/**
	 * Adds the binary operators to the {@linkplain #panel}.
	 *
	 * @param panel
	 *            the panel
	 */
	private void addBinaryOperators(JPanel panel) {
		JButton mul = new JButton("*");
		mul.addActionListener(new BinaryOperatorAction(new MultiplicationOperator()));
		panel.add(mul, new RCPosition(3, 6));

		JButton div = new JButton("/");
		div.addActionListener(new BinaryOperatorAction(new DivisionOperator()));
		panel.add(div, new RCPosition(2, 6));

		JButton add = new JButton("+");
		add.addActionListener(new BinaryOperatorAction(new AdditionOperator()));
		panel.add(add, new RCPosition(5, 6));

		JButton sub = new JButton("-");
		sub.addActionListener(new BinaryOperatorAction(new SubtractionOperator()));
		panel.add(sub, new RCPosition(4, 6));

		JButton eq = new JButton("=");
		eq.addActionListener(new BinaryOperatorAction(null));
		panel.add(eq, new RCPosition(1, 6));

		JButton xn = new JButton("x^n");
		xn.addActionListener(new BinaryOperatorAction(new PowerOperator(), new RootOperator()));
		panel.add(xn, new RCPosition(5, 1));

	}

	/**
	 * Adds the unary operators to the {@linkplain #panel}.
	 *
	 * @param panel
	 *            the panel
	 */
	private void addUnaryOperators(JPanel panel) {
		JButton log = new JButton("log");
		log.addActionListener(new UnaryOperatorAction(new LogarythmOperator(), new PowerOf10Operator()));
		panel.add(log, new RCPosition(3, 1));

		JButton ln = new JButton("ln");
		ln.addActionListener(new UnaryOperatorAction(new NaturalLogarythmOperator(), new ExpOperator()));
		panel.add(ln, new RCPosition(4, 1));

		JButton sin = new JButton("sin");
		sin.addActionListener(new UnaryOperatorAction(new SineOperator(), new ArcusSineOperator()));
		panel.add(sin, new RCPosition(2, 2));

		JButton cos = new JButton("cos");
		cos.addActionListener(new UnaryOperatorAction(new CosineOperator(), new ArcusCosineOperator()));
		panel.add(cos, new RCPosition(3, 2));

		JButton tan = new JButton("tan");
		tan.addActionListener(new UnaryOperatorAction(new TangentOperator(), new ArcusTangentOperator()));
		panel.add(tan, new RCPosition(4, 2));

		JButton ctg = new JButton("ctg");
		ctg.addActionListener(new UnaryOperatorAction(new CoTangentOperator(), new ArcusCoTangentOperator()));
		panel.add(ctg, new RCPosition(5, 2));

		JButton dot = new JButton(".");
		dot.addActionListener(DotAction);
		panel.add(dot, new RCPosition(5, 5));

		JButton changeSign = new JButton("+/-");
		changeSign.addActionListener(SignAction);
		panel.add(changeSign, new RCPosition(5, 4));

		JButton inverse = new JButton("1/x");
		inverse.addActionListener(new UnaryOperatorAction(new InverseOperator(), new InverseOperator()));
		panel.add(inverse, new RCPosition(2, 1));

	}

	/**
	 * Adds the number elements to the {@linkplain #panel}.
	 *
	 * @param panel
	 *            the panel
	 */
	private void addNumbers(JPanel panel) {
		JButton numberButton7 = new JButton("7");
		numberButton7.addActionListener(NumberAction);
		panel.add(numberButton7, new RCPosition(2, 3));

		JButton numberButton8 = new JButton("8");
		numberButton8.addActionListener(NumberAction);
		panel.add(numberButton8, new RCPosition(2, 4));

		JButton numberButton9 = new JButton("9");
		numberButton9.addActionListener(NumberAction);
		panel.add(numberButton9, new RCPosition(2, 5));

		JButton numberButton4 = new JButton("4");
		numberButton4.addActionListener(NumberAction);
		panel.add(numberButton4, new RCPosition(3, 3));

		JButton numberButton5 = new JButton("5");
		numberButton5.addActionListener(NumberAction);
		panel.add(numberButton5, new RCPosition(3, 4));

		JButton numberButton6 = new JButton("6");
		numberButton6.addActionListener(NumberAction);
		panel.add(numberButton6, new RCPosition(3, 5));

		JButton numberButton1 = new JButton("1");
		numberButton1.addActionListener(NumberAction);
		panel.add(numberButton1, new RCPosition(4, 3));

		JButton numberButton2 = new JButton("2");
		numberButton2.addActionListener(NumberAction);
		panel.add(numberButton2, new RCPosition(4, 4));

		JButton numberButton3 = new JButton("3");
		numberButton3.addActionListener(NumberAction);
		panel.add(numberButton3, new RCPosition(4, 5));

		JButton numberButton0 = new JButton("0");
		numberButton0.addActionListener(NumberAction);
		panel.add(numberButton0, new RCPosition(5, 3));

	}

	/**
	 * Adds all of the other operators to the {@linkplain #panel}.
	 *
	 * @param panel
	 *            the panel
	 */
	private void addOther(JPanel panel) {
		JButton clr = new JButton("clr");
		clr.addActionListener(ClearAction);
		panel.add(clr, new RCPosition(1, 7));

		JButton res = new JButton("res");
		res.addActionListener(ClearAction);
		panel.add(res, new RCPosition(2, 7));

		JButton push = new JButton("push");
		push.addActionListener(StackAction);
		panel.add(push, new RCPosition(3, 7));

		JButton pop = new JButton("pop");
		pop.addActionListener(StackAction);
		panel.add(pop, new RCPosition(4, 7));

		JCheckBox inv = new JCheckBox("Inv");
		inv.addActionListener(InvertAction);
		panel.add(inv, new RCPosition(5, 7));
	}

	/**
	 * Toggles the inverse buttons.
	 *
	 * @param panel
	 *            the panel
	 */
	private void toggleInverseButtons(JPanel panel) {
		String[] labels;
		if (calcRam.isInverted()) {
			labels = labelsInv;
		} else {
			labels = labelsReg;
		}

		for (int i = 6; i < 13; i++) {
			JButton nesto = (JButton) panel.getComponent(i);
			nesto.setText(labels[i - 6]);
		}
	}

	/**
	 * Puts a new number onto the display.
	 */
	ActionListener NumberAction = a -> {
		JButton b = (JButton) a.getSource();
		String text = b.getText();
		calcRam.setCurrDisplayContent(text);
		display.setText(calcRam.getCurrDisplayContentValue());
	};

	/**
	 * Performs a desired stack operation (push, pop)
	 */
	ActionListener StackAction = a -> {
		String key = ((JButton) a.getSource()).getText();
		calcRam.executeStackOperation(key);
		if (key.equals("pop")) {
			display.setText(calcRam.getCurrDisplayContentValue());
		}
	};

	/**
	 * Clears the display.
	 */
	ActionListener ClearAction = a -> {
		String key = ((JButton) a.getSource()).getText();
		calcRam.clearDisplay();
		if (key.equals("res")) {
			calcRam.reset();
		}
		display.setText(calcRam.getCurrDisplayContentValue());
	};

	/**
	 * Inverts the operations to their opposites.
	 */
	ActionListener InvertAction = a -> {
		toggleInverseButtons(panel);
		calcRam.invert();
	};

	/**
	 * Indicates that the current number will have a decimal part.
	 */
	ActionListener DotAction = a -> {
		calcRam.convertToDecimal();
		display.setText(calcRam.getCurrDisplayContentValue());
	};

	/**
	 * Changes the sign of the current value displayed.
	 */
	ActionListener SignAction = a -> {
		calcRam.setCurrDisplayContent(-calcRam.getCurrDisplayContent());
		display.setText(calcRam.getCurrDisplayContentValue());
	};

	/**
	 * An implementation of {@linkplain ActionListener} which executes the
	 * desired unary operation using the calculator memory and displays the
	 * result to the {@linkplain #display}.
	 */
	public class UnaryOperatorAction implements ActionListener {

		/**
		 * The {@linkplain UnaryOperator} which will be used in this listener.
		 */
		private UnaryOperator operator;

		/**
		 * The inverse operation of the {@linkplain #operator}.
		 */
		private UnaryOperator inverseOperator;

		/**
		 * Instantiates a new unary operator action.
		 *
		 * @param regular
		 *            the regular operation
		 * @param inverse
		 *            the inverse operation
		 */
		public UnaryOperatorAction(UnaryOperator regular, UnaryOperator inverse) {
			super();
			this.operator = regular;
			this.inverseOperator = inverse;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			calcRam.executeOperation(calcRam.isInverted() ? operator : inverseOperator);
			display.setText(calcRam.getCurrDisplayContentValue());
		}

	}

	/**
	 * An implementation of {@linkplain ActionListener} which executes the
	 * desired binary operation using the calculator memory and displays the
	 * result to the {@linkplain #display}.
	 */
	public class BinaryOperatorAction implements ActionListener {

		/**
		 * The {@linkplain UnaryOperator} which will be used in this listener.
		 */
		private BinaryOperator operator;

		/**
		 * The inverse operation of the {@linkplain #operator}.
		 */
		private BinaryOperator inverseOperator;

		/**
		 * Instantiates a new binary operator action with no matching inverse
		 * operator.
		 *
		 * @param regular
		 *            the regular operator
		 */
		public BinaryOperatorAction(BinaryOperator regular) {
			this(regular, regular);
		}

		/**
		 * Instantiates a new binary operator action.
		 *
		 * @param regular
		 *            the regular operator
		 * @param inverse
		 *            the inverse operator
		 */
		public BinaryOperatorAction(BinaryOperator regular, BinaryOperator inverse) {
			super();
			this.operator = regular;
			this.inverseOperator = inverse;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			calcRam.executeOperation(calcRam.isInverted() ? operator : inverseOperator);
			display.setText(calcRam.getCurrDisplayContentValue());
		}
	}

	/**
	 * The main method, first one to be executed when the program starts.
	 * 
	 * @param args
	 *            - the input arguments, not used here
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new Calculator().setVisible(true);
		});
	}
}
