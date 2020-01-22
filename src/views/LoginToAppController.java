package views;

import app.data.worker.LoggedWorker;
import app.data.worker.Worker;
import app.jdbc.WorkerJdbcClass;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;


public class LoginToAppController {
    @FXML
    private Button sign_in_button;
    @FXML
    private TextArea sign_in_text_area;
    public void sign_to_app(ActionEvent actionEvent){
        String enteredID = sign_in_text_area.getText();

        try {
            if (WorkerJdbcClass.getInstance().logWorker(Integer.parseInt(enteredID))) {
                if (LoggedWorker.getInstance().isIf_manager())
                    JavaFXUtils.changeScene(actionEvent, "mainViewManager.fxml", 800.0, 600.0, getClass());
                else if (LoggedWorker.getInstance().isIf_waiter())
                    JavaFXUtils.changeScene(actionEvent, "mainViewWaiter.fxml", 800.0, 600.0, getClass());
            } else
                views.ErrorBox.showError("Error", "Nonexistent ID ");
        }catch (NumberFormatException e){
            views.ErrorBox.showError("Error", "Input can only be a number");
        }
    }
}
