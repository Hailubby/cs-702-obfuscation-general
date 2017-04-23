import Utilities.CommandLineParser;
import Utilities.JavaExporter;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.comments.BlockComment;
import com.github.javaparser.ast.comments.Comment;
import com.github.javaparser.ast.comments.JavadocComment;
import com.github.javaparser.ast.comments.LineComment;
import com.github.javaparser.ast.stmt.ForeachStmt;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import obfuscation.CommentsInserter;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;

/**
 * Created by User on 21/04/2017.
 */
public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        CommandLineParser cmdLineParser = new CommandLineParser();
        JavaExporter javaExporter = new JavaExporter();
        CommentsInserter cmtInserter = new CommentsInserter();
        File sourceFolder = null;

        try {
            sourceFolder  = new File(Main.class.getClass().getResource("/Original/CustomDiceActivity.java").toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        //Parse the file
        CompilationUnit compilationUnit = JavaParser.parse(sourceFolder);
        cmtInserter.insertComments(compilationUnit);

        System.out.println(compilationUnit.toString());

//        try {
//            sourceFolder = new File(Main.class.getClass().getResource("/Original").toURI());
//        } catch (URISyntaxException e) {
//            e.printStackTrace();
//        }
//
//        cmdLineParser.findJavaFiles(sourceFolder);
//        HashMap<String,CompilationUnit> cuMap = cmdLineParser.getCuMap();
//
//        if (cuMap.size() != 0){
//            Iterator<Map.Entry<String, CompilationUnit>> entries = cuMap.entrySet().iterator();
//            while (entries.hasNext()) {
//                //call visitors for control flow flattening etc etc
//            }
//
//            javaExporter.exportFile(cuMap);
//        }
//        else {
//            System.out.println("No Java files located in folder to obfuscate");
//        }
    }

}
