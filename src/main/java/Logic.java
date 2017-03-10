import java.io.BufferedReader;
import java.awt.Desktop;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javafx.scene.control.MenuItem;
import javafx.scene.paint.Color;


/**
 * Created by Michał on 2017-03-09.
 */
public class Logic {

    private int points;
    private KnowledgeBase knowledgeBase;
    private static Logic instance = null;
    private Exercise exercise;
    private int i = 0;

    public static Logic getInstance() {

        if (instance == null) {
            instance = new Logic();
        }

        return instance;
    }

    private Logic() {
        this.points = 0;
        this.knowledgeBase = new KnowledgeBase("base.txt");
    }

    public KnowledgeBase getKnowledgeBase() {
        return knowledgeBase;
    }

    public void generateExercises() {
        this.exercise = new Exercise();
    }

    public void addActionToOkButton() {

        GUI.getOkButton().setOnAction((event) -> {

            String answer = GUI.getAnswerTextArea().getText();
            String result = exercise.findWordInPl(answer);

            if (result == null) {
                GUI.getWordToTranslateLabel().setText("Błąd! poprawna odpowiedź: " +
                        exercise.findWordInEng(GUI.getWordToTranslateLabel().getText()));
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

            if (i != 20) {

                GUI.getWordToTranslateLabel().setText(exercise.getTasks().get(i).getWord().getWordInPolish());
                GUI.getWordToTranslateLabel().setTextFill(Color.BLACK);
                GUI.getAnswerTextArea().setText("");
                GUI.getOkButton().setDisable(false);
                i++;
            } else {

                GUI.getSave().fire();
            }
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
                } catch (IOException ioe) {
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