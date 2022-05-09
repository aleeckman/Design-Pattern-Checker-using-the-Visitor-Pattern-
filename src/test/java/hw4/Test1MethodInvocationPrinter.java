package hw4;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.eclipse.jdt.core.dom.ASTNode;
import org.junit.jupiter.api.Test;

import hw4.visitors.MethodInvocationPrinter;

public class Test1MethodInvocationPrinter {
    @Test
    public void shouldCountInvocations() {
        ASTNode ast = ParserUtil.parseJavaText(
            """
            class TestSingleton {
                public static TestSingleton getInstance() {
                    System.out.println(42);
                    foo();
                }
            }
            """
        );
        var visitor = new MethodInvocationPrinter();
        assertEquals(0, visitor.getInvocationsSeen());
        ast.accept(visitor);
        assertEquals(2, visitor.getInvocationsSeen());
        assertEquals(0, visitor.getFavoriteMethodCount());
    }

    @Test
    public void shouldVisitNestedInvocations() {
        ASTNode ast = ParserUtil.parseJavaText(
            """
            class TestSingleton {
                public static TestSingleton getInstance() {
                    System.out.println(foo());
                }
            }
            """
        );
        var visitor = new MethodInvocationPrinter();
        assertEquals(0, visitor.getInvocationsSeen());
        ast.accept(visitor);
        assertEquals(2, visitor.getInvocationsSeen());
    }

    @Test
    public void shouldDetectFavoriteMethod() {
        ASTNode ast = ParserUtil.parseJavaText(
            """
            class TestSingleton {
                public static TestSingleton getInstance() {
                    System.out.println();
                    foo();
                    bar();
                }
            }
            """
        );
        var visitor = new MethodInvocationPrinter("foo");
        assertEquals(0, visitor.getFavoriteMethodCount());
        ast.accept(visitor);
        assertEquals(1, visitor.getFavoriteMethodCount());
    }
}
