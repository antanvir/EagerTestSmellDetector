import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainClass {
	
	public static List<String> JavaFilesPath = new ArrayList<String>();

	
	 public static void main(String[] args) {
		 
		 UserInterface gui = new UserInterface();
		 gui.setVisible(true);
		 gui.setInstanceOfThisClass(gui);
		 

	 }

}

