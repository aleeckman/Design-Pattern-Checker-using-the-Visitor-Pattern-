package hw4.visitors;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import hw4.MethodUtil;
import hw4.NodeUtil;

public class PrivateStaticInstanceVarVisitor extends ASTVisitor {
    
    private boolean hasPrivateStaticInstanceVar;

    @Override
	public boolean visit(TypeDeclaration node) {
        hasPrivateStaticInstanceVar = false;

        for (FieldDeclaration field : node.getFields()) {
            int modifiers = field.getModifiers();
            if (Modifier.isPrivate(modifiers) 
                && Modifier.isStatic(modifiers)
                && field.getType().toString().equals(node.getName().getIdentifier())) {
                    hasPrivateStaticInstanceVar = true;
            }
        }

        return true;
    }

    public boolean checkIfInstanceVarExists() {
        return hasPrivateStaticInstanceVar;
    }

}
