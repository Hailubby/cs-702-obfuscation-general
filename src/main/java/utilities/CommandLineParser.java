package utilities;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;

/**
 * Created by User on 23/04/2017.
 */
public class CommandLineParser {
    private HashMap<String, CompilationUnit> cuMap = new HashMap<String, CompilationUnit>();

    public void findJavaFiles(File folder) {
        //traverse folder structure searching for java files
        for(File file : folder.listFiles()) {
            if (file.isDirectory()){
                findJavaFiles(file);
            }
            else if (file.getName().endsWith(".java")) {
                //System.out.println(file.getName());
                //System.out.println(file.getAbsolutePath());
                createCompilationUnits(file.getName(),file.getAbsolutePath());
            }
        }
    }

    private void createCompilationUnits(String fileName, String filePath) {
        File javaFile = new File("" + filePath);
        CompilationUnit compilationUnit = null;
        try {
            compilationUnit = JavaParser.parse(javaFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        cuMap.put(fileName, compilationUnit);
    }

    public HashMap<String, CompilationUnit> getCuMap() {
        return cuMap;
    }
}
