import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseException;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.google.common.base.Strings;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingWorker;

public class ListClassesExample extends SwingWorker<Void, List> {

	List<String> JavaFiles = new ArrayList<String>();
	public File projectDir;
	public UserInterface gui;
	
	public ListClassesExample(UserInterface gui) {
		this.gui = gui;
	}
	
	
//    public void listClasses(File projectDir) {
	
	@Override
	protected Void doInBackground() throws Exception {
    	
        new DirExplorer((level, path, file) -> path.endsWith(".java") && (path.contains("Test") || path.contains("test")),
        		(level, path, file) -> {
        			
            System.out.println(path);
            System.out.println(Strings.repeat("=", path.length()));
            
            try {
                new VoidVisitorAdapter<Object>() {
                    @Override
                    public void visit(ClassOrInterfaceDeclaration n, Object arg) {
                        super.visit(n, arg);
                        System.out.println(" * " + n.getName());
                        JavaFiles.add( n.getName().asString());
                    }
                }.visit(JavaParser.parse(file), null);
                System.out.println(); // empty line

            } catch ( IOException e) {
                new RuntimeException(e);
            }
        }).explore(projectDir);
        
        publish(JavaFiles);
        
        return null;
    }
	
	@Override
	protected void process(final List<List> chunks) {
		// Updates the messages text area
		
	    for (final List fileList : chunks) {
	    	gui.showTestFileList(fileList);
	    }
	}
	
    
    
    public List<String> getClassNames(){
     	return JavaFiles;
     }


	public void setDirectory(File projectDir) {
		this.projectDir = projectDir;
	}
      

}