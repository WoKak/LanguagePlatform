import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Michał on 2017-03-10.
 */
public class Exercise {

    private ArrayList<Word> tasks;

    public Exercise() {

        tasks = new ArrayList<Word>(0);

        Logic logic = Logic.getInstance();

        ArrayList<Integer> alreadyInTasks = new ArrayList<Integer>(0);
        Random random = new Random();

        for(int i = 0; i < 20; i++) {

            int idx = random.nextInt(logic.getKnowledgeBase().getKnowledge().size());

            while (alreadyInTasks.contains(idx)) {
                idx = random.nextInt(logic.getKnowledgeBase().getKnowledge().size());
            }

            Word tmp = logic.getKnowledgeBase().getKnowledge().get(idx);
            tasks.add(tmp);
            alreadyInTasks.add(idx);
        }
    }

    public ArrayList<Word> getTasks() {
        return tasks;
    }

    public String findWordInEng(String wordInPolish) {
        for (Word t: tasks) {
            if (t.getWordInPolish().equals(wordInPolish))
                return t.getWordInEnglish();
        }

        return null;
    }

    public String findWordInPl(String wordInEnglish) {
        for (Word t: tasks) {
            if (t.getWordInEnglish().equals(wordInEnglish))
                return t.getWordInPolish();
        }

        return null;
    }
}
