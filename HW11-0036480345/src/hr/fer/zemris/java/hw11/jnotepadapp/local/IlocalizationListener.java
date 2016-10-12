package hr.fer.zemris.java.hw11.jnotepadapp.local;

/**
 * An interface representing an abstraction for a localization listener. It
 * listens to a subject and starts the {@link #localizationChanged()} method.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public interface IlocalizationListener {

	/**
	 * A method which will be called by the Subject when the selected language
	 * changes.
	 */
	public void localizationChanged();

}