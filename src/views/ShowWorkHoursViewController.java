package views;

import app.data.hours.HoursItemProperty;
import app.data.hours.HoursList;
import app.data.hours.HoursPosition;
import app.data.menu.MenuItemProperty;
import app.data.menu.MenuList;
import app.jdbc.HoursJdbcClass;
import app.jdbc.MenuJdbcClass;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.awt.*;
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
        JavaFXUtils.changeScene(actionEvent, "mainViewManager.fxml", 800, 600, getClass());
    }

    private void initializeTableColumns() {
        hours_worked_table.setEditable(true);
//        menu_items_table.setPrefHeight(centre_menu_view_vbox.getHeight());
//        menu_items_table.setMinHeight(centre_menu_view_vbox.getHeight());
//        menu_items_table.setMaxHeight(centre_menu_view_vbox.getHeight());

        TableColumn<MenuItemProperty, String> positionIDColumn = new TableColumn<>("Employee ID");
        TableColumn<MenuItemProperty, String> positionDateColumn = new TableColumn<>("Date");
        TableColumn<MenuItemProperty, String> positionWageColumn = new TableColumn<>("Wage");
        TableColumn<MenuItemProperty, String> positionHoursColumn = new TableColumn<>("Hours worked");
        hours_worked_table.getColumns().addAll(positionIDColumn, positionDateColumn, positionWageColumn, positionHoursColumn);

        positionIDColumn.setCellValueFactory(new PropertyValueFactory<MenuItemProperty, String>("id"));
        positionDateColumn.setCellValueFactory(new PropertyValueFactory<MenuItemProperty, String>("date"));
        positionWageColumn.setCellValueFactory(new PropertyValueFactory<MenuItemProperty, String>("wage"));
        positionHoursColumn.setCellValueFactory(new PropertyValueFactory<MenuItemProperty, String>("hours"));
    }

    private void showItemsInTable(String ID){
            if(!HoursList.getInstance().isDownloadedData())
                HoursJdbcClass.getInstance().getHoursFromDatabase(ID);
            hours_worked_table.getItems().clear();
            ObservableList<HoursItemProperty> hoursItems = FXCollections.observableArrayList();
            HoursList.getInstance().getMenuPositionList().forEach(position -> hoursItems.add(new HoursItemProperty(position.getID(), position.getDate(),position.getWage(),position.getHours())));
            hours_worked_table.setItems(hoursItems);
        }
    public void get_hours(ActionEvent actionEvent){
        String data;
        data = HoursIDTextField.getCharacters().toString();
        showItemsInTable(data);
    }
}