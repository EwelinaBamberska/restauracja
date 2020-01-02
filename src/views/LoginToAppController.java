package views;

import app.jdbc.WorkerJdbcClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

public class LoginToAppController {
    @FXML
    private Button sign_in_button;
    @FXML
    private TextArea sign_in_text_area;

    public void sign_to_app(ActionEvent actionEvent){
        String enteredID = sign_in_text_area.getText();
        if (WorkerJdbcClass.getInstance().findIfWorkerExist(Integer.parseInt(enteredID))){
            System.out.println("Login into");
        }
    }
}