package hw4.visitors;

import java.util.List;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.StringLiteral;
import org.eclipse.jdt.core.dom.Expression;

/**
 * Another example ASTVisitor example. 
 * This one just prints all the String literals.
 */
public class StringLitteralPrinter extends ASTVisitor {
	public boolean visit(StringLiteral node) {
		System.out.println("-- String Litteral --> " + node.getNodeType());
        // We want to make sure we print any nested methods
        boolean shouldDescendToNestedCode = true;
		return shouldDescendToNestedCode;
	}
}