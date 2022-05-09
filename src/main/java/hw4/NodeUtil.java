package hw4;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.BodyDeclaration;
import org.eclipse.jdt.core.dom.IExtendedModifier;
import org.eclipse.jdt.core.dom.Modifier;

public class NodeUtil {
    /**
     * Helper method to remove the package structure from the type name.
     */
    public static String getUnqualifiedAstNodeName(ASTNode node) {
        String[] pieces = getAstNodeTypeName(node).split("\\.");
        return pieces[pieces.length - 1];
    }
    
    /**
     * @return string with fully qualified type
     */
    public static String getAstNodeTypeName(ASTNode node) {
        return ASTNode.nodeClassForType(node.getNodeType()).getName();
    }

    /**
     * @param decl A declaration. This can be things like fields or methods
     * @return All the keyword modifiers applied onto a declaration. Note
     * this is modifiers actually in the source code AST. This means a declaration
     * might have some default properties not seen in the modifiers.
     * Java also supports user-specified annotation modifiers. These are not returned.
     */
    public static List<Modifier> getKeywordModsFromDeclaration(BodyDeclaration decl) {
        @SuppressWarnings("unchecked")  // the docs specifiy non-generic type
        var kwArgs = getKeywordsFromModifierList(
            (List<IExtendedModifier>) decl.modifiers());
        return kwArgs;
    }

    /** 
     * Filters a modifier list to only be the keyword modifiers 
     * */
    private static List<Modifier> getKeywordsFromModifierList(List<IExtendedModifier> rawMods) {
        var out = new ArrayList<Modifier>();
        for (IExtendedModifier rawMod : rawMods) {
            if (rawMod.isModifier()) {
                out.add((Modifier) rawMod);
            }
        }
        return out;
    }
}
