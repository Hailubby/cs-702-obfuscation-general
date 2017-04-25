import Utilities.CommandLineParser;
import Utilities.JavaExporter;
import com.github.javaparser.ast.CompilationUnit;
import obfuscation.CommentsInserter;
import obfuscation.PackageFlattener;
import obfuscation.PackageVisitor;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


/**
 * Created by User on 21/04/2017.
 */
public class Main {

    public static void main(String[] args) throws FileNotFoundException, URISyntaxException {
        CommandLineParser cmdLineParser = new CommandLineParser();
        JavaExporter javaExporter = new JavaExporter();
        PackageVisitor pkgVisitor = new PackageVisitor();
        CommentsInserter cmtInserter = new CommentsInserter();

        File folder = new File(Main.class.getClass().getResource("/Encrypted").toURI());
        cmdLineParser.findJavaFiles(folder);

        HashMap<String,CompilationUnit> cuMap = cmdLineParser.getCuMap();

        if (cuMap.size() != 0){
            Iterator<Map.Entry<String, CompilationUnit>> entries = cuMap.entrySet().iterator();
            while (entries.hasNext()) {
                Map.Entry<String, CompilationUnit> currentEntry = entries.next();
                cmtInserter.insertComments(currentEntry.getValue());
                pkgVisitor.visit(currentEntry.getValue(), null);
            }

            PackageFlattener packageFlattener = new PackageFlattener(pkgVisitor.getPkgNames());
            packageFlattener.findPkgName();
            Iterator<Map.Entry<String, CompilationUnit>> entries2 = cuMap.entrySet().iterator();
            while (entries2.hasNext()) {
                Map.Entry<String, CompilationUnit> currentEntry = entries2.next();
                packageFlattener.visit(currentEntry.getValue(), null);
            }

            javaExporter.exportFile(cuMap);
        }
        else {
            System.out.println("No Java files located in folder to obfuscate");
        }
    }

}
