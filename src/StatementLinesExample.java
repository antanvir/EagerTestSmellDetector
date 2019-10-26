import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseException;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.google.common.base.Strings;


import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.SwingWorker;

public class StatementLinesExample extends SwingWorker<Void, String> {
	
	public File projectDirectory = null;
	public int counter = 0;
	public File projectDir;
	public UserInterface gui;
	public boolean smellFound = false;
	
	public StatementLinesExample(UserInterface gui) {
		this.gui = gui;
	}
	
//    public void statementsByLine(File projectDir) {
	
	@Override
	protected Void doInBackground() throws Exception {
        new DirExplorer((level, path, file) -> path.endsWith(".java") && (path.contains("Test") || path.contains("test")),
        		(level, path, file) -> {
        			
            System.out.println(path);
            System.out.println(Strings.repeat("=", path.length()));
            try {
//            	String str = "";
                new NodeIterator(new NodeIterator.NodeHandler() {
                    @Override
                    public boolean handle(Node node) {
                        if (node instanceof Statement) {
                        	
                            MethodCallVisitor visitor = new MethodCallVisitor();
                            node.accept(visitor, null);
                            
                            if(visitor.isTestMethod() && visitor.getCounter() >=2 ) {
                            	smellFound = true;
                            	
                            	String str = " [Lines " + node.getBegin().get().line +" - "+ node.getEnd().get().line + " ]\n ";
                            	str += node + "\n";
                            	
//                            	System.out.println(" [Lines " + node.getBegin().get().line +" - "+ node.getEnd().get().line + " ]\n " + node);
//                            	System.out.println();                       
                            	
                            	List<String> listOfEagerTests = visitor.getEagerTestSmellsInsideMethod();
                            	
                            	System.out.println(Strings.repeat("-", 35));
                            	for(String s: listOfEagerTests) {
                            		System.out.println(s);
                            	}
                            	
                            	publish(Integer.toString( counter) );
            					publish(str);
                            }
                            System.out.println("\n");
                            return false;
                        } else {
                            return true;
                        }
                    }
                }).explore(JavaParser.parse(file));
                System.out.println(); // empty line
                
                System.out.println("path: " + path + "\t smellFound Value: " + smellFound + " index counter: " + counter);
                if(!smellFound) {
                	System.out.println("HERE HERE");
                	String str = "No 'Eager Test Smell' found in this class.";
                	publish(Integer.toString( counter) );
                	publish(str);
                }
                
                counter++;
                smellFound = false;
                
                
                
                
                
            } catch (IOException e) {
            	System.out.println("Error in class: " + path);
                new RuntimeException(e);
            }
            
//            if(!smellFound) {
//            	
//            	publish(Integer.toString( counter) );
//            	String str = "\tNo 'Eager Test Smell' found in this class. Good\n\tWell done!";
//            	publish(str);
//            }
//            
//            counter++;
//            smellFound = false;
        }).explore(projectDir);
                       
        return null;
    }
	

	@Override
	protected void process(final List<String> chunks) {
		// Updates the messages text area
		
	    for (final String info : chunks) {
	    	if(info.length() <= 2) {
	    		int index = Integer.parseInt(info);
	    		gui.setCounter(index);
	    	}
	    	else {
	    		gui.displaySmells(info);
	    	}
//	    	try {
//    			Thread.sleep(200);
//    		} catch (Exception e) {
//    			// TODO: handle exception
//    		}
	    }
	}
	
    
	public void setDirectory(File projectDir) {
		this.projectDir = projectDir;
	}

	
}