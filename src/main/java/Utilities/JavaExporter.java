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
    public void exportFile(HashMap<String, CompilationUnit> cuMap) {
        PrintWriter out = null;

        Iterator<Map.Entry<String, CompilationUnit>> entries = cuMap.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry<String, CompilationUnit> currentEntry = entries.next();
            try {
                out = new PrintWriter(currentEntry.getKey());
                out.println(currentEntry.getValue().toString());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } finally {
                out.close();
            }
        }
    }
}
