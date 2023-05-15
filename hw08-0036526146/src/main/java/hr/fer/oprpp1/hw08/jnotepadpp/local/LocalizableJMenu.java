package hr.fer.oprpp1.hw08.jnotepadpp.local;

import javax.swing.JMenu;

public class LocalizableJMenu extends JMenu {

	private static final long serialVersionUID = 1L;

	private ILocalizationProvider provider;
	
	private ILocalizationListener listener;
	
	private String key;
	
	public LocalizableJMenu(ILocalizationProvider provider, String key) {
		this.provider = provider;
		this.key = key;
		this.listener = () -> this.setLocalText();
		setLocalText();
		provider.addLocalizationListener(listener);
	}

	
	private void setLocalText() {
		this.setText(provider.getString(key));

	}
	
	
}
