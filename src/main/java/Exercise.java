import java.util.ArrayList;
import java.util.Optional;
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

        String toReturn = null;

        for (Word t: tasks) {
            if (t.getWordInPolish().equals(wordInPolish))
                toReturn = t.getWordInEnglish();
        }

        return Optional.ofNullable(toReturn).orElse("ERROR");
    }

    public String findWordInPl(String wordInEnglish){

        String toReturn = null;

        for (Word t: tasks) {
            if (t.getWordInEnglish().equals(wordInEnglish))
                toReturn =  t.getWordInPolish();
        }

        return Optional.ofNullable(toReturn).orElse("ERROR");
    }
}
