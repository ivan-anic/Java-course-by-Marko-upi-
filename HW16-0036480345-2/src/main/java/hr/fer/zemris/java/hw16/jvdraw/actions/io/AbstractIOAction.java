package hr.fer.zemris.java.hw16.jvdraw.actions.io;

import javax.swing.AbstractAction;

import hr.fer.zemris.java.hw16.jvdraw.JVDraw;

/**
 * An abstraction for an I/O action to be implemented.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public abstract class AbstractIOAction extends AbstractAction {

	/** Serial version user ID */
	private static final long serialVersionUID = 8148222472731002919L;
	/** Holds reference to the application context. */
	protected JVDraw jvDraw;

	/**
	 * Instantiates a new abstract jv action.
	 *
	 * @param jvDraw
	 *            the jv draw
	 */
	public AbstractIOAction(JVDraw jvDraw) {
		this.jvDraw = jvDraw;
	}

}