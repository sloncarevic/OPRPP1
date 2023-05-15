package hr.fer.oprpp1.hw05.shell.commands;

import java.io.BufferedInputStream;
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
 * Class implementation for creating hex-output of given file
 *
 */
public class HexdumpShellCommand implements ShellCommand {
	
	private static final String COMMAND_NAME = "hexdump";
	
	private static final List<String> DESCRIPTION = List.of("Produces hex-output",  "Takes a single argument");

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
	
		if (arguments == null) throw new NullPointerException("Arguments can't be null!");
		
		String[] argumentsArr = arguments.split(" ");
		
		if (argumentsArr.length != 1 || argumentsArr[0].isBlank()) {
			env.writeln("Command hexdump takes one argument!");
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
		
		try {
			BufferedInputStream is = new BufferedInputStream(Files.newInputStream(path));
			int buffersize = 16;
			byte[] buffer = new byte[buffersize];
			
			int row = 0;
			
			while(true) {
				
				int numOfBytes = is.read(buffer);
				if (numOfBytes == -1) break;
				
				String first = String.format("%s%s:", "0".repeat(8 - Integer.toHexString(row * buffersize).length()), Integer.toHexString(row * buffersize));
				String second = "";
				String third = "";
				String fourth = "";
				
				for (int i = 0; i < buffersize/2; i++) {
					if (buffer[i] == 0) {
						second += "   ";
						fourth += "";
					} else {
					second += String.format(" %02x", buffer[i]);
					if (buffer[i] >= 32 && buffer[i] <= 127)
						fourth += String.format("%s", String.valueOf((char)buffer[i]));
					else
						fourth += String.format("%s", ".");
					}
				}
				
				for (int i = buffersize/2; i < buffersize; i++) {
					if (buffer[i] == 0) {
						third += "   ";
						fourth += "";
					} else {
					third += String.format(" %02x", buffer[i]);
					if (buffer[i] >= 32 && buffer[i] <= 127)
						fourth += String.format("%s", String.valueOf((char)buffer[i]));
					else
						fourth += String.format("%s", ".");
					}
				}
				
				row++;
				for(int i = 0; i < buffersize; i++)
					buffer[i] = 0;
				
				env.writeln(String.format("%s%s|%s | %s", first, second, third, fourth));			
				
			}

			is.close();
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
