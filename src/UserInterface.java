import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileSystemView;

import com.google.common.base.Strings;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JFileChooser;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.UIManager;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class UserInterface extends JFrame {

	private JPanel contentPane;
	private JPanel selectionPanel;
	private JPanel fileListPanel;
	private JPanel smellDisplayPanel;
	private JPanel refreshButtonPanel;
	
	JFileChooser fileChooser;
	
	JProgressBar progressBar;
	private JLabel PathLabel;
	private JLabel FileNameLabel;
	private JTextField pathString;
	JButton folderButton;
	JTabbedPane displayPane;
	JTextArea TestClassses;
	
	public static List<String> JavaFileNames = new ArrayList<String>();
	File projectDir;
	public JList classNameList;
	
	double width, height;
	public UserInterface GuiInstance;
	public int index = -1, prevIndex = -1;
	public JPanel[] tabPanels;
	public JTextArea[] panelTextAreas;
	double N;
	int NumOfTestClasses = 0;

	public UserInterface() {
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		width = screenSize.getWidth() - 10;
		height = screenSize.getHeight() - 15;
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(5, 0, (int)width, (int)height);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		selectionPanel = new JPanel();
		selectionPanel.setBounds( 0, 0, (int) width, (int) (height*(1/6.0)) );
		contentPane.add(selectionPanel);
		selectionPanel.setLayout(null);
		
		JLabel smellDetectorLabel = new JLabel("Eager Test Smell Detector");
		smellDetectorLabel.setForeground(new Color(0, 0, 128));
		smellDetectorLabel.setBackground(new Color(240, 240, 240));
		smellDetectorLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		smellDetectorLabel.setHorizontalAlignment(SwingConstants.CENTER);
		smellDetectorLabel.setBounds((int) (width/2.0 - width/4.0), 5, (int) (width/2.0), 40);
		selectionPanel.add(smellDetectorLabel);
		
		
		folderButton = new JButton("Directory");
		folderButton.setBackground(SystemColor.activeCaption);
		folderButton.setBounds(20, 45, 100, 30);
		selectionPanel.add(folderButton);
		folderButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				DirectoryChooser dir = new DirectoryChooser();
				String path = dir.showFiles();
				pathString.setText(path);
			}
		});

		
		
		
		pathString = new JTextField();
		pathString.setToolTipText("Enter Path of the Source Folder");
		pathString.setBounds(130, 45, (int) (width*(3/4.0) - 125), 30);
		selectionPanel.add(pathString);
		pathString.setColumns(10);
		
		
		progressBar = new JProgressBar();
		progressBar.setBounds((int) (width/(8.0) -30), 95, (int) (width*(3/4.0) + 20), 15);
		selectionPanel.add(progressBar);
		progressBar.setValue(0);
		progressBar.setVisible(false);
		progressBar.setForeground(Color.GREEN);
		
		JButton startButton = new JButton("Analyze");
		startButton.setBackground(new Color(154, 205, 50));
		startButton.setBounds((int) (width*(3/4.0) + 30), 45, (int) (width/4.0 - 60), 30);
		selectionPanel.add(startButton);
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
//				System.out.println("Hello prevInd : " + prevIndex);
				if(pathString.getText() != null) {
					
					extractTestClasses();
					analyzeSmmells();
				}
				else {
					JOptionPane.showMessageDialog(null, "Select Source Folder");
				}
				

			}
		});
		
		fileListPanel = new JPanel();
		fileListPanel.setBackground(UIManager.getColor("Button.light"));
		fileListPanel.setBounds(10, (int) (height/6.0), (int) (width/(8.0) + 100), (int) (height*(4/6.0)) + 30);
		contentPane.add(fileListPanel);
		fileListPanel.setLayout(null);
		
		FileNameLabel = new JLabel("\t==  TEST FILES  ==");
		FileNameLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		FileNameLabel.setBackground(new Color(238, 232, 170));
		FileNameLabel.setBounds(5, 5, (int) (width/(8.0) + 90), 20);
		fileListPanel.add(FileNameLabel);
		FileNameLabel.setVisible(false);
		

		TestClassses = new JTextArea();
		TestClassses.setBounds(5, 30, (int) (width/(8.0) + 90), (int) (height*(4/6.0)) );
		TestClassses.setVisible(true);
