package views;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;


import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

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

    public static Button createButton(String text){
        Button button = new Button();
        button.setPrefSize(150, 40);
        button.setMaxSize(150, 40);
        button.setMinSize(150, 40);

        button.setText(text);
        return button;
    }

    public static final LocalDate parseToLocalDate(Date date){
        LocalDate convertedDate = date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        convertedDate = LocalDate.parse(convertedDate.format(formatter));
        return convertedDate;
    }

    public static final Date parseToDate(LocalDate localDate){
        return java.sql.Date.valueOf(localDate);
    }
}
