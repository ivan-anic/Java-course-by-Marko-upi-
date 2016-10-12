package hr.fer.zemris.java.gui.layouts;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;

/**
 * An interface representing an abstraction for all size parameters.
 * 
 * @see #determineSize(Component)
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public interface IParameter {

	/**
	 * Specifies the desired dimensions being calculated for the specified
	 * {@linkplain Container}, given the components it contains and the
	 * dimension to be calculated.
	 * 
	 * @param target
	 *            the component whose size is being computed
	 * @return the desired {@linkplain Dimension}
	 *
	 * @see CalcLayout#maximumLayoutSize
	 * @see CalcLayout#minimumLayoutSize
	 * @see CalcLayout#preferredLayoutSize
	 */
	Dimension determineSize(Component target);
}
