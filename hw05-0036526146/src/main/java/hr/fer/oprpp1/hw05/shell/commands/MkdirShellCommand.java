package hr.fer.oprpp1.hw05.shell.commands;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

/**
 * Class implementation for creating a directory
 *
 */
public class MkdirShellCommand implements ShellCommand{
	
	private static final String COMMAND_NAME = "mkdir";
	
	private static final List<String> DESCRIPTION = List.of("Creates directory",  "Takes a single argument");


	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if (arguments == null) throw new NullPointerException("Arguments can't be null!");
		
		arguments = arguments.replace("\\\"", "");
		arguments = arguments.replace("\\\\", "\\");
		String[] argumentsArr = arguments.split(" ", 1);
		
		if (argumentsArr.length != 1 || argumentsArr[0].isBlank()) {
			env.writeln("Command mkdir takes one argument!");
			return ShellStatus.CONTINUE;
		}
		
		Path path;
		
		try {
			path = Paths.get(argumentsArr[0]);
		} catch (InvalidPathException e) {
			env.writeln(e.getMessage());
			return ShellStatus.CONTINUE;
		}
		
		if(path == null) {
			return ShellStatus.CONTINUE;
		}
		
		try {
			Files.createDirectories(path);
		} catch (IOException e) {
			env.writeln(e.getMessage());
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
