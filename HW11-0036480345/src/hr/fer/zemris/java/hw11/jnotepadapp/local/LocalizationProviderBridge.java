package hr.fer.zemris.java.hw11.jnotepadapp.local;

/**
 * A decorator for some other {@linkplain IlocalizationProvider}. This class
 * offers two additional methods:
 * <ul>
 * <li>{@link #connect()}</li>
 * <li>{@link #disconnect()}</li>
 * 
 * </ul>
 * When asked to resolve a key, this class delegates the request to wrapped
 * (decorated) {@linkplain IlocalizationProvider} object. When user calls
 * connect() on it, the method will register an instance of anonimous
 * {@linkplain IlocalizationListener} on the decorated object. When user calls
 * disconnect(), this object will be deregistered from decorated object.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class LocalizationProviderBridge extends AbstractLocalizationProvider {
	/**
	 * Keeps the information about whether the listener is connected to the
	 * decorated object.
	 */
	private boolean connected;
	/** A reference to the provider. */
	private IlocalizationProvider provider;
	/** A reference to the listener. */
	private IlocalizationListener listener;

	/**
	 * Instantiates a new localization provider bridge.
	 *
	 * @param provider
	 *            the provider
	 */
	protected LocalizationProviderBridge(IlocalizationProvider provider) {
		this.provider = provider;
	}

	/**
	 * Disconnects this object from the decorated object.
	 */
	public void disconnect() {
		if (!connected) {
			return;
		}
		provider.removeLocalizationListener(listener);
		connected = false;
	}

	/**
	 * Registers an instance of anonimous {@linkplain IlocalizationListener} on
	 * the decorated object.
	 */
	public void connect() {
		if (connected) {
			return;
		}
		listener = (new IlocalizationListener() {
			@Override
			public void localizationChanged() {
				fire();
			}
		});
		provider.addLocalizationListener(listener);
		connected = true;
	}

	@Override
	public String getString(String key) {
		return provider.getString(key);
	}

	@Override
	public String getLanguage() {
		return provider.getLanguage();
	}

}