//		fileListPanel.add(TestClassses);
		TestClassses.setEditable(false);
		JScrollPane scroll = new JScrollPane(TestClassses);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scroll.setBounds(5, 30, (int) (width/(8.0) + 90), (int) (height*(4/6.0)));
		fileListPanel.add(scroll);
		
		smellDisplayPanel = new JPanel();
		smellDisplayPanel.setBackground(new Color(255, 255, 255));
		smellDisplayPanel.setBounds((int) (width/(8.0) + 120), (int) (height/6.0), (int) (width*(7/8.0) - 150), (int) (height*(4/6.0) + 30) );
		contentPane.add(smellDisplayPanel);
		smellDisplayPanel.setLayout(null);
		
		displayPane = new JTabbedPane();
		displayPane.setBounds(0, 0, (int) (width*(7/8.0) - 150), (int) (height*(4/6.0) + 30) );
		smellDisplayPanel.add(displayPane);
		displayPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		
		refreshButtonPanel = new JPanel();
		refreshButtonPanel.setBackground(SystemColor.control);
		refreshButtonPanel.setBounds(0, (int) (height*(5/6.0) + 30), (int) width, (int) (height/6.0 - 50) );
		contentPane.add(refreshButtonPanel);
		refreshButtonPanel.setLayout(null);
		
		JButton refreshButton = new JButton("Refresh");
		refreshButton.setForeground(new Color(0, 0, 0));
		refreshButton.setBackground(new Color(222, 184, 135));
		refreshButton.setBounds((int) (width*(3/4.0) + 30), 10, (int) (width/4.0 - 60), 30);
		refreshButtonPanel.add(refreshButton);
		refreshButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pathString.setText("");
				displayPane.removeAll();
				TestClassses.setVisible(false);
				FileNameLabel.setVisible(false);
				progressBar.setVisible(false);
				projectDir = null;
				JavaFileNames = new ArrayList<String>();
				
				index = -1; prevIndex = -1;
				NumOfTestClasses = 0;
				N = 0;
			}
		});
	}
	
	
	
	
	public void extractTestClasses() {
//		projectDir = new File("F:\\06\\Testing\\Gradebook-master");
		
		projectDir = new File(pathString.getText());
		ListTestClasses classeExtractor = new ListTestClasses(getInstanceOfThisClass());
		classeExtractor.setDirectory(projectDir);
		classeExtractor.listClasses(projectDir);
		this.JavaFileNames.addAll( classeExtractor.getTestClassList() );
		this.NumOfTestClasses = this.JavaFileNames.size();
		
		System.out.println(NumOfTestClasses);
		showTestFileList(this.JavaFileNames);
		initializeNecessaryVaribles();
		
	}
	
	
	public void analyzeSmmells(){
		TestMethods methodParser = new TestMethods(getInstanceOfThisClass());
		methodParser.setDirectory(projectDir);
		methodParser.execute();
	}
		

	
	public void initializeNecessaryVaribles() {
		int size = getNumOfTestClasses();
		
		tabPanels = new JPanel[size];
		panelTextAreas = new JTextArea[size];
		
		progressBar.setVisible(true);
		N = size + 1.0;
		updateProgressBar( (int) (1 * 100.0 / N) );
	}
	
	
	public int getNumOfTestClasses() {
		return NumOfTestClasses;
	}
	
	public void showTestFileList(List<String> JavaFileNames){
		
		
		FileNameLabel.setVisible(true);
		TestClassses.setVisible(true);
		String text = "";
		for(String name: JavaFileNames) {
			text += "\n  " + name;
		}
		TestClassses.setFont(new Font("Tahoma", Font.PLAIN, 13));
		TestClassses.setText(text);

	}
	
	
	public void setInstanceOfThisClass(UserInterface gui) {
		GuiInstance = gui;
	}
	
	public UserInterface getInstanceOfThisClass() {
		return GuiInstance;
	}
	
	public void setCounter(int index) {
		prevIndex = this.index;
		this.index = index;
		addTabPane();
	}
	
	public int getCounter() {
		return index;
	}
	
	public void addTabPane() {

		if(prevIndex != index) {

			tabPanels[index] = new JPanel();
			panelTextAreas[index] = new JTextArea(200, 100);
			
			panelTextAreas[index].setText("\t *** EAGER TEST SMELL  LOG *** \n");
			String str = Strings.repeat("=", panelTextAreas[index].getText().length() * 2);
			panelTextAreas[index].setText( panelTextAreas[index].getText() + str + "\n");
			
			tabPanels[index].add(panelTextAreas[index]);
			displayPane.addTab( JavaFileNames.get(index), new JScrollPane(tabPanels[index]) );
			
			updateProgressBar( (int) ((index + 2) * 100.0 / N) );
		}
	}
	
	public void displaySmells(String smellyMethod) {
;
		String str = panelTextAreas[index].getText();
		
		str += "\n" + smellyMethod;
		panelTextAreas[index].setText("");
		panelTextAreas[index].setText(str);
	}
	
	public void updateProgressBar(int n) {
		progressBar.setValue(n);
		try {
			Thread.sleep(200);
		} catch (Exception e) {
			// TODO: handle exception
		}

	}
}
