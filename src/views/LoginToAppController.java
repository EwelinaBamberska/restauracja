package views;

import app.data.worker.LoggedWorker;

import app.jdbc.WorkerJdbcClass;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.DragEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginToAppController implements Initializable {
    @FXML
    private Button sign_in_button;
    @FXML
    private TextArea sign_in_text_area;

    public void sign_to_app(ActionEvent actionEvent){
        String enteredID = sign_in_text_area.getText();
        if (WorkerJdbcClass.getInstance().logWorker(Integer.parseInt(enteredID))){
            if(LoggedWorker.getInstance().isIf_manager())
                JavaFXUtils.changeScene(actionEvent, "mainViewManager.fxml", 800.0, 600.0, getClass());
            else if (LoggedWorker.getInstance().isIf_waiter())
                JavaFXUtils.changeScene(actionEvent, "mainViewWaiter.fxml", 800.0, 600.0, getClass());
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sign_in_text_area.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.ENTER)  {
                    String enteredID = sign_in_text_area.getText();
                    if (WorkerJdbcClass.getInstance().logWorker(Integer.parseInt(enteredID))){
                        if(LoggedWorker.getInstance().isIf_manager()) {
                            try {
                                Stage stage = (Stage) ((Node) keyEvent.getSource()).getScene().getWindow();
                                Parent parent = FXMLLoader.load(getClass().getResource("mainViewManager.fxml"));
                                stage.setScene(new Scene(parent, 800, 600));
                            } catch (IOException e){
                                e.printStackTrace();
                            }
                        }
                        else if (LoggedWorker.getInstance().isIf_waiter()){
                            try {
                                Stage stage = (Stage) ((Node) keyEvent.getSource()).getScene().getWindow();
                                Parent parent = FXMLLoader.load(getClass().getResource("mainViewWaiter.fxml"));
                                stage.setScene(new Scene(parent, 800, 600));
                            } catch (IOException e){
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        });
    }
}
