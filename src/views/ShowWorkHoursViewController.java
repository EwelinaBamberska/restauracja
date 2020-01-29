package views;

import app.data.hours.HoursItemProperty;
import app.data.hours.HoursList;
import app.data.worker.LoggedWorker;
import app.jdbc.HoursJdbcClass;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class ShowWorkHoursViewController implements Initializable {

    @FXML
    private Button showHoursButton;
    @FXML
    private Button goBackButton;
    @FXML
    private TextField HoursIDTextField;
    @FXML
    private TableView hours_worked_table;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeTableColumns();
    }

    public void go_to_menu(ActionEvent actionEvent) {
        if(LoggedWorker.getInstance().isIf_manager()){
        JavaFXUtils.changeScene(actionEvent, "mainViewManager.fxml", 800, 600, getClass());}
        else{
            JavaFXUtils.changeScene(actionEvent, "mainViewWaiter.fxml",800,600,getClass());
        }

    }

    private void initializeTableColumns() {
        hours_worked_table.setEditable(true);
//        menu_items_table.setPrefHeight(centre_menu_view_vbox.getHeight());
//        menu_items_table.setMinHeight(centre_menu_view_vbox.getHeight());
//        menu_items_table.setMaxHeight(centre_menu_view_vbox.getHeight());

        TableColumn<HoursItemProperty, String> positionIDColumn = new TableColumn<>("Employee ID");
        TableColumn<HoursItemProperty, String> positionDateColumn = new TableColumn<>("Date");
        TableColumn<HoursItemProperty, String> positionWageColumn = new TableColumn<>("Wage");
        TableColumn<HoursItemProperty, String> positionHoursColumn = new TableColumn<>("Hours worked");
        hours_worked_table.getColumns().addAll(positionIDColumn, positionDateColumn, positionWageColumn, positionHoursColumn);

        positionIDColumn.setCellValueFactory(new PropertyValueFactory<HoursItemProperty, String>("ID"));
        positionDateColumn.setCellValueFactory(new PropertyValueFactory<HoursItemProperty, String>("date"));
        positionWageColumn.setCellValueFactory(new PropertyValueFactory<HoursItemProperty, String>("wage"));
        positionHoursColumn.setCellValueFactory(new PropertyValueFactory<HoursItemProperty, String>("hours"));
    }

    private void showItemsInTable(String ID) {
        int checking = 0;

        if(ID ==null||ID.isEmpty()){
        views.ErrorBox.showError("Error", "Input can't be empty");
        checking++;
        }
        HoursIDTextField.clear();

        if(app.data.DataValidation.checkSpecialChars(ID)==""){
        views.ErrorBox.showError("Error", "ID consist only of numbers");
        checking++;

        }
        if(app.data.DataValidation.checkOnlyNumbers(ID)==""){
            views.ErrorBox.showError("Error", "ID consist only of numbers");
            checking++;
        }

        if(checking ==0){

                HoursList.getInstance().getHoursPositionList().clear();
                HoursJdbcClass.getInstance().getHoursFromDatabase(ID);
                hours_worked_table.getItems().clear();
                ObservableList<HoursItemProperty> hoursItems = FXCollections.observableArrayList();
                HoursList.getInstance().getHoursPositionList().forEach(position -> hoursItems.add(new HoursItemProperty(position.getID(), position.getDate(), position.getWage(), position.getHours())));
                hours_worked_table.setItems(hoursItems);

            }

    }
    public void get_hours(ActionEvent actionEvent){
        String data;
        data = HoursIDTextField.getCharacters().toString();
        if(LoggedWorker.getInstance().isIf_manager()) {
            showItemsInTable(data);
        }else if(Integer.parseInt(data)== LoggedWorker.getInstance().getId_prac()) {
            showItemsInTable(data);
        }else
                views.ErrorBox.showError("Error", "You can only check your own hours");
        }

}