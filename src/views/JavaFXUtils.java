package views;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;


import java.io.IOException;
import java.time.LocalDate;
import java.sql.Date;

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

    public static void changeScene(KeyEvent actionEvent, String name, double width, double height, Class clazz) {
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
        LocalDate convertedDate = date.toLocalDate();
        return convertedDate;
    }

    public static final Date parseToDate(LocalDate localDate){
        return java.sql.Date.valueOf(localDate);
    }

    public static TextFlow buildTextFlow(String text, String filter) {
        int filterIndex = text.toLowerCase().indexOf(filter.toLowerCase());
        Text textBefore = new Text(text.substring(0, filterIndex));
        Text textAfter = new Text(text.substring(filterIndex + filter.length()));
        Text textFilter = new Text(text.substring(filterIndex,  filterIndex + filter.length())); //instead of "filter" to keep all "case sensitive"
        textFilter.setFill(Color.ORANGE);
//        textFilter.setFont(Font.font("Helvetica", FontWeight.BOLD, 12));
        return new TextFlow(textBefore, textFilter, textAfter);
    }
}
