package hw4.visitors;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.TypeDeclaration;

public class ConstructorCalledCorrectlyVisitor extends ASTVisitor {
    
    private int numberOfCallsToConstructor;

    @Override
	public boolean visit(TypeDeclaration node) {
        
        String className = node.getName().getIdentifier();
        numberOfCallsToConstructor = 0;

        node.accept(new ASTVisitor() {

            @Override
            public boolean visit(IfStatement ifElseStatement) {
                
                Statement ifStatement = ifElseStatement.getThenStatement();
                Statement elseStatement = ifElseStatement.getElseStatement();

                if (ifStatement != null) {

                    ifStatement.accept(new ASTVisitor() {
                        
                        @Override
                        public boolean visit(ClassInstanceCreation instance) {
                            
                            if (instance.getType().toString().equals(className)) {
                                numberOfCallsToConstructor += 1;
                            }

                            return false;
                        }
        
                    });
        
                }

                if (elseStatement != null) {

                    elseStatement.accept(new ASTVisitor() {
                        
                        @Override
                        public boolean visit(ClassInstanceCreation instance) {
                            
                            if (instance.getType().toString().equals(className)) {
                                numberOfCallsToConstructor += 1;
                            }

                            return false;
                        }
        
                    });
        
                }

                return false;
            }
        });

        return true;
    }

    public int getNumberOfCallsToConstructor() {
        return numberOfCallsToConstructor;
    }
}
