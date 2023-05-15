package hr.fer.oprpp1.hw05.shell.commands;

import java.nio.charset.Charset;
import java.util.Collections;
import java.util.List;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

/**
 * Class implementation for listing names of supported charsets for Java platform
 *
 */
public class CharsetsShellCommand implements ShellCommand{
	
	private static final String COMMAND_NAME = "charsets";
	
	private static final List<String> DESCRIPTION = List.of("Lists names of supported charsets for your Java platform");

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if (arguments == null) throw new NullPointerException("Arguments can't be null!");
		
		if (!arguments.isEmpty()) {
			env.writeln("Command charsets takes no arguments!");
			return ShellStatus.CONTINUE;
		}
		
		for (var k : Charset.availableCharsets().keySet()) {
			env.writeln(k);
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
