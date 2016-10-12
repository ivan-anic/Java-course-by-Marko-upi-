package hr.fer.zemris.java.gui.layouts.demo;

import java.awt.Color;
import java.awt.Container;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import hr.fer.zemris.java.gui.layouts.CalcLayout;
import hr.fer.zemris.java.gui.layouts.RCPosition;

/**
 * Demonstration of the {@linkplain CalcLayout}
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class CalcLayoutDemo extends JFrame {

	/** The user ID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new calc layout demo.
	 */
	public CalcLayoutDemo() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Prozor1");
		setLocation(20, 20);
		setSize(500, 200);
		initGUI();
	}

	/**
	 * Initialises the graphical user interface. Adds all the necessary elements
	 * and listeners.
	 */
	private void initGUI() {
		Container cp = getContentPane();

		JPanel p = new JPanel(new CalcLayout(3));
		JLabel lab = new JLabel("x");
		lab.setBackground(Color.ORANGE);
		p.add(new JButton("x"), new RCPosition(1, 1));
		p.add(new JButton("x"), new RCPosition(2, 1));

		p.add(new JButton("y"), new RCPosition(2, 3));
		p.add(new JButton("z"), new RCPosition(2, 7));
		p.add(new JButton("w"), new RCPosition(4, 2));
		p.add(new JButton("a"), new RCPosition(4, 5));
		p.add(new JButton("b"), new RCPosition(4, 7));

		cp.add(p);
	}

	/**
	 * The main method, first one to be executed when the program starts.
	 * 
	 * @param args
	 *            - the input arguments, a path to a test file should be
	 *            entered.
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new CalcLayoutDemo().setVisible(true);
		});
	}
}
