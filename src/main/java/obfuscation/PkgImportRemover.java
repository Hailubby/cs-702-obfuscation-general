package obfuscation;

import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.visitor.ModifierVisitor;

/**
 * Created by User on 26/04/2017.
 */
public class PkgImportRemover extends ModifierVisitor<Void> {
    private String[] splitShortPkg;

    public PkgImportRemover(String[] splitShortPkg) {
        this.splitShortPkg = splitShortPkg;
    }

    //Removes imports of old packages which have been flattened
    @Override
    public Node visit(ImportDeclaration n, Void arg) {
        String[] importedPkg = n.getNameAsString().split("\\.");

        boolean isMatching = true;
        for (int i = 0; i < splitShortPkg.length - 1; i++) {
            if (isMatching) {
                if (!splitShortPkg[i].equals(importedPkg[i])) {
                    isMatching = false;
                }
            }
        }

        if (isMatching) {
            if(importedPkg.length > splitShortPkg.length) {
                return  null;
            }
        }

        return n;
    }
}
