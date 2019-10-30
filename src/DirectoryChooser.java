import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileSystemView;

public class DirectoryChooser extends JFrame {

	private JPanel contentPane;
	JFileChooser jfc;


	public DirectoryChooser() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 594, 440);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		jfc = new JFileChooser(new File("This PC"));
		jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
		jfc.setDialogTitle("CHOOSE A DIRECTORY TO RUN DIAGNOSIS");
		jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		
		contentPane.add(jfc);
		
		
	}
	
	public String showFiles() {
		
		String path = null;
		int returnValue = jfc.showOpenDialog(null);
		
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			if (jfc.getSelectedFile().isDirectory()) {
				path = jfc.getSelectedFile().getAbsolutePath();		
			}
		}
		
		return path;
	}

}
