package views;

import app.data.worker.Worker;
import app.data.worker.WorkerList;
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

    private enum Position{Menedżer, Kelner, Kucharz}
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
        if (actualPosition.equals(Position.Kelner)){
            waiter = "T";
        }
        else if(actualPosition.equals(Position.Menedżer)){
            manager = "T";
        }
        else if(actualPosition.equals(Position.Kucharz)){
            cook = "T";
        }
        if(!name.equals("") && !surname.equals("") && workDatePicker != null && actualPosition != null) {
            int id = WorkerJdbcClass.getInstance().addWorker(name, surname, date, waiter, manager, cook);
            Worker newWorker = new Worker(id, name, surname, date, waiter, cook, manager);
//            WorkerList.getInstance().addWorker(newWorker);
            goToWorkerView(actionEvent);
        }
        else {
            //BŁĄD Dane nieuzupełnione
        }
    }

    public void changePositionToManager(ActionEvent actionEvent) {
        actualPosition = Position.Menedżer;
        positionButton.setText(String.valueOf(Position.Menedżer));
    }

    public void changePositionToWaiter(ActionEvent actionEvent) {
        actualPosition = Position.Kelner;
        positionButton.setText(String.valueOf(Position.Kelner));
    }

    public void changePositionToCook(ActionEvent actionEvent) {
        actualPosition = Position.Kucharz;
        positionButton.setText(String.valueOf(Position.Kucharz));
    }
}
