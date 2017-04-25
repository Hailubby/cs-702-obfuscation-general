package obfuscation;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.comments.BlockComment;
import com.github.javaparser.ast.comments.JavadocComment;
import com.github.javaparser.ast.comments.LineComment;

/**
 * Created by User on 23/04/2017.
 */
public class CommentsInserter {

    //Goes through all nodes in the compilation unit and adds comments if possible
    public void insertComments(Node node){
        // If current node is not a comment node, insert random comment
        if (node.getClass() != LineComment.class && node.getClass() != BlockComment.class && node.getClass() != JavadocComment.class){
            String comment = randomString();
            int commentType = (int)(Math.floor(Math.random()*2));
            if (commentType == 0){
                //block comment if 0
                node.setBlockComment(comment);
            } else {
                //line comment if 1
                node.setLineComment(comment);
            }
        }

        for (Node child : node.getChildNodes()){
            insertComments(child);
        }
    }

    //Randomly generates a string for the comment
    private String randomString(){
        int numWords = (int)(Math.floor((Math.random() * 15)) + 9);
        String[] randomWords = new String[numWords];

        for(int i = 0; i < numWords; i++) {
            int wordLength = (int)(Math.floor((Math.random()*13)) + 2);
            char[] word = new char[wordLength];

            for(int j = 0; j < wordLength; j++) {
                if(j == (wordLength - 1) && i != (numWords - 1)) {
                    word[j] = ' ';
                } else {
                    word[j] = (char)('a' + (int)(Math.floor(Math.random()*26)));
                }
            }

            randomWords[i] = new String(word);
        }

        return strArrayToStr(randomWords);
    }

    //convert string array to string
    private String strArrayToStr(String[] stringArray) {
        StringBuilder strBuilder = new StringBuilder();

        for(int i = 0; i < stringArray.length; i++) {
            strBuilder.append(stringArray[i]);
        }

        String string = strBuilder.toString();

        return string;
    }
}
