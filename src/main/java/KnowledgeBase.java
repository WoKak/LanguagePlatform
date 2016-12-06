import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Michał on 2016-11-24.
 */
public class KnowledgeBase {

    private static ArrayList<Word> knowledge;

    public KnowledgeBase (String source) {
        knowledge = new ArrayList<Word>(0);

        File file = new File(source);
        try {
            FileReader reader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line = bufferedReader.readLine();
            while (line != null) {
                String[] tokens = line.split(", ");
                Word tmp = new Word(tokens[0], tokens[1]);
                knowledge.add(tmp);
                line = bufferedReader.readLine();
            }
        } catch (IOException ex) {
            System.out.println("Błąd w czytaniu!");
        }
    }

    public static ArrayList<Word> getKnowledge() {
        return knowledge;
    }
    
    public static String findWordInEng(String wordInPolish) {
        for (Word w: knowledge) {
            if (w.getWordInPolish().equals(wordInPolish))
                return w.getWordInEnglish();
        }

        return null;
    }

    public static String findWordInPl(String wordInEnglish) {
        for (Word w: knowledge) {
            if (w.getWordInEnglish().equals(wordInEnglish))
                return w.getWordInPolish();
        }

        return null;
    }
}
