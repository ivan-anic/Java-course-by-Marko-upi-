package hr.fer.zemris.java.hw11.jnotepadapp.local;

import javax.swing.AbstractAction;
import javax.swing.Action;

/**
 * An abstract class derived from {@linkplain AbstractAction}. It adds a
 * listener to the provider which listens to language changes.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public abstract class LocalizableAction extends AbstractAction {

	/** The generated serial user ID */
	private static final long serialVersionUID = -7458126743879259152L;

	/**
	 * Instantiates a new localizable action. Adds a listener to the provider
	 * which listens to language changes.
	 *
	 * @param key
	 *            the key
	 * @param provider
	 *            the provider
	 */
	public LocalizableAction(String key, IlocalizationProvider provider) {
		super();

		String translation = provider.getString(key);
		putValue(NAME, translation);

		String translationTooltip = provider.getString(key + "_tooltip");
		putValue(Action.SHORT_DESCRIPTION, translationTooltip);

		provider.addLocalizationListener(new IlocalizationListener() {
			@Override
			public void localizationChanged() {
				String translation = provider.getString(key);
				putValue(NAME, translation);

				String translationTooltip = provider.getString(key + "_tooltip");
				putValue(Action.SHORT_DESCRIPTION, translationTooltip);
			}
		});

	}
}
