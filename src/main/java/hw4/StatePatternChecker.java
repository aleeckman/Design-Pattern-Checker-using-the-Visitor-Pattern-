package hw4;

import java.util.HashSet;

import org.eclipse.jdt.core.dom.ASTNode;

import hw4.visitors.ContextMethodNameVisitor;


public final class StatePatternChecker {
    private StatePatternChecker() {}

    public static boolean checkContextHasMatchingMethodNames(ASTNode context, ASTNode abstractState) {
        
        var contextVisitor = new ContextMethodNameVisitor();
        var abstractStateVisitor = new ContextMethodNameVisitor();;

        context.accept(contextVisitor);
        abstractState.accept(abstractStateVisitor);

        HashSet<String> contextPublicMethods = contextVisitor.getPublicMethods();
        HashSet<String> abstractStatePublicMethods = abstractStateVisitor.getPublicMethods();

        abstractStatePublicMethods.removeAll(contextPublicMethods);
        
        return abstractStatePublicMethods.isEmpty();
    }
}
