package duke;

import java.io.IOException;
import duke.gui.MainWindow;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * A GUI for Duke using FXML.
 */
public class Main extends Application {

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setMaxWidth(415);
            stage.setMinWidth(415);
            stage.setScene(scene);
            Duke duke = new Duke(stage);
            fxmlLoader.<MainWindow>getController().setDuke(duke);
            assert stage != null : "stage should not be null";
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
