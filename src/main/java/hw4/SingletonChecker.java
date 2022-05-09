package hw4;
import org.eclipse.jdt.core.dom.ASTNode;

import hw4.visitors.ConstructorCalledCorrectlyVisitor;
import hw4.visitors.ConstructorStatsVisitor;
import hw4.visitors.InstanceGetterVisitor;
import hw4.visitors.PrivateStaticInstanceVarVisitor;
// TODO probably need new visitors


public final class SingletonChecker {
    private SingletonChecker() {}

    public static boolean checkPrivateConstructor(ASTNode ast) {
        
        var visitor = new ConstructorStatsVisitor();
        ast.accept(visitor);
        
        if (visitor.getPrivateConstructorsVisited() >= 1 
            && visitor.getPrivateConstructorsVisited() == visitor.getConstructorsVisitedCount()) {
            return true;
        }

        return false;
    }

    public static boolean checkInstanceGetter(ASTNode ast) {
        var visitor = new InstanceGetterVisitor();
        ast.accept(visitor);

        return visitor.getContainsInstanceGetter();
    }

    public static boolean checkPrivateStaticVarForInstance(ASTNode ast) {
        var visitor = new PrivateStaticInstanceVarVisitor();
        ast.accept(visitor);

        return visitor.checkIfInstanceVarExists();
    }

    public static boolean checkConstructorCalledCorrectly(ASTNode ast) {
        var visitor = new ConstructorCalledCorrectlyVisitor();
        ast.accept(visitor);

        return visitor.getNumberOfCallsToConstructor() == 1;
    }

    public static boolean checkCouldBeSingleton(ASTNode ast) {
        return checkPrivateConstructor(ast)
            && checkInstanceGetter(ast)
            && checkPrivateStaticVarForInstance(ast)
            && checkConstructorCalledCorrectly(ast);
    }
}
