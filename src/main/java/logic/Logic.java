package logic;

import java.io.BufferedReader;
import java.awt.Desktop;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.Optional;

import database.Connection;
import database.CreateTable;
import database.DropTable;
import javafx.scene.control.MenuItem;
import javafx.scene.paint.Color;

import app.*;
import javafx.util.Pair;


/**
 * Created by Michał on 2017-03-09.
 */
public class Logic {

    private int points;
    private static Logic instance = null;
    private Exercise exercise;
    private int i = 0;

    public static Logic getInstance() {

        return Optional.ofNullable(instance).orElse(instance = new Logic());
    }

    private Logic() {
        this.points = 0;
    }

    public void generateExercises() {
        this.exercise = new Exercise();
    }

    public void addActionToOkButton() {

        GUI.getOkButton().setOnAction((event) -> {

            String answer = GUI.getAnswerTextArea().getText();
            String result = exercise.findWordInPl(answer);

            if (result.equals("ERROR")) {
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

                GUI.getWordToTranslateLabel().setText(exercise.getTasks().get(i).getWordInPolish());
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

    public void addActionToAddButton(){

        GUI.getAddButton().setOnAction((event -> {

            Optional<Pair<String, String>> resultFromDialog = AddNewWordDialog.showANWDialog();

            resultFromDialog.ifPresent(toAdd -> {

                try (java.sql.Connection conn = Connection.getConnection()) {

                    Statement stat = conn.createStatement();
                    Integer size = 0;

                    try (ResultSet result = stat.executeQuery("SELECT * FROM Main"))
                    {
                        while (result.next())
                            size++;
                    }

                    String insertUpdate = "INSERT INTO Main VALUES (?, ?, ?)";

                    PreparedStatement pstat = conn.prepareStatement(insertUpdate);
                    pstat.setString(1, toAdd.getKey());
                    pstat.setString(2, toAdd.getValue());
                    pstat.setString(3, size.toString());
                    pstat.executeUpdate();

                } catch (SQLException ex) {
                    System.out.println("SQL error!");
                } catch (IOException ex) {
                    System.out.println("IO error!");
                }

            });
        }));
    }

    public void addActionToImportItem(MenuItem item) {

        GUI.getAddButton().setOnAction((event -> {

            try {

                if (isTableExist()) {

                    DropTable.runDrop();
                    CreateTable.runCreate();

                } else {

                    CreateTable.runCreate();
                }

            } catch (SQLException ex) {

                for (Throwable t : ex)
                    t.printStackTrace();

            } catch (IOException ex) {

                System.out.print("Read error!");
            }

        }));
    }

    public static boolean isTableExist() throws SQLException, IOException {

        boolean tableExists = false;

        try (java.sql.Connection conn = Connection.getConnection()) {

            DatabaseMetaData md = conn.getMetaData();
            ResultSet rs = md.getTables(null, null, "main", null);
            while (rs.next()) {
                tableExists = true;
            }
        }

        return tableExists;
    }
}