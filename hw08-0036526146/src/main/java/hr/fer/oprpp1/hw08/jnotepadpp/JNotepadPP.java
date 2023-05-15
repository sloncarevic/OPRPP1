package hr.fer.oprpp1.hw08.jnotepadpp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.nio.file.Path;
import java.text.Collator;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Locale;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.WindowConstants;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultEditorKit;

import hr.fer.oprpp1.hw08.jnotepadpp.local.FormLocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LocalizableAction;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LocalizableJMenu;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LocalizableJToolbar;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LocalizationProvider;

/**
 * Class representation of Notepad++ - a simple text file editor
 *
 */
public class JNotepadPP extends JFrame{

	private static final long serialVersionUID = 1L;
	
	private DefaultMultipleDocumentModel multipleDocumentModel;
	
	private String title = "JNotepad++";
	
	private FormLocalizationProvider flp;
	
	private Action createNewBlankDocumentAction;
	private Action openExistingDocumentAction;
	private Action saveDocumentAction;
	private Action saveAsDocumentAction;
	private Action closeDocumentAction;
	private Action cutTextAction;
	private Action copyTextAction;
	private Action pasteTextAction;
	private Action statisticalInfoAction;
	private Action exitApplicationAction;
	private Action languageEnglishAction;
	private Action languageCroatianAction;
	private Action languageGermanAction;
	
	private Action toUpperCaseAction;
	private Action toLowerCaseAction;
	private Action invertCaseAction;
	
	private Action sortAscendingAction;
	private Action sortDescendingAction;
	private Action uniqueAction;
	
	private Timer clock;
	
	private JLabel lenLabel;
	
	private JButton saveButton;
	
	private JLabel textInfoLabel;
	
