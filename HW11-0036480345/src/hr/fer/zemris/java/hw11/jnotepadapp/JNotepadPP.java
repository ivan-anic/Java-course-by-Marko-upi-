package hr.fer.zemris.java.hw11.jnotepadapp;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.border.BevelBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;

import hr.fer.zemris.java.hw11.jnotepadapp.Actions.AbstractActionChangeCase;
import hr.fer.zemris.java.hw11.jnotepadapp.Actions.AbstractActionSave;
import hr.fer.zemris.java.hw11.jnotepadapp.Actions.AbstractActionSort;
import hr.fer.zemris.java.hw11.jnotepadapp.Actions.ActionCopy;
import hr.fer.zemris.java.hw11.jnotepadapp.Actions.ActionCut;
import hr.fer.zemris.java.hw11.jnotepadapp.Actions.ActionExit;
import hr.fer.zemris.java.hw11.jnotepadapp.Actions.ActionInvertCase;
import hr.fer.zemris.java.hw11.jnotepadapp.Actions.ActionLowerCase;
import hr.fer.zemris.java.hw11.jnotepadapp.Actions.ActionNewDocument;
import hr.fer.zemris.java.hw11.jnotepadapp.Actions.ActionOpen;
import hr.fer.zemris.java.hw11.jnotepadapp.Actions.ActionPaste;
import hr.fer.zemris.java.hw11.jnotepadapp.Actions.ActionSave;
import hr.fer.zemris.java.hw11.jnotepadapp.Actions.ActionSaveAs;
import hr.fer.zemris.java.hw11.jnotepadapp.Actions.ActionSortAscending;
import hr.fer.zemris.java.hw11.jnotepadapp.Actions.ActionSortDescending;
import hr.fer.zemris.java.hw11.jnotepadapp.Actions.ActionStatistics;
import hr.fer.zemris.java.hw11.jnotepadapp.Actions.ActionUpperCase;
import hr.fer.zemris.java.hw11.jnotepadapp.local.FormLocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadapp.local.LocalizationProvider;

