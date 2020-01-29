package views;

import app.data.worker.Worker;
import app.jdbc.WorkerJdbcClass;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

public class WorkerInfoController implements Initializable {
    @FXML
    private Button backToWorkerViewButton;
    @FXML
    private Button deleteWorkerButton;
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
    @FXML
    private DatePicker workDayDatePicker;
    @FXML
    private TextField amountOfHoursTextField;
    @FXML
    private Button saveHoursButton;
    @FXML
    private VBox centerVBox;
    @FXML
    private TextField hourRateTextField;

    private enum Position{Manager, Waiter, Cook}
    private Position actualPosition;
    private Worker workerToShow;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void initData(Worker worker){
        this.workerToShow = worker;
        nameTextField.setText(workerToShow.getName());
        surnameTextField.setText(workerToShow.getSurname());
        workDatePicker.setValue(JavaFXUtils.parseToLocalDate(workerToShow.getDate()));
        if (workerToShow.isIf_waiter()){
            actualPosition = Position.Waiter;
            positionButton.setText(String.valueOf(Position.Waiter));
        }
        if (workerToShow.isIf_cooker()) {
            actualPosition = Position.Cook;
            positionButton.setText(String.valueOf(Position.Cook));
        }
        if (workerToShow.isIf_manager()) {
            actualPosition = Position.Manager;
            positionButton.setText(String.valueOf(Position.Manager));
        }
        addChangeButton();
    }

    public void goToWorkerView(ActionEvent actionEvent) {
        JavaFXUtils.changeScene(actionEvent, "allWorkersView.fxml", 800, 600, getClass());
    }

    public void deleteWorker(ActionEvent actionEvent) {
        WorkerJdbcClass.getInstance().fireWorker(workerToShow);
        goToWorkerView(actionEvent);
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

    private void addChangeButton() {
        Button modifyDataButton = JavaFXUtils.createButton("Save changes");
        modifyDataButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                workerToShow.setName(nameTextField.getCharacters().toString());
                workerToShow.setSurname(surnameTextField.getCharacters().toString());
                workerToShow.setDate((Date) JavaFXUtils.parseToDate(workDatePicker.getValue()));
                if(actualPosition.equals(Position.Waiter))
                    workerToShow.setWaiter();
                else if(actualPosition.equals(Position.Cook))
                    workerToShow.setCook();
                else
                    workerToShow.setManager();
                WorkerJdbcClass.getInstance().modifyWorker(workerToShow);
            }
        });
        centerVBox.getChildren().add(4, modifyDataButton);
    }

    public void saveHours(ActionEvent actionEvent) {
        System.out.println(Float.parseFloat(hourRateTextField.getText().replace(",", ".")));
        WorkerJdbcClass.getInstance().saveHoursToDB(workerToShow.getId_prac(),
                (Date) JavaFXUtils.parseToDate(workDayDatePicker.getValue()),
                Float.parseFloat(amountOfHoursTextField.getText().replace(",", ".")),
                Float.parseFloat(hourRateTextField.getText().replace(",", ".")));
    }

    public void setChangedData(InputMethodEvent inputMethodEvent) {
    }
}
