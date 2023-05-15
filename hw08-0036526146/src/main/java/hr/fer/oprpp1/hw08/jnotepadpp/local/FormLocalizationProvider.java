package hr.fer.oprpp1.hw08.jnotepadpp.local;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

public class FormLocalizationProvider extends LocalizationProviderBridge{

	public FormLocalizationProvider(LocalizationProvider parent, JFrame jFrame) {
		super(parent);
		jFrame.addWindowListener(new WindowAdapter() {
		@Override
		public void windowOpened(WindowEvent e) {
			super.windowOpened(e);
			connect();
		}
		@Override
			public void windowClosed(WindowEvent e) {
				super.windowClosed(e);
				disconnect();
			}
		});
	}

}
