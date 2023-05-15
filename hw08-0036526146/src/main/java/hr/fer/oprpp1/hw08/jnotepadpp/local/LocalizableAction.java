package hr.fer.oprpp1.hw08.jnotepadpp.local;

import javax.swing.AbstractAction;
import javax.swing.Action;

public abstract class LocalizableAction extends AbstractAction {
	
	private static final long serialVersionUID = 1L;

	private ILocalizationListener listener;
	
	private ILocalizationProvider provider;
	
	private String key;
	
	public LocalizableAction(ILocalizationProvider provider, String key) {
		this.provider = provider;
		this.key = key;
		this.listener = () -> this.putValues();
		this.putValues();
		provider.addLocalizationListener(listener);
	}
	
	
	private void putValues(){
		this.putValue(NAME, provider.getString(key));

		this.putValue(Action.SHORT_DESCRIPTION, provider.getString(key + "description"));
	}
}
