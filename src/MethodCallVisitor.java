import java.util.ArrayList;
import java.util.List;

import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.google.common.base.Strings;

public class MethodCallVisitor extends VoidVisitorAdapter<Void> {
		
		public int counter = 0;
		public List<String> listEagerTestsInsideThisMethod = new ArrayList<String>();
		public boolean isTestMethod = false;
		
		
        @Override
        public void visit(MethodCallExpr n, Void arg) {
        	// Don't forget to call super, it may find more method calls inside the arguments of this method call, for example.
        	super.visit(n, arg);
//        	System.out.println(" [Line " + n.getBegin().get().line +  "] " + n);       	
        	
        	
        	String methodName = n.getNameAsString();
        	if(methodName.contains("assert")) {
        		setIsTestMethod(true);
        	}
        	
        	if(!methodName.contains("assert") && !methodName.contains("set")) {
        		counter++;
        		String str = " [Line " + n.getBegin().get().line +  "] " + methodName;
        		listEagerTestsInsideThisMethod.add(str);
        	}      	
        }
        
        public int getCounter() {
        	return counter;
        }
        
        public List<String> getEagerTestSmellsInsideMethod(){
        	return listEagerTestsInsideThisMethod;
        }
        
        public void setIsTestMethod(boolean flag) {
        	isTestMethod = flag;
        }
        
        public boolean isTestMethod() {
        	return isTestMethod;
        }
        
    }