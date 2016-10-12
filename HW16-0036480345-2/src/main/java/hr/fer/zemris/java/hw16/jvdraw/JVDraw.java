package hr.fer.zemris.java.hw16.jvdraw;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.nio.file.Path;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.border.BevelBorder;

import hr.fer.zemris.java.hw16.jvdraw.actions.geometry.CircleAction;
import hr.fer.zemris.java.hw16.jvdraw.actions.geometry.FilledCircleAction;
import hr.fer.zemris.java.hw16.jvdraw.actions.geometry.LineAction;
import hr.fer.zemris.java.hw16.jvdraw.actions.io.OpenAction;
import hr.fer.zemris.java.hw16.jvdraw.actions.io.SaveAction;
import hr.fer.zemris.java.hw16.jvdraw.actions.io.SaveAsAction;
import hr.fer.zemris.java.hw16.jvdraw.components.CustomLabel;
import hr.fer.zemris.java.hw16.jvdraw.components.GeometricalObject;
import hr.fer.zemris.java.hw16.jvdraw.components.JColorArea;
import hr.fer.zemris.java.hw16.jvdraw.components.JDrawingCanvas;
import hr.fer.zemris.java.hw16.jvdraw.models.DrawingModelImpl;
import hr.fer.zemris.java.hw16.jvdraw.models.DrawingObjectsListModel;

/**
 * An implementation of a simple drawing application. It supports drawing three
 * different components:
 * <ul>
 * <li>A simple plain line</li>
 * <li>An empty circle</li>
 * <li>A filled circle</li>
 * </ul>
 * 
 * It also offers the following file-based operations:
 * <ul>
 * <li>Open - opens the file and draws its content onto the screen</li>
 * <li>Save - saves the current drawing into a textual file</li>
 * <li>Save as - saves and offers the option to pick a different name</li>
 * </ul>
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class JVDraw extends JFrame {

	/** Serial version user ID */
	private static final long serialVersionUID = 3474389407464006976L;
	/** A reference to the status bar. */
	private JPanel statusBar;
	/**
	 * A reference to the {@linkplain JColorArea} instance mapped to the
	 * background colour.
	 */
	private JColorArea backColor;
	/**
	 * A reference to the {@linkplain JColorArea} instance mapped to the
	 * foreground colour.
	 */
	private JColorArea frontColor;
	/** A reference to the {@linkplain DrawingModelImpl} instance */
	public DrawingModelImpl source;
	/** A reference to the {@linkplain JDrawingCanvas} instance */
	private JDrawingCanvas canvas;
	/** A reference to the currently used file path. */
	private Path currentPath;

	/**
	 * Instantiates a new JVDraw application.
	 */
	public JVDraw() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setSize(800, 600);
		setLocation(50, 50);

		initGUI();
	}

	/**
	 * Initialises the graphical user interface. Adds all the necessary elements
	 * and listeners.
	 */
	private void initGUI() {
		this.getContentPane().setLayout(new BorderLayout());

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ignorable) {
		}

		source = new DrawingModelImpl();

		createToolbar();
		createBottomBar();
		createObjectsList();
		createMenu();

		canvas = new JDrawingCanvas(source, backColor, frontColor);
		source.addDrawingModelListener(canvas);

		getContentPane().add(canvas, BorderLayout.CENTER);

	}

	/**
	 * Creates the bottom bar component which displays the currently selected
	 * colours.
	 */
	private void createBottomBar() {
		statusBar = new JPanel();
		statusBar.setBorder(new BevelBorder(BevelBorder.LOWERED));
		add(statusBar, BorderLayout.SOUTH);
		statusBar.setPreferredSize(new Dimension(getWidth(), 16));
		statusBar.setLayout(new BorderLayout());

		CustomLabel colourLabel = new CustomLabel(frontColor, backColor);

		frontColor.addColorChangeListener(colourLabel);
		backColor.addColorChangeListener(colourLabel);

		statusBar.add(colourLabel, BorderLayout.WEST);

		add(statusBar, BorderLayout.SOUTH);
	}

	/**
	 * Creates the toolbar containing the availible actions.
	 */
	private void createToolbar() {
		JToolBar toolBar = new JToolBar("Tools");
		toolBar.setLayout(new FlowLayout(FlowLayout.LEFT));

		toolBar.setFloatable(false);

		backColor = new JColorArea(Color.RED);
		frontColor = new JColorArea(Color.BLACK);

		ButtonGroup bGroup = new ButtonGroup();
		JToggleButton bLine = new JToggleButton("Line");
		JToggleButton bCircle = new JToggleButton("Circle");
		JToggleButton bFilledCircle = new JToggleButton("Filled Circle");
		bLine.addActionListener(z -> {
			canvas.setObjectCommand(new LineAction());
		});

		bCircle.addActionListener(z -> {
			canvas.setObjectCommand(new CircleAction());
		});

		bFilledCircle.addActionListener(z -> {
			canvas.setObjectCommand(new FilledCircleAction());
		});

		bGroup.add(bLine);
		bGroup.add(bCircle);
		bGroup.add(bFilledCircle);

		JLabel backLabel = new JLabel("Background color ");
		JLabel frontLabel = new JLabel("Foreground color ");

		toolBar.add(backLabel);
		toolBar.add(backColor);

		toolBar.addSeparator();

		toolBar.add(frontLabel);
		toolBar.add(frontColor);

		toolBar.addSeparator();

		toolBar.add(bLine);
		toolBar.add(bCircle);
		toolBar.add(bFilledCircle);

		this.getContentPane().add(toolBar, BorderLayout.PAGE_START);
	}

	/**
	 * Creates the menu containing the availible actions.
	 */
	private void createMenu() {
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu fileMenu = new JMenu("File");
		menuBar.add(fileMenu);
		fileMenu.add(new JMenuItem(new OpenAction(this)));
		fileMenu.add(new JMenuItem(new SaveAction(this)));
		fileMenu.add(new JMenuItem(new SaveAsAction(this)));
		// fileMenu.add(new JMenuItem(exportAction));
		// fileMenu.add(new JMenuItem(exitAction));
		// TODO:
		// this.getContentPane().add(menuBar, BorderLayout.PAGE_START);
	}

	/**
	 * Creates the objects list located on the right side of the application.
	 */
	private void createObjectsList() {
		DrawingObjectsListModel listModel = new DrawingObjectsListModel(source);
		source.addDrawingModelListener(listModel);
		JList<String> list = new JList<String>(listModel);

		list.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_DELETE) {
					source.remove(source.getObject(list.getSelectedIndex()));
				}
			}
		});

		list.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					int index = list.getSelectedIndex();
					GeometricalObject o = source.getObject(index);

					if (o.invokePropertyDialog(JVDraw.this.getSize())) {
						source.getListeners().forEach(z -> z.objectsChanged(source, index, index));
						listModel.objectsChanged(source, index, index);
					}
				}
			}
		});

		JScrollPane scrollPane = new JScrollPane(list);
		scrollPane.setPreferredSize(new Dimension(getWidth() / 5, getHeight()));
		getContentPane().add(scrollPane, BorderLayout.EAST);
	}

	/**
	 * Gets the current path.
	 *
	 * @return the current path
	 */
	public Path getCurrentPath() {
		return currentPath;
	}

	/**
	 * Sets the current path.
	 *
	 * @param currentPath
	 *            the new current path
	 */
	public void setCurrentPath(Path currentPath) {
		this.currentPath = currentPath;
	}

	/**
	 * The main method, first one to be executed when the program starts.
	 * 
	 * @param args
	 *            - the input arguments, not used here
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new JVDraw().setVisible(true);
		});
	}
}
