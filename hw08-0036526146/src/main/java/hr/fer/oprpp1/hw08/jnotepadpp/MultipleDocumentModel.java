package hr.fer.oprpp1.hw08.jnotepadpp;

import java.nio.file.Path;

import javax.swing.JComponent;

/**
 * MultipleDocumentModel represents a model capable of holding zero, one or more documents, 
 * where each document and having a concept of current document – the one which is shown to the 
 * user and on which user works
 */
public interface MultipleDocumentModel extends Iterable<SingleDocumentModel> {
	
	/**Getter
	 * @return visual component
	 */
	JComponent getVisualComponent();
	
	/**New document creator
	 * @return SingleDocumentModel document
	 */
	SingleDocumentModel createNewDocument();
	
	/**Getter
	 * @return SingleDocumentModel document
	 */
	SingleDocumentModel getCurrentDocument();
	
	/**Document loader
	 * @param path
	 * @return SingleDocumentModel document from path
	 */
	SingleDocumentModel loadDocument(Path path);
	
	/** Save document to path
	 * @param model
	 * @param newPath
	 */
	void saveDocument(SingleDocumentModel model, Path newPath);
	
	/**Close document
	 * @param model
	 */
	void closeDocument(SingleDocumentModel model);
	
	/**Add listener
	 * @param l
	 */
	void addMultipleDocumentListener(MultipleDocumentListener l);
	
	/**Remove listener
	 * @param l
	 */
	void removeMultipleDocumentListener(MultipleDocumentListener l);
	
	/**Getter
	 * @return number of documents
	 */
	int getNumberOfDocuments();
	
	/** Getter
	 * @param index
	 * @return SingleDocumentModel document
	 */
	SingleDocumentModel getDocument(int index);
	
	/** Find by path
	 * @param path
	 * @return SingleDocumentModel document
	 */
	SingleDocumentModel findForPath(Path path); //null, if no such model exists

	/** getter
	 * @param doc
	 * @return int index of document
	 */
	int getIndexOfDocument(SingleDocumentModel doc); //-1 if not present

	
}
