package hw4.visitors;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import hw4.MethodUtil;
import hw4.NodeUtil;

public class InstanceGetterVisitor extends ASTVisitor {
    private boolean containsInstanceGetter;

    @Override
	public boolean visit(TypeDeclaration node) {
        containsInstanceGetter = false;
        for (MethodDeclaration method : node.getMethods()) {
            int modifiers = method.getModifiers();
            if (Modifier.isPublic(modifiers) 
                && Modifier.isStatic(modifiers)
                && method.getReturnType2().toString().equals(node.getName().getIdentifier())) {
                    containsInstanceGetter = true;
            }
        }

        return true;
    }

    public boolean getContainsInstanceGetter() {
        return containsInstanceGetter;
    }
}
