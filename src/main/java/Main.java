import Utilities.CommandLineParser;
import Utilities.JavaExporter;
import com.github.javaparser.ast.CompilationUnit;

import java.io.File;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by User on 21/04/2017.
 */
public class Main {
    public static void main(String[] args) {
        CommandLineParser cmdLineParser = new CommandLineParser();
        JavaExporter javaExporter = new JavaExporter();
        File sourceFolder = null;

        try {
            sourceFolder = new File(Main.class.getClass().getResource("/Original").toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        cmdLineParser.findJavaFiles(sourceFolder);
        HashMap<String,CompilationUnit> cuMap = cmdLineParser.getCuMap();

        if (cuMap.size() != 0){
            Iterator<Map.Entry<String, CompilationUnit>> entries = cuMap.entrySet().iterator();
            while (entries.hasNext()) {
                //call visitors for control flow flattening etc etc
            }

            javaExporter.exportFile(cuMap);
        }
        else {
            System.out.println("No Java files located in folder to obfuscate");
        }
    }
}
