package utilities;

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

    //Exports hashmap of compilation units into java files with their respective names
    public void exportFile(HashMap<String, CompilationUnit> cuMap, HashMap<String,String> classNamesMap) {
        PrintWriter out = null;

        //Iterate through hashmap of compilation units to retrieve their original file name and corresponding compilation unit
        Iterator<Map.Entry<String, CompilationUnit>> entries = cuMap.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry<String, CompilationUnit> currentEntry = entries.next();
            try {
                String oldName = currentEntry.getKey().split("\\.")[0];
                //Gets new name of java file and names the outputted javafile with it
                String newName = classNamesMap.get(oldName) + ".java";
                out = new PrintWriter(newName);
                //Print the compilation unit to the file
                out.println(currentEntry.getValue().toString());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } finally {
                out.close();
            }
        }
    }

    //Exports mapping of old class name to newly generated class names to a txt file
    public void exportTxtFile(HashMap<String, String> classNamesMap) {
        PrintWriter out = null;
        try {
            out = new PrintWriter("GeneratedClassNames.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //Goes through hash map and prints the key (old name) and value (new name) to txt file
        Iterator<Map.Entry<String, String>> entries = classNamesMap.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry<String, String> currentEntry = entries.next();
            out.println(currentEntry.getKey() + " = " + currentEntry.getValue().toString());
        }

        out.close();
    }
}
