import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.awt.*;
import java.io.*;
import java.util.Random;

/**
 * Created by Michał on 2017-02-21.
 */
public class GUI extends Application {

    private Label wordToTranslateLabel;
    private TextArea answerTextArea;
    private Button okButton;
    private Button nextButton;
    private int points = 0;
    private KnowledgeBase knowledgeBase;
    private MenuItem save;
    private MenuItem show;

    public String theme = GUI.class.getResource("etc/style.css").toExternalForm();

    @Override
    public void start(Stage primaryStage) throws Exception {

        knowledgeBase = new KnowledgeBase("base.txt");

        wordToTranslateLabel = new Label("Aby rozpocząć kliknij przycisk Next");
        wordToTranslateLabel.setFont(Font.font("Comic Sans MS", 20));

        answerTextArea = new TextArea();
        answerTextArea.setPrefSize(100,40);
        answerTextArea.setFont(Font.font("Comic Sans MS", 20));

        okButton = new Button("OK");
        addActionToOkButton();
        okButton.setTooltip(new Tooltip("Sprawdź"));
        okButton.setId("ok");
        okButton.setPrefSize(100,50);
        okButton.setFont(Font.font("Comic Sans MS", 20));
        okButton.setDisable(true);

        nextButton = new Button("Next");
        addActionToNextButton();
        nextButton.setTooltip(new Tooltip("Następne słowo"));
        nextButton.setId("next");
        nextButton.setPrefSize(100,50);
        nextButton.setFont(Font.font("Comic Sans MS", 20));

        MenuBar menuBar = new MenuBar();
        Menu progressMenu = new Menu("Postępy");
        menuBar.getMenus().add(progressMenu);

        save = new Menu("Zapisz postępy i zakończ");
        addActionToSaveMenu();
        progressMenu.getItems().add(save);


        show = new MenuItem("Zobacz postępy");
        addActionToShowItem();
        progressMenu.getItems().add(show);

        VBox mainBox = new VBox(10);
        mainBox.setAlignment(Pos.BOTTOM_CENTER);
        mainBox.setPadding(new Insets(3));
        mainBox.getChildren().add(wordToTranslateLabel);
        mainBox.getChildren().add(answerTextArea);


        HBox buttonsBox = new HBox(10);
        buttonsBox.setPadding(new Insets(2));
        buttonsBox.setAlignment(Pos.CENTER);
        buttonsBox.getChildren().add(okButton);
        buttonsBox.getChildren().add(nextButton);

        mainBox.getChildren().add(buttonsBox);

        VBox root = new VBox(80);
        root.setPadding(new Insets(2));
        root.getChildren().add(menuBar);
        root.getChildren().add(mainBox);

        Scene scene = new Scene(root, 500, 375);
        primaryStage.setTitle("plang");
        primaryStage.getIcons().add(new Image("images/pug.jpg"));
        scene.getStylesheets().add(theme);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private Label getWordToTranslateLabel() {
        return wordToTranslateLabel;
    }

    private TextArea getAnswerTextArea() {
        return answerTextArea;
    }

    private void addActionToOkButton() {

        okButton.setOnAction((event) -> {

                String answer = getAnswerTextArea().getText();
                String result = knowledgeBase.findWordInPl(answer);

                if (result == null) {
                    getWordToTranslateLabel().setText("Błąd! poprawna odpowiedź: " +
                            knowledgeBase.findWordInEng(getWordToTranslateLabel().getText()));
                    getWordToTranslateLabel().setTextFill(Color.RED);
                    okButton.setDisable(true);
                } else {
                    getWordToTranslateLabel().setText("Dobrze!");
                    getWordToTranslateLabel().setTextFill(Color.PALEGREEN);
                    points++;
                }
        });
    }

    private void addActionToNextButton() {

        nextButton.setOnAction((event) -> {

                Random random = new Random();
                int idx = random.nextInt(knowledgeBase.getKnowledge().size());
                getWordToTranslateLabel().setText(knowledgeBase.getKnowledge().get(idx).getWordInPolish());
                getWordToTranslateLabel().setTextFill(Color.BLACK);
                getAnswerTextArea().setText("");
                okButton.setDisable(false);
        });
    }

    private void addActionToSaveMenu() {

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
                    System.exit(0);
                } catch (IOException ex) {
                }
        });
    }

    private void addActionToShowItem() {

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
}
