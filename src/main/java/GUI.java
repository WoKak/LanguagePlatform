import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

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

    public String theme = GUI.class.getResource("style.css").toExternalForm();

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

        nextButton = new Button("Next");
        addActionToNextButton();
        nextButton.setTooltip(new Tooltip("Następne słowo"));
        nextButton.setId("next");
        nextButton.setPrefSize(100,50);
        nextButton.setFont(Font.font("Comic Sans MS", 20));

        MenuBar menuBar = new MenuBar();
        Menu progressMenu = new Menu("Postępy");
        menuBar.getMenus().add(progressMenu);

        VBox root = new VBox(10);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(3));
        root.getChildren().add(wordToTranslateLabel);
        root.getChildren().add(answerTextArea);


        HBox buttonsBox = new HBox(10);
        buttonsBox.setPadding(new Insets(2));
        buttonsBox.setAlignment(Pos.CENTER);
        buttonsBox.getChildren().add(okButton);
        buttonsBox.getChildren().add(nextButton);

        root.getChildren().add(buttonsBox);

        Scene scene = new Scene(root, 500, 375);
        primaryStage.setTitle("plang");
        primaryStage.getIcons().add(new Image("pug.jpg"));
        scene.getStylesheets().add(theme);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public Label getWordToTranslateLabel() {
        return wordToTranslateLabel;
    }

    public TextArea getAnswerTextArea() {
        return answerTextArea;
    }

    public void addActionToOkButton() {

        okButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {

                String answer = getAnswerTextArea().getText();
                String result = knowledgeBase.findWordInPl(answer);

                if (result == null) {
                    getWordToTranslateLabel().setText("Błąd! poprawna odpowiedź: " +
                            knowledgeBase.findWordInEng(getWordToTranslateLabel().getText()));
                    getWordToTranslateLabel().setTextFill(Color.RED);
                    okButton.setDisable(true);
                } else {
                    getWordToTranslateLabel().setText("Dobrze!");
                    getWordToTranslateLabel().setTextFill(Color.GREEN);
                    points++;
                }
            }
        });
    }

    public void addActionToNextButton() {

        nextButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {

                Random random = new Random();
                int idx = random.nextInt(knowledgeBase.getKnowledge().size());
                getWordToTranslateLabel().setText(knowledgeBase.getKnowledge().get(idx).getWordInPolish());
                getWordToTranslateLabel().setTextFill(Color.BLACK);
                getAnswerTextArea().setText("");
                okButton.setDisable(false);
            }
        });
    }
}
