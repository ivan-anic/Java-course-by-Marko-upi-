package hr.fer.zemris.java.hw11.jnotepadapp;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.plaf.basic.BasicButtonUI;

import hr.fer.zemris.java.hw11.jnotepadapp.Actions.AbstractActionSave;

/**
 * Component to be used as tabComponent; Contains a {@linkplain JLabel} to show
 * the text and a {@linkplain JButton} to close the tab it belongs to
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class ButtonTabComponent extends JPanel {
	/** The generated serial user ID */
	private static final long serialVersionUID = 5976950417953537982L;
	/** A reference to the tabbed pane. */
	private final JTabbedPane pane;
	/** A reference to the label which displays the file name */
	JLabel label;
	/** A reference to the file displayed */
	private File file;
	/**
	 * A reference to the diskette icon indicating whether the file is changed
	 * or not
	 */
	JLabel labelIcon;

	/** Keeps a reference to the {@linkplain IUtilComm} interface. */
	IUtilComm util;

	/**
	 * Instantiates a new button tab component with the desired parameters.
	 *
	 * @param pane
	 *            the pane where this component will be placed
	 * @param file
	 *            the file displayed
	 * @param util
	 *            the util a reference to the {@linkplain IUtilComm} interface.
	 */
	public ButtonTabComponent(final JTabbedPane pane, DocumentWrapper file, IUtilComm util) {
		super(new FlowLayout(FlowLayout.LEFT, 0, 0));
		if (pane == null) {
			throw new NullPointerException("TabbedPane is null");
		}
		this.util = util;
		this.pane = pane;
		setOpaque(false);

		String absPath = (file.getFile().canRead()) ? file.getFile().getAbsolutePath() : file.getFile().toString();

		setToolTipText(absPath);
		setName(file.getFile().getAbsolutePath());

		label = new JLabel() {
			/** The generated serial user ID */
			private static final long serialVersionUID = 440805764214470808L;

			public String getText() {
				int i = pane.indexOfTabComponent(ButtonTabComponent.this);
				if (i != -1) {
					return pane.getTitleAt(i);
				}
				return null;
			}

		};

		label.setToolTipText(absPath);

		label.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseReleased(MouseEvent e) {
				int i = pane.indexOfTabComponent(ButtonTabComponent.this);
				pane.setSelectedIndex(i);
			}
		});

		labelIcon = new JLabel();
		setImage(false);

		JButton button = new TabButton();

		label.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 5));

		add(labelIcon);
		add(label);

		add(button);

		setBorder(BorderFactory.createEmptyBorder(2, 0, 0, 0));
	}

	/**
	 * Represents a tab button used for closing the tab.
	 * 
	 * @author Ivan Anić
	 * @version 1.0
	 */
	private class TabButton extends JButton implements ActionListener {

		/** The generated serial user ID */
		private static final long serialVersionUID = -9084896003818852716L;

		/**
		 * Instantiates a new tab button.
		 */
		public TabButton() {
			int size = 17;
			setPreferredSize(new Dimension(size, size));
			setToolTipText("close this tab");

			setUI(new BasicButtonUI());

			setContentAreaFilled(false);

			setFocusable(false);
			setBorder(BorderFactory.createEtchedBorder());
			setBorderPainted(false);

			setRolloverEnabled(true);

			addActionListener(this);
		}

		public void actionPerformed(ActionEvent e) {
			int i = pane.indexOfTabComponent(ButtonTabComponent.this);

			JTextArea temp = (JTextArea) pane.getComponentAt(i);
			if (temp.getDocument().getProperty("changed").equals(true)) {
				int res = JOptionPane.showConfirmDialog(util.getNotepad(),
						"You have some unsaved changes. Would you like to save them?",
						pane.getTitleAt(i),
						JOptionPane.YES_NO_OPTION);

				if (res == JOptionPane.YES_OPTION) {
					AbstractActionSave.perform();
					temp.getDocument().putProperty("changed", false);
				} else {
					return;
				}
			}
			pane.remove(i);
		}

		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D) g.create();

			if (getModel().isPressed()) {
				g2.translate(1, 1);
			}
			g2.setStroke(new BasicStroke(2));
			g2.setColor(Color.BLACK);

			if (getModel().isRollover()) {
				g2.setColor(Color.MAGENTA);
			}

			int delta = 6;
			g2.drawLine(delta, delta, getWidth() - delta - 1, getHeight() - delta - 1);
			g2.drawLine(getWidth() - delta - 1, delta, delta, getHeight() - delta - 1);

			g2.dispose();
		}
	}

	/**
	 * Gets the file currently being displayed.
	 *
	 * @return the file currently being displayed
	 */
	public File getFile() {
		return file;
	}

	/**
	 * Sets the title of the tab to the desired text.
	 *
	 * @param title
	 *            the new title
	 */
	public void setTitle(String title) {
		pane.setTitleAt(pane.indexOfTabComponent(ButtonTabComponent.this), title);
	}

	/**
	 * Sets the image of the tab according to the information whether the file
	 * has changed or not.
	 *
	 * @param changed
	 *            indicated which image will be set; red if true
	 */
	public void setImage(boolean changed) {
		labelIcon.setIcon(getImages((changed) ? "red" : "green"));
	}

	/**
	 * Gets the image from the memory and returns it.
	 *
	 * @param colour
	 *            the desired colour
	 * @return the image for the tab component
	 */
	private ImageIcon getImages(String colour) {
		try (InputStream is = this.getClass()
				.getResourceAsStream("/hr/fer/zemris/java/hw11/jnotepadapp/icons/floppy_" + colour + "_small.png")) {
			return new ImageIcon(ImageIO.read(is));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
