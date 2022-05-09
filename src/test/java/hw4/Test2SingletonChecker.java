package hw4;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.eclipse.jdt.core.dom.ASTNode;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;


public class Test2SingletonChecker {
    @Nested
    class TestCheckPrivateConstructor {
        @Test
        public void shouldDetectPrivateConstructor() {
            ASTNode ast = ParserUtil.parseJavaText(
                """
                class Test {
                    private Test() {

                    }
                }
                """
            );
            assertTrue(SingletonChecker.checkPrivateConstructor(ast));
        }

        @Test
        public void shouldDetectPublicIsNotPrivate() {
            ASTNode ast = ParserUtil.parseJavaText(
                """
                class Test {
                    public Test() {

                    }
                }
                """
            );
            assertFalse(SingletonChecker.checkPrivateConstructor(ast));
        }

        @Test
        public void shouldOnlyConsiderConstructors() {
            ASTNode ast = ParserUtil.parseJavaText(
                """
                class Test {
                    public foo() {

                    }
                }
                """
            );
            assertFalse(SingletonChecker.checkPrivateConstructor(ast));
        }

        @Test
        public void shouldHandleMultiplePrivateConstructors() {
            ASTNode ast = ParserUtil.parseJavaText(
                """
                class Test {
                    private Test() {}

                    private Test(int foo) {}
                }
                """
            );
            assertTrue(SingletonChecker.checkPrivateConstructor(ast));
        }
    }

    @Nested
    class TestCheckInstanceGetter {
        @Test
        public void shouldDetectSimpleGetter() {
            ASTNode ast = ParserUtil.parseJavaText(
                """
                class Test {
                    public static Test getInstance() {}
                }
                """
            );
            assertTrue(SingletonChecker.checkInstanceGetter(ast));
        }

        @Test
        public void shouldRequireGetterIsStatic() {
            ASTNode ast = ParserUtil.parseJavaText(
                """
                class Test {
                    public Test getInstance() {}
                }
                """
            );
            assertFalse(SingletonChecker.checkInstanceGetter(ast));
        }

        @Test
        public void shouldRequireGetterIsPublic() {
            ASTNode ast = ParserUtil.parseJavaText(
                """
                class Test {
                    static Test getInstance() {}
                }
                """
            );
            assertFalse(SingletonChecker.checkInstanceGetter(ast));
        }

        @Test
        public void shouldHaveNoGetterWhenEmpty() {
            ASTNode ast = ParserUtil.parseJavaText(
                """
                class Test {}
                """
            );
            assertFalse(SingletonChecker.checkInstanceGetter(ast));
        }
    }

    @Nested
    class TestCheckPrivateStaticVarForInstance {
        @Test
        public void shouldDetectVarInstance() {
            ASTNode ast = ParserUtil.parseJavaText(
                """
                class Test {
                    private static Test instance;
                }
                """
            );
            assertTrue(SingletonChecker.checkPrivateStaticVarForInstance(ast));
        }

        @Test
        public void shouldEnsureNameMatches() {
            ASTNode ast = ParserUtil.parseJavaText(
                """
                class Test {
                    private static Object instance;
                }
                """
            );
            assertFalse(SingletonChecker.checkPrivateStaticVarForInstance(ast));
        }
    }

    @Nested
    class TestCheckConstructorCalledCorrectly {
        @Test
        public void shouldDetectConstructorCall() {
            ASTNode ast = ParserUtil.parseJavaText(
                """
                class Test {
                    private static Test instance;

                    public static Test getInstance() {
                        if(instance == null) {
                            instance = new Test();
                        }
                        return instance;
                    }
                }
                """
            );
            assertTrue(SingletonChecker.checkConstructorCalledCorrectly(ast));
        }

        @Test
        public void shouldNotCountIfNotInIf() {
            ASTNode ast = ParserUtil.parseJavaText(
                """
                class Test {
                    private static Test instance;

                    public static Test getInstance() {
                        instance = new Test();
                        return instance;
                    }
                }
                """
            );
            assertFalse(SingletonChecker.checkConstructorCalledCorrectly(ast));
        }

        @Test
        public void shouldWorkIfInElseBlock() {
            ASTNode ast = ParserUtil.parseJavaText(
                """
                class Test {
                    private static Test instance;

                    public static Test getInstance() {
                        if(instance != null) {
                            return instance;
                        } else {
                            instance = new Test();
                        }
                    }
                }
                """
            );
            assertTrue(SingletonChecker.checkConstructorCalledCorrectly(ast));
        }

        @Test
        public void shouldCheckConstructorType() {
            ASTNode ast = ParserUtil.parseJavaText(
                """
                class Test {
                    private static String instance;

                    public static Test getInstance() {
                        instance = new String();
                        return instance;
                    }
                }
                """
            );
            assertFalse(SingletonChecker.checkConstructorCalledCorrectly(ast));
        }

        @Test
        public void shouldStillWorkEvenForBadCondition() {
            ASTNode ast = ParserUtil.parseJavaText(
                """
                class Test {
                    public static Test getInstance() {
                        if (true) {
                            instance = new Test();
                        }
                        return instance;
                    }
                }
                """
            );
            // While this isn't really a singleton, it meets all the conditions
            //  given in the spec for the checker.
            assertTrue(SingletonChecker.checkConstructorCalledCorrectly(ast));
        }

        @Test
        public void shouldWorkWithNestedIfs() {
            ASTNode ast = ParserUtil.parseJavaText(
                """
                class Test {
                    private static Test instance;

                    public static Test getInstance() {
                        if(instance == null) {
                            if (true) {
                                instance = new Test();
                            }
                        }
                        return instance;
                    }
                }
                """
            );
            assertTrue(SingletonChecker.checkConstructorCalledCorrectly(ast));
        }

    }

    @Nested
    class TestCompleteIsSingelton {
        @Test
        public void shouldDetectSingleton() {
            ASTNode ast = ParserUtil.parseJavaText(
                """
                public class LibraryLogger {
                    private static LibraryLogger instance = null;
                    private ArrayList<String> writtenLinesLog = new ArrayList<String>();

                    private LibraryLogger() {
                        ExpensiveComputeToy.performExpensiveLogSetup();
                    }

                    public void writeLine(String line) {}

                    public String[] getWrittenLines() {
                        return writtenLinesLog.toArray(
                            new String[writtenLinesLog.size()]
                        );
                    }

                    public static LibraryLogger getInstance() {
                        if(instance == null) {
                            instance = new LibraryLogger();
                        }
                        return instance;
                    }
                }
                """
            );
            assertTrue(SingletonChecker.checkCouldBeSingleton(ast));
        }
    }
}
