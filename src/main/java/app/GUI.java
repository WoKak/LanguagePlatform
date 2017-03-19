package app;

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
import javafx.scene.text.Font;
import javafx.stage.Stage;
import logic.Logic;


/**
 * Created by Michał on 2017-02-21.
 */
public class GUI extends Application {

    private static Label wordToTranslateLabel;
    private static TextArea answerTextArea;
    private static Button okButton;
    private static Button nextButton;
    private static Button addButton;
    private static MenuItem save;
    private static MenuItem show;
    private MenuItem blue, green, red, yellow;
    private Scene scene;

    private String theme = "etc/styles/zielony.css";

    @Override
    public void start(Stage primaryStage) throws Exception {

        Logic logic = Logic.getInstance();
        logic.generateExercises();

        wordToTranslateLabel = new Label("Aby rozpocząć kliknij przycisk Next");
        wordToTranslateLabel.setFont(Font.font("Comic Sans MS", 20));

        answerTextArea = new TextArea();
        answerTextArea.setPrefSize(100,40);
        answerTextArea.setFont(Font.font("Comic Sans MS", 20));
        answerTextArea.setId("answer");

        okButton = new Button("OK");
        logic.addActionToOkButton();
        okButton.setTooltip(new Tooltip("Sprawdź"));
        okButton.setId("ok");
        okButton.setPrefSize(100,50);
        okButton.setFont(Font.font("Comic Sans MS", 20));
        okButton.setDisable(true);

        nextButton = new Button("Next");
        logic.addActionToNextButton();
        nextButton.setTooltip(new Tooltip("Następne słowo"));
        nextButton.setId("next");
        nextButton.setPrefSize(100,50);
        nextButton.setFont(Font.font("Comic Sans MS", 20));

        addButton = new Button("+");
        addButton.setTooltip(new Tooltip("Dodaj słówko"));
        addButton.setId("add");
        addButton.setPrefSize(50,50);
        addButton.setFont(Font.font("Comic Sans MS", 20));

        MenuBar menuBar = new MenuBar();
        Menu progressMenu = new Menu("Postępy");
        menuBar.getMenus().add(progressMenu);

        save = new MenuItem("Zapisz postępy i zakończ");
        logic.addActionToSaveMenu(save);
        progressMenu.getItems().add(save);


        show = new MenuItem("Zobacz postępy");
        logic.addActionToShowItem(show);
        progressMenu.getItems().add(show);

        VBox mainBox = new VBox(10);
        mainBox.setAlignment(Pos.BOTTOM_CENTER);
        mainBox.setPadding(new Insets(4));
        mainBox.getChildren().add(wordToTranslateLabel);
        mainBox.getChildren().add(answerTextArea);


        HBox buttonsBox = new HBox(10);
        buttonsBox.setPadding(new Insets(2));
        buttonsBox.setAlignment(Pos.CENTER);
        buttonsBox.getChildren().add(okButton);
        buttonsBox.getChildren().add(nextButton);

        mainBox.getChildren().add(buttonsBox);

        HBox addBox = new HBox();
        addBox.setAlignment(Pos.BOTTOM_RIGHT);
        addBox.getChildren().add(addButton);
        addBox.setPrefSize(50,50);
        logic.addActionToAddButton();

        mainBox.getChildren().add(addBox);

        VBox root = new VBox(80);
        root.setPadding(new Insets(2));
        root.getChildren().add(menuBar);
        root.getChildren().add(mainBox);

        scene = new Scene(root, 500, 375);
        primaryStage.setTitle("plang");
        primaryStage.getIcons().add(new Image("images/pug.png"));
        scene.getStylesheets().add(theme);
        primaryStage.setScene(scene);

        Menu styleMenu = new Menu("Style");
        menuBar.getMenus().add(styleMenu);

        makeStyleMenuItem("niebieski", blue, scene, styleMenu);
        makeStyleMenuItem("zielony", green, scene, styleMenu);
        makeStyleMenuItem("czerwony", red, scene, styleMenu);
        makeStyleMenuItem("żółty", yellow, scene, styleMenu);

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static Label getWordToTranslateLabel() {
        return wordToTranslateLabel;
    }

    public static TextArea getAnswerTextArea() {
        return answerTextArea;
    }

    public static Button getOkButton() {
        return okButton;
    }

    public static Button getNextButton() {
        return nextButton;
    }

    public static Button getAddButton() {
        return addButton;
    }

    public static MenuItem getShow() {
        return show;
    }

    public static MenuItem getSave() {
        return save;
    }

    private void makeStyleMenuItem(String name, MenuItem item, Scene scene, Menu menu) {

        item = new MenuItem(name);
        addActionToStyleItem(item, scene);
        menu.getItems().add(item);
    }

    private void addActionToStyleItem(MenuItem item, Scene scene) {

        item.setOnAction((event) -> {

            String style = "etc/styles/" + item.getText() + ".css";
            theme = style;
            scene.getStylesheets().add(theme);
        });
    }
}
