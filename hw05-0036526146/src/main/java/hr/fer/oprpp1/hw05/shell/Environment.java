package hr.fer.oprpp1.hw05.shell;

import java.util.SortedMap;

/**
 * Interface represents an abstraction which will be passed to each defined command.
 *
 */
public interface Environment {
	
	 /**
	 * @return Returns user input as String
	 * @throws ShellIOException
	 */
	String readLine() throws ShellIOException;
	 
	 /**
	  * Writes string to console
	 * @param text
	 * @throws ShellIOException
	 */
	void write(String text) throws ShellIOException;
	 
	 /**
	  * Writes string to console
	 * @param text
	 * @throws ShellIOException
	 */
	void writeln(String text) throws ShellIOException;
	 
	 /**
	 * @return Returns map of commands
	 */
	SortedMap<String, ShellCommand> commands();
	 
	 /**
	 * @return Returns multiline symbol
	 */
	Character getMultilineSymbol();
	 
	 /**
	  * Sets multiline symbol
	 * @param symbol
	 */
	void setMultilineSymbol(Character symbol);
	 
	 /**
	 * @return Returns prompt symbol
	 */
	Character getPromptSymbol();
	 
	 /**
	  * Sets prompt symbol
	 * @param symbol
	 */
	void setPromptSymbol(Character symbol);
	 
	 /**
	 * @return Returns morelines symbol
	 */
	Character getMorelinesSymbol();
	 
	 /**
	  * Sets morelines symbol
	 * @param symbol
	 */
	void setMorelinesSymbol(Character symbol);

}
