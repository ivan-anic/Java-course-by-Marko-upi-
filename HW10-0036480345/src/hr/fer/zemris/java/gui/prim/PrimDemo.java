package hr.fer.zemris.java.gui.prim;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * Demonstrates the functionalities of the {@linkplain PrimListModel}.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class PrimDemo extends JFrame {

	/**
	 * The version user ID* private static final long serialVersionUID
	 * =2552614965736703542L;
	 */
	private static final long serialVersionUID = 2552614965736703542L;

	/**
	 * The version user ID* private static final long serialVersionUID
	 * =2552614965736703542L;
	 */
	public PrimDemo() {
		setLocation(20, 50);
		setSize(300, 200);
		setTitle("Primes window");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		initGUI();
	}

	/**
	 * Initialises the graphical user interface. Adds all the necessary elements
	 * and listeners.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());

		PrimListModel<Integer> model = new PrimListModel(1);

		JList<Integer> list = new JList<>(model);
		JList<Integer> list2 = new JList<>(model);

		JPanel bottomPanel = new JPanel(new GridLayout(1, 0));

		JButton dodaj = new JButton("Next");
		bottomPanel.add(dodaj);

		dodaj.addActionListener(e -> {
			model.add(model.next());
		});

		JPanel centerPanel = new JPanel(new GridLayout(1, 2));

		centerPanel.add(new JScrollPane(list2), BorderLayout.EAST);
		centerPanel.add(new JScrollPane(list), BorderLayout.EAST);
		cp.add(centerPanel, BorderLayout.CENTER);
		cp.add(bottomPanel, BorderLayout.PAGE_END);
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
			JFrame frame = new PrimDemo();
			frame.pack();
			frame.setVisible(true);
		});
	}
}
