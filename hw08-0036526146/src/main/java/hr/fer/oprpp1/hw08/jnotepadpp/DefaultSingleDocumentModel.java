package hr.fer.oprpp1.hw08.jnotepadpp;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class DefaultSingleDocumentModel implements SingleDocumentModel {
	
	private Path filePath;
	
	@SuppressWarnings("unused")
	private String textContent;
	
	private JTextArea jTextArea;
	
	private List<SingleDocumentListener> listeners = new ArrayList<SingleDocumentListener>();
	
	private boolean modified;
	
	/**
	 * Constructor
	 * @param filePath
	 * @param textContent
	 */
	public DefaultSingleDocumentModel(Path filePath, String textContent) {
		if (filePath == null) 
			this.filePath = null;
		else
			this.filePath = filePath.toAbsolutePath();
		
		this.textContent = "";
		if (textContent != null)
			this.textContent = textContent;
		
		this.jTextArea = new JTextArea(textContent);
		
		this.jTextArea.getDocument().addDocumentListener(
				new DocumentListener() {
					
					@Override
					public void removeUpdate(DocumentEvent e) {
						//setModified(true);
						setModified(!textContent.equals(jTextArea.getText()));
					}
					
					@Override
					public void insertUpdate(DocumentEvent e) {
						//setModified(true);
						setModified(!textContent.equals(jTextArea.getText()));
						
					}
					
					@Override
					public void changedUpdate(DocumentEvent e) {
						//setModified(true);
						setModified(!textContent.equals(jTextArea.getText()));
						
					}
				});
		
	}

	@Override
	public JTextArea getTextComponent() {
		return this.jTextArea;
	}

	@Override
	public Path getFilePath() {
		return this.filePath;
	}

	@Override
	public void setFilePath(Path path) {
		if (path == null) throw new NullPointerException("Path can't be null!");
		this.filePath = path.toAbsolutePath();
		this.notifyListenersFilePath();
		
	}

	@Override
	public boolean isModified() {
		return this.modified;
	}

	@Override
	public void setModified(boolean modified) {
		this.modified = modified;
		this.notifyListenersModifyStatus();
	}

	@Override
	public void addSingleDocumentListener(SingleDocumentListener l) {
		if (l == null) throw new NullPointerException("Document listener can't be null!");
		this.listeners.add(l);
	}

	@Override
	public void removeSingleDocumentListener(SingleDocumentListener l) {
		this.listeners.remove(l);
		
	}
	
	private void notifyListenersFilePath() {
		this.listeners.forEach(l -> l.documentFilePathUpdated(this));
	}

	private void notifyListenersModifyStatus() {
		this.listeners.forEach(l -> l.documentModifyStatusUpdated(this));
	}
}
