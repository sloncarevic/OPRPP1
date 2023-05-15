package hr.fer.oprpp1.hw05.shell.commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellIOException;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

/**
 * Class implementation for opening given file and writing its content to console
 *
 */
public class CatShellCommand implements ShellCommand {
	
	private static final String COMMAND_NAME = "cat";
	
	private static final List<String> DESCRIPTION = List.of("Opens given file and writes its content to console", "Takes one or two arguments");

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if (arguments == null) throw new NullPointerException("Arguments can't be null!");
		
		char[] charArr = arguments.toCharArray();
		int open = 0;
		int pos = -1;
		for (int i = 0; i < charArr.length; i++) {
			if (charArr[i] == '\"') {
				open++;
			}
			if (charArr[i] == ' ' && open % 2 == 0) {
				pos = i;
				break;
			}
		}
		
		String[] argumentsArr = new String[1];
		if (open == 0 && pos == -1)
			argumentsArr[0] = arguments.replace("\\\\", "\\");
		else if(pos == -1) {
			argumentsArr[0] = arguments.replace("\\\"", "").replace("\\\\", "\\");
		}
		else {
			argumentsArr = new String[2];
			argumentsArr[0] = arguments.substring(0, pos).replace("\\\"", "").replace("\\\\", "\\");
			argumentsArr[1] = arguments.substring(pos+1).replace("\\\"", "").replace("\\\\", "\\");
		}
		
		if(argumentsArr.length != 1 && argumentsArr.length != 2 || argumentsArr[0].isEmpty()) {
			env.writeln("Command cat takes one or two arguments");
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
		
		if (!Files.exists(path) || !Files.isRegularFile(path)) {
			env.writeln("Invalid path");
			return ShellStatus.CONTINUE;
		}
		
		Charset charset;
		
		if (argumentsArr.length == 1) {
			charset = Charset.defaultCharset();
		}
		else {
			try {
				charset = Charset.forName(argumentsArr[1]);
			} catch (IllegalArgumentException e) {
				env.writeln("Invalid charset name");
				return ShellStatus.CONTINUE;
			}
		}
		
		try {
			BufferedReader br = Files.newBufferedReader(path, charset);
			String line = br.readLine();
			while (line != null) {
				env.writeln(line);
				line = br.readLine();
			}
		} catch (IOException e) {
			throw new ShellIOException(e.getMessage());
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
