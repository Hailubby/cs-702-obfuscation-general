package obfuscation;

import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.util.ArrayList;

/**
 * Created by User on 25/04/2017.
 */
public class PackageFlattener extends VoidVisitorAdapter<Void> {
    private ArrayList<String> pkgList;
    private String shortPkg;
    private String[] splitShortPkg;

    public PackageFlattener(ArrayList<String> pkgList) {
        this.pkgList = pkgList;
    }

    @Override
    public void visit(PackageDeclaration n, Void arg) {
        n.setName(shortPkg);

        super.visit(n,arg);
    }

    @Override
    public void visit(ImportDeclaration n, Void arg) {
        String[] importedPkg = n.getNameAsString().split("\\.");

        boolean isMatching = true;
        for (int i = 0; i < splitShortPkg.length; i++) {
            if (isMatching) {
                if (!splitShortPkg[i].equals(importedPkg[i])) {
                    isMatching = false;
                }
            }
        }

        if (isMatching) {
            String importedClass = importedPkg[importedPkg.length - 1];
            String newImported = shortPkg + "." + importedClass;
            n.setName(newImported);
        }

        super.visit(n, arg);
    }

    //Finds highest level package of existing classes
    public void findPkgName(){
        String pkgName = "";
        int minLength = 0;

        for(int i = 0; i < pkgList.size(); i++) {
            if (i == 0) {
                pkgName = pkgList.get(i);
                minLength = pkgName.split("\\.").length;
            } else {
                if( pkgList.get(i).split("\\.").length < minLength) {
                    pkgName = pkgList.get(i);
                    minLength = pkgName.split("\\.").length;
                }
            }
        }

        this.shortPkg = pkgName;
        this.splitShortPkg = pkgName.split("\\.");
    }

}