/**
 * An implementation of a notepad-like application. It offers various
 * functionalities (actions):
 * <ul>
 * <li>o{@linkplain AbstractActionChangeCase}</li>
 * <li>{@linkplain AbstractActionSave}</li>
 * <li>{@linkplain AbstractActionSort}</li>
 * <li>{@linkplain ActionCopy}</li>
 * <li>{@linkplain ActionExit}</li>
 * <li>{@linkplain ActionNewDocument}</li>
 * <li>{@linkplain ActionOpen}</li>
 * <li>{@linkplain ActionCut}</li>
 * <li>{@linkplain ActionCopy}</li>
 * <li>{@linkplain ActionPaste}</li>
 * <li>{@linkplain ActionSave}</li>
 * <li>{@linkplain ActionSaveAs}</li>
 * </ul>
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class JNotepadPP extends JFrame implements IUtilComm {

	/** The generated serial user ID */
	private static final long serialVersionUID = -5271235602299831837L;
	/** A reference to the currently opened text area. */
	private JTextArea editor;
	/** A reference to the current opened file. */
	private Path openedFilePath = Paths.get("");
	/** A reference to the scrollpane. */
	private JScrollPane scPane;
	/** A reference to the HR menu item. */
	JMenuItem itemHR;
	/** A reference to the EN menu item. */
	JMenuItem itemEN;
	/** A reference to the DE menu item. */
	JMenuItem itemDE;
	/** A reference to the togglecase menu item. */
	JMenuItem itemInvert;
	/** A reference to the uppercase menu item. */
	JMenuItem itemUpper;
	/** A reference to the lowercase menu item. */
	JMenuItem itemLower;
	/** A reference to the file menu. */
	JMenu fileMenu;
	/** A reference to the edit menu. */
	JMenu editMenu;
	/** A reference to the lang menu. */
	JMenu langMenu;
	/** A reference to the tools menu. */
	JMenu toolsMenu;
	/** A reference to the sort menu. */
	JMenu sortMenu;

	/** A reference to the label showing file status. */
	JLabel statusLabel;
	/** A reference to the label showing the current line. */
	JLabel lineLabel;
	/** A reference to the label showing the current column. */
	JLabel columnLabel;
	/** A reference to the label showing length of the selected text. */
	JLabel selectedLabel;

	/** A reference to the tabbed pane. */
	private final JTabbedPane pane = new JTabbedPane();
	/** the provider which listens to language changes */
	FormLocalizationProvider flp;

	/**
	 * Instantiates a new notepad application.
	 */
	public JNotepadPP() {
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setSize(800, 600);
		setLocation(50, 50);

		flp = new FormLocalizationProvider(LocalizationProvider.getInstance(), this);
		flp.addLocalizationListener(() -> {
			fileMenu.setText(flp.getString("file"));
			editMenu.setText(flp.getString("edit"));
			langMenu.setText(flp.getString("lang"));
			toolsMenu.setText(flp.getString("tools"));

		});

		pane.addChangeListener(z -> {
			setOpenedFilePath(getOpenedFilePath());
			setTitle(openedFilePath.toString() + " - JNotepad++");
		});

		addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {

				int numComponents = pane.getTabCount();
				boolean saved = false;

				for (int i = 0; i < numComponents; i++) {
					JTextArea temp = (JTextArea) pane.getComponentAt(i);
					if (temp.getDocument().getProperty("changed").equals(true)) {
						int res = JOptionPane.showConfirmDialog(JNotepadPP.this,
								"You have some unsaved changes. Would you like to save them?",
								pane.getTitleAt(i),
								JOptionPane.YES_NO_OPTION);

						if (res == JOptionPane.YES_OPTION) {
							// tu nesto sredi sa staticima
							AbstractActionSave.perform();
							saved = true;
						} else {
							continue;
						}
					}
				}

				if (saved != true) {
					int res = JOptionPane.showConfirmDialog(JNotepadPP.this,
							"Jeste li sigurni?",
							"zatvaranje",
							JOptionPane.YES_NO_OPTION);

					if (res != JOptionPane.YES_OPTION) {
						return;
					}
				}
				dispose();
			}
		});
		initGUI();
	}

	/**
	 * Initialises the graphical user interface. Adds all the necessary elements
	 * and listeners.
	 */
	private void initGUI() {
		editor = new JTextArea();
		this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().add(new JScrollPane(editor), BorderLayout.CENTER);

		scPane = new JScrollPane(editor);
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(scPane, BorderLayout.CENTER);

		add(pane);

		JPanel statusBar = new JPanel();
		JLabel welcomedate;
		welcomedate = new JLabel();

		statusBar.setBorder(new BevelBorder(BevelBorder.LOWERED));
		add(statusBar, BorderLayout.SOUTH);
		statusBar.setPreferredSize(new Dimension(getWidth(), 16));
		statusBar.setLayout(new GridLayout(1, 4));

		statusLabel = new JLabel();
		lineLabel = new JLabel();
		columnLabel = new JLabel();
		selectedLabel = new JLabel();

		statusLabel.setHorizontalAlignment(SwingConstants.LEFT);

		statusBar.add(statusLabel);

		statusBar.add(lineLabel);
		statusBar.add(columnLabel);
		statusBar.add(selectedLabel);

		statusBar.add(welcomedate, BorderLayout.EAST);

		javax.swing.Timer time = new javax.swing.Timer(1000, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				java.util.Date now = new java.util.Date();
				String ss = SimpleDateFormat.getDateTimeInstance().format(now);
				welcomedate.setText(ss);
			}
		});
		time.start();

		add(statusBar, BorderLayout.SOUTH);

		setVisible(true);

		createMenus();
		createToolbars();

		createLanguageMenu();

	}

	/**
	 * Adds a new tab into the tabbed pane
	 *
	 * @param file
	 *            the file
	 * @param text
	 *            the text which
	 */
	public void addTab(DocumentWrapper file, String text) {
		JTextArea textEditor = new JTextArea();

		textEditor.setText(text);
		textEditor.getDocument().putProperty("changed", false);
		textEditor.addCaretListener(new CaretListener() {
			@Override
			public void caretUpdate(CaretEvent arg0) {
				int dot = arg0.getDot();
				int mark = arg0.getMark();

				int caretpos = textEditor.getCaretPosition();
				int lineNum = 1;
				int columnNum = 1;
				try {
					lineNum = textEditor.getLineOfOffset(caretpos);
					columnNum = 1 + caretpos - textEditor.getLineStartOffset(lineNum);
					lineNum += 1;

				} catch (BadLocationException e) {
					e.printStackTrace();
				}

				statusLabel.setText("length : " + textEditor.getText().length());
				lineLabel.setText("Ln : " + lineNum);
				columnLabel.setText("Col : " + columnNum);
				selectedLabel.setText("Sel : " + Math.abs(dot - mark));
				;

				if (dot == mark) {
					itemInvert.setEnabled(false);
					itemLower.setEnabled(false);
					itemUpper.setEnabled(false);
				} else {
					itemInvert.setEnabled(true);
					itemLower.setEnabled(true);
					itemUpper.setEnabled(true);
				}
			}
		});

		textEditor.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void insertUpdate(DocumentEvent e) {
				textEditor.getDocument().putProperty("changed", true);

				ButtonTabComponent comp = (ButtonTabComponent) pane.getTabComponentAt(pane.getSelectedIndex());

				comp.setImage(true);
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				textEditor.getDocument().putProperty("changed", true);

			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				textEditor.getDocument().putProperty("changed", true);

			}
		});

		file.setEditor(textEditor);

		JScrollPane scPane = new JScrollPane(textEditor);
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(scPane, BorderLayout.CENTER);

		int index = pane.getTabCount();

		pane.addTab(file.getName(), textEditor);

		initTabComponent(file, index);
	}

	/**
	 * Inititlizes a new tab component.
	 *
	 * @param file
	 *            the file
	 * @param i
	 *            the i
	 */
	private void initTabComponent(DocumentWrapper file, int i) {
		pane.setTabComponentAt(i,
				new ButtonTabComponent(pane, file, this));
	}

	/**
	 * Creates the menus containing the availible actions.
	 */
	private void createMenus() {
		JMenuBar menuBar = new JMenuBar();

		fileMenu = new JMenu(flp.getString("file"));
		menuBar.add(fileMenu);

		fileMenu.add(new JMenuItem(new ActionNewDocument(flp, this)));
		fileMenu.add(new JMenuItem(new ActionOpen(flp, this)));
		fileMenu.add(new JMenuItem(new ActionSave(flp, this)));
		fileMenu.add(new JMenuItem(new ActionSaveAs(flp, this)));
		fileMenu.addSeparator();
		fileMenu.add(new JMenuItem(new ActionExit(flp, this)));
		fileMenu.add(new JMenuItem(new ActionStatistics(flp, this)));

		editMenu = new JMenu(flp.getString("edit"));

		editMenu.add(new JMenuItem(new ActionCut(flp, this)));
		editMenu.add(new JMenuItem(new ActionCopy(flp, this)));
		editMenu.add(new JMenuItem(new ActionPaste(flp, this)));

		toolsMenu = new JMenu(flp.getString("tools"));

		itemInvert = new JMenuItem(new ActionInvertCase(flp, this));
		itemInvert.setEnabled(false);
		toolsMenu.add(itemInvert);

		itemLower = new JMenuItem(new ActionLowerCase(flp, this));
		itemLower.setEnabled(false);
		toolsMenu.add(itemLower);

		itemUpper = new JMenuItem(new ActionUpperCase(flp, this));
		itemUpper.setEnabled(false);
		toolsMenu.add(itemUpper);

		sortMenu = new JMenu(flp.getString("sort"));
		sortMenu.add(new JMenuItem(new ActionSortAscending(flp, this)));
		sortMenu.add(new JMenuItem(new ActionSortDescending(flp, this)));

		menuBar.add(editMenu);
		menuBar.add(createLanguageMenu());
		menuBar.add(toolsMenu);
		menuBar.add(sortMenu);

		this.setJMenuBar(menuBar);
	}

	/**
	 * Creates the language menu.
	 *
	 * @return the language menu which will be added to the toolbar
	 */
	private JMenu createLanguageMenu() {
		Action nestoAction = new AbstractAction() {

			/** The generated serial user ID */
			private static final long serialVersionUID = -4439263551767223123L;

			@Override
			public void actionPerformed(ActionEvent e) {
				JMenuItem src = (JMenuItem) e.getSource();
				String arg = src.getText();
				LocalizationProvider.getInstance().setLanguage(arg);
				LocalizationProvider.getInstance().fire();
			}
		};

		itemHR = new JMenuItem(nestoAction);
		itemHR.setText("hr");
		itemEN = new JMenuItem(nestoAction);
		itemEN.setText("en");
		itemDE = new JMenuItem(nestoAction);
		itemDE.setText("de");

		langMenu = new JMenu(flp.getString("lang"));

		langMenu.add(itemHR);
		langMenu.add(itemEN);
		langMenu.add(itemDE);

		return langMenu;
	}

	/**
	 * Creates the toolbars containing the availible actions.
	 */
	private void createToolbars() {
		JToolBar toolBar = new JToolBar("Tools");
		toolBar.setFloatable(true);

		toolBar.add(new JButton(new ActionNewDocument(flp, this)));
		toolBar.add(new JButton(new ActionOpen(flp, this)));
		toolBar.add(new JButton(new ActionSave(flp, this)));
		toolBar.add(new JButton(new ActionSaveAs(flp, this)));
		toolBar.addSeparator();
		toolBar.add(new JButton(new ActionCut(flp, this)));
		toolBar.add(new JButton(new ActionCopy(flp, this)));
		toolBar.add(new JButton(new ActionPaste(flp, this)));
		toolBar.addSeparator();
		toolBar.add(new JButton(new ActionStatistics(flp, this)));

		this.getContentPane().add(toolBar, BorderLayout.PAGE_START);
	}

	/**
	 * The main method, first one to be executed when the program starts.
	 * 
	 * @param args
	 *            - the input arguments, not used here
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new JNotepadPP().setVisible(true);
		});
	}

	@Override
	public JNotepadPP getNotepad() {
		return this;
	}

	@Override
	public JTextArea getContext() {
		return (JTextArea) pane.getComponentAt(pane.getSelectedIndex());
	}

	@Override
	public Path getOpenedFilePath() {

		ButtonTabComponent comp;
		try {
			comp = (ButtonTabComponent) pane.getTabComponentAt(pane.getSelectedIndex());
		} catch (ArrayIndexOutOfBoundsException ex) {
			comp = null;
		}
		return Paths.get((comp == null) ? "untitled" : comp.getName());
	}

	@Override
	public void setOpenedFilePath(Path path) {
		this.openedFilePath = path;
	}

	@Override
	public Path getSelectedFilePath() {
		return openedFilePath;
	}

	@Override
	public void setImage(boolean changed) {
		ButtonTabComponent comp = (ButtonTabComponent) pane.getTabComponentAt(pane.getSelectedIndex());

		comp.setImage(changed);
	}

	@Override
	public void updateTabName() {
		ButtonTabComponent comp = (ButtonTabComponent) pane.getTabComponentAt(pane.getSelectedIndex());
		comp.setTitle(openedFilePath.getFileName().toString());
	}
}
