package views;


import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;

public class GetTableNumber{

    static String answer;

    public static String getTableNumber() {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Restauracja");
        window.setMinWidth(250);
        Label label = new Label();
        label.setText("Choose a table:");

        Button confirmButton = new Button("Confirm");
        TextField tableTextField = new TextField();

        //Clicking will set answer and close window
        confirmButton.setOnAction(e -> {
            answer = tableTextField.getText();
            window.close();
        });

        VBox layout = new VBox(10);

        //Add buttons
        layout.getChildren().addAll(label, tableTextField,confirmButton);
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();

        //Make sure to return answer
        return answer;
    }

}