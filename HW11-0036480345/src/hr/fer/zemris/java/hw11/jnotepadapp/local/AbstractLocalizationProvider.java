package hr.fer.zemris.java.hw11.jnotepadapp.local;

import java.util.ArrayList;
import java.util.List;

/**
 * Implements {@linkplain IlocalizationProvider} interface and adds the ability
 * to register, de-register and inform ({@link #fire()}) listeners.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public abstract class AbstractLocalizationProvider implements IlocalizationProvider {

	/** Keeps references to the registered listeners. */
	List<IlocalizationListener> listeners;

	/**
	 * Instantiates a new abstract localization provider.
	 */
	public AbstractLocalizationProvider() {
		listeners = new ArrayList<>();
	}

	@Override
	public void addLocalizationListener(IlocalizationListener listener) {
		if (listener == null) {
			throw new IllegalArgumentException("Can't be null!");
		}
		if (!listeners.contains(listener)) {
			listeners.add(listener);
		}
	}

	@Override
	public void removeLocalizationListener(IlocalizationListener listener) {
		if (listener == null) {
			throw new IllegalArgumentException("Can't be null!");
		}
		listeners.remove(listener);
	}

	/**
	 * Informs all registered listeners that a change has occurred.
	 */
	public void fire() {
		listeners.forEach(z -> z.localizationChanged());
	}
}
