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
        	super.visit(n, arg);      	
        	
        	
        	String methodName = n.getNameAsString();
        	if(methodName.contains("assert")) {
        		setIsTestMethod(true);
        	}
        	
//        	if(!methodName.contains("assert") && !methodName.contains("set") && !methodName.contains("get")) {
        	if(!methodName.contains("assert") && !methodName.contains("set") ) {
        		counter++;
        		String str = " [Line " + n.getBegin().get().line +  "] " + n;
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