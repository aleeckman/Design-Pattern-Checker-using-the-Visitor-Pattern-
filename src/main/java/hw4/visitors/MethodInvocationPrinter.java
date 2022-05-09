package hw4.visitors;

import java.util.List;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.MethodInvocation;

import hw4.MethodUtil;
import hw4.NodeUtil;

import org.eclipse.jdt.core.dom.Expression;

/**
 * <p>Serves as an example of a visitor.
 * It is just supposed to descend down an AST and track when
 * seeing a MethodInvocation node. A MethodInvocation in java
 * occurs when you call a method using the `()` syntax. For
 * example in `Foo.bar()` there would be an invocation of the
 * `bar` method. This is different than MethodDeclaration's.
 *  Declaration look like `String bar() { implementation }`.</p>
 * 
 * <p>This method exposes counts of invocations. It also should accept
 * a `favoriteMethodName` parameter, and perform a special action
 * when a method of that name is invoked. </p>
 */
public class MethodInvocationPrinter extends ASTVisitor {
	// TODO p1 fix bug with visit method not descending to children
	// TODO p1 fix bug with favorite method not working

	private String favoriteMethodName;
	private int invocationsSeenCount;
	private int favoriteMethodCount;

	public MethodInvocationPrinter(String favoriteMethodName) {
		this.favoriteMethodName = favoriteMethodName;
	}

	public MethodInvocationPrinter() {}

	/**
	 * This method is availabe because we override ASTVisitor.
	 * It will get called for us everytime a MethodInvocation is
	 * encountered in the AST. If we return true, all the children
	 * of this node will be visited.
	 */
	@Override
	public boolean visit(MethodInvocation node) {
		printToStdOut(node);
		invocationsSeenCount += 1;
		if (invocationIsFavoriteMethod(node)) {
			printFavoriteMethod(node);
			favoriteMethodCount += 1; 
		}
		return true;
	}

	private void printToStdOut(MethodInvocation node) {
		System.out.println("-- MethodInvocation --> " + node.getName());
		List<Expression> args = MethodUtil.getMethodInvocationArgs(node);
		for (int i = 0; i < args.size(); i++) {
			var arg = args.get(i);
			System.out.println(
                "    Argument " + i + ": " 
				+ NodeUtil.getUnqualifiedAstNodeName(arg) + " -- " + arg
			);
		}
	}

	private void printFavoriteMethod(MethodInvocation node) {
		System.out.println("!!! " + favoriteMethodName + " !!!");
	}

	private boolean invocationIsFavoriteMethod(MethodInvocation node) {
		return 
			favoriteMethodName != null
			&& node.getName().toString().equals(favoriteMethodName);
	}

	public int getInvocationsSeen() {
		return invocationsSeenCount;
	}

	public int getFavoriteMethodCount() {
		return favoriteMethodCount;
	}

}