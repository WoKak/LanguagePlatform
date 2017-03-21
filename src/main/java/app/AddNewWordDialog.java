package app;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.util.Optional;

/**
 * Created by Michał on 2017-03-21.
 */
public class AddNewWordDialog extends Dialog {

    public static Optional<Pair<String, String>> showANWDialog() {

        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Dodaj słówko");
        dialog.setHeaderText("Dodaj nowe słówko do bazy");
        dialog.setGraphic(new ImageView(("images/for_dialog.png")));

        // Get the Stage.
        Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();

        // Add a custom icon.
        stage.getIcons().add(new Image(("images/pug.png")));


        ButtonType confirmButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(confirmButtonType, ButtonType.CANCEL);

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20, 150, 10, 10));

        TextField wordInPolish = new TextField();
        TextField wordInEnglish = new TextField();

        gridPane.add(new Label("po polsku:"), 0, 0);
        gridPane.add(wordInPolish, 1, 0);
        gridPane.add(new Label("po angielsku:"), 0, 1);
        gridPane.add(wordInEnglish, 1, 1);

        dialog.getDialogPane().setContent(gridPane);

        // Request focus on the username field by default.
        Platform.runLater(() -> wordInPolish.requestFocus());

        // Convert the result to a username-password-pair when the login button is clicked.
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == confirmButtonType) {
                return new Pair<>(wordInPolish.getText(), wordInEnglish.getText());
            }
            return null;
        });

        Optional<Pair<String, String>> result = dialog.showAndWait();

        return result;
    }
}
