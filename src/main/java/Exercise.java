import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Michał on 2017-03-10.
 */
public class Exercise {

    private ArrayList<Task> tasks;

    public Exercise() {

        tasks = new ArrayList<Task>(0);

        Logic logic = Logic.getInstance();

        ArrayList<Integer> alreadyInTasks = new ArrayList<Integer>(0);
        Random random = new Random();

        for(int i = 0; i < 20; i++) {

            int idx = random.nextInt(logic.getKnowledgeBase().getKnowledge().size());

            while (alreadyInTasks.contains(idx)) {
                idx = random.nextInt(logic.getKnowledgeBase().getKnowledge().size());
            }

            Task tmp = new Task(logic.getKnowledgeBase().getKnowledge().get(idx));
            tasks.add(tmp);
            alreadyInTasks.add(idx);
        }
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public String findWordInEng(String wordInPolish) {
        for (Task t: tasks) {
            if (t.getWord().getWordInPolish().equals(wordInPolish))
                return t.getWord().getWordInEnglish();
        }

        return null;
    }

    public String findWordInPl(String wordInEnglish) {
        for (Task t: tasks) {
            if (t.getWord().getWordInEnglish().equals(wordInEnglish))
                return t.getWord().getWordInPolish();
        }

        return null;
    }
}
