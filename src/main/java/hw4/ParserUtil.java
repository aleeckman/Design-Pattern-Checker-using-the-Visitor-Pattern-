package hw4;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.AST;

// Modifying discouraged. During grading this file will be set 
//  to be the same as the starter files.


public final class ParserUtil {
    /**
     * Parses a java file into an AST
     */
    public static CompilationUnit parseJavaText(String javaSrc) {
        ASTParser parser = ASTParser.newParser(AST.JLS12);
        parser.setSource(javaSrc.toCharArray());
        final CompilationUnit cu = (CompilationUnit) parser.createAST(null);
        return cu;
    }

    /** Helper to read the text of a file to a String. 
     * Shouldn't be needed for assignment, but could be used 
     * wanted to play around with an external Java file.
     * */
    public static String readFileText(Path filePath) {
        try {
            return Files.readString(filePath);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
