package hr.fer.oprpp1.hw08.jnotepadpp.local;

import javax.swing.JToolBar;

public class LocalizableJToolbar extends JToolBar{

	private static final long serialVersionUID = 1L;
	
	private ILocalizationProvider provider;
	
	private ILocalizationListener listener;
	
	private String key;
	
	public LocalizableJToolbar(ILocalizationProvider provider, String key) {
		this.provider = provider;
		this.key = key;
		this.listener = () -> this.setLocalText();
		this.provider.addLocalizationListener(listener);
		this.setLocalText();
	}
	
	private void setLocalText() {
		this.setName(this.provider.getString(key));
	}

}
