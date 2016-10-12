package hr.fer.zemris.java.hw11.jnotepadapp.local;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Implements the
 * <a href="https://en.wikipedia.org/wiki/Singleton_pattern">Singleton</a>
 * design pattern. It loads the resource bundle for this language and stores
 * reference to it. Also sets the language to “en” by default.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class LocalizationProvider extends AbstractLocalizationProvider {

	/**
	 * Keeps a reference to the {@linkplain ResourceBundle}object that
	 * represents our translations for the given language
	 */
	private ResourceBundle bundle;
	/** A reference to the currently selected language. */
	private String language;
	/** A singleton intance of the {@linkplain LocalizationProvider}. */
	private static final LocalizationProvider instance = new LocalizationProvider();

	/**
	 * Instantiates a new localization provider. Sets the default language to
	 * english.
	 */
	private LocalizationProvider() {
		super();
		setLanguage("en");
	}

	/**
	 * Gets the single instance of LocalizationProvider.
	 *
	 * @return single instance of LocalizationProvider
	 */
	public static LocalizationProvider getInstance() {
		return instance;
	}

	@Override
	public String getString(String key) {
		return bundle.getString(key);
	}

	/**
	 * Sets the language to the desired value.
	 *
	 * @param language
	 *            the new language
	 */
	public void setLanguage(String language) {
		this.language = language;
		Locale locale = Locale.forLanguageTag(language);
		bundle = ResourceBundle.getBundle("hr.fer.zemris.java.hw11.jnotepadapp.local.prijevodi",
				locale);

		fire();
	}

	/**
	 * Gets the currently selected language.
	 *
	 * @return the language currently selected language
	 */
	@Override
	public String getLanguage() {
		return language;
	}

}
