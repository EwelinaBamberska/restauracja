package views;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class JavaFXUtils {

    public static void changeScene(ActionEvent actionEvent, String name, double width, double height, Class clazz) {
        try {
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Parent parent = FXMLLoader.load(clazz.getResource(name));
            stage.setScene(new Scene(parent, width, height));
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
