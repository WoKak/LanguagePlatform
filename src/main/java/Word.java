/**
 * Created by Michał on 2016-11-24.
 */
public class Word {

    private String wordInPolish;
    private String wordInEnglish;

    public Word(String wordInPolish, String wordInEnglish) {
        this.wordInPolish = wordInPolish;
        this.wordInEnglish = wordInEnglish;
    }

    public String getWordInPolish() {
        return wordInPolish;
    }

    public String getWordInEnglish() {
        return wordInEnglish;
    }
}
