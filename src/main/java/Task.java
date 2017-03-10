/**
 * Created by Michał on 2017-03-10.
 */
public class Task {

    private Word word;
    private boolean isGuessed;

    public Task(Word word) {
        this.word = word;
        this.isGuessed = false;
    }

    public Word getWord() {
        return word;
    }

    public boolean isGuessed() {
        return isGuessed;
    }
}
