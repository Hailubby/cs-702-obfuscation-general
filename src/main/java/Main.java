import obfuscation.comments.CommentsInserter;
import obfuscation.packageflattening.PackageFlattener;
import obfuscation.packageflattening.PackageVisitor;
import obfuscation.packageflattening.PkgImportRemover;
import utilities.CommandLineParser;
import utilities.JavaExporter;
import com.github.javaparser.ast.CompilationUnit;
import obfuscation.classrename.ClassNameGenerator;
import obfuscation.classrename.ClassUsageVisitor;

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
        //Initialise visitors and helpers needed
        CommandLineParser cmdLineParser = new CommandLineParser();
        JavaExporter javaExporter = new JavaExporter();
        PackageVisitor pkgVisitor = new PackageVisitor();
        CommentsInserter cmtInserter = new CommentsInserter();
        ClassNameGenerator classNameGenerator = new ClassNameGenerator();

        //Retrieves the folder "Encrypted" from the resources folder
        File folder = new File(Main.class.getClass().getResource("/Encrypted").toURI());
        cmdLineParser.findJavaFiles(folder);

        HashMap<String,CompilationUnit> cuMap = cmdLineParser.getCuMap();

        Iterator<Map.Entry<String, CompilationUnit>> entries = cuMap.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry<String, CompilationUnit> currentEntry = entries.next();
            //Sets up new class names hashmap
            classNameGenerator.visit(currentEntry.getValue(), null);
        }

        //Retrieve new class names hashmap
        HashMap<String, String> classNamesMap = classNameGenerator.getClassNamesMap();

        //Set up class usage visitor
        ClassUsageVisitor classUsageVisitor = new ClassUsageVisitor(classNamesMap);

        if (cuMap.size() != 0){
            entries = cuMap.entrySet().iterator();
            while (entries.hasNext()) {
                Map.Entry<String, CompilationUnit> currentEntry = entries.next();
                //Refactor classes with new names
                classUsageVisitor.visit(currentEntry.getValue(), null);

                //inserts comments
                cmtInserter.insertComments(currentEntry.getValue());

                //Sets up package array list
                pkgVisitor.visit(currentEntry.getValue(), null);
            }


            //Input package array list and gets highest level package
            PackageFlattener packageFlattener = new PackageFlattener(pkgVisitor.getPkgNames());
            packageFlattener.findPkgName();

            //Sets up package import remover as classes will be in a single hierarchy after flattening
            PkgImportRemover pkgImportRemover = new PkgImportRemover(packageFlattener.getSplitShortPkg());


            entries = cuMap.entrySet().iterator();
            while (entries.hasNext()) {
                Map.Entry<String, CompilationUnit> currentEntry = entries.next();
                //Flatten package
                packageFlattener.visit(currentEntry.getValue(), null);
                //Remove imports to do with package hierarchies
                pkgImportRemover.visit(currentEntry.getValue(), null);
            }

            //Export files
            javaExporter.exportFile(cuMap, classNamesMap);
            javaExporter.exportTxtFile(classNamesMap);
        }
        else {
            System.out.println("No Java files located in folder to obfuscate");
        }
    }

}
