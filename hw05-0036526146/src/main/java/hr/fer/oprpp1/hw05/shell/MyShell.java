package hr.fer.oprpp1.hw05.shell;

import java.util.Collections;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;

import hr.fer.oprpp1.hw05.shell.commands.CatShellCommand;
import hr.fer.oprpp1.hw05.shell.commands.CharsetsShellCommand;
import hr.fer.oprpp1.hw05.shell.commands.CopyShellCommand;
import hr.fer.oprpp1.hw05.shell.commands.ExitShellCommand;
import hr.fer.oprpp1.hw05.shell.commands.HelpShellCommand;
import hr.fer.oprpp1.hw05.shell.commands.HexdumpShellCommand;
import hr.fer.oprpp1.hw05.shell.commands.LsShellCommand;
import hr.fer.oprpp1.hw05.shell.commands.MkdirShellCommand;
import hr.fer.oprpp1.hw05.shell.commands.SymbolShellCommand;
import hr.fer.oprpp1.hw05.shell.commands.TreeShellCommand;

/**
 * Class represents shell implementation
 *
 */
public class MyShell implements Environment {
	
	private Character PROMPTSYMBOL;
	
	private Character MORELINESSYMBOL;
	
	private Character MULTILINESYMBOL;
	
	private ShellStatus shellStatus;
		
	private final SortedMap<String, ShellCommand> commands;
	
	private Scanner sc;
	
	/**
	 * Consrtuctor
	 * @param sc Scanner
	 */
	public MyShell(Scanner sc) {
		this.PROMPTSYMBOL = '>';
		this.MORELINESSYMBOL = '\\';
		this.MULTILINESYMBOL = '|';
		
		this.shellStatus = ShellStatus.CONTINUE;
		
		this.commands = new TreeMap<String, ShellCommand>();
		this.commands.put("charsets", new CharsetsShellCommand());
		this.commands.put("cat", new CatShellCommand());
		this.commands.put("ls", new LsShellCommand());		
		this.commands.put("tree", new TreeShellCommand());
		this.commands.put("copy", new CopyShellCommand());
		this.commands.put("mkdir", new MkdirShellCommand());
		this.commands.put("hexdump", new HexdumpShellCommand());
		this.commands.put("help", new HelpShellCommand());
		this.commands.put("symbol", new SymbolShellCommand());
		this.commands.put("exit", new ExitShellCommand());
		
		this.sc = sc;
	}
	
	
	@Override
	public String readLine() throws ShellIOException {
		try {
			String lines = "";
			while(true) {
				String line = sc.nextLine().strip();
				
				if (line.endsWith(getMorelinesSymbol().toString())) {
					lines += line.substring(0, line.length()-1);
					write(getMultilineSymbol().toString() + " ");
				} else {
					lines += line.substring(0, line.length());
					return lines;
				}
								
			}
			
		} catch (Exception e) {
			throw new ShellIOException(e.getMessage());
		}
	}

	@Override
	public void write(String text) throws ShellIOException {
		try  {
			if (text == null) throw new NullPointerException("Text can't be null");
			System.out.print(text);
		} catch (Exception e) {
			throw new ShellIOException(e.getMessage());
		}
	}

	@Override
	public void writeln(String text) throws ShellIOException {
		write(text + '\n');
	}

	@Override
	public SortedMap<String, ShellCommand> commands() {
		return Collections.unmodifiableSortedMap(commands);
	}

	@Override
	public Character getMultilineSymbol() {
		return this.MULTILINESYMBOL;
	}

	@Override
	public void setMultilineSymbol(Character symbol) {
		if (symbol == null) throw new NullPointerException("Symbol can't be null!");
		this.MULTILINESYMBOL = symbol;
	}

	@Override
	public Character getPromptSymbol() {
		return this.PROMPTSYMBOL;
	}

	@Override
	public void setPromptSymbol(Character symbol) {
		if (symbol == null) throw new NullPointerException("Symbol can't be null!");
		this.PROMPTSYMBOL = symbol;		
	}

	@Override
	public Character getMorelinesSymbol() {
		return this.MORELINESSYMBOL;
	}

	@Override
	public void setMorelinesSymbol(Character symbol) {
		if (symbol == null) throw new NullPointerException("Symbol can't be null!");
		this.MORELINESSYMBOL = symbol;
	}
	
	/**
	 * Main method
	 * @param args
	 */
	public static void main(String[] args) {
		
		try {
			Scanner sc = new Scanner(System.in);
			
			MyShell myShell = new MyShell(sc);
			myShell.writeln("Welcome to MyShell v 1.0");
			
			while (myShell.shellStatus != ShellStatus.TERMINATE) {
				try {
					myShell.write(myShell.getPromptSymbol().toString() + " ");
					String[] input = myShell.readLine().strip().split(" ", 2);
					String commandName = input[0];
					
					String arguments;
					if (input.length == 1)
						arguments = "";
					else
						arguments = input[1];

					ShellCommand shellCommand;
					if (myShell.commands.containsKey(commandName)) {
						shellCommand = myShell.commands.get(commandName);
					} else {
						myShell.writeln("Invalid command!");
						continue;
					}
					myShell.shellStatus = shellCommand.executeCommand(myShell, arguments);
					
					
				} catch (ShellIOException e) {
					return;
					
				} catch (Exception e) {
					myShell.writeln(e.getClass() + ": " + e.getMessage());
				}
			
			}	
			
			sc.close();
		
		} catch (Exception e) {
			// TODO: handle exception
		}	
		
		
	}

}
