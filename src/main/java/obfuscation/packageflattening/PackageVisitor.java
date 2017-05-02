package obfuscation.packageflattening;

import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.util.ArrayList;

/**
 * Created by User on 25/04/2017.
 */
public class PackageVisitor extends VoidVisitorAdapter<Void> {
    ArrayList<String> pkgNames = new ArrayList<String>();

    //Visitor that visits all package declaration nodes within the compilation unit
    @Override
    public void visit(PackageDeclaration n, Void arg) {
        //If package declaration is not already in the array list, add it in.
        if (!pkgNames.contains(n.getNameAsString())) {
            pkgNames.add(n.getNameAsString());
        }

        super.visit(n,arg);
    }

    //Retrieve array list of package names
    public ArrayList<String> getPkgNames() {
        return this.pkgNames;
    }
}
