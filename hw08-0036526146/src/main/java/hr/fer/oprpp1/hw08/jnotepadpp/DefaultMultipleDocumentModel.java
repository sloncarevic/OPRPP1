package hr.fer.oprpp1.hw08.jnotepadpp;

import java.awt.BorderLayout;
import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import hr.fer.oprpp1.hw08.jnotepadpp.local.FormLocalizationProvider;

public class DefaultMultipleDocumentModel extends JTabbedPane implements MultipleDocumentModel {

	private static final long serialVersionUID = 1L;
	
	private List<SingleDocumentModel> documents = new ArrayList<SingleDocumentModel>();
	
	private SingleDocumentModel currentDocument;
	
	private List<MultipleDocumentListener> listeners = new ArrayList<MultipleDocumentListener>();
	
	private String redIconPath = "./icons/redDisk.png";
	private String blueIconPath = "./icons/blueDisk.png";
	
	private FormLocalizationProvider flp;
	
	/**Constructor
	 * @param flp
	 */
	public DefaultMultipleDocumentModel(FormLocalizationProvider flp) {
		super();
		this.currentDocument = null;
		
		this.flp = flp;
		
		this.addChangeListener( new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				SingleDocumentModel oldDocumentModel = currentDocument;
				if (getSelectedIndex() == -1)
					currentDocument = null;
				else
					currentDocument = documents.get(getSelectedIndex());
				
				notifyListenersDocumentChanged(oldDocumentModel);
			}
		});
		
	}

	@Override
	public Iterator<SingleDocumentModel> iterator() {
		return this.documents.iterator();
	}

	@Override
	public JComponent getVisualComponent() {
		return this;
	}

	@Override
	public SingleDocumentModel createNewDocument() {
		SingleDocumentModel previous = this.currentDocument;
		
		this.currentDocument = new DefaultSingleDocumentModel(null, "");
		
		this.addDocument(this.currentDocument);
		
		this.notifyListenersDocumentAdded(this.currentDocument);
		
		this.notifyListenersDocumentChanged(previous);
		
		return this.currentDocument;
	}

	@Override
	public SingleDocumentModel getCurrentDocument() {
		return this.currentDocument;
	}

	@Override
	public SingleDocumentModel loadDocument(Path path) {
		if (path == null) throw new NullPointerException("Path can't be null!");
		
		for (var document : documents) {
			if (document.getFilePath() != null && document.getFilePath().equals(path)) {
				SingleDocumentModel oldDocumentModel = this.currentDocument;
				this.currentDocument = document;
				this.notifyListenersDocumentChanged(oldDocumentModel);
				return document;
			}
		}
		
		byte[] documentContent;
		try {
			documentContent = Files.readAllBytes(path);
			
			SingleDocumentModel previousDocumentModel = this.currentDocument;
			
			this.currentDocument = new DefaultSingleDocumentModel(path, new String(documentContent, StandardCharsets.UTF_8));
			
			this.addDocument(this.currentDocument);
			//add ??
			this.notifyListenersDocumentAdded(currentDocument);
			
			this.notifyListenersDocumentChanged(previousDocumentModel);
			
			return this.currentDocument;
		
		} catch (IOException e) {
			System.err.println("Can't read file!");
			return null;
		}
				
	}

	private void addDocument(SingleDocumentModel documentModel) {
		this.documents.add(documentModel);
		
		documentModel.addSingleDocumentListener(new SingleDocumentListener() {
			
			@Override
			public void documentModifyStatusUpdated(SingleDocumentModel model) {
				changeIcon(model);
			}
			
			@Override
			public void documentFilePathUpdated(SingleDocumentModel model) {
				changePath(model);
			}
		});
		
		//this.notifyListenersDocumentAdded(currentDocument);
		//this.notifyListenersDocumentChanged(currentDocument);
		
		this.addDocumentTab(documentModel);
		
	}
	
	private void addDocumentTab(SingleDocumentModel documentModel) {
		JPanel jpanel = new JPanel();
		jpanel.setLayout(new BorderLayout());
		jpanel.add(new JScrollPane(documentModel.getTextComponent()), BorderLayout.CENTER);
		
		if (documentModel.getFilePath() != null) {
			this.addTab(documentModel.getFilePath().getFileName().toString(), 
				getIcon(blueIconPath), 
				jpanel,
				documentModel.getFilePath().toAbsolutePath().toString());
		} else {
			this.addTab(flp.getString("unnamed"), 
					getIcon(blueIconPath), 
					jpanel,
					flp.getString("unnamed"));
		}
		
		setSelectedComponent(jpanel);
		
		//TODO set caret
	}
	
	private void changeIcon(SingleDocumentModel documentModel) {
		if (documentModel.isModified()) {
			setIconAt(documents.indexOf(documentModel), getIcon(redIconPath));
		} else {
			setIconAt(documents.indexOf(documentModel), getIcon(blueIconPath));
		}
	}
	
	private void changePath(SingleDocumentModel documentModel) {
		setTitleAt(documents.indexOf(documentModel), documentModel.getFilePath().getFileName().toString());
		//setToolTipText();
	}
	
	private ImageIcon getIcon(String iconPath) {
		try {
			InputStream is = this.getClass().getResourceAsStream(iconPath);
			if (is == null) {
				throw new NullPointerException("Input stream is null!");
			} else {
				Image image = new ImageIcon(is.readAllBytes()).getImage();
				
				ImageIcon imageIcon = new ImageIcon(image.getScaledInstance(20, 20, Image.SCALE_SMOOTH));
				
				return imageIcon;
			}
		} catch (IOException e) {
			System.err.println("Can't read icon!");
			return null;
		}
	}
	

	@Override
	public void saveDocument(SingleDocumentModel model, Path newPath) {
		if (model == null) throw new NullPointerException("Document model can't be null!");
		
		if (newPath == null) {
			newPath = model.getFilePath();
		} else {
			model.setFilePath(newPath);
		}
		
		try {
			byte[] content = model.getTextComponent().getText().getBytes(StandardCharsets.UTF_8);
			
			Files.write(newPath, content);
			
		} catch (IOException e) {
			System.err.println("Can't write to file!");
			return;
		}
		
		model.setModified(false);
		this.notifyListenersDocumentChanged(model); //??
		//TODO setIconAt ???
	}

	@Override
	public void closeDocument(SingleDocumentModel model) {
		int modelindex = documents.indexOf(model);
		int index = documents.indexOf(model) - 1;
		if (index < 0) index = 0;
		if (this.documents.remove(model)) {
			
			this.notifyListenersDocumentRemoved(model);
			
			removeTabAt(modelindex);
			
			if (this.documents.isEmpty())
				this.currentDocument = null;
			else
				this.currentDocument = this.documents.get(index);
		}
		
		if (this.getTabCount() != 0)
			this.setSelectedIndex(index);
		
	}

	@Override
	public void addMultipleDocumentListener(MultipleDocumentListener l) {
		if (l == null) throw new NullPointerException("Listener can't be null!");
		this.listeners.add(l);		
	}

	@Override
	public void removeMultipleDocumentListener(MultipleDocumentListener l) {
		this.listeners.remove(l);
	}

	@Override
	public int getNumberOfDocuments() {
		return this.documents.size();
	}

	@Override
	public SingleDocumentModel getDocument(int index) {
		return this.documents.get(index);
	}

	@Override
	public SingleDocumentModel findForPath(Path path) {
		if (path == null) throw new NullPointerException("Path must not be null!");
		for (var document : this.documents) {
			if (document.getFilePath().equals(path))
				return document;
		}
		return null;
	}

	@Override
	public int getIndexOfDocument(SingleDocumentModel doc) {
		return this.documents.indexOf(this.currentDocument);
	}

	private void notifyListenersDocumentChanged(SingleDocumentModel old) {
		this.listeners.forEach(l -> l.currentDocumentChanged(old, this.currentDocument));
	}
	
	private void notifyListenersDocumentRemoved(SingleDocumentModel old) {
		this.listeners.forEach(l -> l.documentRemoved(old));
	}
	
	private void notifyListenersDocumentAdded(SingleDocumentModel documentModel) {
		this.listeners.forEach(l -> l.documentAdded(documentModel));
	}
	
}
