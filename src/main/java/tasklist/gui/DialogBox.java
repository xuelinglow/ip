package tasklist.gui;
import java.io.IOException;
import java.util.Collections;

import java.awt.Desktop;
import java.net.URI;
import java.net.URISyntaxException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import tasklist.TaskList;

/**
 * An example of a custom control using FXML.
 * This control represents a dialog box consisting of an ImageView to represent the speaker's face and a label
 * containing text from the speaker.
 */
public class DialogBox extends HBox {
    @FXML
    private Label dialog;
    @FXML
    private ImageView displayPicture;
    @FXML
    private Button actionButton;

    String link = "";

    private DialogBox(String text, Image img, boolean isUser, TaskList taskList) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/DialogBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            Parent root = fxmlLoader.load();
            if (isUser) {
                String existingStyle = root.getStyle();
                existingStyle += "-fx-background-color: #C4A484;";
                root.setStyle(existingStyle);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (text == "helpg") {
            System.out.println("Help");
            Hyperlink hyperlink = new Hyperlink("Click here to visit the User Guide");
            hyperlink.setOnAction(event -> {
                try {
                    Desktop.getDesktop().browse(new URI("https://xuelinglow.github.io/ip/"));
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
            });
            dialog.setGraphic(hyperlink);
            text = "";
        }

        dialog.setText(text);
        displayPicture.setImage(img);

        if (taskList != null) {
            actionButton.setVisible(true);
            actionButton.setOnAction(event -> {
                try {
                    FXMLLoader fxmlLoaderSched = new FXMLLoader(MainWindow.class
                        .getResource("/view/ScheduleWindow.fxml"));
                    Parent root = fxmlLoaderSched.load();

                    Stage popupStage = new Stage();
                    Scene scene = new Scene(root);
                    popupStage.setScene(scene);

                    ScheduleWindow controller = fxmlLoaderSched.getController();
                    controller.setStage(popupStage, taskList);

                    popupStage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    /**
     * Flips the dialog box such that the ImageView is on the left and text on the right.
     */
    private void flip() {
        ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
        Collections.reverse(tmp);
        getChildren().setAll(tmp);
        setAlignment(Pos.TOP_LEFT);
    }

    public static DialogBox getUserDialog(String text, Image img) {
        return new DialogBox(text, img, true, null);
    }

    public static DialogBox getMichelleDialog(String text, Image img, TaskList tasklist) {
        var db = new DialogBox(text, img, false, tasklist);
        db.flip();
        return db;
    }
}
