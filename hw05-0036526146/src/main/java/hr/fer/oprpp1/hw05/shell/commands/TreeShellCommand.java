package hr.fer.oprpp1.hw05.shell.commands;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Collections;
import java.util.List;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

/**
 * Class implementation for printing a tree of directories to console
 *
 */
public class TreeShellCommand implements ShellCommand {
	
	private static final String COMMAND_NAME = "tree";
	
	private static final List<String> DESCRIPTION = List.of("Prints a tree (each directory level shifts output two charatcers to the right)",  "Takes a single argument");

	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if (arguments == null) throw new NullPointerException("Arguments can't be null!");
		
		//String[] argumentsArr = arguments.split(" ");
		arguments = arguments.replace("\\\"", "");
		arguments = arguments.replace("\\\\", "\\");
		String[] argumentsArr = arguments.split(" ", 1);
		
		if (argumentsArr.length != 1 || argumentsArr[0].isBlank()) {
			env.writeln("Command tree takes a single argument");
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
			Files.walkFileTree(path, new MySimpleFileVisitor(env));
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

	
	private static class MySimpleFileVisitor extends SimpleFileVisitor<Path>{

		private int shift;
		
		private Environment env;
		
		public MySimpleFileVisitor(Environment env) {
			this.shift = 0;
			this.env = env;
		}
		
		@Override
		public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
			String s = String.format("%s%s", " ".repeat(shift*2), dir.getFileName().toString());
			env.writeln(s);
			
			shift++;
			
			return FileVisitResult.CONTINUE;
		}
		
		@Override
		public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
			String s = String.format("%s%s", " ".repeat(shift*2), file.getFileName().toString());
			env.writeln(s);
		
			
			return FileVisitResult.CONTINUE;
		}
		
		@Override
		public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {		
			shift--;
			
			if (exc != null) throw new IOException(exc.getMessage());
			
			return FileVisitResult.CONTINUE;
		}
		
		
	}
	
	
}
