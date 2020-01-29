package views;

import app.data.worker.Worker;
import app.jdbc.WorkerJdbcClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

public class AddWorkerViewController implements Initializable {
    @FXML
    private Button backToWorkerViewButton;
    @FXML
    private Button addWorkerButton;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField surnameTextField;
    @FXML
    private DatePicker workDatePicker;
    @FXML
    private MenuButton positionButton;
    @FXML
    private MenuItem managerMenuItem;
    @FXML
    private MenuItem waiterMenuItem;
    @FXML
    private MenuItem cookMenuItem;

    private enum Position{Manager, Waiter, Cook}
    private Position actualPosition = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void goToWorkerView(ActionEvent actionEvent) {
        JavaFXUtils.changeScene(actionEvent, "allWorkersView.fxml", 800, 600, getClass());
    }

    public void addWorker(ActionEvent actionEvent) {
        String name = nameTextField.getText();
        String surname = surnameTextField.getText();
        Date date = (Date) JavaFXUtils.parseToDate(workDatePicker.getValue());
        String waiter = "F";
        String manager = "F";
        String cook = "F";
        if (actualPosition.equals(Position.Waiter)){
            waiter = "T";
        }
        else if(actualPosition.equals(Position.Manager)){
            manager = "T";
        }
        else if(actualPosition.equals(Position.Cook)){
            cook = "T";
        }
        if(!name.equals("") && !surname.equals("") && workDatePicker != null) {
            int id = WorkerJdbcClass.getInstance().addWorker(name, surname, date, waiter, manager, cook);
            goToWorkerView(actionEvent);
        }
        else {
            //BŁĄD Dane nieuzupełnione
        }
    }

    public void changePositionToManager(ActionEvent actionEvent) {
        actualPosition = Position.Manager;
        positionButton.setText(String.valueOf(Position.Manager));
    }

    public void changePositionToWaiter(ActionEvent actionEvent) {
        actualPosition = Position.Waiter;
        positionButton.setText(String.valueOf(Position.Waiter));
    }

    public void changePositionToCook(ActionEvent actionEvent) {
        actualPosition = Position.Cook;
        positionButton.setText(String.valueOf(Position.Cook));
    }
}
