package hw4;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.eclipse.jdt.core.dom.ASTNode;
import org.junit.jupiter.api.Test;

public class StudentTest {
    @Test
    public void shouldStillWorkWithInstanceCreationsOfOtherTypes() {

        ASTNode ast = ParserUtil.parseJavaText(
            """
            class Test {
                public static Test getInstance() {

                    private static Test actualInstance;
                    private static String decoyInstance1;
                    private static HashSet decoyInstance2;
                    private static HashMap decoyInstance3;

                    if (instance == null) {
                        actualInstance = new Test();
                    }

                    if (decoyInstance1 == null) {
                        decoyInstance1 = new String();
                    }

                    if (decoyInstance2 == null) {
                        decoyInstance2 = new HashSet();
                    }

                    if (decoyInstance3 == null) {
                        decoyInstance3 = new HashMap();
                    }

                    return actualInstance;
                }
            }
            """
        );

        assertTrue(SingletonChecker.checkConstructorCalledCorrectly(ast));
    }

    @Test
    public void shouldWorkWithInnerClasses() {
        ASTNode contextAST = ParserUtil.parseJavaText(
            """
            class LibraryBook {
                public class class1 {
                    private int attr1;

                    public void intToString() {}
                }

                public void issue() {}
                public void extend() {}
                public void returnIt() {}
            }
            """
        );

        ASTNode abstractStateAST = ParserUtil.parseJavaText(
            """
            interface LBState {
                public void intToString() {}
                public void issue() {}
                public void extend() {}
                public void returnIt() {}
            }
            """
        );

        assertTrue(StatePatternChecker.checkContextHasMatchingMethodNames(contextAST, abstractStateAST));

    }
}
