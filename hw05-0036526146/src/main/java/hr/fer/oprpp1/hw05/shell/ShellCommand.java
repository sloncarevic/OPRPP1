package hr.fer.oprpp1.hw05.shell;

import java.util.List;

/**
 * Interface represents method that command offers
 *
 */
public interface ShellCommand {

	/**
	 * Executes the command
	 * @param env Environment
	 * @param arguments Arguments
	 * @return ShellStatus.CONTIUNUE if execution was succesful, otherwise ShellStatus.TERMINATE
	 */
	ShellStatus executeCommand(Environment env, String arguments);
	
	/**
	 * @return Returns command name
	 */
	String getCommandName();
	
	/**
	 * @return Returns command description
	 */
	List<String> getCommandDescription();
	
}
