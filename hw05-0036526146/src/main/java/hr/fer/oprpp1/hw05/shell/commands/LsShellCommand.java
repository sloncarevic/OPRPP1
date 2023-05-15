package hr.fer.oprpp1.hw05.shell.commands;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

/**
 * Class implementation for writing a directory listing (not recursive) to console
 *
 */
public class LsShellCommand implements ShellCommand {

	private static final String COMMAND_NAME = "ls";
	
	private static final List<String> DESCRIPTION = List.of("Writes a directory listing (not recursive)",  "Takes a single argument");
	
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if (arguments == null) throw new NullPointerException("Arguments can't be null!");
		
		arguments = arguments.replace("\\\"", "");
		arguments = arguments.replace("\\\\", "\\");
		String[] argumentsArr = arguments.split(" ", 1);
		
		if (argumentsArr.length != 1 || argumentsArr[0].isBlank()) {
			env.writeln("Command ls takes a single argument");
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
		
		if (!Files.exists(path) || !Files.isDirectory(path)) {
			env.writeln("Invalid path");
			return ShellStatus.CONTINUE;
		}
		
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Stream<Path> s = Files.list(path);
			List<Path> l = s.collect(Collectors.toList());
			s.close();
			for (var file : l) {
				BasicFileAttributeView faView = Files.getFileAttributeView(file,
						BasicFileAttributeView.class, LinkOption.NOFOLLOW_LINKS);
				BasicFileAttributes attributes = faView.readAttributes();
				FileTime fileTime = attributes.creationTime();
				String formattedDateTime = sdf.format(new Date(fileTime.toMillis()));
				
				String formatedString = String.format("%s%s%s%s %10d %s %s", attributes.isDirectory() ? "d" : "-", 
						Files.isReadable(file) ? "r" : "-", Files.isWritable(file) ? "w" : "-", 
						Files.isExecutable(file) ? "x" : "-",
						attributes.size(), formattedDateTime, file.getFileName().toString());
				
				if(formatedString == null) return ShellStatus.CONTINUE;
				env.writeln(formatedString);
				
			}
			
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
