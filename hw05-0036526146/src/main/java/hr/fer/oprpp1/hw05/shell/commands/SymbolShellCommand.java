package hr.fer.oprpp1.hw05.shell.commands;

import java.util.Collections;
import java.util.List;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

/**
 * Class implementation for checking and changing shell special symbols
 *
 */
public class SymbolShellCommand implements ShellCommand {

	private static final String COMMAND_NAME = "symbol";

	private static final List<String> DESCRIPTION = List.of("Shows or changes special symbol", "Takes one or two arguments");


	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if (arguments == null) throw new NullPointerException("Arguments can't be null!");

		String[] argumentsArr = arguments.split(" ");

		if(argumentsArr.length != 1 && argumentsArr.length != 2 || argumentsArr[0].isEmpty()) {
			env.writeln("Command symbol takes one or two arguments");
			return ShellStatus.CONTINUE;
		}

		if (argumentsArr.length == 1) {
			if (argumentsArr[0].equalsIgnoreCase("prompt")) {
				env.writeln("Symbol for " + argumentsArr[0].toUpperCase() + " is '" + env.getPromptSymbol() + "'");
			}
			else if (argumentsArr[0].equalsIgnoreCase("morelines")) {
				env.writeln("Symbol for " + argumentsArr[0].toUpperCase() + " is '" + env.getMorelinesSymbol() + "'");
			}
			else if (argumentsArr[0].equalsIgnoreCase("multiline")) {
				env.writeln("Symbol for " + argumentsArr[0].toUpperCase() + " is '" + env.getMultilineSymbol() + "'");
			}
			else {
				env.writeln("Invalid argument");
			}
		} else {
			if (argumentsArr[1].length() == 1) {
				if (argumentsArr[0].equalsIgnoreCase("prompt")) {

					char oldSymbol = env.getPromptSymbol();
					env.setPromptSymbol(argumentsArr[1].charAt(0));

					env.writeln("Symbol for " + argumentsArr[0].toUpperCase() + " changed from '" + oldSymbol + "' to '" + env.getPromptSymbol() + "'");
				}
				else if (argumentsArr[0].equalsIgnoreCase("morelines")) {
					char oldSymbol = env.getMorelinesSymbol();
					env.setMorelinesSymbol(argumentsArr[1].charAt(0));

					env.writeln("Symbol for " + argumentsArr[0].toUpperCase() + " changed from '" + oldSymbol + "' to '" + env.getMorelinesSymbol() + "'");
				}
				else if (argumentsArr[0].equalsIgnoreCase("multiline")) {
					char oldSymbol = env.getMultilineSymbol();
					env.setMultilineSymbol(argumentsArr[1].charAt(0));

					env.writeln("Symbol for " + argumentsArr[0].toUpperCase() + " changed from '" + oldSymbol + "' to '" + env.getMultilineSymbol() + "'");
				}
				else {
					env.writeln("Invalid argument");
				}
			} else {
				env.writeln("Invalid argument");
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
