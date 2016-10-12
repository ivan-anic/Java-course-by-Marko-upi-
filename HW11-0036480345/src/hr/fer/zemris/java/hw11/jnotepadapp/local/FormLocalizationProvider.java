package hr.fer.zemris.java.hw11.jnotepadapp.local;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

/**
 * A class derived from {@linkplain LocalizationProviderBridge}; When frame is
 * opened, it calls connect and when frame is closed, it calls disconnect.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class FormLocalizationProvider extends LocalizationProviderBridge {

	/**
	 * Instantiates a new form localization provider. Registeres this object as
	 * a WindowListener to its JFrame.
	 *
	 * @param provider
	 *            the provider
	 * @param frame
	 *            the frame
	 */
	public FormLocalizationProvider(IlocalizationProvider provider, JFrame frame) {
		super(provider);

		frame.addWindowListener(new WindowAdapter() {

			@Override
			public void windowOpened(WindowEvent e) {
				connect();
			}

			@Override
			public void windowClosed(WindowEvent e) {
				disconnect();
			}
		});
	}

}
