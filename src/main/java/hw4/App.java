package hw4;

import org.eclipse.jdt.core.dom.ASTNode;
import hw4.visitors.MethodInvocationPrinter;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {
        
        ASTNode ast = ParserUtil.parseJavaText(
            """
            class Hello {
                public static void main(String[] args) {
                    System.out.println(42);
                    foo('a', 'b');
                    int radius = 2;
                    int r_squared = Math.pow(radius, 2);
                    System.out.println(Math.sqrt(r_squared));
                    if (true) {
                        bar();
                    }
                }
            }
            """
        );
        
        var visitor = new MethodInvocationPrinter("sqrt");
        ast.accept(visitor);
    }
}
