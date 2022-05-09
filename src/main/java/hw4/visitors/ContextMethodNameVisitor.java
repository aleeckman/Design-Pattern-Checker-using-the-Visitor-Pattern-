package hw4.visitors;

import java.util.HashSet;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.TypeDeclaration;

public class ContextMethodNameVisitor extends ASTVisitor {
    HashSet<String> methodNames = new HashSet<>();

    @Override
	public boolean visit(TypeDeclaration node) {

        for (MethodDeclaration method : node.getMethods()) {
            int modifiers = method.getModifiers();
            if (Modifier.isPublic(modifiers)) {
                methodNames.add(method.getName().toString());
            }
        }

        return true;
    }

    public HashSet<String> getPublicMethods() {
        return new HashSet<>(methodNames);
    }
}