	/**
	 * Default constructor
	 */
	public JNotepadPP() {
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				exitWindow();
			}
		});
		
		setLocation(20, 20);
		setSize(700, 700);
		setTitle(title);
		
		initGUI();
		
	}
	
	private void exitWindow() {
		for (int i = 0; i < this.multipleDocumentModel.getNumberOfDocuments(); i++) {
			this.multipleDocumentModel.setSelectedIndex(i);
			if (this.multipleDocumentModel.getCurrentDocument() != null &&
					this.multipleDocumentModel.getCurrentDocument().isModified()) {
				String filename = this.flp.getString("unnamed");
				if (this.multipleDocumentModel.getCurrentDocument().getFilePath() != null)
					filename = this.multipleDocumentModel.getCurrentDocument().getFilePath().getFileName().toString();
				int ans = JOptionPane.showOptionDialog(JNotepadPP.this,
						this.flp.getString("document") + " "
						+filename + " "
						+this.flp.getString("unsaved"),
						this.flp.getString("unsavedTitle"), 
						JOptionPane.DEFAULT_OPTION, 
						JOptionPane.QUESTION_MESSAGE, null,
						new String[] {this.flp.getString("save"), this.flp.getString("exit")}, "cancel");
				
				if (ans == 0) {
					saveButton.doClick();
					return;
				} else if (ans == 1) {
					continue;
				} else {
					return;
				}
			}
		}
		this.clock.stop();
		this.dispose();
	}
	
	/**
	 * GUI initialisation
	 */
	private void initGUI() {
		this.flp = new FormLocalizationProvider(LocalizationProvider.getInstance(), this);
		
		this.multipleDocumentModel = new DefaultMultipleDocumentModel(this.flp);

		this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().add(this.multipleDocumentModel.getVisualComponent(), BorderLayout.CENTER);
		
		multipleDocumentModel.createNewDocument();
		
		String path = this.flp.getString("unnamed");
		if (multipleDocumentModel.getCurrentDocument().getFilePath() != null)
			path = multipleDocumentModel.getCurrentDocument().getFilePath().toString();
		
		setTitle(path + " - " + title);
		
		setListener();
		
		createActions();
		
		createMenus();
		
		createToolbars();
		
		createStatusbar();
	}
	
	/**
	 * Status bar creator method
	 */
	private void createStatusbar() {
		JToolBar statusToolBar = new LocalizableJToolbar(this.flp, "statusbar");
		statusToolBar.setLayout(new GridLayout(1,3));
		
		this.lenLabel = new JLabel("length : 0");
		statusToolBar.add(this.lenLabel);
		
		this.textInfoLabel = new JLabel();
		statusToolBar.add(this.textInfoLabel);
		
		JLabel clockLabel = new JLabel();
		clock = new Timer(500, e -> {
			LocalDateTime now = LocalDateTime.now();
			int y = now.getYear();
            int m = now.getMonth().getValue();
            int d = now.getDayOfMonth();

            int h = now.getHour();
            int min = now.getMinute();
            int sec = now.getSecond();
            
            clockLabel.setText(y+"/"+m+"/"+d+" "+h+":"+min+":"+sec);
		});
		this.clock.start();
		
		clockLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		statusToolBar.add(clockLabel);
		
		statusToolBar.setFloatable(true);
		statusToolBar.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.gray));
		
		this.getContentPane().add(statusToolBar, BorderLayout.PAGE_END);
		
		this.multipleDocumentModel.getCurrentDocument().getTextComponent().addCaretListener(new CaretListener() {
			@Override
			public void caretUpdate(CaretEvent e) {
				statusbarChange();	
			}
		});
	}
	
	/**
	 * Status bar update data method
	 */
	private void statusbarChange() {
		if (multipleDocumentModel.getCurrentDocument() == null)
			lenLabel.setText(flp.getString("length") + " : " + "-");
		else
			lenLabel.setText(flp.getString("length") + " : " +
		multipleDocumentModel.getCurrentDocument().getTextComponent().getText().length());
		try {
			if (multipleDocumentModel.getCurrentDocument() == null)
				textInfoLabel.setText("Ln : "+ "-" + "  Col : "+"-"+ "  Sel: "+"-");
			else {
				JTextArea textArea = multipleDocumentModel.getCurrentDocument().getTextComponent();
				int currLine = textArea.getLineOfOffset(textArea.getCaretPosition());
				int currCol = textArea.getCaretPosition() - textArea.getLineStartOffset(currLine);
				int currSel = Math.abs(textArea.getCaret().getDot() - textArea.getCaret().getMark());
				
				this.textInfoLabel.setText("Ln : "+(currLine+1) + "  Col : "+(currCol+1)+ "  Sel: "+currSel);
			}
		} catch (BadLocationException e) {
			System.err.println(e.getMessage());
			// handle exception
		}
		
	}
	
	/**
	 * Multiple document listener setter
	 */
	private void setListener() {
		multipleDocumentModel.addMultipleDocumentListener(new MultipleDocumentListener() {

			@Override
			public void documentRemoved(SingleDocumentModel model) {
				//

			}

			@Override
			public void documentAdded(SingleDocumentModel model) {
				multipleDocumentModel.getCurrentDocument().getTextComponent().addCaretListener(new CaretListener() {
					@Override
					public void caretUpdate(CaretEvent e) {
						statusbarChange();	
					}
				});
				String path = flp.getString("unnamed");
				if (multipleDocumentModel.getCurrentDocument() != null && 
						multipleDocumentModel.getCurrentDocument().getFilePath() != null)
					path = multipleDocumentModel.getCurrentDocument().getFilePath().toString();

				setTitle(path + " - " + title);

			}

			@Override
			public void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel) {
				String path = flp.getString("unnamed");
				if (multipleDocumentModel.getCurrentDocument() != null && 
						multipleDocumentModel.getCurrentDocument().getFilePath() != null)
					path = multipleDocumentModel.getCurrentDocument().getFilePath().toString();

				setTitle(path + " - " + title);
				
				statusbarChange();

			}
		});
	}
	
	/**
	 * Action creator method
	 */
	private void createActions() {

		this.createNewBlankDocumentAction = new LocalizableAction(this.flp, "createNewBlankDocumentAction") {
			
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				multipleDocumentModel.createNewDocument();
				
			}
		};
		
		createNewBlankDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control N"));
		createNewBlankDocumentAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_N);
		
		this.openExistingDocumentAction = new LocalizableAction(this.flp, "openExistingDocumentAction") {
			
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser jFileChooser = new JFileChooser();
				jFileChooser.setDialogTitle("Open file");
				if (jFileChooser.showOpenDialog(JNotepadPP.this) != JFileChooser.APPROVE_OPTION) return;
				Path filePath = jFileChooser.getSelectedFile().toPath();
				
				try {
					multipleDocumentModel.loadDocument(filePath);
				} catch (Exception e2) {
					System.err.println(e2.getMessage());
					//handle
				}
				
			}
		};
		
		openExistingDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control O"));
		openExistingDocumentAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_O);
		
		this.saveDocumentAction = new LocalizableAction(this.flp, "saveDocumentAction") {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				if (multipleDocumentModel.getCurrentDocument().getFilePath() == null) {
					saveAsDocumentAction.actionPerformed(e);
					return;
				}
				
				//try {
				multipleDocumentModel.saveDocument(multipleDocumentModel.getCurrentDocument(), null);
				//} catch (Exception e2) {
				//	System.err.println(e2.getMessage());
				//}
				
			}
		};
		
		saveDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control S"));
		saveDocumentAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
		
		this.saveAsDocumentAction = new LocalizableAction(this.flp, "saveAsDocumentAction") {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser jFileChooser = new JFileChooser();
				jFileChooser.setDialogTitle("Save document as");
				if (jFileChooser.showSaveDialog(JNotepadPP.this) != JFileChooser.APPROVE_OPTION) {
					//System.err.println("Not saved!");
					return;
				}
				Path path = jFileChooser.getSelectedFile().toPath();
				//try {
				multipleDocumentModel.saveDocument(multipleDocumentModel.getCurrentDocument(), path);
				//} catch (Exception e2) {
				//	System.err.println(e2.getMessage());
				//}
				
			}
		};
		
		//saveDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control shift S"));
		//saveAsDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK | ActionEvent.SHIFT_MASK));
		saveAsDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.SHIFT_MASK));
		saveAsDocumentAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_A);
		
		this.closeDocumentAction = new LocalizableAction(this.flp, "closeDocumentAction") {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				if (!multipleDocumentModel.getCurrentDocument().isModified()) {
					multipleDocumentModel.closeDocument(multipleDocumentModel.getCurrentDocument());
					return;
				}
				
				multipleDocumentModel.closeDocument(multipleDocumentModel.getCurrentDocument());
				//status
			}
		};
		
		closeDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control W"));
		closeDocumentAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_W);
		
		this.cutTextAction = new LocalizableAction(this.flp, "cutTextAction") {

			private static final long serialVersionUID = 1L;
			
			private AbstractAction a = new DefaultEditorKit.CutAction();
			@Override
			public void actionPerformed(ActionEvent e) {
				a.actionPerformed(e);
			}
		};
		
		cutTextAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control X"));
		cutTextAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_X);
				
				
		this.copyTextAction = new LocalizableAction(this.flp, "copyTextAction") {

			private static final long serialVersionUID = 1L;
			
			private AbstractAction a = new DefaultEditorKit.CopyAction();
			@Override
			public void actionPerformed(ActionEvent e) {
				a.actionPerformed(e);
			}
		};
		
		copyTextAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control C"));
		copyTextAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_C);
		
		this.pasteTextAction = new LocalizableAction(this.flp, "pasteTextAction") {

			private static final long serialVersionUID = 1L;
			
			private AbstractAction a = new DefaultEditorKit.PasteAction();
			@Override
			public void actionPerformed(ActionEvent e) {
				a.actionPerformed(e);
			}
		};
		
		pasteTextAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control V"));
		pasteTextAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_V);
		
		this.statisticalInfoAction = new LocalizableAction(this.flp, "statisticalInfoAction") {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				String content = multipleDocumentModel.getCurrentDocument().getTextComponent().getText();
				int numChar = content.length();
				int numNBChar = content.replaceAll("\\s+", "").length();
				int numLines = multipleDocumentModel.getCurrentDocument().getTextComponent().getLineCount();
				
				String s = flp.getString("statinfo1")+" "+numChar+" "+flp.getString("statinfo2")+" "+numNBChar+" "+flp.getString("statinfo3")+" "+numLines+" "+flp.getString("statinfo4");
				JOptionPane.showOptionDialog(JNotepadPP.this,
						s,
						flp.getString("statinfo"),
						JOptionPane.OK_OPTION,
						JOptionPane.INFORMATION_MESSAGE,
						null,
						new String[]{flp.getString("OK")},
						new String()
						);
			}
		};
		
		statisticalInfoAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control R"));
		statisticalInfoAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_R);
		
		this.exitApplicationAction = new LocalizableAction(this.flp, "exitApplicationAction") {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				exitWindow();
				
			}
		};
		exitApplicationAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control E"));
		exitApplicationAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_E);
		
		this.languageEnglishAction = new LocalizableAction(this.flp, "en") {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				LocalizationProvider.getInstance().setLanguage("en");
				
			}
		};
		
		this.languageCroatianAction = new LocalizableAction(this.flp, "hr") {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				LocalizationProvider.getInstance().setLanguage("hr");
				
			}
		};
		
		this.languageGermanAction = new LocalizableAction(this.flp, "de") {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				LocalizationProvider.getInstance().setLanguage("de");
				
			}
		};
		
		this.toUpperCaseAction = new LocalizableAction(this.flp, "touppercase") {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				JTextArea textArea = multipleDocumentModel.getCurrentDocument().getTextComponent();
				int offset = Math.min(textArea.getCaret().getDot(), textArea.getCaret().getMark());
				int lenSel = Math.abs(textArea.getCaret().getDot() - textArea.getCaret().getMark());
				try {
					String newText = textArea.getSelectedText().toUpperCase();
					textArea.getDocument().remove(offset, lenSel);
					textArea.getDocument().insertString(offset, newText, null);
				} catch (BadLocationException e2) {
					System.err.println(e2.getMessage());
				}
							
			}
		};
		
		this.toLowerCaseAction = new LocalizableAction(this.flp, "tolowercase") {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				JTextArea textArea = multipleDocumentModel.getCurrentDocument().getTextComponent();
				int offset = Math.min(textArea.getCaret().getDot(), textArea.getCaret().getMark());
				int lenSel = Math.abs(textArea.getCaret().getDot() - textArea.getCaret().getMark());
				try {
					String newText = textArea.getSelectedText().toLowerCase();
					textArea.getDocument().remove(offset, lenSel);
					textArea.getDocument().insertString(offset, newText, null);
				} catch (BadLocationException e2) {
					System.err.println(e2.getMessage());
				}
							
			}
		};
		
		this.invertCaseAction = new LocalizableAction(this.flp, "invertcase") {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				JTextArea textArea = multipleDocumentModel.getCurrentDocument().getTextComponent();
				int offset = Math.min(textArea.getCaret().getDot(), textArea.getCaret().getMark());
				int lenSel = Math.abs(textArea.getCaret().getDot() - textArea.getCaret().getMark());
				try {
					String newText = textArea.getSelectedText();
					char[] charArr = newText.toCharArray();
					for (int i = 0; i < charArr.length; i++) {
						if (Character.isLowerCase(charArr[i]))
							charArr[i] = Character.toUpperCase(charArr[i]);
						else if (Character.isUpperCase(charArr[i]))
							charArr[i] = Character.toLowerCase(charArr[i]);
					}
					newText = String.valueOf(charArr);
					textArea.getDocument().remove(offset, lenSel);
					textArea.getDocument().insertString(offset, newText, null);
				} catch (BadLocationException e2) {
					System.err.println(e2.getMessage());
				}
							
			}
		};
		
		this.sortAscendingAction = new LocalizableAction(this.flp, "sortascending") {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				Collator collator = Collator.getInstance(new Locale(flp.getCurrentLanguage()));
				JTextArea textArea = multipleDocumentModel.getCurrentDocument().getTextComponent();
				int offset = Math.min(textArea.getCaret().getDot(), textArea.getCaret().getMark());
				int lenSel = Math.abs(textArea.getCaret().getDot() - textArea.getCaret().getMark());
				try {
					String text = textArea.getSelectedText();
					String[] lines = text.split("\\r?\\n");
					Arrays.sort(lines, collator);
					String newText = "";
					for (int i = 0; i < lines.length; i++) {
						newText += lines[i];
						if (i < lines.length-1)
							newText+= "\n";
					}
					textArea.getDocument().remove(offset, lenSel);
					textArea.getDocument().insertString(offset, newText, null);
				} catch (BadLocationException e2) {
					System.err.println(e2.getMessage());
					//handle exception
				}
				
				
			}
		};
		
		
		this.sortDescendingAction = new LocalizableAction(this.flp, "sortdescending") {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				Collator collator = Collator.getInstance(new Locale(flp.getCurrentLanguage()));
				JTextArea textArea = multipleDocumentModel.getCurrentDocument().getTextComponent();
				int offset = Math.min(textArea.getCaret().getDot(), textArea.getCaret().getMark());
				int lenSel = Math.abs(textArea.getCaret().getDot() - textArea.getCaret().getMark());
				try {
					String text = textArea.getSelectedText();
					String[] lines = text.split("\\r?\\n");
					Arrays.sort(lines, collator.reversed());
					String newText = "";
					for (int i = 0; i < lines.length; i++) {
						newText += lines[i];
						if (i < lines.length-1)
							newText+= "\n";
					}
					textArea.getDocument().remove(offset, lenSel);
					textArea.getDocument().insertString(offset, newText, null);
				} catch (BadLocationException e2) {
					System.err.println(e2.getMessage());
					// handle exception
				}
				
				
			}
		};
		
		this.uniqueAction = new LocalizableAction(this.flp, "unique") {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				JTextArea textArea = multipleDocumentModel.getCurrentDocument().getTextComponent();
				int offset = Math.min(textArea.getCaret().getDot(), textArea.getCaret().getMark());
				int lenSel = Math.abs(textArea.getCaret().getDot() - textArea.getCaret().getMark());
				try {
					String text = textArea.getSelectedText();
					String[] lines = text.split("\\r?\\n");
					lines = Arrays.stream(lines).distinct().toArray(String[]::new);
					String newText = "";
					for (int i = 0; i < lines.length; i++) {
						newText += lines[i];
						if (i < lines.length-1)
							newText+= "\n";
					}
					textArea.getDocument().remove(offset, lenSel);
					textArea.getDocument().insertString(offset, newText, null);
					
				} catch (BadLocationException e2) {
					System.err.println(e2.getMessage());
					// handle exception
				}
				
			}
		};
		
		
		
	}

	/**
	 * Menu creator method
	 */
	private void createMenus() {
		JMenuBar menuBar = new JMenuBar();
		
		JMenu fileMenu = new LocalizableJMenu(this.flp, "file");
		menuBar.add(fileMenu);
		
		fileMenu.add(new JMenuItem(createNewBlankDocumentAction));
		fileMenu.add(new JMenuItem(openExistingDocumentAction));
		fileMenu.add(new JMenuItem(saveDocumentAction));
		fileMenu.add(new JMenuItem(saveAsDocumentAction));
		fileMenu.addSeparator();
		fileMenu.add(new JMenuItem(statisticalInfoAction));
		fileMenu.addSeparator();
		fileMenu.add(new JMenuItem(closeDocumentAction));
		fileMenu.add(new JMenuItem(exitApplicationAction));
		
		JMenu editMenu = new LocalizableJMenu(this.flp, "edit");
		menuBar.add(editMenu);
		
		editMenu.add(new JMenuItem(cutTextAction));
		editMenu.add(new JMenuItem(copyTextAction));
		editMenu.add(new JMenuItem(pasteTextAction));
		
		JMenu languageMenu = new LocalizableJMenu(this.flp, "languages");
		menuBar.add(languageMenu);
		
		languageMenu.add(new JMenuItem(languageEnglishAction));
		languageMenu.add(new JMenuItem(languageCroatianAction));
		languageMenu.add(new JMenuItem(languageGermanAction));
		
		JMenu toolsMenu = new LocalizableJMenu(this.flp, "tools");
		menuBar.add(toolsMenu);
		
		toolsMenu.add(new JMenuItem(toUpperCaseAction));
		toolsMenu.add(new JMenuItem(toLowerCaseAction));
		toolsMenu.add(new JMenuItem(invertCaseAction));
		
		JMenu sortMenu = new LocalizableJMenu(this.flp, "sort");
		toolsMenu.add(sortMenu);
		
		sortMenu.add(new JMenuItem(sortAscendingAction));
		sortMenu.add(new JMenuItem(sortDescendingAction));
		
		toolsMenu.add(new JMenuItem(uniqueAction));
		
		
		
		this.setJMenuBar(menuBar);
		
	}
	
	/**
	 * Toolbar creator method
	 */
	private void createToolbars() {
		JToolBar toolBar = new LocalizableJToolbar(this.flp, "toolbar");
		
		toolBar.add(new JButton(this.createNewBlankDocumentAction));
		toolBar.add(new JButton(this.openExistingDocumentAction));
		this.saveButton = new JButton(this.saveDocumentAction);
		toolBar.add(this.saveButton);
		toolBar.add(new JButton(this.saveAsDocumentAction));
		toolBar.add(new JButton(this.closeDocumentAction));
		toolBar.addSeparator();
		toolBar.add(new JButton(this.cutTextAction));
		toolBar.add(new JButton(this.copyTextAction));
		toolBar.add(new JButton(this.pasteTextAction));
		toolBar.addSeparator();
		toolBar.add(new JButton(this.statisticalInfoAction));
		toolBar.addSeparator();
		toolBar.add(new JButton(this.exitApplicationAction));
		
		
		toolBar.setFloatable(true);
		
		this.getContentPane().add(toolBar, BorderLayout.PAGE_START);
	}
	
	/**
	 * Main method
	 * @param args
	 */
	public static void main(String[] args) {
		
		SwingUtilities.invokeLater(() -> new JNotepadPP().setVisible(true));

	}

}
