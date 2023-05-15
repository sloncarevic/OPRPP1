package hr.fer.oprpp1.hw05.shell.commands;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
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
 * Class implementation for copying source file to destination
 *
 */
public class CopyShellCommand implements ShellCommand{

	private static final String COMMAND_NAME = "copy";
	
	private static final List<String> DESCRIPTION = List.of("Copies the file to destination",  "Takes two arguments");

	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if (arguments == null) throw new NullPointerException("Arguments can't be null!");
		
		//String[] argumentsArr = arguments.split(" ");
		
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
		
		
		if (argumentsArr.length != 2 || argumentsArr[0].isBlank() || argumentsArr[1].isBlank()) {
			env.writeln("Command copy takes two arguments");
			return ShellStatus.CONTINUE;
		}
		
		Path pathSrc;
		
		try {
			pathSrc = Paths.get(argumentsArr[0]);
		} catch (InvalidPathException e) {
			env.writeln(e.getMessage());
			return ShellStatus.CONTINUE;
		}
		
		if(pathSrc == null) {
			return ShellStatus.CONTINUE;
		}
		
		if (!Files.exists(pathSrc) || !Files.isRegularFile(pathSrc)) {
			env.writeln("Invalid path");
			return ShellStatus.CONTINUE;
		}
		
		Path pathDst;
		
		try {
			pathDst = Paths.get(argumentsArr[1]);
		} catch (InvalidPathException e) {
			env.writeln(e.getMessage());
			return ShellStatus.CONTINUE;
		}
		
		if(pathDst == null) {
			return ShellStatus.CONTINUE;
		}
		
		if(Files.isDirectory(pathDst)) {
			pathDst = Paths.get(argumentsArr[1] + "/" + pathSrc.getFileName().toString());
		}
		
		if (Files.exists(pathDst)) {
			env.writeln("Overwrite existing file? Enter yes or no: ");
			if (env.readLine().strip().toLowerCase().equals("yes")) {
				try {
					copy(pathSrc, pathDst);
				} catch (IOException e) {
					env.writeln(e.getMessage());
				}
				return ShellStatus.CONTINUE;
				
			} else {
				return ShellStatus.CONTINUE;
			}
		}
		try {
			copy(pathSrc, pathDst);
		} catch (IOException e) {
			env.writeln(e.getMessage());
		}
		
		return ShellStatus.CONTINUE;
	}
	
	private void copy(Path pathSrc, Path pathDst) throws IOException {
		try {
			BufferedInputStream is = new BufferedInputStream(Files.newInputStream(pathSrc));
			BufferedOutputStream os = new BufferedOutputStream(Files.newOutputStream(pathDst));
			
			byte[] buffer = new byte[4096];
			
			while(true) {
				int numOfBytes = is.read(buffer);
				if (numOfBytes == -1) break;
				
				os.write(buffer, 0, numOfBytes);
			}
			
			is.close();
			os.close();
		} catch (IOException e) {
			throw e;
		}
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
