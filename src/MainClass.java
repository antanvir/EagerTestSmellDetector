import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainClass {
	
	public static List<String> JavaFilesPath = new ArrayList<String>();

	
	 public static void main(String[] args) {
		 
		 UserInterface gui = new UserInterface();
//		 gui.pack();
		 gui.setVisible(true);
		 gui.setInstanceOfThisClass(gui);
		 
//		 File projectDir = new File("F:\\06\\Testing\\Gradebook-master");
//		 ListClassesExample javaFiles = new ListClassesExample();
//		 javaFiles.listClasses(projectDir);
//		 
//		 JavaFilesPath = javaFiles.getJavaFilesPath(); 
//		 
//		 StatementLinesExample methodParser = new StatementLinesExample();
////		 methodParser.setProjectDirectory(projectDir);
//		 methodParser.statementsByLine(projectDir);
	 }

}

