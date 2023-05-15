package hr.fer.oprpp1.hw05.shell.commands;

import java.util.Collections;
import java.util.List;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

/**
 * Class implementation for writing help information to console
 *
 */
public class HelpShellCommand implements ShellCommand{
	
	private static final String COMMAND_NAME = "help";
	
	private static final List<String> DESCRIPTION = List.of(" If started with no arguments, it lists names of all supported commands",  "If started with single argument, it prints name and the description of selected command");

	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if (arguments == null) throw new NullPointerException("Arguments can't be null!");
		
		String[] argumentsArr = arguments.split(" ");
		
		if (argumentsArr.length > 1) {
			env.writeln("Command help takes one argument!");
			return ShellStatus.CONTINUE;
		}
		
		if (argumentsArr[0].isBlank()) {
			for (var c : env.commands().keySet()) {
				env.writeln(c);
			}
		} else {
			if(env.commands().containsKey(argumentsArr[0])) {
				env.writeln(argumentsArr[0]);
				for (var i : env.commands().get(argumentsArr[0]).getCommandDescription()) {
					env.writeln("\t" + i);
				}
			} else {
				env.writeln("No such command");
			}
		}
		
		return ShellStatus.CONTINUE;
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
