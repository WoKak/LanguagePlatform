import java.io.BufferedReader;
import java.awt.Desktop;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

import javafx.scene.control.MenuItem;
import javafx.scene.paint.Color;


/**
 * Created by Michał on 2017-03-09.
 */
public class Logic {

    private int points;
    private KnowledgeBase knowledgeBase;

    public Logic() {
        this.points = 0;
        this.knowledgeBase = new KnowledgeBase("base.txt");
    }

    public void addActionToOkButton() {

        GUI.getOkButton().setOnAction((event) -> {

            String answer = GUI.getAnswerTextArea().getText();
            String result = knowledgeBase.findWordInPl(answer);

            if (result == null) {
                GUI.getWordToTranslateLabel().setText("Błąd! poprawna odpowiedź: " +
                        knowledgeBase.findWordInEng(GUI.getWordToTranslateLabel().getText()));
                GUI.getWordToTranslateLabel().setTextFill(Color.RED);
                GUI.getOkButton().setDisable(true);
            } else {
                GUI.getWordToTranslateLabel().setText("Dobrze!");
                GUI.getWordToTranslateLabel().setTextFill(Color.LIMEGREEN);
                points++;
            }
        });
    }

    public void addActionToNextButton() {

        GUI.getNextButton().setOnAction((event) -> {

            Random random = new Random();
            int idx = random.nextInt(knowledgeBase.getKnowledge().size());
            GUI.getWordToTranslateLabel().setText(knowledgeBase.getKnowledge().get(idx).getWordInPolish());
            GUI.getWordToTranslateLabel().setTextFill(Color.BLACK);
            GUI.getAnswerTextArea().setText("");
            GUI.getOkButton().setDisable(false);

        });
    }

    public void addActionToSaveMenu(MenuItem save) {

        save.setOnAction((event) -> {

            File file = null;
            try {
                file = new File("etc/progress/progress.txt");
                FileReader reader = new FileReader(file);
                BufferedReader bufferedReader = new BufferedReader(reader);


                String line = bufferedReader.readLine();

                String[] data = line.split(",");

                reader.close();

                FileWriter writer = new FileWriter(file);

                for (int i = 0; i < data.length; i++) {
                    writer.write(data[i] + ",");
                }

                writer.write(points + ",");
                writer.close();
                GUI.getShow().fire();
                System.exit(0);
            } catch (IOException ex) {
            }
        });
    }

    public void addActionToShowItem(MenuItem show) {

        show.setOnAction((event) -> {

            File graph = new File("etc/progress/graph.svg");
            File progress = new File("etc/progress/progress.txt");

            Graph.drawGraph(graph, progress);

            if (!Desktop.isDesktopSupported()) {
                System.err.println("Desktop not supported!");
                System.exit(-1);
            }

            Desktop desktop = Desktop.getDesktop();

            if (desktop.isSupported(Desktop.Action.OPEN)) {
                try {
                    desktop.open(graph);
                }
                catch (IOException ioe) {
                    System.err.println("Unable to open: " + graph.getName());
                }
            }
        });
    }

    public void addActionToAddButton() {

        GUI.getAddButton().setOnAction((event -> {


        }));
    }
}
