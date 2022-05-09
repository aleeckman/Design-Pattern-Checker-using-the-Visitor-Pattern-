package hw4;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;


public class Test3StatePatternChecker {
    @Test
    public void shouldDetectCorrectState() {
        assertTrue(
            StatePatternChecker.checkContextHasMatchingMethodNames(
                ParserUtil.parseJavaText(
                    """
                    class LibraryBook {
                        public void issue() {}
                        public void extend() {}
                        public void returnIt() {}
                    }
                    """
                ),
                ParserUtil.parseJavaText(
                    """
                    interface LBState {
                        public void issue() {}
                        public void extend() {}
                        public void returnIt() {}
                    }
                    """
                )
            )
        );
    }

    @Test
    public void shouldDetectMissingStateMethods() {
        assertFalse(
            StatePatternChecker.checkContextHasMatchingMethodNames(
                ParserUtil.parseJavaText(
                    """
                    class LibraryBook {
                        public void issue() {}
                        public void returnIt() {}
                    }
                    """
                ),
                ParserUtil.parseJavaText(
                    """
                    interface LBState {
                        public void issue() {}
                        public void extend() {}
                        public void returnIt() {}
                    }
                    """
                )
            )
        );
    }

}
