package hr.fer.oprpp1.hw08.jnotepadpp;

import java.nio.file.Path;

import javax.swing.JTextArea;

/**
 * SingleDocumentModel represents a model of single document, having information about file path 
 * from which document was loaded (can be null for new document), document modification status 
 * and reference to Swing component which is used for editing (each document has its own editor 
 * component).
 */
public interface SingleDocumentModel {
	
	/**Getter 
	 * @return JTextArea component
	 */
	JTextArea getTextComponent();
	
	/**Getter
	 * @return Path
	 */
	Path getFilePath();
	
	/**Setter
	 * @param path
	 */
	void setFilePath(Path path);
	
	/**
	 * @return boolean if modified
	 */
	boolean isModified();
	
	/**Setter
	 * @param modified
	 */
	void setModified(boolean modified);
	
	/**Add listener
	 * @param l
	 */
	void addSingleDocumentListener(SingleDocumentListener l);
	
	/**Remove listener
	 * @param l
	 */
	void removeSingleDocumentListener(SingleDocumentListener l);

}
