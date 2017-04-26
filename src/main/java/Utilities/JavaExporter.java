package Utilities;

import com.github.javaparser.ast.CompilationUnit;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by User on 23/04/2017.
 */
public class JavaExporter {

    public void exportFile(HashMap<String, CompilationUnit> cuMap, HashMap<String,String> classNamesMap) {
        PrintWriter out = null;

        Iterator<Map.Entry<String, CompilationUnit>> entries = cuMap.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry<String, CompilationUnit> currentEntry = entries.next();
            try {
                String oldName = currentEntry.getKey().split("\\.")[0];
                String newName = classNamesMap.get(oldName) + ".java";

                out = new PrintWriter(newName);
                out.println(currentEntry.getValue().toString());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } finally {
                out.close();
            }
        }
    }

    public void exportTxtFile(HashMap<String, String> classNamesMap) {
        PrintWriter out = null;
        try {
            out = new PrintWriter("GeneratedClassNames.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Iterator<Map.Entry<String, String>> entries = classNamesMap.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry<String, String> currentEntry = entries.next();
            out.println(currentEntry.getKey() + " = " + currentEntry.getValue().toString());
        }

        out.close();
    }
}
