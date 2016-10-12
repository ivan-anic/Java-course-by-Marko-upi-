package hr.fer.zemris.java.hw11.jnotepadapp.local;

/**
 * An interface representing an abstraction for a localization provider. Objects
 * which are instances of classes that implement this interface will be able to
 * give us the translations for given keys.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public interface IlocalizationProvider {

	/**
	 * Adds a new localization listener.
	 *
	 * @param listener
	 *            the listener to be added
	 */
	public void addLocalizationListener(IlocalizationListener listener);

	/**
	 * Removes the specified localization listener.
	 *
	 * @param listener
	 *            the listener
	 */
	public void removeLocalizationListener(IlocalizationListener listener);

	/**
	 * Takes a key and gives back the localised value
	 *
	 * @param value
	 *            the key
	 * @return the localised value
	 */
	public String getString(String value);

	/**
	 * Gets the currently set language.
	 *
	 * @return the currently set language
	 */
	public String getLanguage();

}
