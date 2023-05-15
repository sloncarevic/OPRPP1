package hr.fer.oprpp1.hw08.jnotepadpp.local;

public class LocalizationProviderBridge extends AbstractLocalizationProvider{

	private boolean connected;
	
	private LocalizationProvider parent;
	
	private ILocalizationListener listener;
	
	private String cachedLang;
	
	public LocalizationProviderBridge(LocalizationProvider parent) {
		this.parent = parent;
		listener = () -> this.fire();
	}
	
	public void disconnect() {
		parent.removeLocalizationListener(listener);
		connected = false;
		this.cachedLang = this.parent.getCurrentLanguage();
		
	}
	
	public void connect() {
		if (!this.connected) {
			parent.addLocalizationListener(listener);
			connected = true;
			if (this.cachedLang != null) {
				fire();
				this.cachedLang = this.parent.getCurrentLanguage();
			}
				
		}

	}

	@Override
	public String getString(String s) {
		return parent.getString(s);
	}

	@Override
	public String getCurrentLanguage() {
		return parent.getCurrentLanguage();
	}
	

	
}
