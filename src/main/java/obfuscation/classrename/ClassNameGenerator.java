package obfuscation.classrename;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.util.HashMap;

/**
 * Created by User on 26/04/2017.
 */
public class ClassNameGenerator extends VoidVisitorAdapter<Void> {
    //Key is original class name, value is new class name
    private HashMap<String, String> classNamesMap = new HashMap<String, String>();
    private char[] letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    private char[] symbols = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

    //Visit Class and Interface declarations and generates a random string for the new name and puts in hash map
    @Override
    public void visit(ClassOrInterfaceDeclaration n, Void arg) {
        String newName = stringGen();
        while (classNamesMap.containsValue(newName)) {
            newName = stringGen();
        }

        classNamesMap.put(n.getNameAsString(), newName);
        super.visit(n, arg);
    }


    //Helper method for random string generation
    private String stringGen(){
        char[] c = new char[16];

        for (int i = 0; i < 16; i++){
            if (i == 0) {
                int num = (int)Math.floor(Math.random() * 26);
                c[i] = letters[num];
            } else {
                int num = (int) Math.floor(Math.random() * 62);
                c[i] = symbols[num];
            }
        }

        return new String(c);
    }

    //classNameMap getter
    public HashMap<String, String> getClassNamesMap() {
        return this.classNamesMap;
    }
}
