package obfuscation.packageflattening;

import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.util.ArrayList;

/**
 * Created by User on 25/04/2017.
 */
public class PackageFlattener extends VoidVisitorAdapter<Void> {
    private ArrayList<String> pkgList;
    private String shortPkg;

    public PackageFlattener(ArrayList<String> pkgList) {
        this.pkgList = pkgList;
    }

    @Override
    public void visit(PackageDeclaration n, Void arg) {
        //Set package name of compilation unit to the highest level package
        n.setName(shortPkg);

        super.visit(n,arg);
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
    }

    //Returns split array of the highest level package
    public String[] getSplitShortPkg() {
        return shortPkg.split("\\.");
    }

}
