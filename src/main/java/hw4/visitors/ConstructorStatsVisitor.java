package hw4.visitors;


import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Modifier;

import hw4.MethodUtil;
import hw4.NodeUtil;

public class ConstructorStatsVisitor extends ASTVisitor {
    private int constructorsVisitedCount = 0;
    private int privateConstructorsVisitedCount = 0;

    @Override
	public boolean visit(MethodDeclaration node) {
        int modifiers = node.getModifiers();
        if (node.isConstructor()) {
            constructorsVisitedCount += 1;
            if (Modifier.isPrivate(modifiers)) {
                privateConstructorsVisitedCount += 1;
            }
        }

        return true;
    }
    
    public int getConstructorsVisitedCount() {
        return constructorsVisitedCount;
    }

    public int getPrivateConstructorsVisited() {
        return privateConstructorsVisitedCount;
    }
}
