package hw4;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.eclipse.jdt.core.dom.ASTNode;
import org.junit.jupiter.api.Test;

import hw4.visitors.ConstructorStatsVisitor;

public class Test2ConstructorStatsVisitor {
    @Test
    public void shouldCountSinglePrivateConstructor() {
        ASTNode ast = ParserUtil.parseJavaText(
            """
            class Test {
                private Test() {

                }
            }
            """
        );
        ConstructorStatsVisitor visitor = new ConstructorStatsVisitor();
        assertEquals(0, visitor.getConstructorsVisitedCount());
        assertEquals(0, visitor.getPrivateConstructorsVisited());
        ast.accept(visitor);
        assertEquals(1, visitor.getConstructorsVisitedCount());
        assertEquals(1, visitor.getPrivateConstructorsVisited());
    }

    @Test
    public void shouldDetectSinglePublicConstructor() {
        ASTNode ast = ParserUtil.parseJavaText(
            """
            class Test {
                public Test() {

                }
            }
            """
        );
        ConstructorStatsVisitor visitor = new ConstructorStatsVisitor();
        assertEquals(0, visitor.getConstructorsVisitedCount());
        assertEquals(0, visitor.getPrivateConstructorsVisited());
        ast.accept(visitor);
        assertEquals(1, visitor.getConstructorsVisitedCount());
        assertEquals(0, visitor.getPrivateConstructorsVisited());
    }

    @Test
    public void shouldDetectUnmodedConstructor() {
        ASTNode ast = ParserUtil.parseJavaText(
            """
            class Test {
                Test() {

                }
            }
            """
        );
        ConstructorStatsVisitor visitor = new ConstructorStatsVisitor();
        ast.accept(visitor);
        assertEquals(1, visitor.getConstructorsVisitedCount());
        assertEquals(0, visitor.getPrivateConstructorsVisited());
    }

    @Test
    public void shouldHandleNoConstructor() {
        ASTNode ast = ParserUtil.parseJavaText(
            """
            class Test {
                void foo() {

                }
            }
            """
        );
        ConstructorStatsVisitor visitor = new ConstructorStatsVisitor();
        ast.accept(visitor);
        assertEquals(0, visitor.getConstructorsVisitedCount());
        assertEquals(0, visitor.getPrivateConstructorsVisited());
    }
}
