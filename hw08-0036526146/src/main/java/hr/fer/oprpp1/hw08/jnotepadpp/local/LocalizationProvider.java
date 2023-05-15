package hr.fer.oprpp1.hw08.jnotepadpp.local;

import java.util.Locale;
import java.util.ResourceBundle;

public class LocalizationProvider extends AbstractLocalizationProvider{
	
	private String language;
	
	private ResourceBundle bundle;
	
	private static LocalizationProvider instance = new LocalizationProvider();
	
	public LocalizationProvider() {
		this.language = "en";
		Locale locale = Locale.forLanguageTag(language);
		bundle = ResourceBundle.getBundle("hr.fer.oprpp1.hw08.jnotepadpp.local.prijevodi", locale);
	}

	public static LocalizationProvider getInstance() {
		return instance;
	}
	
	public void setLanguage(String language) {
		this.language = language;
		Locale locale = Locale.forLanguageTag(language);
		this.bundle = ResourceBundle.getBundle("hr.fer.oprpp1.hw08.jnotepadpp.local.prijevodi", locale);
		this.fire();
	}

	@Override
	public String getString(String s) {
		return bundle.getString(s);
		
	}

	@Override
	public String getCurrentLanguage() {
		return this.language;
	}
}
