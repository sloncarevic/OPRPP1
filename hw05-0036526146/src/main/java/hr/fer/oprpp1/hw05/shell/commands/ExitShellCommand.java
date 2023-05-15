package hr.fer.oprpp1.hw05.shell.commands;

import java.util.Collections;
import java.util.List;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

/**
 * Class implementation for exiting shell environment
 *
 */
public class ExitShellCommand implements ShellCommand {
	
	private static final String COMMAND_NAME = "exit";
	
	private static final List<String> DESCRIPTION = List.of("Terminates shell");

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if (arguments == null) throw new NullPointerException("Arguments can't be null!");
		
		if (!arguments.isEmpty()) {
			env.writeln("Command exit takes no arguments!");
			return ShellStatus.CONTINUE;
		}
		
		return ShellStatus.TERMINATE;		
	}

	@Override
	public String getCommandName() {
		return COMMAND_NAME;
	}

	@Override
	public List<String> getCommandDescription() {
		return Collections.unmodifiableList(DESCRIPTION);
	}

}
