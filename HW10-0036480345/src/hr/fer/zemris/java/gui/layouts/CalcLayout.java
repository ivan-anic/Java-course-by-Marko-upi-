package hr.fer.zemris.java.gui.layouts;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager2;
import java.util.HashMap;
import java.util.Map;

/**
 * An implementation of {@linkplain LayoutManager2}, represents a <i>grid</i>
 * layout with final dimensions 5x7. Uses instances of {@linkplain RCPosition}
 * for positioning desired objects inside the layout.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class CalcLayout implements LayoutManager2 {

	/** Represents a final number of rows available for this layout. */
	private final static int NUMBER_OF_ROWS = 5;
	/** Represents a final number of columns available for this layout. */
	private final static int NUMBER_OF_COLUMNS = 7;
	/**
	 * Represents a final value of spacing between rows and columns. Initially
	 * is set to zero pixels.
	 */
	private final static int DEFAULT_GAP = 0;
	/**
	 * Represents a final value of default layout alignment. Initially is set to
	 * 0.5f (representing centre alignment).
	 */
	private final static float DEFAULT_LAYOUT_ALIGNMENT = 0.5f;

	/**
	 * The Constant MAX_COLUMN_POSITION, indicating the reserved positions for
	 * the display bar.
	 */
	private final static int MAX_ROW_POSITION = 1;

	/**
	 * The Constant MAX_COLUMN_POSITION, indicating the reserved positions for
	 * the display bar.
	 */
	private final static int MAX_COLUMN_POSITION = 5;

	/** The Constant bar. */
	private final static RCPosition bar = new RCPosition(1, 1);

	/** Keeps the specified spacing between rows and columns, in pixels. */
	private int layoutGap;
	/** Keeps references to all elements contained in this layout. */
	private Map<Component, RCPosition> contents;

	/**
	 * Instantiates a new {@linkplain CalcLayout}. The spacing between rows and
	 * columns will be set to default value, {@linkplain #DEFAULT_GAP}.
	 */
	public CalcLayout() {
		this(DEFAULT_GAP);
	}

	/**
	 * Instantiates a new {@linkplain CalcLayout}.
	 *
	 * @param layoutGap
	 *            the desired spacing between rows in columns, in pixels.
	 */
	public CalcLayout(int layoutGap) {
		if (layoutGap < 0) {
			throw new IllegalArgumentException("Gap can't be null!");
		}
		this.layoutGap = layoutGap;
		this.contents = new HashMap<>();
	}

	@Override
	public void addLayoutComponent(String name, Component comp) {
	}

	@Override
	public void addLayoutComponent(Component comp, Object constraints) {
		if (constraints instanceof RCPosition) {
			RCPosition pos = (RCPosition) constraints;
			if (pos.getColumn() == 1 && pos.getRow() == 1) {
				pos = bar;
			}

			if (pos.getRow() <= 0 || pos.getColumn() <= 0) {
				throw new IllegalArgumentException(
						"Cannot add to this layout: row and column parameters must be positive!");
			} else if (contents.containsKey(comp)) {
				throw new IllegalArgumentException("Given component is already in the layout!");
			} else if (pos.getColumn() <= MAX_COLUMN_POSITION &&
					pos.getColumn() > 1 &&
					pos.getRow() <= MAX_ROW_POSITION) {
				throw new IllegalArgumentException(
						"Illegal arguments. This position is reserved for a bar with 1x5 on location 1x1");
			} else {
				contents.put(comp, pos);
			}
		} else if (constraints != null) {
			throw new IllegalArgumentException("Cannot add to this layout: constraint must be instance of RCPosition");
		}
	}

	@Override
	public void removeLayoutComponent(Component comp) {
		if (comp == null) {
			throw new IllegalArgumentException("Can't remove a null component!");
		} else if (!contents.containsKey(comp)) {
			throw new IllegalArgumentException("Given component isn't in the layout!");
		} else {
			contents.remove(comp);
		}
	}

	@Override
	public Dimension preferredLayoutSize(Container parent) {
		return determineLayoutSize(parent, z -> z.getPreferredSize());
	}

	@Override
	public Dimension minimumLayoutSize(Container parent) {
		return determineLayoutSize(parent, z -> z.getMinimumSize());
	}

	@Override
	public Dimension maximumLayoutSize(Container target) {
		return determineLayoutSize(target, z -> z.getMaximumSize());
	}

	/**
	 * Calculates the desired dimensions for the specified container, given the
	 * components it contains and the dimension to be calculated.
	 * 
	 * @param parent
	 *            the container to be laid out
	 * @param func
	 *            the desired {@code Dimension} to be calculated
	 * @return the desired {@code Dimension}
	 *
	 * @see #maximumLayoutSize
	 * @see #minimumLayoutSize
	 * @see #preferredLayoutSize
	 */
	private Dimension determineLayoutSize(Container parent, IParameter func) {
		synchronized (parent.getTreeLock()) {
			int width = 0;
			int height = 0;
			Insets insets = parent.getInsets();

			for (Component c : contents.keySet()) {
				RCPosition pos = contents.get(c);
				if (c.getMinimumSize() != null) {
					width = (int) Math.max(pos.getRow(), func.determineSize(c).getWidth());
					height = (int) Math.max(pos.getColumn(), func.determineSize(c).getHeight());
				}
			}
			return new Dimension(
					(insets.left + insets.right + NUMBER_OF_COLUMNS * width + (NUMBER_OF_COLUMNS - 1) * layoutGap),
					(insets.top + insets.bottom + NUMBER_OF_ROWS * height + (NUMBER_OF_ROWS - 1) * layoutGap));
		}
	}

	@Override
	public void layoutContainer(Container parent) {
		synchronized (parent.getTreeLock()) {
			Insets insets = parent.getInsets();
			int nComponents = parent.getComponentCount();

			if (nComponents == 0) {
				return;
			}

			int totalGapsWidth = (NUMBER_OF_COLUMNS - 1) * layoutGap;
			int widthWOInsets = parent.getWidth() - (insets.left + insets.right);
			int widthOnComponent = (widthWOInsets - totalGapsWidth) / NUMBER_OF_COLUMNS;
			int extraWidthAvailable = (widthWOInsets - (widthOnComponent * NUMBER_OF_COLUMNS + totalGapsWidth)) / 2;

			int totalGapsHeight = (NUMBER_OF_ROWS - 1) * layoutGap;
			int heightWOInsets = parent.getHeight() - (insets.top + insets.bottom);
			int heightOnComponent = (heightWOInsets - totalGapsHeight) / NUMBER_OF_ROWS;
			int extraHeightAvailable = (heightWOInsets - (heightOnComponent * NUMBER_OF_ROWS + totalGapsHeight)) / 2;

			int row;
			int col;
			for (Component c : contents.keySet()) {
				RCPosition pos = contents.get(c);
				col = insets.left + extraWidthAvailable + (pos.getColumn() - 1) * widthOnComponent
						+ (pos.getColumn() - 1) * layoutGap;

				row = insets.top + extraHeightAvailable + (pos.getRow() - 1) * heightOnComponent
						+ (pos.getRow() - 1) * layoutGap;

				if (pos.equals(bar)) {
					c.setBounds(col, row, widthOnComponent * 5 + 4 * layoutGap, heightOnComponent);
				} else {
					c.setBounds(col, row, widthOnComponent, heightOnComponent);
				}
			}
		}
	}

	@Override
	public float getLayoutAlignmentX(Container target) {
		return DEFAULT_LAYOUT_ALIGNMENT;
	}

	@Override
	public float getLayoutAlignmentY(Container target) {
		return DEFAULT_LAYOUT_ALIGNMENT;
	}

	@Override
	public void invalidateLayout(Container target) {
	}
}
