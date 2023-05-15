package hr.fer.oprpp1.hw08.jnotepadpp.local;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractLocalizationProvider implements ILocalizationProvider {
	
	private List<ILocalizationListener> listeners;
	
	public AbstractLocalizationProvider(){
		this.listeners = new ArrayList<ILocalizationListener>();
	}

	@Override
	public void addLocalizationListener(ILocalizationListener listener) {
		listeners.add(listener);
		
	}

	@Override
	public void removeLocalizationListener(ILocalizationListener listener) {
		listeners.remove(listener);
		
	}

	public void fire() {
		for (var l : this.listeners) {
			l.localizationChanged();
		}
	}

}
