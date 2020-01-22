package views;

import app.data.bill.Bill;
import app.data.bill.BillItemProperty;
import app.data.bill.BillList;
import app.data.worker.LoggedWorker;
import app.data.worker.WorkerItemProperty;
import app.data.worker.WorkerList;
import app.jdbc.BillJdbcClass;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MainViewManagerController implements Initializable {
    @FXML
    private Button create_bill_button;
    @FXML
    private Button take_item_from_magazine_button;
    @FXML
    private Button work_hours_button;
    @FXML
    private Button log_out_button;
    @FXML
    private Button menu_button;
    @FXML
    private Button orders_button;
    @FXML
    private Button magazine_button;
    @FXML
    private Button workers_button;
    @FXML
    private TableView open_bills_list;
    @FXML
    private CheckBox toPaidCheckBox;
    @FXML
    private CheckBox paidCheckBox;
    @FXML
    private CheckBox onlyManagerCheckBox;
    @FXML
    private TableView billsTableView;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
//            onlyManagerCheckBox.setVisible(false);
//            onlyManagerCheckBox.setSelected(true);
        BillTableViewController.initializeBillTable(billsTableView, getClass());
        BillTableViewController.showBills(toPaidCheckBox.isSelected(), paidCheckBox.isSelected(),
                onlyManagerCheckBox.isSelected(), billsTableView);
    }

    public void go_to_create_bill_view(ActionEvent actionEvent) {
    }

    public void go_to_take_item_from_magazine(ActionEvent actionEvent) {
        JavaFXUtils.changeScene(actionEvent, "takeItemFromMagazineView.fxml", 800, 600, getClass());
    }

    public void show_work_hours(ActionEvent actionEvent) {
    }

    public void log_out(ActionEvent actionEvent) {
        JavaFXUtils.changeScene(actionEvent, "loginToApp.fxml", 800, 600, getClass());
    }

    public void go_to_menu_options(ActionEvent actionEvent) {
        JavaFXUtils.changeScene(actionEvent, "menuView.fxml", 800, 600, getClass());
    }

    public void go_to_orders_options(ActionEvent actionEvent) {
        JavaFXUtils.changeScene(actionEvent, "ordersView.fxml", 800, 600, getClass());
    }

    public void go_to_workers_options(ActionEvent actionEvent) {
        JavaFXUtils.changeScene(actionEvent, "allWorkersView.fxml", 800, 600, getClass());
    }

    public void go_to_magazine_options(ActionEvent actionEvent) {
        JavaFXUtils.changeScene(actionEvent, "magazineView.fxml", 800, 600, getClass());
    }

    public void showSelectedBills(ActionEvent actionEvent) {
        BillTableViewController.showBills(toPaidCheckBox.isSelected(), paidCheckBox.isSelected(),
                onlyManagerCheckBox.isSelected(), billsTableView);
    }
}
