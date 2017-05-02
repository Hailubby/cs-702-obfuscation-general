package obfuscation.packageflattening;

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
        //Splits import declaration by '.'
        String[] importedPkg = n.getNameAsString().split("\\.");

        //Checks if import statement is part of same project package hierarchy. If it isn't, it must be a java/system import which we do not wish to remove
        boolean isMatching = true;
        for (int i = 0; i < splitShortPkg.length - 1; i++) {
            if (isMatching) {
                if (!splitShortPkg[i].equals(importedPkg[i])) {
                    isMatching = false;
                }
            }
        }

        //If import declaration is part of the project package hierarchy, but not of the highest level, remove it as all classes have been package flattened.
        if (isMatching) {
            if(importedPkg.length > splitShortPkg.length) {
                return  null;
            }
        }

        return n;
    }
}
